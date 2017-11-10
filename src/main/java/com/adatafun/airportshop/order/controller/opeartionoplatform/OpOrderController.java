package com.adatafun.airportshop.order.controller.opeartionoplatform;

import com.adatafun.airportshop.order.common.annotation.ApiPath;
import com.adatafun.airportshop.order.common.enums.ChannelType;
import com.adatafun.airportshop.order.pojo.dto.OrderListQueryParamDTO;
import com.adatafun.airportshop.order.pojo.vo.OrderDetailVO;
import com.adatafun.airportshop.order.pojo.vo.OrderItemVO;
import com.adatafun.airportshop.order.service.OrderService;
import com.adatafun.utils.api.ResUtils;
import com.adatafun.utils.api.Result;
import com.adatafun.utils.mybatis.common.ResponsePage;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * desc :
 * Created by Lin on 2017/11/9.
 */
@Component
public class OpOrderController {
    @Autowired
    private OrderService orderService;


    /**
     * 订单详情
     *
     * @param request
     * @return
     */
    @ApiPath(value = "op/orderDetail")
    public String orderDetail(JSONObject request) {
        String orderId = request.getString("orderId");
        String language = request.getString("language");
        OrderDetailVO orderDetailVO = orderService.orderDetail(orderId, language);
        return JSONObject.toJSONString(ResUtils.result(orderDetailVO));
    }


    /**
     * pos订单列表
     *
     * @param request
     * @return
     */
    @ApiPath(value = "op/orderList")
    public String orderList(JSONObject request) {

        OrderListQueryParamDTO queryParam = request.toJavaObject(OrderListQueryParamDTO.class);
        ResponsePage<List<OrderItemVO>> responsePage = orderService.orderListByPage(queryParam);

        return JSONObject.toJSONString(ResUtils.result(responsePage));
    }


    /**
     * @param request
     * @return
     */
    @ApiPath(value = "op/cancelOrder")
    public String cancelOrder(JSONObject request) {
        String operatorId = null;
        String orderId = request.getString("orderIds");
        List<String> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        Result result = orderService.orderCancel(orderIds, ChannelType.POS, operatorId);
        return JSONObject.toJSONString(result);
    }
}
