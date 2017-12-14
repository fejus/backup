package com.adatafun.airportshop.order.conf;

import com.adatafun.airportshop.order.conf.util.ConfigCenterUtils;
import com.wyun.thrift.client.Factory.ServiceClientProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Map;

/**
  + * desc :
 + * Created by Lin on 2017/7/20.
 */
@Configuration
@Order(1)
public class ThriftClientConfig {


    @Bean(name = "memberCenterClient")
    public ServiceClientProxyFactory initMemberCenterClient() {
        ServiceClientProxyFactory serviceClientProxyFactory = new ServiceClientProxyFactory();
        Map<String, String> properties = ConfigCenterUtils.getAppProperties().getProperties("protocol");
        initServiceClient(serviceClientProxyFactory, properties, "member.center.client");
        return serviceClientProxyFactory;
    }

    @Bean(name = "shopProductClient")
    public ServiceClientProxyFactory initShopProductClient() {
        ServiceClientProxyFactory serviceClientProxyFactory = new ServiceClientProxyFactory();
        Map<String, String> properties = ConfigCenterUtils.getAppProperties().getProperties("protocol");
        initServiceClient(serviceClientProxyFactory, properties, "shop.product.client");
        return serviceClientProxyFactory;
    }

    @Bean(name = "shopInfoClient")
    public ServiceClientProxyFactory initShopInfoClient() {
        ServiceClientProxyFactory serviceClientProxyFactory = new ServiceClientProxyFactory();
        Map<String, String> properties = ConfigCenterUtils.getAppProperties().getProperties("protocol");
        initServiceClient(serviceClientProxyFactory, properties, "shop.info.client");
        return serviceClientProxyFactory;
    }

    private void initServiceClient(ServiceClientProxyFactory serviceClientProxyFactory, Map<String, String> properties, String prefix) {
        serviceClientProxyFactory.setIdleTime(Integer.valueOf(properties.get(prefix + ".thrift.client.idleTime")));
        serviceClientProxyFactory.setMaxActive(Integer.valueOf(properties.get(prefix + ".thrift.client.maxActive")));
        serviceClientProxyFactory.setServerName(properties.get(prefix + ".thrift.client.serverName"));
        serviceClientProxyFactory.setServerPackage(properties.get(prefix + ".thrift.client.package"));
        serviceClientProxyFactory.setService(properties.get(prefix + ".thrift.client.address"));
        serviceClientProxyFactory.setPort(Integer.valueOf(properties.get(prefix + ".thrift.client.port")));

    }
}
