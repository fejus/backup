package com.adatafun.airportshop.order.pojo.po;

import java.util.Date;

/**
 * @table_name: ord_sub_order_language.java
 * @date: 2017-11-08 10:42:17
 * @author: Lin
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
*/
public class OrdSubOrderLanguage {
    /**
     * 主键
     */
    private Integer subOrderLanguageId;

    /**
     * 语言类型
     */
    private String language;

    /**
     * 子订单编码
     */
    private String subOrderId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 规格名称
     */
    private String specificationName;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String createUser;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String updateUser;

    /**
     * 
     */
    private Integer isDeleted;

    /**
     * 主键
     * @return sub_order_language_id 主键
     */
    public Integer getSubOrderLanguageId() {
        return subOrderLanguageId;
    }

    /**
     * 主键
     * @param subOrderLanguageId 主键
     */
    public void setSubOrderLanguageId(Integer subOrderLanguageId) {
        this.subOrderLanguageId = subOrderLanguageId;
    }

    /**
     * 语言类型
     * @return language 语言类型
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 语言类型
     * @param language 语言类型
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 子订单编码
     * @return sub_order_id 子订单编码
     */
    public String getSubOrderId() {
        return subOrderId;
    }

    /**
     * 子订单编码
     * @param subOrderId 子订单编码
     */
    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId;
    }

    /**
     * 产品名称
     * @return product_name 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 产品名称
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 规格名称
     * @return specification_name 规格名称
     */
    public String getSpecificationName() {
        return specificationName;
    }

    /**
     * 规格名称
     * @param specificationName 规格名称
     */
    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return create_user 
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * @param createUser 
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * @return update_user 
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * @param updateUser 
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * @return is_deleted 
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 
     * @param isDeleted 
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}