package com.adatafun.airportshop.order.mapper;

import com.adatafun.airportshop.order.pojo.po.OrdCashOrder;

public interface OrdCashOrderMapper {
    /**
     *
     * @mbg.generated 2018-03-27
     */
    int deleteByPrimaryKey(String orderId);

    /**
     *
     * @mbg.generated 2018-03-27
     */
    int insert(OrdCashOrder record);

    /**
     *
     * @mbg.generated 2018-03-27
     */
    int insertSelective(OrdCashOrder record);

    /**
     *
     * @mbg.generated 2018-03-27
     */
    OrdCashOrder selectByPrimaryKey(String orderId);

    /**
     *
     * @mbg.generated 2018-03-27
     */
    int updateByPrimaryKeySelective(OrdCashOrder record);

    /**
     *
     * @mbg.generated 2018-03-27
     */
    int updateByPrimaryKey(OrdCashOrder record);
}