package com.adatafun.airportshop.order.mapper;

import com.adatafun.airportshop.order.pojo.dto.OrderListQueryParamDTO;
import com.adatafun.airportshop.order.pojo.po.OrdOrder;
import com.adatafun.airportshop.order.pojo.vo.OrderDetailVO;
import com.adatafun.airportshop.order.pojo.vo.OrderItemVO;
import com.adatafun.airportshop.order.pojo.vo.OrderListExportResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrdOrder record);

    int insertSelective(OrdOrder record);

    OrdOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrdOrder record);

    int updateByPrimaryKey(OrdOrder record);

    OrderDetailVO selectOrderDetailByOrderId(@Param(value = "orderId") String orderId, @Param(value = "language") String language);

    OrderDetailVO selectOrderDetailByOrderNo(@Param(value = "orderNo") String orderNo, @Param(value = "language") String language);

    List<OrderItemVO> selectOrderList(OrderListQueryParamDTO queryParam);

    List<OrderItemVO> selectOrderListByPage(OrderListQueryParamDTO queryParam);

    int countOrder(OrderListQueryParamDTO queryParam);

    /**
     *  查询订单导出结果
     * @param queryParam
     * @return
     */
    List<OrderListExportResultVO> selectOrderListForExport(OrderListQueryParamDTO queryParam);


}