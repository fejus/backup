package com.adatafun.airportshop.order.pojo.po;

import java.util.Date;

/**
 * @table_name: ord_order_language.java
 * @date: 2017-11-08 10:42:17
 * @author: Lin
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
*/
public class OrdOrderLanguage {
    /**
     * 主键
     */
    private Integer multiLanguageId;

    /**
     * 语言
     */
    private String language;

    /**
     * 订单编码
     */
    private String orderId;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 门店备注
     */
    private String storeRemarks;

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
     * @return multi_language_id 主键
     */
    public Integer getMultiLanguageId() {
        return multiLanguageId;
    }

    /**
     * 主键
     * @param multiLanguageId 主键
     */
    public void setMultiLanguageId(Integer multiLanguageId) {
        this.multiLanguageId = multiLanguageId;
    }

    /**
     * 语言
     * @return language 语言
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 语言
     * @param language 语言
     */
    public void setLanguage(String language) {
        this.language = language;
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
     * 商户名称
     * @return enterprise_name 商户名称
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }

    /**
     * 商户名称
     * @param enterpriseName 商户名称
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    /**
     * 门店名称
     * @return store_name 门店名称
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * 门店名称
     * @param storeName 门店名称
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * 备注
     * @return remarks 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 门店备注
     * @return store_remarks 门店备注
     */
    public String getStoreRemarks() {
        return storeRemarks;
    }

    /**
     * 门店备注
     * @param storeRemarks 门店备注
     */
    public void setStoreRemarks(String storeRemarks) {
        this.storeRemarks = storeRemarks;
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