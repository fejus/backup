package com.adatafun.airportshop.order.pojo.dto;

/**
 * desc : 取餐通知模板消息内容
 * Created by Lin on 2017/11/23.
 */
public class NotifyGetFoodInfoDTO {
    private String platform;//平台
    private String openId;//openId
    private String enterpriseId;//企业编码
    private String orderNo;//订单编码
    private String getFoodNumber;//取餐码
    private String storeName;//取餐地址
    private String productName;//产品名称
    private String createTime;//取餐时间
    private String orderType;//订单类型
    private String formId;//小程序formId
    private String url;//跳转url
    private String remark;//备注



    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGetFoodNumber() {
        return getFoodNumber;
    }

    public void setGetFoodNumber(String getFoodNumber) {
        this.getFoodNumber = getFoodNumber;
    }
}
