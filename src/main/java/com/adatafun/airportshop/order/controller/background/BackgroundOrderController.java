package com.adatafun.airportshop.order.controller.background;

import com.adatafun.airportshop.order.common.enums.ChannelType;
import com.adatafun.airportshop.order.pojo.dto.BackgroundOrderDTO;
import com.adatafun.airportshop.order.pojo.dto.OrderListQueryParamDTO;
import com.adatafun.airportshop.order.pojo.dto.SubOrderDTO;
import com.adatafun.airportshop.order.pojo.po.OrdOrder;
import com.adatafun.airportshop.order.pojo.po.OrdOrderLanguage;
import com.adatafun.airportshop.order.pojo.po.OrdSubOrder;
import com.adatafun.airportshop.order.pojo.vo.OrderDetailVO;
import com.adatafun.airportshop.order.pojo.vo.OrderItemVO;
import com.adatafun.airportshop.order.pojo.vo.OrderListExportResultVO;
import com.adatafun.airportshop.order.service.OrderServiceImpl;
import com.adatafun.airportshop.order.service.interfaces.OrderService;
import com.adatafun.airportshop.order.service.rpc.MemberUserService;
import com.adatafun.common.springthrift.annotation.RequestBody;
import com.adatafun.common.springthrift.annotation.RequestMapping;
import com.adatafun.common.springthrift.annotation.RequestParam;
import com.adatafun.common.springthrift.annotation.ThriftRequest;
import com.adatafun.utils.api.ResUtils;
import com.adatafun.utils.api.Result;
import com.adatafun.utils.common.StringUtils;
import com.adatafun.utils.data.BeanValidateUtil;
import com.adatafun.utils.mybatis.common.ResponsePage;
import com.alibaba.fastjson.JSON;
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
public class BackgroundOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberUserService memberUserService;


    /**
     * 下单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "pos/addOrder")
    public String addOrder(@ThriftRequest JSONObject request) {

        String user_id = request.getString("user_id");
        BackgroundOrderDTO orderInfo = JSON.toJavaObject(request, BackgroundOrderDTO.class);

        //校验参数
        String message = getCheckParamMsg(orderInfo);
        if (message != null) {
            return JSONObject.toJSONString(ResUtils.result(Result.STATUS.BAD_REQUEST.getStatus(), message));
        }

        //获得操作人信息
        JSONObject userInfo = memberUserService.getMemberUserByUserId("");

        //主订单信息
        OrdOrder ordOrder = new OrdOrder();
        ordOrder.setEnterpriseId(orderInfo.getEnterpriseId());
        ordOrder.setStoreId(orderInfo.getStoreId());
        ordOrder.setDeskNumber(orderInfo.getDeskNumber());
        ordOrder.setUseNumber(orderInfo.getUseNumber());
        ordOrder.setSubOrderNumber(orderInfo.getSubOrderNumber());
        ordOrder.setOrderChannel(ChannelType.POS.value());
        ordOrder.setLanguage(orderInfo.getLanguage());
        ordOrder.setCashierId(user_id);
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
        ordOrder.setSubOrderNumber(subOrders.size());

        String result = orderService.saveOrder(ordOrder, oriOrderLanguage, subOrders, null);
        return result;

    }

    /**
     * pos现金支付时 改变订单的状态
     * @param request
     * @return
     */
    @RequestMapping("pos/changeOrderStatus")
    public String changeOrderStatus(@ThriftRequest JSONObject request) {
        String user_id = request.getString("user_id");

        String orderId = request.getString("orderId");

        String language = request.getString("language");

        String bodyNumber = request.getString("bodyNumber");

        return orderService.changeOrderStatus(user_id, orderId, bodyNumber, language);
    }

    /**
     * pos 推送
     * @param request
     * @return
     */
    @RequestMapping("pos/push")
    public String push(@ThriftRequest JSONObject request) {
        String orderId = request.getString("orderId");

        String language = request.getString("language");

        String bodyNumber = request.getString("bodyNumber");

        String result = orderService.push(orderId, bodyNumber, language);

        return result;
    }

    /**
     * pos 打印小票
     * @param request
     * @return
     */
    @RequestMapping("pos/smallTicketPrint")
    public String smallTicketPrint(@ThriftRequest JSONObject request) {
        String orderId = request.getString("orderId");

        String language = request.getString("language");

        String storeId = request.getString("storeId");

        String result = orderService.smallTicketPrint(orderId, storeId, language);

        return result;
    }

    @RequestMapping("background/smallTicketPrint")
    public String printSmallTicket(@ThriftRequest JSONObject request) {
        String orderId = request.getString("orderId");

        String language = request.getString("language");

        String storeId = request.getString("storeId");

        String result = orderService.printSmallTicket(orderId, storeId, language);

        return result;

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
     * @param
     * @return
     */
    @RequestMapping(value = "background/orderDetail")
    public String orderDetail(@RequestParam(name = "orderId") String orderId, @RequestParam(name = "language") String language) {
        OrderDetailVO orderDetailVO = orderService.orderDetail(orderId, language);
        return JSONObject.toJSONString(ResUtils.result(orderDetailVO));
    }


    /**
     * 订单列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "background/orderList")
    public String orderList(@ThriftRequest JSONObject request) {
        OrderListQueryParamDTO queryParam = request.toJavaObject(OrderListQueryParamDTO.class);

        ResponsePage<List<OrderItemVO>> responsePage = orderService.orderListByPage(queryParam);


        return JSONObject.toJSONString(ResUtils.result(responsePage));
    }


    /**
     * pos订单列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "pos/orderList")
    public String posOrderList(@ThriftRequest JSONObject request) {
        OrderListQueryParamDTO queryParam = request.toJavaObject(OrderListQueryParamDTO.class);

        ResponsePage<List<OrderItemVO>> responsePage = orderService.orderListByPage(queryParam);

        return JSONObject.toJSONStringWithDateFormat(ResUtils.result(responsePage), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 订单详情
     *
     * @param
     * @return
     */
    @RequestMapping(value = "pos/orderDetail")
    public String posOrderDetail(@RequestParam(name = "orderId") String orderId, @RequestParam(name = "language") String language) {
        OrderDetailVO orderDetailVO = orderService.orderDetail(orderId, language);
        return JSONObject.toJSONStringWithDateFormat(ResUtils.result(orderDetailVO), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 商家添加备注
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "order/addRemarks")
    public String addRemark(@ThriftRequest JSONObject request) {
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
        return JSONObject.toJSONString(ResUtils.result(Result.STATUS.SUCCESS.getStatus(), "备注成功"));
    }

    @RequestMapping("background/notifyGetFood")
    public String notifyGetFood(@ThriftRequest JSONObject request) {

        String orderId = request.getString("orderId");
        Result result = orderService.notifyGetFood(orderId, null);

        return JSONObject.toJSONString(result);
    }

    /**
     * @param orderId
     * @return
     */
    @RequestMapping(value = "pos/cancelOrder")
    public String cancelOrder(@RequestParam(name = "orderId") String orderId) {
        String operatorId = null;
        List<String> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        Result result = orderService.orderCancel(orderIds, ChannelType.POS, operatorId);
        return JSONObject.toJSONString(result);
    }

    /**
     * @param orderId
     * @return
     */
    @RequestMapping(value = "background/cancelOrder")
    public String backgroundCancelOrder(@RequestParam(name = "orderId") String orderId) {
        String operatorId = null;
        List<String> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        Result result = orderService.orderCancel(orderIds, ChannelType.POS, operatorId);
        return JSONObject.toJSONString(result);
    }

    @RequestMapping(value = "background/orderExport")
    public String orderExport(@ThriftRequest JSONObject request) {
        OrderListQueryParamDTO queryParam = request.toJavaObject(OrderListQueryParamDTO.class);

        List<OrderListExportResultVO> list = orderService.queryOrderListForExport(queryParam);

        return JSONObject.toJSONString(ResUtils.result(list));
    }
}
