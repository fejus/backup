package com.adatafun.airportshop.order.common.enums;

/**
 * desc :支付状态
 * Created by Lin on 2017/7/17.
 */
public enum PayStatus {
    DISABLE("-1", "已失效"),
    NO_PAY("0", "未支付"),
    HAVE_PAY("1", "已支付"),
    PAY_FAIL("2", "支付失败"),
    WAITING_REFUND("3", "待退款"),
    HAVE_REFUND("4", "已退款"),
    HAVE_CANCEL("5", "已取消");

    private String value;
    private String display;

    public String value() {
        return this.value;
    }

    public String display() {
        return this.display;
    }

    private PayStatus(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String getDisplayByValue(String value) {
        PayStatus payStatus = getEnumByValue(value);
        return payStatus !=null ? payStatus.display() : null;
    }
    public static PayStatus getEnumByValue(String value) {

        for(PayStatus payStatus : values()){
            if(payStatus.value() == value ){
                return payStatus;
            }
        }
        return null;
    }
}
