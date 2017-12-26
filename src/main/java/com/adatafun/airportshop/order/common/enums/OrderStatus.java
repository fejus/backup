package com.adatafun.airportshop.order.common.enums;

/**
 * desc : 订单状态 对应数据库表
 * Created by Lin on 2017/11/7.
 */
public enum OrderStatus {
    /**
     * 备注 结账 通知取餐 打印小票 取消订单
     */
    NEW("0", "未支付"),
    /**
     * 备注 通知取餐 打印小票 取消订单
     */
    HAVE_PAY("1", "已支付"),
    /**
     * 备注 通知取餐 打印小票 取消订单
     */
    HAVE_NOTIFY("2", "已完成"),
    /**
     * 备注
     */
    HAVE_CANCEL("3", "已取消");

    private String value;
    private String display;

    public String value() {
        return this.value;
    }

    public String display() {
        return this.display;
    }

    private OrderStatus(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String getDisplayByValue(String value) {
        OrderStatus orderStatus = getEnumByValue(value);
        return orderStatus != null ? orderStatus.display() : null;
    }

    public static OrderStatus getEnumByValue(String value) {

        for (OrderStatus orderStatus : values()) {
            if (orderStatus.value() == value) {
                return orderStatus;
            }
        }
        return null;
    }
}
