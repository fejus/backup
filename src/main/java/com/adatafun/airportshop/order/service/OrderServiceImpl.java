package com.adatafun.airportshop.order.service;

import com.adatafun.airportshop.order.common.config.PaymentConfig;
import com.adatafun.airportshop.order.common.enums.*;
import com.adatafun.airportshop.order.common.util.HttpClientUtils;
import com.adatafun.airportshop.order.common.util.OrderNoUtil;
import com.adatafun.airportshop.order.mapper.*;
import com.adatafun.airportshop.order.pojo.dto.*;
import com.adatafun.airportshop.order.pojo.vo.*;
import com.adatafun.airportshop.order.pojo.po.*;
import com.adatafun.airportshop.order.service.interfaces.OrderService;
import com.adatafun.airportshop.order.service.rpc.*;
import com.adatafun.utils.api.ResUtils;
import com.adatafun.utils.api.Result;
import com.adatafun.utils.common.DateUtils;
 import com.adatafun.utils.common.UUIDUtil;
import com.adatafun.utils.mybatis.common.ResponsePage;
import com.adatafun.utils.translate.LanguageEnum;
import com.adatafun.utils.translate.LanguageUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.payment.utils.sign.TransUtil;
import com.zhiweicloud.guest.common.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * desc : 订单服务
 * Created by Lin on 2017/11/6.
 */
@Service()
@Transactional(propagation = Propagation.REQUIRED)
public class OrderServiceImpl  implements OrderService{
    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    private OrdOrderMapper ordOrderMapper;
    @Autowired
    private OrdOrderLanguageMapper ordOrderLanguageMapper;
    @Autowired
    private OrdSubOrderMapper ordSubOrderMapper;
    @Autowired
    private OrdSubOrderLanguageMapper ordSubOrderLanguageMapper;
    @Autowired
    private OrdBillMapper ordBillMapper;
    @Autowired
    private MemberUserService memberUserService;
    @Autowired
    private ShopProductService shopProductService;
    @Autowired
    private ShopInfoService shopInfoService;
    @Autowired
    private HardwareCenterService hardwareCenterService;

    /**
     * 下单
     *
     * @param ordOrder         主订单
     * @param oriOrderLanguage 主订单语言
     * @param subOrders        子订单
     * @param ordBill          发票信息
     * @return
     */
    public String saveOrder(OrdOrder ordOrder, OrdOrderLanguage oriOrderLanguage, List<OrdSubOrder> subOrders, OrdBill ordBill) {
        //查询商户信息
        List<EnterpriseInfoDTO> enterpriseInfoLanguages = shopInfoService.getEnterpriseInfo(ordOrder.getEnterpriseId());

        //查询门店信息
        List<StoreInfoDTO> storeInfoLanguages = shopInfoService.getStoreInfo(ordOrder.getStoreId());

        // 查询帐号信息
        UserAccountDTO user = ShopAccountService.getUserInfo(ordOrder.getCashierId());
        if (user != null) {
            ordOrder.setCashierName(user.getUserName());
        }

        //查询菜品及其规格信息
        List<String> specicationIds = new ArrayList<>();
        for (OrdSubOrder ordSubOrder : subOrders) {
            specicationIds.add(ordSubOrder.getSpecificationId());
        }
        List<ProductsForOrderDTO> specifications = shopProductService.getProductInfo(StringUtils.collectionToDelimitedString(specicationIds, ","));
        //to 做产品信息的校验


        String orderId = UUIDUtil.getUUID();
        //保存子订单
        saveSubOrder(orderId, subOrders, specifications, ordOrder.getLanguage());
        //保存主订单
        ordOrder.setOrderId(orderId);
        saveMainOrder(ordOrder, subOrders, oriOrderLanguage, enterpriseInfoLanguages, storeInfoLanguages);
        //TODO 进行菜品库存的更改


        //保存发票信息
        //saveBillInfo(ordOrder, ordBill);

        JSONObject result = new JSONObject();
        result.put("orderId",orderId);
        result.put("actualAmount", ordOrder.getActualAmount());

        return JSONObject.toJSONString(ResUtils.result(result));

    }

    /**
     * 现金支付时改变订单的状态
     *
     * @param user_id
     * @param orderId
     * @return
     */
    @Override
    public String changeOrderStatus(String user_id, String orderId, String bodyNumber, String language) {
        OrderDetailVO orderDetailVO = ordOrderMapper.selectOrderDetailByOrderId(orderId, language);
        if (orderDetailVO == null) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "订单编号有误"));
        }
        OrdOrder ordOrder = new OrdOrder();
        ordOrder.setOrderId(orderId);
        ordOrder.setOrderStatus(OrderStatus.HAVE_PAY.value());
        ordOrder.setPayStatus(PayStatus.HAVE_PAY.value());
        // 更新订单状态
        ordOrderMapper.updateByPrimaryKeySelective(ordOrder);

        // 更新成功 进行推送
        String result = push(orderId, bodyNumber, language);
        log.info("推送结果" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if ("10200".equals(jsonObject.getString("status"))) {
            jsonObject.put("data", "支付成功");
        }
        return jsonObject.toJSONString();
    }

    /**
     * 推送
     *
     * @param orderId  订单id
     * @param bodyNumber  门店id
     * @param language 语言
     * @return
     */
    @Override
    public String push(String orderId, String bodyNumber, String language) {

        OrderDetailVO orderDetailVO = ordOrderMapper.selectOrderDetailByOrderId(orderId, language);
        if (orderDetailVO == null) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "订单编号有误"));
        }

        //JSONObject param = getSmallTicketPrintParam(storeInfoLanguages, orderDetailVO, language);
        JSONObject param = new JSONObject();
        param.put("orderId", orderId);
        param.put("bodyNumber", bodyNumber);
        param.put("language", language);

        JSONObject jsonObject = hardwareCenterService.push(param);

        return jsonObject.toJSONString();
    }

    /**
     * 打印小票
     *
     * @param orderId
     * @param storeId
     * @param language
     * @return
     */
    @Override
    public String smallTicketPrint(String orderId, String storeId, String language) {
        //查询门店信息
        List<StoreInfoDTO> storeInfoLanguages = shopInfoService.getStoreInfo(storeId);

        if (storeInfoLanguages == null || storeInfoLanguages.size() == 0) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "门店id有误"));
        }

        OrderDetailVO orderDetailVO = ordOrderMapper.selectOrderDetailByOrderId(orderId, language);
        if (orderDetailVO == null) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "订单编号有误"));
        }

        // 获取打印小票信息
        JSONObject param = getSmallTicketPrintParam(storeInfoLanguages, orderDetailVO, language);

        JSONObject jsonObject = hardwareCenterService.smallTicketPrint(param);

        return jsonObject.toJSONString();
    }

    /**
     * 商户后台打印小票 只推送pos机进行打印
     *
     * @param orderId
     * @param storeId
     * @param language
     * @return
     */
    @Override
    public String printSmallTicket(String orderId, String storeId, String language) {
        //查询门店信息
        List<StoreInfoDTO> storeInfoLanguages = shopInfoService.getStoreInfo(storeId);

        if (storeInfoLanguages == null || storeInfoLanguages.size() == 0) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "门店id有误"));
        }

        OrderDetailVO orderDetailVO = ordOrderMapper.selectOrderDetailByOrderId(orderId, language);
        if (orderDetailVO == null) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "订单编号有误"));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId", orderId);
        JSONObject result = hardwareCenterService.printSmallTicket(jsonObject);
        return result.toJSONString();
    }


    private JSONObject getSmallTicketPrintParam(List<StoreInfoDTO> storeInfoLanguages, OrderDetailVO orderDetailVO, String language) {
        JSONObject param = new JSONObject();
        List<Map<String, Object>> productParam = new ArrayList<>();

        for (StoreInfoDTO storeInfo : storeInfoLanguages) {
            if (storeInfo.getLanguage().equals(language)) {
                param.put("storeId", storeInfo.getStoreId());
                param.put("storeName", storeInfo.getStoreName());
                break;
            }
        }
        param.put("orderId", orderDetailVO.getOrderId());
        param.put("payStatus", orderDetailVO.getPayStatus());
        param.put("destNumber", orderDetailVO.getDeskNumber());
        param.put("orderTime", orderDetailVO.getCreateTime());
        param.put("eatingCode", orderDetailVO.getGetFoodNumber());
        param.put("remarks", orderDetailVO.getRemarks());

        for (SubOrderDetailVO subOrder : orderDetailVO.getSubOrderProList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("productName", subOrder.getProductName());
            map.put("amount", subOrder.getProductNumber());
            map.put("price", subOrder.getUnitPrice());
            map.put("totalPrice", subOrder.getTotalPrice());
            productParam.add(map);
        }

        param.put("product", productParam);

        return param;
    }

    /**
     * 保存主订单信息
     *
     * @param oriOrderLanguage 语言
     * @param subOrders        子订单
     * @param ordOrder         订单
     * @param enterpriseInfoLanguages     商户信息
     * @param storeInfoLanguages        门店信息
     * @return
     */
    public void saveMainOrder(OrdOrder ordOrder, List<OrdSubOrder> subOrders, OrdOrderLanguage oriOrderLanguage, List<EnterpriseInfoDTO> enterpriseInfoLanguages, List<StoreInfoDTO> storeInfoLanguages) {
        //生成订单编码
        String orderNo = OrderNoUtil.createOrderNo(OrderType.TS_ORDER, ChannelType.getEnumByValue(ordOrder.getOrderChannel()));
        ordOrder.setOrderNo(orderNo);

        //计算总价格
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrdSubOrder subOrder : subOrders) {
            totalAmount = totalAmount.add(new BigDecimal(subOrder.getTotalPrice().intValue()));
        }
        ordOrder.setOrderAmount(totalAmount.intValue());
        ordOrder.setActualAmount(totalAmount.intValue());
        ordOrder.setSubOrderNumber(subOrders.size());
        //ordOrder.setCreateTime(new Date());

        ordOrder.setOrderStatus(OrderStatus.NEW.value());
        ordOrder.setPayStatus(PayStatus.NO_PAY.value());
        ordOrder.setCreateTime(new Date());
        ordOrder.setIsDeleted(BooleanEnum.NO.value());
        String language = oriOrderLanguage.getLanguage();

        // 预计出餐时间 门店位置指引图
        for (StoreInfoDTO storeInfoDTO : storeInfoLanguages) {
            if (language != null && language.equals(storeInfoDTO.getLanguage())) {
                ordOrder.setGenFoodTime(storeInfoDTO.getGenFoodTime());
                ordOrder.setLocationPointImg(storeInfoDTO.getLocationPointImg());
            }
        }


        ordOrderMapper.insert(ordOrder);

        //处理多语言
        saveMainOrderLanguage(ordOrder, oriOrderLanguage, enterpriseInfoLanguages, storeInfoLanguages);


    }


    /**
     * 保存主订单多语言
     *
     * @param ordOrder
     * @param oriOrderLanguage
     * @param enterpriseInfoLanguages     商户信息
     * @param storeInfoLanguages        门店信息
     */
    public void saveMainOrderLanguage(OrdOrder ordOrder, OrdOrderLanguage oriOrderLanguage, List<EnterpriseInfoDTO> enterpriseInfoLanguages, List<StoreInfoDTO> storeInfoLanguages) {
        //多语言处理
        LanguageEnum oriLanguageEnum = LanguageEnum.getEnumByValue(ordOrder.getLanguage());
        List<OrdOrderLanguage> ordOrderLanguages = new ArrayList<>();

        List<String> languages = new ArrayList<>();//商家支持的多语言列表
        languages.add(LanguageEnum.ZH_CN.value());
        languages.add(LanguageEnum.EN.value());
        for (String language : languages) {
            LanguageEnum languageEnum = LanguageEnum.getEnumByValue(language);
            if (!ordOrder.getLanguage().equals(language)) {
                OrdOrderLanguage orderLanguage = new OrdOrderLanguage();
                orderLanguage.setLanguage(language);
                for (EnterpriseInfoDTO enterpriseInfoDTO : enterpriseInfoLanguages) {
                    if (language.equals(enterpriseInfoDTO.getLanguage())) {
                        orderLanguage.setEnterpriseName(enterpriseInfoDTO.getCompanyName());//商户名称
                    }
                }
                for (StoreInfoDTO storeInfoDTO : storeInfoLanguages) {
                    if (language.equals(storeInfoDTO.getLanguage())) {
                        orderLanguage.setStoreName(storeInfoDTO.getStoreName());//门店名称
                    }
                }
                orderLanguage.setOrderId(ordOrder.getOrderId());
                if (!StringUtils.isEmpty(oriOrderLanguage.getRemarks())) {
                    orderLanguage.setRemarks(LanguageUtil.getTransResult(oriOrderLanguage.getRemarks(), oriLanguageEnum, languageEnum));
                }
                if (!StringUtils.isEmpty(oriOrderLanguage.getStoreRemarks())) {
                    orderLanguage.setRemarks(LanguageUtil.getTransResult(oriOrderLanguage.getStoreRemarks(), oriLanguageEnum, languageEnum));
                }
                orderLanguage.setCreateTime(oriOrderLanguage.getCreateTime());
                orderLanguage.setCreateUser(orderLanguage.getCreateUser());
                orderLanguage.setIsDeleted(BooleanEnum.NO.value());
                orderLanguage.setCreateTime(ordOrder.getCreateTime());

                ordOrderLanguages.add(orderLanguage);
            } else {
                oriOrderLanguage.setLanguage(language);
                for (EnterpriseInfoDTO enterpriseInfoDTO : enterpriseInfoLanguages) {
                    if (language.equals(enterpriseInfoDTO.getLanguage())) {
                        oriOrderLanguage.setEnterpriseName(enterpriseInfoDTO.getCompanyName());//商户名称
                    }
                }
                for (StoreInfoDTO storeInfoDTO : storeInfoLanguages) {
                    if (language.equals(storeInfoDTO.getLanguage())) {
                        oriOrderLanguage.setStoreName(storeInfoDTO.getStoreName());//门店名称
                    }
                }
                oriOrderLanguage.setOrderId(ordOrder.getOrderId());
                if (!StringUtils.isEmpty(oriOrderLanguage.getRemarks())) {
                    oriOrderLanguage.setRemarks(LanguageUtil.getTransResult(oriOrderLanguage.getRemarks(), oriLanguageEnum, languageEnum));
                }
                if (!StringUtils.isEmpty(oriOrderLanguage.getStoreRemarks())) {
                    oriOrderLanguage.setRemarks(LanguageUtil.getTransResult(oriOrderLanguage.getStoreRemarks(), oriLanguageEnum, languageEnum));
                }
                oriOrderLanguage.setCreateTime(oriOrderLanguage.getCreateTime());
                oriOrderLanguage.setCreateUser(ordOrder.getCreateUser());
                oriOrderLanguage.setIsDeleted(BooleanEnum.NO.value());
                oriOrderLanguage.setCreateTime(ordOrder.getCreateTime());

                ordOrderLanguages.add(oriOrderLanguage);
            }
        }

        //循环插入
        for (OrdOrderLanguage ordOrderLanguage : ordOrderLanguages) {
            ordOrderLanguageMapper.insert(ordOrderLanguage);
        }

    }


    /**
     * 保存子订单
     *
     * @param orderId 订单编码
     * @param subOrders      子订单列表
     * @param specifications 菜品规格列表
     * @param language 语言
     */
    public void saveSubOrder(String orderId, List<OrdSubOrder> subOrders, List<ProductsForOrderDTO> specifications, String language) {
        for (int i = 0; i < subOrders.size(); i++) {
            OrdSubOrder subOrder = subOrders.get(i);
            subOrder.setSubOrderId(UUIDUtil.getUUID());
            ProductsForOrderDTO specification = specifications.get(i);
            subOrder.setOrderId(orderId);
            subOrder.setProductImg(specification.getProductForOrderDTOList().get(0).getProductCoverImg());//图片
            subOrder.setUnitPrice(specification.getProductForOrderDTOList().get(0).getSpecificationPrice());//单价
            subOrder.setTotalPrice(subOrder.getProductNumber() * subOrder.getUnitPrice());//单品总价
            subOrder.setCreateTime(new Date());
            subOrder.setIsDeleted(BooleanEnum.NO.value());
            ordSubOrderMapper.insert(subOrder);
            //处理多语言
            saveSubOrderLanguage(language, subOrder, specification);

        }
    }

    /**
     * 处理子订单多语言
     *
     * @param subOrder
     * @param specification
     */
    public void saveSubOrderLanguage(String oriLanguage, OrdSubOrder subOrder, ProductsForOrderDTO specification) {
        //多语言处理
        LanguageEnum oriLanguageEnum = LanguageEnum.getEnumByValue(oriLanguage);
        List<OrdSubOrderLanguage> orderLanguages = new ArrayList<>();

        List<String> languages = new ArrayList<>();//商家支持的多语言列表
        languages.add(LanguageEnum.ZH_CN.value());
        languages.add(LanguageEnum.EN.value());
        for (String language : languages) {
            LanguageEnum languageEnum = LanguageEnum.getEnumByValue(language);
            OrdSubOrderLanguage orderLanguage = new OrdSubOrderLanguage();
            orderLanguage.setLanguage(language);
            for (ProductForOrder productForOrder : specification.getProductForOrderDTOList()) {
                if (language.equals(productForOrder.getLanguage())) {
                    orderLanguage.setProductName(productForOrder.getProductName());
                    orderLanguage.setSpecificationName(productForOrder.getSpecificationName());
                }
            }
            orderLanguage.setSubOrderId(subOrder.getSubOrderId());
            orderLanguage.setCreateTime(subOrder.getCreateTime());
            orderLanguages.add(orderLanguage);
        }

        //循环插入
        for (OrdSubOrderLanguage ordOrderLanguage : orderLanguages) {
            ordOrderLanguage.setIsDeleted(BooleanEnum.NO.value());
            ordSubOrderLanguageMapper.insert(ordOrderLanguage);
        }
    }


    /**
     * 添加商家备注
     *
     * @param orderId        订单编码
     * @param remarks        备注
     * @param language       当前语言
     * @param operatorUserId 操作人编码
     */
    public void addStoreRemarks(String orderId, String remarks, String language, String operatorUserId) {
        Date now = new Date();
        //查询翻译列表
        List<OrdOrderLanguage> ordOrderLanguages = ordOrderLanguageMapper.selectLanguageListByOrderId(orderId);
        String remarksLanguage = remarks;
        LanguageEnum oriLanguage = LanguageEnum.getEnumByValue(language);
        for (OrdOrderLanguage ordOrderLanguage : ordOrderLanguages) {
            //翻译
            if (!ordOrderLanguage.getLanguage().equals(language)) {
                remarksLanguage = LanguageUtil.getTransResult(remarks, oriLanguage, LanguageEnum.getEnumByValue(ordOrderLanguage.getLanguage()));
            }

            ordOrderLanguage.setStoreRemarks(remarksLanguage);
            ordOrderLanguage.setUpdateTime(now);
            ordOrderLanguage.setUpdateUser(operatorUserId);
            ordOrderLanguageMapper.updateByPrimaryKeySelective(ordOrderLanguage);
        }


    }

    /**
     * 保存发票信息
     *
     * @param ordOrder 订单信息
     * @param ordBill  发票信息
     */
    public void saveBillInfo(OrdOrder ordOrder, OrdBill ordBill) {
        if (ordBill != null) {
            ordBill.setOrderId(ordOrder.getOrderId());
            ordBill.setCreateTime(ordOrder.getCreateTime());
            ordBillMapper.insert(ordBill);
        }
    }

    /**
     * 查看订单详情
     *
     * @param orderId
     * @param language
     * @return
     */
    public OrderDetailVO orderDetail(String orderId, String language) {

        OrderDetailVO orderDetailVO = ordOrderMapper.selectOrderDetailByOrderId(orderId, language);



        return orderDetailVO;
    }

    /**
     * 订单分页
     *
     * @param queryParam
     * @return
     */
    public ResponsePage<List<OrderItemVO>> orderListByPageForMemberUser(OrderListQueryParamDTO queryParam) {
        ResponsePage<List<OrderItemVO>> responsePage = new ResponsePage<>();

        List<OrderItemVO> list = new ArrayList<>();
        int totalRows = ordOrderMapper.countOrder(queryParam);
        if (totalRows > 0) {
            list = ordOrderMapper.selectOrderListByPage(queryParam);
        }

        //设置分页结果
        responsePage.setPage(queryParam.getPage());
        responsePage.setRows(queryParam.getRows());
        responsePage.setTotalRows(totalRows);
        responsePage.setList(list);

        return responsePage;
    }


    /**
     * 订单分页
     *
     * @param queryParam
     * @return
     */
    public ResponsePage<List<OrderItemVO>> orderListByPage(OrderListQueryParamDTO queryParam) {
        ResponsePage<List<OrderItemVO>> responsePage = new ResponsePage<>();

        List<OrderItemVO> list = new ArrayList<>();
        int totalRows = ordOrderMapper.countOrder(queryParam);
        if (totalRows > 0) {
            list = ordOrderMapper.selectOrderListByPage(queryParam);
        }

        //设置分页结果
        responsePage.setPage(queryParam.getPage());
        responsePage.setRows(queryParam.getRows());
        responsePage.setTotalRows(totalRows);
        responsePage.setList(list);

        return responsePage;
    }

    /**
     * 通知取餐
     * @param orderId    订单编码
     * @param operatorId
     */
    public Result notifyGetFood(String orderId, String operatorId) {
        OrdOrder ordOrder = ordOrderMapper.selectByPrimaryKey(orderId);
        if (ordOrder != null) {
            OrderDetailVO orderDetailVO = ordOrderMapper.selectOrderDetailByOrderId(orderId, ordOrder.getLanguage());
            //查询会员信息
            JSONObject userInfo = memberUserService.getMemberUserByUserId(ordOrder.getClientId());
            if (null == userInfo) {
                return ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "查询不到该用户信息");
            }
            String getFoodNumber = OrderNoUtil.createGetFoodNumber(ordOrder.getEnterpriseId());
            if (null == getFoodNumber) {
                return ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "生成取餐码失败");
            }
            //发送取餐通知
            NotifyGetFoodInfoDTO notifyGetFoodInfoDTO = new NotifyGetFoodInfoDTO();
            notifyGetFoodInfoDTO.setCreateTime(DateUtils.format(ordOrder.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            notifyGetFoodInfoDTO.setEnterpriseId(ordOrder.getEnterpriseId());
            notifyGetFoodInfoDTO.setFormId(ordOrder.getFormId());
            notifyGetFoodInfoDTO.setGetFoodNumber(getFoodNumber);
            notifyGetFoodInfoDTO.setOpenId(userInfo.getString("openId"));
            notifyGetFoodInfoDTO.setOrderNo(ordOrder.getOrderNo());
            notifyGetFoodInfoDTO.setOrderType(OrderType.TS_ORDER.value());
            notifyGetFoodInfoDTO.setPlatform(ordOrder.getOrderChannel());

            StringBuilder productNames = new StringBuilder();
            for (SubOrderDetailVO subOrderDetailVO : orderDetailVO.getSubOrderProList()) {
                productNames.append(subOrderDetailVO.getProductName() + "x" + subOrderDetailVO.getProductNumber() + ";");
            }
            notifyGetFoodInfoDTO.setProductName(productNames.toString());
            notifyGetFoodInfoDTO.setStoreName(orderDetailVO.getStoreName());
            notifyGetFoodInfoDTO.setRemark("这是一条备注");
            notifyGetFoodInfoDTO.setUrl("www.baidu.com");

            String requestUrl = "https://wx.funnycode.cn/msgTemplate/notifyGetFood";
            try {
                String result = HttpClientUtils.post(requestUrl, JSONObject.toJSONString(notifyGetFoodInfoDTO));
                Result notifyResult = JSONObject.parseObject(result, Result.class);
                if (Result.STATUS.SUCCESS.getStatus() != notifyResult.getStatus()) {
                    return ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "通知取餐失败");
                }
            } catch (Exception e) {
                log.error("通知取餐失败", e);
                return ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "通知取餐失败");
            }
            //修改订单状态
            ordOrder.setOrderStatus(OrderStatus.HAVE_NOTIFY.value());
            ordOrder.setUpdateTime(new Date());
            ordOrder.setUpdateUser(operatorId);
            ordOrderMapper.updateByPrimaryKeySelective(ordOrder);
            return ResUtils.result(Result.STATUS.SUCCESS);
        } else {
            return ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "查询不到该订单");
        }
    }


    /**
     * 批量取消订单
     *
     * @param orderIds    订单编码集合
     * @param channelType 取消渠道
     * @param operatorId  操作人编码
     */
    public Result orderCancel(List<String> orderIds, ChannelType channelType, String operatorId) {
        Result result = null;
        List<OrdOrder> orders = new ArrayList<>();
        for (String orderId : orderIds) {
            OrdOrder ordOrder = ordOrderMapper.selectByPrimaryKey(orderId);
            if (null == ordOrder) {
                result = ResUtils.result(Result.STATUS.NOT_FOUND.getStatus(), "订单不存在");
                break;
            } else if (OrderStatus.HAVE_CANCEL.value().equals(ordOrder.getOrderStatus())) {
                result = ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "订单已取消，请勿重复取消");
                break;
            } else {
                orders.add(ordOrder);
            }
        }

        if (null == result) {
            //遍历列表 取消订单
            for (OrdOrder ordOrder : orders) {
                cancelSingleOrder(ordOrder, channelType, operatorId);
            }
            result = ResUtils.result(Result.STATUS.SUCCESS.getStatus(),Result.STATUS.SUCCESS.getMsg());
        }


        return result;
    }

    /**
     * 取消订单
     *
     * @param ordOrder
     * @param channelType
     * @param operatorId
     */
    public void cancelSingleOrder(OrdOrder ordOrder, ChannelType channelType, String operatorId) {
        ordOrder.setOrderStatus(OrderStatus.HAVE_CANCEL.value());
        ordOrder.setUpdateUser(operatorId);
        ordOrder.setUpdateTime(new Date());

        //是否已支付 已支付则发起退款
        if (PayStatus.HAVE_PAY.value().equals(ordOrder.getPayStatus())) {
            //当日00:00前订单取消，金额原路返回；超期订单只做订单状态处理，资金线下退款
            if (DateUtils.format(ordOrder.getCreateTime(), "yyyy-MM-dd").equals(DateUtils.format(new Date(), "yyyy-MM-dd"))) {
                //发起退款
            }
        }
        ordOrderMapper.updateByPrimaryKeySelective(ordOrder);

    }

    /**
     * 查询导出结果
     *
     * @param queryParam
     * @return
     */
    public List<OrderListExportResultVO> queryOrderListForExport(OrderListQueryParamDTO queryParam) {
        List<OrderListExportResultVO> list = ordOrderMapper.selectOrderListForExport(queryParam);

        //做枚举值的转换
        for (OrderListExportResultVO vo : list) {
            vo.setOrderStatus(OrderStatus.getDisplayByValue(vo.getOrderStatus()));
            vo.setPayStatus(PayStatus.getDisplayByValue(vo.getPayStatus()));
            vo.setPayType(PayType.getDisplayByValue(vo.getPayType()));
        }
        return list;
    }

    /**
     * 二维码收款
     * */
    public Result cashOrder(JSONObject request){
        String tradeType = request.getString("trade_type");
        String body = request.getString("body");//订单标题
        String outTradeNo = request.getString("out_trade_no"); //TODO 无订单关联 自行生成
        String totalFee = request.getString("total_fee");
        String storeId = request.getString("store_id");


        //下单生成签名
        SortedMap<String, Object> sortedMap = JSONObject.parseObject(request.toJSONString(),
                new TypeReference<TreeMap<String, Object>>() {});
        String sign = TransUtil.createSignByMd5(sortedMap, PaymentConfig.SIGN_KEY, true);
        request.put("sign", sign);

        //TODO 准备订单保存数据，订单状态为待下单
        OrdCashOrder oco = new OrdCashOrder();
        String orderId = UUIDUtil.getUUID();
        oco.setOrderId(orderId);
        oco.setTradeType(tradeType);
        oco.setTotalFee(request.getIntValue("total_fee"));
        oco.setStatus("PRE");
        oco.setBody(body);
        oco.setStoreId(storeId);

        //支付下单地址
        String payUrl = null;
        if( "wxpay".equals( tradeType ) ){
            payUrl = PaymentConfig.WXPAY_URL;
        }else if( "alipay".equals( tradeType ) ){
            payUrl = PaymentConfig.ALIPAY_URL ;
        }

        JSONObject responseJson = new JSONObject();
        try {
            String resp = HttpClientUtil.httpPostRequest(payUrl, JSON.parseObject(request.toJSONString(), Map.class));
            JSONObject respJson = JSON.parseObject(resp);
            oco.setStatus("ORDER");
            if(respJson != null && respJson.getString("return_code")!=null
                    && "SUCCESS".equals(respJson.getString("return_code"))){
                //TODO 修改订单状态为 待支付
                oco.setStatus("PAID");
                //返回客户端 （微信支付调用JSSDK调起微信支付，支付宝直接页面跳转支付链接）
                return ResUtils.result(JSON.parseObject(resp));

            }
            responseJson.put("return_code", "FAIL");
            responseJson.put("return_msg", "下单失败");
            return ResUtils.result(responseJson);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }


}
