package com.adatafun.airportshop.order.common.config;

import com.adatafun.airportshop.order.conf.util.ConfigCenterUtils;

import java.util.Map;

public class PaymentConfig {
    private static Map<String , String> openProperties = ConfigCenterUtils.getAppProperties().getProperties("custom");

    //业务端标识
    public static String APP_NAME = openProperties.get("pay.appName");
    //支付宝扫码下单url
    public static String ALIPAY_URL = openProperties.get("pay.apliay.url");
    //微信支付下单url
    public static String WXPAY_URL = openProperties.get("pay.wx.url");
    //签名key
    public static String SIGN_KEY = openProperties.get("bYTMptU4BJ4zkQJ6");


}
