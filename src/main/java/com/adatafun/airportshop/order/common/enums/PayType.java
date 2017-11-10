package com.adatafun.airportshop.order.common.enums;

/**
 * desc :
 * Created by Lin on 2017/8/8.
 */
public enum  PayType {
    WX_PAY("1","微信"),
    USER_BALANCE("2","用户余额"),
    ALIPAY("3","支付宝")
    ;

    private String value;
    private String display;

    public String value() {
        return this.value;
    }

    public String display() {
        return this.display;
    }

    private PayType(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String getDisplayByValue(String value) {
        PayType payType = getEnumByValue(value);
        return payType !=null ? payType.display() : null;
    }
    public static PayType getEnumByValue(String value) {

        for(PayType payType : values()){
            if(payType.value().equals(value)){
                return  payType;
            }
        }
        return null;
    }
}
