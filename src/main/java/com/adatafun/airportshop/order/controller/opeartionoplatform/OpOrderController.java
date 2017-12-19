package com.adatafun.airportshop.order.controller.opeartionoplatform;

import com.adatafun.airportshop.order.service.interfaces.OrderService;
import com.adatafun.common.springthrift.annotation.RequestMapping;
import com.adatafun.common.springthrift.annotation.RequestParam;
import com.adatafun.common.springthrift.annotation.ThriftRequest;
import com.adatafun.airportshop.order.common.enums.ChannelType;
import com.adatafun.airportshop.order.pojo.dto.OrderListQueryParamDTO;
import com.adatafun.airportshop.order.pojo.vo.OrderDetailVO;
import com.adatafun.airportshop.order.pojo.vo.OrderItemVO;
import com.adatafun.airportshop.order.service.OrderServiceImpl;
import com.adatafun.utils.api.ResUtils;
import com.adatafun.utils.api.Result;
import com.adatafun.utils.mybatis.common.ResponsePage;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * desc :
 * Created by Lin on 2017/11/9.
 */
@Controller
@RequestMapping(value = "op")
public class OpOrderController {
    @Autowired
    private OrderService orderService;


    /**
     * 订单详情
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/orderDetail")
    public String orderDetail(@RequestParam(name = "orderId") String orderId, @RequestParam(name = "language") String language) {
        OrderDetailVO orderDetailVO = orderService.orderDetail(orderId, language);
        return JSONObject.toJSONString(ResUtils.result(orderDetailVO));
    }


    /**
     * pos订单列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderList")
    public String orderList(@ThriftRequest  JSONObject request) {

        OrderListQueryParamDTO queryParam = request.toJavaObject(OrderListQueryParamDTO.class);
        ResponsePage<List<OrderItemVO>> responsePage = orderService.orderListByPage(queryParam);

        return JSONObject.toJSONString(ResUtils.result(responsePage));
    }


    /**
     * 运营平台取消订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelOrder")
    public String cancelOrder(@ThriftRequest JSONObject request) {
        String operatorId = null;
        String orderId = request.getString("orderIds");
        List<String> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        Result result = orderService.orderCancel(orderIds, ChannelType.POS, operatorId);
        return JSONObject.toJSONString(result);
    }
}
