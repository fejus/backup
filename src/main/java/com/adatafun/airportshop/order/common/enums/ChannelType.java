package com.adatafun.airportshop.order.common.enums;

/**
 * desc :支付类型
 * Created by Lin on 2017/7/17.
 */
public enum ChannelType {
    MINI_PROGRAM("1", "小程序"),
    H5("2", "H5"),
    POS("3", "POS");
    private String value;
    private String display;

    public String value() {
        return this.value;
    }

    public String display() {
        return this.display;
    }

    private ChannelType(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String getDisplayByValue(String value) {
        ChannelType channelType = getEnumByValue(value);
        return channelType != null ? channelType.display() : null;
    }

    public static ChannelType getEnumByValue(String value) {

        for (ChannelType channelType : values()) {
            if (channelType.value().equals(value)) {
                return channelType;
            }
        }
        return null;
    }
}
