package com.adatafun.airportshop.order.pojo.dto;

/**
 * desc : 发票信息参数
 * Created by Lin on 2017/11/6.
 */
public class OrderBillInfoDTO {

    private String billType;// 发票类型
    private String companyName;//企业名称
    private String taxNumber;//税号
    private String personName;//个人姓名

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
