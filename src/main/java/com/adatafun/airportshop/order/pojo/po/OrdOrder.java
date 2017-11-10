package com.adatafun.airportshop.order.pojo.po;

import java.util.Date;

/**
 * @table_name: ord_order.java
 * @date: 2017-11-08 10:42:17
 * @author: Lin
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
*/
public class OrdOrder {
    /**
     * 
     */
    private String orderId;

    /**
     * 订单编码
     */
    private String orderNo;

    /**
     * 商户编码

     */
    private String enterpriseId;

    /**
     * 门店编码
     */
    private String storeId;

    /**
     * 收银员编码
     */
    private String cashierId;

    /**
     * 收银员名字
     */
    private String cashierName;

    /**
     * C端用户编码
     */
    private String clientId;

    /**
     * C端用户手机
     */
    private String clientMobile;

    /**
     * C端用户名字
     */
    private String clientName;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 子订单的数量
     */
    private Integer subOrderNumber;

    /**
     * 就餐人数
     */
    private Integer useNumber;

    /**
     * 桌号
     */
    private String destNumber;

    /**
     * 订单渠道
     */
    private String orderChannel;

    /**
     * 订单金额
     */
    private Integer orderAmount;

    /**
     * 实付金额
     */
    private Integer actualAmount;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付编码
     */
    private String payNo;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 
     */
    private String language;

    /**
     * 
     */
    private String airportCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除标志，1 表示删除，0 表示未删除
     */
    private Integer isDeleted;

    /**
     * 
     * @return order_id 
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * @param orderId 
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 订单编码
     * @return order_no 订单编码
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 订单编码
     * @param orderNo 订单编码
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 商户编码

     * @return enterprise_id 商户编码

     */
    public String getEnterpriseId() {
        return enterpriseId;
    }

    /**
     * 商户编码

     * @param enterpriseId 商户编码

     */
    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    /**
     * 门店编码
     * @return store_id 门店编码
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * 门店编码
     * @param storeId 门店编码
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    /**
     * 收银员编码
     * @return cashier_id 收银员编码
     */
    public String getCashierId() {
        return cashierId;
    }

    /**
     * 收银员编码
     * @param cashierId 收银员编码
     */
    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    /**
     * 收银员名字
     * @return cashier_name 收银员名字
     */
    public String getCashierName() {
        return cashierName;
    }

    /**
     * 收银员名字
     * @param cashierName 收银员名字
     */
    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    /**
     * C端用户编码
     * @return client_id C端用户编码
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * C端用户编码
     * @param clientId C端用户编码
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * C端用户手机
     * @return client_mobile C端用户手机
     */
    public String getClientMobile() {
        return clientMobile;
    }

    /**
     * C端用户手机
     * @param clientMobile C端用户手机
     */
    public void setClientMobile(String clientMobile) {
        this.clientMobile = clientMobile;
    }

    /**
     * C端用户名字
     * @return client_name C端用户名字
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * C端用户名字
     * @param clientName C端用户名字
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 订单状态
     * @return order_status 订单状态
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * 订单状态
     * @param orderStatus 订单状态
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 子订单的数量
     * @return sub_order_number 子订单的数量
     */
    public Integer getSubOrderNumber() {
        return subOrderNumber;
    }

    /**
     * 子订单的数量
     * @param subOrderNumber 子订单的数量
     */
    public void setSubOrderNumber(Integer subOrderNumber) {
        this.subOrderNumber = subOrderNumber;
    }

    /**
     * 就餐人数
     * @return use_number 就餐人数
     */
    public Integer getUseNumber() {
        return useNumber;
    }

    /**
     * 就餐人数
     * @param useNumber 就餐人数
     */
    public void setUseNumber(Integer useNumber) {
        this.useNumber = useNumber;
    }

    /**
     * 桌号
     * @return dest_number 桌号
     */
    public String getDestNumber() {
        return destNumber;
    }

    /**
     * 桌号
     * @param destNumber 桌号
     */
    public void setDestNumber(String destNumber) {
        this.destNumber = destNumber;
    }

    /**
     * 订单渠道
     * @return order_channel 订单渠道
     */
    public String getOrderChannel() {
        return orderChannel;
    }

    /**
     * 订单渠道
     * @param orderChannel 订单渠道
     */
    public void setOrderChannel(String orderChannel) {
        this.orderChannel = orderChannel;
    }

    /**
     * 订单金额
     * @return order_amount 订单金额
     */
    public Integer getOrderAmount() {
        return orderAmount;
    }

    /**
     * 订单金额
     * @param orderAmount 订单金额
     */
    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * 实付金额
     * @return actual_amount 实付金额
     */
    public Integer getActualAmount() {
        return actualAmount;
    }

    /**
     * 实付金额
     * @param actualAmount 实付金额
     */
    public void setActualAmount(Integer actualAmount) {
        this.actualAmount = actualAmount;
    }

    /**
     * 支付类型
     * @return pay_type 支付类型
     */
    public String getPayType() {
        return payType;
    }

    /**
     * 支付类型
     * @param payType 支付类型
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

    /**
     * 支付时间
     * @return pay_time 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 支付时间
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 支付编码
     * @return pay_no 支付编码
     */
    public String getPayNo() {
        return payNo;
    }

    /**
     * 支付编码
     * @param payNo 支付编码
     */
    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    /**
     * 支付状态
     * @return pay_status 支付状态
     */
    public String getPayStatus() {
        return payStatus;
    }

    /**
     * 支付状态
     * @param payStatus 支付状态
     */
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 
     * @return language 
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 
     * @param language 
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 
     * @return airport_code 
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * 
     * @param airportCode 
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
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
     * 是否删除标志，1 表示删除，0 表示未删除
     * @return is_deleted 是否删除标志，1 表示删除，0 表示未删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除标志，1 表示删除，0 表示未删除
     * @param isDeleted 是否删除标志，1 表示删除，0 表示未删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}