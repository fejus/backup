package com.adatafun.airportshop.order.common.annotation;

import java.lang.annotation.*;

/**
 * desc :
 * Created by Lin on 2017/8/5.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ApiParam {

    /**
     * 参数名
     *
     * @return
     */
    public String value();



    /**
     * 是否必须
     * @return
     */
    boolean required() default false;
}
