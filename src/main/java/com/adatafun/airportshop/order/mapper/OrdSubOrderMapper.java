package com.adatafun.airportshop.order.mapper;

import com.adatafun.airportshop.order.pojo.po.OrdSubOrder;

public interface OrdSubOrderMapper {
    int deleteByPrimaryKey(String subOrderId);

    int insert(OrdSubOrder record);

    int insertSelective(OrdSubOrder record);

    OrdSubOrder selectByPrimaryKey(String subOrderId);

    int updateByPrimaryKeySelective(OrdSubOrder record);

    int updateByPrimaryKey(OrdSubOrder record);
}