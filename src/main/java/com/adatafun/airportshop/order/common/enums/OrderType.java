package com.adatafun.airportshop.order.common.enums;

/**
 * desc :
 * Created by Lin on 2017/11/27.
 */
public enum OrderType {
    TS_ORDER("1", "堂食订单");

    private String value;
    private String display;

    public String value() {
        return this.value;
    }

    public String display() {
        return this.display;
    }

    private OrderType(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String getDisplayByValue(String value) {
        OrderType orderType = getEnumByValue(value);
        return orderType != null ? orderType.display() : null;
    }

    public static OrderType getEnumByValue(String value) {

        for (OrderType orderType : values()) {
            if (orderType.value().equals(value)) {
                return orderType;
            }
        }
        return null;
    }
}
