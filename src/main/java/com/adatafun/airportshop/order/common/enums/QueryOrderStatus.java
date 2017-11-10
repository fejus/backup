package com.adatafun.airportshop.order.common.enums;

/**
 * desc :
 * Created by Lin on 2017/11/10.
 */
public enum  QueryOrderStatus {

    HAVE_PAY("1", "待支付"),
    HAVE_NOTIFY("2", "已完成"),
    HAVE_CANCEL("3", "已取消");

    private String value;
    private String display;

    public String value() {
        return this.value;
    }

    public String display() {
        return this.display;
    }

    private QueryOrderStatus(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String getDisplayByValue(String value) {
        QueryOrderStatus orderStatus = getEnumByValue(value);
        return orderStatus != null ? orderStatus.display() : null;
    }

    public static QueryOrderStatus getEnumByValue(String value) {

        for (QueryOrderStatus orderStatus : values()) {
            if (orderStatus.value() == value) {
                return orderStatus;
            }
        }
        return null;
    }
}
