package com.adatafun.airportshop.order.controller.h5;

import com.adatafun.airportshop.order.common.annotation.ApiPath;
import com.adatafun.airportshop.order.common.enums.ChannelType;
import com.adatafun.airportshop.order.pojo.dto.H5OrderDTO;
import com.adatafun.airportshop.order.pojo.dto.OrderListQueryParamDTO;
import com.adatafun.airportshop.order.pojo.dto.SubOrderDTO;
import com.adatafun.airportshop.order.pojo.po.OrdBill;
import com.adatafun.airportshop.order.pojo.po.OrdOrder;
import com.adatafun.airportshop.order.pojo.po.OrdOrderLanguage;
import com.adatafun.airportshop.order.pojo.po.OrdSubOrder;
import com.adatafun.airportshop.order.pojo.vo.OrderDetailVO;
import com.adatafun.airportshop.order.pojo.vo.OrderItemVO;
import com.adatafun.airportshop.order.service.OrderService;
import com.adatafun.utils.api.ResUtils;
import com.adatafun.utils.api.Result;
import com.adatafun.utils.data.BeanValidateUtil;
import com.adatafun.utils.mybatis.common.ResponsePage;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * desc : 处理小程序/h5接口
 * Created by Lin on 2017/11/6.
 */
@Component
public class H5OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 下单
     *
     * @param request
     * @return
     */
    @ApiPath(value = "h5/addOrder")
    public String addOrder(JSONObject request) {
        H5OrderDTO orderInfo = request.toJavaObject(H5OrderDTO.class);

        //校验参数
        String message = getCheckParamMsg(orderInfo);
        if (message != null) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), message));
        }

        //获得会员信息
        JSONObject userInfo = new JSONObject();

        //主订单信息
        OrdOrder ordOrder = new OrdOrder();
        ordOrder.setClientId(orderInfo.getClientId());
        ordOrder.setEnterpriseId(orderInfo.getEnterpriseId());
        ordOrder.setStoreId(orderInfo.getStoreId());
        ordOrder.setDestNumber(orderInfo.getDestNumber());
        ordOrder.setUseNumber(orderInfo.getUseNumber());
        ordOrder.setSubOrderNumber(orderInfo.getSubOrderNumber());
        ordOrder.setClientName(userInfo.getString(""));
        ordOrder.setClientMobile(userInfo.getString(""));


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

        //发票信息
        OrdBill ordBill = null;
        if (orderInfo.getBillInfo() != null) {
            ordBill = new OrdBill();
            ordBill.setBillType(orderInfo.getBillInfo().getBillType());
            ordBill.setCompanyName(orderInfo.getBillInfo().getCompanyName());
            ordBill.setTaxNumber(orderInfo.getBillInfo().getTaxNumber());
            ordBill.setPersonName(orderInfo.getBillInfo().getPersonName());
        }


        orderService.saveOrder(ordOrder, oriOrderLanguage, subOrders, ordBill);
        return JSONObject.toJSONString(ResUtils.result(200));

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
    @ApiPath(value = "h5/orderDetail")
    public String orderDetail(JSONObject request) {
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
    @ApiPath(value = "h5/orderList")
    public String orderList(JSONObject request) {
        OrderListQueryParamDTO queryParam = request.toJavaObject(OrderListQueryParamDTO.class);

        ResponsePage<List<OrderItemVO>> responsePage = orderService.orderListByPage(queryParam);

        return JSONObject.toJSONString(ResUtils.result(responsePage));
    }

    /**
     * @param request
     * @return
     */
    @ApiPath(value = "h5/cancelOrder")
    public String cancelOrder(JSONObject request) {
        String clientId = request.getString("clientId");
        String orderId = request.getString("orderIds");
        List<String> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        Result result = orderService.orderCancel(orderIds, ChannelType.MINI_PROGRAM, clientId);
        return JSONObject.toJSONString(result);
    }
}
