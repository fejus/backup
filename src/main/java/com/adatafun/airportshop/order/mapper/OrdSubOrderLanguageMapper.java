package com.adatafun.airportshop.order.mapper;

import com.adatafun.airportshop.order.pojo.po.OrdSubOrderLanguage;

public interface OrdSubOrderLanguageMapper {
    int deleteByPrimaryKey(Integer subOrderLanguageId);

    int insert(OrdSubOrderLanguage record);

    int insertSelective(OrdSubOrderLanguage record);

    OrdSubOrderLanguage selectByPrimaryKey(Integer subOrderLanguageId);

    int updateByPrimaryKeySelective(OrdSubOrderLanguage record);

    int updateByPrimaryKey(OrdSubOrderLanguage record);
}