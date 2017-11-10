package com.adatafun.airportshop.order.common.annotation;

import java.lang.annotation.*;

/**
 * desc : api注解
 * Created by Lin on 2017/8/1.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiPath {

    /**
     *  api路径名
     *  @return
     */
    public String value();
}
