package com.adatafun.airportshop.order.pojo.po;

import java.util.Date;

/**
 * @table_name: ord_sub_order.java
 * @date: 2017-11-08 10:42:17
 * @author: Lin
 * @version: 1.0
 * Copyright(C) 2017 杭州风数信息科技有限公司
 * http://www.adatafun.com/
*/
public class OrdSubOrder {
    /**
     * 子订单编码
     */
    private String subOrderId;

    /**
     * 主订单编码
     */
    private String orderId;

    /**
     * 菜品编码
     */
    private String productId;

    /**
     * 规格编码
     */
    private String specificationId;

    /**
     * 规格数量
     */
    private Integer productNumber;

    /**
     * 产品图片
     */
    private String productImg;

    /**
     * 单价
     */
    private Integer unitPrice;

    /**
     * 总价
     */
    private Integer totalPrice;

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
     * 主订单编码
     * @return order_id 主订单编码
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 主订单编码
     * @param orderId 主订单编码
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 菜品编码
     * @return product_id 菜品编码
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 菜品编码
     * @param productId 菜品编码
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 规格编码
     * @return specification_id 规格编码
     */
    public String getSpecificationId() {
        return specificationId;
    }

    /**
     * 规格编码
     * @param specificationId 规格编码
     */
    public void setSpecificationId(String specificationId) {
        this.specificationId = specificationId;
    }

    /**
     * 规格数量
     * @return product_number 规格数量
     */
    public Integer getProductNumber() {
        return productNumber;
    }

    /**
     * 规格数量
     * @param productNumber 规格数量
     */
    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    /**
     * 产品图片
     * @return product_img 产品图片
     */
    public String getProductImg() {
        return productImg;
    }

    /**
     * 产品图片
     * @param productImg 产品图片
     */
    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    /**
     * 单价
     * @return unit_price 单价
     */
    public Integer getUnitPrice() {
        return unitPrice;
    }

    /**
     * 单价
     * @param unitPrice 单价
     */
    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 总价
     * @return total_price 总价
     */
    public Integer getTotalPrice() {
        return totalPrice;
    }

    /**
     * 总价
     * @param totalPrice 总价
     */
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
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