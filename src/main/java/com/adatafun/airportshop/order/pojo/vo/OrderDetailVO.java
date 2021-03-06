package com.adatafun.airportshop.order.pojo.vo;

import com.adatafun.airportshop.order.pojo.po.OrdOrder;

import java.util.Date;
import java.util.List;

/**
 * desc  订单详情
 * Created by Lin on 2017/11/7.
 */
public class OrderDetailVO   {
    /**
     *
     */
    private String orderId;

    /**
     * 下单渠道
     */
    private String orderChannel;

    /**
     * 订单编码
     */
    private String orderNo;



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
    private String deskNumber;

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
     * 通知取餐状态
     */
    private Integer notifyStatus;

    /**
     * 通知取餐阅读状态
     */
    private Integer notifyReadStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    private String language;//语言
    private String enterpriseId;//商户编码
    private String enterpriseName;//商户名称
    private String storeId;//门店编码
    private String storeName;//门店名称
    private List<SubOrderDetailVO> subOrderProList;//子订单列表
    private BillInfoVO billInfo;//发票信息
    private String remarks;//备注
    private String storeRemarks;//门店备注
    private String getFoodNumber;//取餐码

    /**
     * 预计出菜时间
     */
    private String genFoodTime;

    /**
     * 位置指引图
     */
    private String locationPointImg;



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(String orderChannel) {
        this.orderChannel = orderChannel;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientMobile() {
        return clientMobile;
    }

    public void setClientMobile(String clientMobile) {
        this.clientMobile = clientMobile;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getSubOrderNumber() {
        return subOrderNumber;
    }

    public void setSubOrderNumber(Integer subOrderNumber) {
        this.subOrderNumber = subOrderNumber;
    }

    public Integer getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(Integer useNumber) {
        this.useNumber = useNumber;
    }

    public String getDeskNumber() {
        return deskNumber;
    }

    public void setDeskNumber(String deskNumber) {
        this.deskNumber = deskNumber;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Integer actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }



    public Integer getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public Integer getNotifyReadStatus() {
        return notifyReadStatus;
    }

    public void setNotifyReadStatus(Integer notifyReadStatus) {
        this.notifyReadStatus = notifyReadStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getGetFoodNumber() {
        return getFoodNumber;
    }

    public void setGetFoodNumber(String getFoodNumber) {
        this.getFoodNumber = getFoodNumber;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<SubOrderDetailVO> getSubOrderProList() {
        return subOrderProList;
    }

    public void setSubOrderProList(List<SubOrderDetailVO> subOrderProList) {
        this.subOrderProList = subOrderProList;
    }

    public BillInfoVO getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(BillInfoVO billInfo) {
        this.billInfo = billInfo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStoreRemarks() {
        return storeRemarks;
    }

    public void setStoreRemarks(String storeRemarks) {
        this.storeRemarks = storeRemarks;
    }

    public String getGenFoodTime() {
        return genFoodTime;
    }

    public void setGenFoodTime(String genFoodTime) {
        this.genFoodTime = genFoodTime;
    }

    public String getLocationPointImg() {
        return locationPointImg;
    }

    public void setLocationPointImg(String locationPointImg) {
        this.locationPointImg = locationPointImg;
    }
}
