package com.adatafun.airportshop.order.mapper;

import com.adatafun.airportshop.order.pojo.po.OrdOrderLanguage;

import java.util.List;

public interface OrdOrderLanguageMapper {
    int deleteByPrimaryKey(Integer multiLanguageId);

    int insert(OrdOrderLanguage record);

    int insertSelective(OrdOrderLanguage record);

    OrdOrderLanguage selectByPrimaryKey(Integer multiLanguageId);

    int updateByPrimaryKeySelective(OrdOrderLanguage record);

    int updateByPrimaryKey(OrdOrderLanguage record);

    List<OrdOrderLanguage>  selectLanguageListByOrderId(String orderId);

}