/*
 * Copyright (c) 2016. Hangzhou Quantum Finance Co., Ltd. All rights reserved.
 * Created by luojing@wyunbank.com.
 */

package com.adatafun.airportshop.order.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan("com.adatafun.airportshop.order")
public class SpringConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}

