package com.adatafun.airportshop.order.service;

import com.adatafun.airportshop.order.common.enums.BooleanEnum;
import com.adatafun.airportshop.order.common.enums.ChannelType;
import com.adatafun.airportshop.order.common.enums.OrderStatus;
import com.adatafun.airportshop.order.common.enums.PayStatus;
import com.adatafun.airportshop.order.mapper.*;
import com.adatafun.airportshop.order.pojo.dto.OrderListQueryParamDTO;
import com.adatafun.airportshop.order.pojo.po.*;
import com.adatafun.airportshop.order.pojo.vo.OrderDetailVO;
import com.adatafun.airportshop.order.pojo.vo.OrderItemVO;
import com.adatafun.utils.api.ResUtils;
import com.adatafun.utils.api.Result;
import com.adatafun.utils.common.DateUtils;
import com.adatafun.utils.common.StringUtils;
import com.adatafun.utils.common.UUIDUtil;
import com.adatafun.utils.mybatis.common.ResponsePage;
import com.adatafun.utils.translate.LanguageEnum;
import com.adatafun.utils.translate.LanguageUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * desc : 订单服务
 * Created by Lin on 2017/11/6.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderService {

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
        JSONObject merchantInfo = new JSONObject();

        //查询门店信息
        JSONObject storeInfo = new JSONObject();

        //查询菜品及其规格信息
        List<JSONObject> products = new ArrayList<>();
        List<JSONObject> specifications = new ArrayList<>();

        //保存主订单
        saveMainOrder(ordOrder, subOrders, oriOrderLanguage, merchantInfo, storeInfo);

        //保存子订单
        saveSubOrder(ordOrder, subOrders, products, specifications);

        //保存发票信息
        saveBillInfo(ordOrder, ordBill);

        return null;

    }

    /**
     * 保存主订单信息
     *
     * @param oriOrderLanguage 语言
     * @param subOrders        子订单
     * @param ordOrder         订单
     * @param merchantInfo     商户信息
     * @param storeInfo        门店信息
     * @return
     */
    public void saveMainOrder(OrdOrder ordOrder, List<OrdSubOrder> subOrders, OrdOrderLanguage oriOrderLanguage, JSONObject merchantInfo, JSONObject storeInfo) {
        //生成订单编码
        String orderNo = new Date().getTime() + new Random().nextInt(100) + "";
        ordOrder.setOrderNo(orderNo);
        ordOrder.setOrderId(UUIDUtil.getUUID());

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
        ordOrderMapper.insert(ordOrder);

        //处理多语言
        saveMainOrderLanguage(ordOrder, oriOrderLanguage, merchantInfo, storeInfo);


    }


    /**
     * 保存主订单多语言
     *
     * @param ordOrder
     * @param oriOrderLanguage
     * @param merchantInfo     商户信息
     * @param storeInfo        门店信息
     */
    public void saveMainOrderLanguage(OrdOrder ordOrder, OrdOrderLanguage oriOrderLanguage, JSONObject merchantInfo, JSONObject storeInfo) {
        //多语言处理
        LanguageEnum oriLanguageEnum = LanguageEnum.getEnumByValue(ordOrder.getLanguage());
        List<OrdOrderLanguage> ordOrderLanguages = new ArrayList<>();
        ordOrderLanguages.add(oriOrderLanguage);

        List<String> languages = new ArrayList<>();//商家支持的多语言列表
        for (String language : languages) {
            LanguageEnum languageEnum = LanguageEnum.getEnumByValue(language);
            if (!ordOrder.getLanguage().equals(language)) {
                OrdOrderLanguage orderLanguage = new OrdOrderLanguage();
                orderLanguage.setLanguage(language);
                orderLanguage.setEnterpriseName(LanguageUtil.getTransResult("肯德基", oriLanguageEnum, languageEnum));//商户名称
                orderLanguage.setStoreName(LanguageUtil.getTransResult("肯德基一店", oriLanguageEnum, languageEnum));//门店名称
                orderLanguage.setOrderId(ordOrder.getOrderId());
                if (StringUtils.isNotBlank(oriOrderLanguage.getRemarks())) {
                    orderLanguage.setRemarks(LanguageUtil.getTransResult(oriOrderLanguage.getRemarks(), oriLanguageEnum, languageEnum));
                }
                if (StringUtils.isNotBlank(oriOrderLanguage.getStoreRemarks())) {
                    orderLanguage.setRemarks(LanguageUtil.getTransResult(oriOrderLanguage.getStoreRemarks(), oriLanguageEnum, languageEnum));
                }
                orderLanguage.setCreateTime(oriOrderLanguage.getCreateTime());
                orderLanguage.setCreateUser(orderLanguage.getCreateUser());
                orderLanguage.setIsDeleted(BooleanEnum.NO.value());

                ordOrderLanguages.add(orderLanguage);
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
     * @param ordOrder       主订单数据
     * @param subOrders      子订单列表
     * @param products       菜品列表
     * @param specifications 菜品规格列表
     */
    public void saveSubOrder(OrdOrder ordOrder, List<OrdSubOrder> subOrders, List<JSONObject> products, List<JSONObject> specifications) {
        for (int i = 0; i < subOrders.size(); i++) {
            OrdSubOrder subOrder = subOrders.get(i);
            subOrder.setSubOrderId(UUIDUtil.getUUID());
            JSONObject specification = specifications.get(i);
            JSONObject product = products.get(i);
            subOrder.setOrderId(ordOrder.getOrderId());
            subOrder.setProductImg(specification.getString(""));//图片
            subOrder.setUnitPrice(specification.getInteger("price"));//单价
            subOrder.setTotalPrice(subOrder.getProductNumber() * subOrder.getUnitPrice());//单品总价
            subOrder.setCreateTime(ordOrder.getCreateTime());
            subOrder.setCreateUser(ordOrder.getCreateUser());
            ordSubOrderMapper.insert(subOrder);

            //处理多语言
            saveSubOrderLanguage(ordOrder, subOrder, product, specification);

        }
    }

    /**
     * 处理子订单多语言
     *
     * @param ordOrder
     * @param subOrder
     * @param product
     * @param specification
     */
    public void saveSubOrderLanguage(OrdOrder ordOrder, OrdSubOrder subOrder, JSONObject product, JSONObject specification) {
        //多语言处理
        LanguageEnum oriLanguageEnum = LanguageEnum.getEnumByValue(ordOrder.getLanguage());
        List<OrdSubOrderLanguage> orderLanguages = new ArrayList<>();

        List<String> languages = new ArrayList<>();//商家支持的多语言列表
        for (String language : languages) {
            LanguageEnum languageEnum = LanguageEnum.getEnumByValue(language);
            OrdSubOrderLanguage orderLanguage = new OrdSubOrderLanguage();
            orderLanguage.setLanguage(language);
            orderLanguage.setProductName(product.getString(""));
            orderLanguage.setSpecificationName(specification.getString(""));
            orderLanguage.setSubOrderId(subOrder.getSubOrderId());
            orderLanguage.setCreateTime(subOrder.getCreateTime());
            orderLanguages.add(orderLanguage);
        }

        //循环插入
        for (OrdSubOrderLanguage ordOrderLanguage : orderLanguages) {
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
        responsePage.setData(list);

        return responsePage;
    }

    /**
     * @param orderId    订单编码
     * @param operatorId
     */
    public void notifyGetFood(String orderId, String operatorId) {
        OrdOrder ordOrder = ordOrderMapper.selectByPrimaryKey(orderId);
        if (ordOrder != null) {
            //查询会员信息

            //发送取餐通知

            //修改订单状态
            ordOrder.setOrderStatus(OrderStatus.HAVE_NOTIFY.value());
            ordOrder.setUpdateTime(new Date());
            ordOrder.setUpdateUser(operatorId);
            ordOrderMapper.updateByPrimaryKeySelective(ordOrder);
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
            result = ResUtils.result(Result.STATUS.SUCCESS);
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


}
