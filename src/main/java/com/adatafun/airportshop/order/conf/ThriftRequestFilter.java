package com.adatafun.airportshop.order.conf;

import com.adatafun.common.springthrift.handler.RequestFilter;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * desc :
 * Created by Lin on 2017/12/9.
 */
@Component(value = "requestFilter")
public class ThriftRequestFilter implements RequestFilter {
    @Override
    public JSONObject doFilter(JSONObject jsonObject) {
        System.out.println("这是过滤器");
        return null;
    }
}
