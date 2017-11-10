package com.adatafun.airportshop.order.mapper;

import com.adatafun.airportshop.order.pojo.po.OrdBill;

public interface OrdBillMapper {
    int deleteByPrimaryKey(Integer orderBillId);

    int insert(OrdBill record);

    int insertSelective(OrdBill record);

    OrdBill selectByPrimaryKey(Integer orderBillId);

    int updateByPrimaryKeySelective(OrdBill record);

    int updateByPrimaryKey(OrdBill record);
}