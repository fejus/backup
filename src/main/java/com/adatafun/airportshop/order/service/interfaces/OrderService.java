package com.adatafun.airportshop.order.service.interfaces;

import com.adatafun.airportshop.order.common.enums.ChannelType;
import com.adatafun.airportshop.order.pojo.dto.OrderListQueryParamDTO;
import com.adatafun.airportshop.order.pojo.po.OrdBill;
import com.adatafun.airportshop.order.pojo.po.OrdOrder;
import com.adatafun.airportshop.order.pojo.po.OrdOrderLanguage;
import com.adatafun.airportshop.order.pojo.po.OrdSubOrder;
import com.adatafun.airportshop.order.pojo.vo.OrderDetailVO;
import com.adatafun.airportshop.order.pojo.vo.OrderItemVO;
import com.adatafun.airportshop.order.pojo.vo.OrderListExportResultVO;
import com.adatafun.utils.api.Result;
import com.adatafun.utils.mybatis.common.ResponsePage;

import java.util.List;

/**
 * desc :
 * Created by Lin on 2017/12/18.
 */
public interface OrderService {
    /**
     * 下单
     *
     * @param ordOrder         主订单
     * @param oriOrderLanguage 主订单语言
     * @param subOrders        子订单
     * @param ordBill          发票信息
     * @return
     */
    public String saveOrder(OrdOrder ordOrder, OrdOrderLanguage oriOrderLanguage, List<OrdSubOrder> subOrders, OrdBill ordBill);


    /**
     * 查看订单详情
     *
     * @param orderId
     * @param language
     * @return
     */
    public OrderDetailVO orderDetail(String orderId, String language) ;

    /**
     * 添加商家备注
     *
     * @param orderId        订单编码
     * @param remarks        备注
     * @param language       当前语言
     * @param operatorUserId 操作人编码
     */
    public void addStoreRemarks(String orderId, String remarks, String language, String operatorUserId);


    /**
     * 订单分页
     *
     * @param queryParam
     * @return
     */
    public ResponsePage<List<OrderItemVO>> orderListByPage(OrderListQueryParamDTO queryParam);


    /**
     * 订单分页
     *
     * @param queryParam
     * @return
     */
    public ResponsePage<List<OrderItemVO>> orderListByPageForMemberUser(OrderListQueryParamDTO queryParam);


    /**
     * 通知取餐
     * @param orderId    订单编码
     * @param operatorId
     */
    public Result notifyGetFood(String orderId, String operatorId);

    /**
     * 批量取消订单
     *
     * @param orderIds    订单编码集合
     * @param channelType 取消渠道
     * @param operatorId  操作人编码
     */
    public Result orderCancel(List<String> orderIds, ChannelType channelType, String operatorId);


    /**
     * 取消订单
     *
     * @param ordOrder
     * @param channelType
     * @param operatorId
     */
    public void cancelSingleOrder(OrdOrder ordOrder, ChannelType channelType, String operatorId);


    /**
     * 查询导出结果
     *
     * @param queryParam
     * @return
     */
    public List<OrderListExportResultVO> queryOrderListForExport(OrderListQueryParamDTO queryParam);
}