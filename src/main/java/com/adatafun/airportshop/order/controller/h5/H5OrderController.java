package com.adatafun.airportshop.order.controller.h5;

import com.adatafun.airportshop.order.common.config.PaymentConfig;
import com.adatafun.airportshop.order.pojo.po.*;
import com.adatafun.airportshop.order.service.interfaces.OrderService;
import com.adatafun.common.springthrift.annotation.RequestBody;
import com.adatafun.common.springthrift.annotation.RequestMapping;
import com.adatafun.common.springthrift.annotation.RequestParam;
import com.adatafun.common.springthrift.annotation.ThriftRequest;
import com.adatafun.airportshop.order.common.enums.ChannelType;
import com.adatafun.airportshop.order.pojo.dto.H5OrderDTO;
import com.adatafun.airportshop.order.pojo.dto.OrderListQueryParamDTO;
import com.adatafun.airportshop.order.pojo.dto.SubOrderDTO;
import com.adatafun.airportshop.order.pojo.vo.OrderDetailVO;
import com.adatafun.airportshop.order.pojo.vo.OrderItemVO;
import com.adatafun.airportshop.order.service.OrderServiceImpl;
import com.adatafun.airportshop.order.service.rpc.MemberUserService;
import com.adatafun.utils.api.ResUtils;
import com.adatafun.utils.api.Result;
 import com.adatafun.utils.common.StringUtils;
import com.adatafun.utils.common.UUIDUtil;
import com.adatafun.utils.data.BeanValidateUtil;
import com.adatafun.utils.mybatis.common.ResponsePage;
import com.adatafun.utils.secure.token.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.payment.service.SignatureService;
import com.payment.utils.sign.TransUtil;
import com.zhiweicloud.guest.common.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * desc : 处理小程序/h5接口
 * Created by Lin on 2017/11/6.
 */
@Controller
public class H5OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberUserService memberUserService;

    @Autowired
    private SignatureService signatureService;

    /**
     * 下单
     *
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "h5/addOrder")
    public String addOrder(@RequestParam(name = "accessToken") String accessToken, @RequestBody H5OrderDTO orderInfo) {
        //token解析
        JSONObject tokenInfo = JWTUtil.validateJWT(accessToken, "adatafun");
        String userId = null;
        if (tokenInfo != null) {
            if (JWTUtil.SUCCESS == tokenInfo.getInteger("code")) {
                userId = tokenInfo.getJSONObject("data").getString("id");
            } else if (JWTUtil.JWT_ERRCODE_EXPIRE == tokenInfo.getInteger("code")) {
                return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "用户身份过期"));
            }
        }
        if (StringUtils.isBlank(userId)) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "用户身份校验失败"));
        }

        //校验参数
        String message = getCheckParamMsg(orderInfo);
        if (message != null) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), message));
        }

        //获得会员信息
        JSONObject userInfo = memberUserService.getMemberUserByUserId(userId);
        if (null == userInfo) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "查询不到该用户信息"));
        }

        //主订单信息
        OrdOrder ordOrder = new OrdOrder();
        ordOrder.setClientId(userInfo.getString("userId"));
        ordOrder.setEnterpriseId(orderInfo.getEnterpriseId());
        ordOrder.setStoreId(orderInfo.getStoreId());
        ordOrder.setDeskNumber(orderInfo.getDeskNumber());
        ordOrder.setUseNumber(orderInfo.getUseNumber());
        ordOrder.setSubOrderNumber(orderInfo.getSubOrderNumber());
        ordOrder.setClientName(userInfo.getString("nickName"));
        ordOrder.setClientMobile(userInfo.getString("mobile"));
        ordOrder.setOrderChannel(orderInfo.getChannelType());
        ordOrder.setLanguage(orderInfo.getLanguage());
        ordOrder.setFormId(orderInfo.getFormId());

        //主订单翻译字段信息
        OrdOrderLanguage oriOrderLanguage = new OrdOrderLanguage();
        oriOrderLanguage.setRemarks(orderInfo.getRemarks());

        //子订单
        List<OrdSubOrder> subOrders = new ArrayList<>();
        for (SubOrderDTO subOrderDTO : orderInfo.getSubOrderProList()) {
            OrdSubOrder subOrder = new OrdSubOrder();
            subOrder.setProductId(subOrderDTO.getProductId());
            subOrder.setSpecificationId(subOrderDTO.getSpecificationId());
            subOrder.setProductNumber(subOrderDTO.getProductNumber());
            subOrders.add(subOrder);
        }
        ordOrder.setSubOrderNumber(subOrders.size());

        //发票信息
        OrdBill ordBill = null;
        if (orderInfo.getBillInfo() != null) {
            ordBill = new OrdBill();
            ordBill.setBillType(orderInfo.getBillInfo().getBillType());
            ordBill.setCompanyName(orderInfo.getBillInfo().getCompanyName());
            ordBill.setTaxNumber(orderInfo.getBillInfo().getTaxNumber());
            ordBill.setPersonName(orderInfo.getBillInfo().getPersonName());
        }


        String result = orderService.saveOrder(ordOrder, oriOrderLanguage, subOrders, ordBill);
        return result;

    }

    /**
     * 参数校验
     *
     * @param orderInfo
     * @return
     */
    private String getCheckParamMsg(H5OrderDTO orderInfo) {
        String message = BeanValidateUtil.getValidateMessage(orderInfo);
        if (message == null) {
            for (SubOrderDTO subOrderDTO : orderInfo.getSubOrderProList()) {
                message = BeanValidateUtil.getValidateMessage(subOrderDTO);
                if (message != null) {
                    break;
                }
            }
        }
        return message;
    }

    /**
     * 订单详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "h5/orderDetail")
    public String orderDetail(@ThriftRequest JSONObject request) {
        String orderId = request.getString("orderId");
        String language = request.getString("language");
        OrderDetailVO orderDetailVO = orderService.orderDetail(orderId, language);
        return JSONObject.toJSONString(ResUtils.result(orderDetailVO));
    }

    /**
     * 订单列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "h5/orderList")
    public String orderList(@ThriftRequest JSONObject request) {
        OrderListQueryParamDTO queryParam = request.toJavaObject(OrderListQueryParamDTO.class);

        ResponsePage<List<OrderItemVO>> responsePage = orderService.orderListByPageForMemberUser(queryParam);

        return JSONObject.toJSONString(ResUtils.result(responsePage));
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "h5/cancelOrder")
    public String cancelOrder(@ThriftRequest JSONObject request) {
        String clientId = request.getString("clientId");
        String orderId = request.getString("orderId");
        List<String> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        Result result = orderService.orderCancel(orderIds, ChannelType.MINI_PROGRAM, clientId);
        return JSONObject.toJSONString(result);
    }

    /**
     * 收款码下单
     * */
    @RequestMapping(value = "h5/tradePay")
    public String orderTrade(@ThriftRequest JSONObject request){
        //token解析
        JSONObject tokenInfo = JWTUtil.validateJWT(request.getString("accessToken"), "adatafun");
        String userId = null;
        if (tokenInfo != null) {
            if (JWTUtil.SUCCESS == tokenInfo.getInteger("code")) {
                userId = tokenInfo.getJSONObject("data").getString("id");
            } else if (JWTUtil.JWT_ERRCODE_EXPIRE == tokenInfo.getInteger("code")) {
                return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "用户身份过期"));
            }
        }
        if (StringUtils.isBlank(userId)) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "用户身份校验失败"));
        }
        //下单参数
        String tradeType = request.getString("trade_type");
        String body = request.getString("body");//订单标题
        String outTradeNo = request.getString("out_trade_no"); //TODO 无订单关联 自行生成
        String totalFee = request.getString("total_fee");
        String storeId = request.getString("store_id");

        if(tradeType == null || body == null || outTradeNo == null
                || totalFee == null || storeId == null){
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(),
                    "参数不能为空"));
        }
        //支付下单地址
        if( !"wxpay".equals( tradeType ) && !"alipay".equals( tradeType )){
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(),
                    "支付方式不存在或暂未开通"));
        }

        Result result = orderService.cashOrder(request);

        return JSONObject.toJSONString(result);
    }
}
