package com.adatafun.airportshop.order.thrift;

import com.adatafun.common.springthrift.handler.HandlerAdapter;
import com.adatafun.common.springthrift.handler.RequestFilter;
import com.adatafun.common.springthrift.handler.RequestMappingHandler;
import com.adatafun.common.springthrift.thrift.BusinessService;
import com.wyun.utils.SpringBeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;

/**
 * desc :thrift服务端配置
 * Created by Lin on 2017/12/3.
 */
@Configuration
public class ThriftServerConfig    {

    @Bean
    public RequestMappingHandler initRequestMappingHandler(){
        return new RequestMappingHandler();
    }

    @Bean
    public HandlerAdapter initHandler(){
        return new HandlerAdapter();
    }

    @Bean
    public RequestFilter initRequestFilter(){
        return SpringBeanUtil.getBean("requestFilter");
    }


    @Bean(name = "businessService")
    public BusinessService initThriftServer(RequestMappingHandler requestMappingHandler, HandlerAdapter handlerAdapter, RequestFilter requestFilter){
        BusinessService businessService = new BusinessService();
        businessService.init(requestMappingHandler,handlerAdapter,requestFilter);
        return  businessService;
    }





}
