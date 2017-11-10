package com.adatafun.airportshop.order.controller.background;

import com.adatafun.airportshop.order.common.annotation.ApiPath;
import com.adatafun.airportshop.order.common.enums.ChannelType;
import com.adatafun.airportshop.order.pojo.dto.BackgroundOrderDTO;
import com.adatafun.airportshop.order.pojo.dto.OrderListQueryParamDTO;
import com.adatafun.airportshop.order.pojo.dto.SubOrderDTO;
import com.adatafun.airportshop.order.pojo.po.OrdOrder;
import com.adatafun.airportshop.order.pojo.po.OrdOrderLanguage;
import com.adatafun.airportshop.order.pojo.po.OrdSubOrder;
import com.adatafun.airportshop.order.pojo.vo.OrderDetailVO;
import com.adatafun.airportshop.order.pojo.vo.OrderItemVO;
import com.adatafun.airportshop.order.service.OrderService;
import com.adatafun.utils.api.ResUtils;
import com.adatafun.utils.api.Result;
import com.adatafun.utils.common.StringUtils;
import com.adatafun.utils.data.BeanValidateUtil;
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
public class BackgroundOrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 下单
     *
     * @param request
     * @return
     */
    @ApiPath(value = "pos/addOrder")
    public String addOrder(JSONObject request) {
        BackgroundOrderDTO orderInfo = request.toJavaObject(BackgroundOrderDTO.class);

        //校验参数
        String message = getCheckParamMsg(orderInfo);
        if (message != null) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), message));
        }

        //获得操作人信息
        JSONObject userInfo = new JSONObject();

        //主订单信息
        OrdOrder ordOrder = new OrdOrder();
        ordOrder.setCashierId(orderInfo.getCashierId());
        ordOrder.setEnterpriseId(orderInfo.getEnterpriseId());
        ordOrder.setStoreId(orderInfo.getStoreId());
        ordOrder.setDestNumber(orderInfo.getDestNumber());
        ordOrder.setUseNumber(orderInfo.getUseNumber());
        ordOrder.setSubOrderNumber(orderInfo.getSubOrderNumber());
        //操作人姓名
        //to do

        //主订单翻译字段信息
        OrdOrderLanguage oriOrderLanguage = new OrdOrderLanguage();
        oriOrderLanguage.setStoreRemarks(orderInfo.getStoreRemarks());

        //子订单
        List<OrdSubOrder> subOrders = new ArrayList<>();
        for (SubOrderDTO subOrderDTO : orderInfo.getSubOrderProList()) {
            OrdSubOrder subOrder = new OrdSubOrder();
            subOrder.setProductId(subOrderDTO.getProductId());
            subOrder.setSpecificationId(subOrderDTO.getSpecificationId());
            subOrder.setProductNumber(subOrderDTO.getProductNumber());
            subOrders.add(subOrder);
        }

        orderService.saveOrder(ordOrder, oriOrderLanguage, subOrders, null);
        return JSONObject.toJSONString(ResUtils.result(Result.STATUS.SUCCESS));

    }

    /**
     * 参数校验
     *
     * @param orderInfo
     * @return
     */
    private String getCheckParamMsg(BackgroundOrderDTO orderInfo) {
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
    @ApiPath(value = "background/orderDetail")
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
    @ApiPath(value = "background/orderList")
    public String orderList(JSONObject request) {
        OrderListQueryParamDTO queryParam = request.toJavaObject(OrderListQueryParamDTO.class);

        ResponsePage<List<OrderItemVO>> responsePage = orderService.orderListByPage(queryParam);


        return JSONObject.toJSONString(ResUtils.result(responsePage));
    }

    /**
     * 商家添加备注
     *
     * @param request
     * @return
     */
    @ApiPath(value = "order/addRemarks")
    public String addRemark(JSONObject request) {
        String language = request.getString("language");
        String orderId = request.getString("orderId");
        String remarks = request.getString("remarks");
        if (StringUtils.isBlank(orderId)) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "订单编码不能为空"));
        }
        if (StringUtils.isBlank(remarks)) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), "备注不能为空"));
        }

        orderService.addStoreRemarks(orderId, remarks, language, null);
        return JSONObject.toJSONString(ResUtils.result(Result.STATUS.SUCCESS));
    }

    @ApiPath("background/notifyGetFood")
    public String notifyGetFood(JSONObject request) {

        String orderId = request.getString("orderId");
        orderService.notifyGetFood(orderId, null);


        return JSONObject.toJSONString(ResUtils.result(Result.STATUS.SUCCESS));
    }

    /**
     * @param request
     * @return
     */
    @ApiPath(value = "pos/cancelOrder")
    public String cancelOrder(JSONObject request) {
        String operatorId = null;
        String orderId = request.getString("orderIds");
        List<String> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        Result result = orderService.orderCancel(orderIds, ChannelType.POS, operatorId);
        return JSONObject.toJSONString(result);
    }
}
