package com.adatafun.airportshop.order.pojo.po;

import java.util.Date;

/**
 * ord_cash_order 2018-03-27
 */
public class OrdCashOrder {
    /**
     * 主键
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 支付方式（WXPAY,ALIPAY）
     */
    private String tradeType;

    /**
     * 订单标题
     */
    private String body;

    /**
     * 门店id
     */
    private String storeId;

    /**
     * 订单金额，单位为分。
     */
    private Integer totalFee;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 主键
     * @return order_id 主键
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 主键
     * @param orderId 主键
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 订单编号
     * @return order_no 订单编号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 订单编号
     * @param orderNo 订单编号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 支付方式（WXPAY,ALIPAY）
     * @return trade_type 支付方式（WXPAY,ALIPAY）
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 支付方式（WXPAY,ALIPAY）
     * @param tradeType 支付方式（WXPAY,ALIPAY）
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 订单标题
     * @return body 订单标题
     */
    public String getBody() {
        return body;
    }

    /**
     * 订单标题
     * @param body 订单标题
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 门店id
     * @return store_id 门店id
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * 门店id
     * @param storeId 门店id
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    /**
     * 订单金额，单位为分。
     * @return total_fee 订单金额，单位为分。
     */
    public Integer getTotalFee() {
        return totalFee;
    }

    /**
     * 订单金额，单位为分。
     * @param totalFee 订单金额，单位为分。
     */
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 订单状态
     * @return status 订单状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 订单状态
     * @param status 订单状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 订单创建时间
     * @return create_time 订单创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 订单创建时间
     * @param createTime 订单创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}