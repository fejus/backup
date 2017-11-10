package com.adatafun.airportshop.order.pojo.po;

import java.util.Date;

/**
 * @table_name: ord_bill.java
 * @date: 2017-11-08 10:42:17
 * @author: Lin
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
*/
public class OrdBill {
    /**
     * 
     */
    private Integer orderBillId;

    /**
     * 订单编码
     */
    private String orderId;

    /**
     * 发票类型
     */
    private String billType;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 税号
     */
    private String taxNumber;

    /**
     * 个人姓名
     */
    private String personName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 
     * @return order_bill_id 
     */
    public Integer getOrderBillId() {
        return orderBillId;
    }

    /**
     * 
     * @param orderBillId 
     */
    public void setOrderBillId(Integer orderBillId) {
        this.orderBillId = orderBillId;
    }

    /**
     * 订单编码
     * @return order_id 订单编码
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 订单编码
     * @param orderId 订单编码
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 发票类型
     * @return bill_type 发票类型
     */
    public String getBillType() {
        return billType;
    }

    /**
     * 发票类型
     * @param billType 发票类型
     */
    public void setBillType(String billType) {
        this.billType = billType;
    }

    /**
     * 企业名称
     * @return company_name 企业名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 企业名称
     * @param companyName 企业名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 税号
     * @return tax_number 税号
     */
    public String getTaxNumber() {
        return taxNumber;
    }

    /**
     * 税号
     * @param taxNumber 税号
     */
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    /**
     * 个人姓名
     * @return person_name 个人姓名
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * 个人姓名
     * @param personName 个人姓名
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建人
     * @return create_user 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 更新人
     * @return update_user 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新人
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 是否删除
     * @return is_deleted 是否删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除
     * @param isDeleted 是否删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}