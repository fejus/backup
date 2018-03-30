package com.adatafun.airportshop.order;

import com.adatafun.airportshop.order.conf.PropertyPlacheholderConfigurerHelp;
import com.adatafun.airportshop.order.conf.util.ConfigCenterUtils;
import com.adatafun.utils.common.StringUtils;
import com.wyun.thrift.server.business.IBusinessService;
import com.wyun.thrift.server.server.Server;
import com.wyun.utils.SpringBeanUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

 /**
 * Created by luojing@wyunbank.com on 02/05/2017.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        ConfigCenterUtils.connect("order-service");

        init();

    }

    private static void init() throws InterruptedException {

        // need config
        Map<String, String> thriftProp = ConfigCenterUtils.getAppProperties().getProperties("protocol");
        Map<String, String> extentionProp = ConfigCenterUtils.getAppProperties().getProperties("extention");

        int serverPort;
        String serviceName;
        String profileName;

        if (thriftProp == null) {
            serverPort = 8080;
        } else {
            serverPort = StringUtils.getInteger(thriftProp.get("thrift.server.port"), 8080);
        }

        if (extentionProp == null) {
            serviceName = "businessService";
            profileName = "production";
        } else {
            serviceName = StringUtils.getString(extentionProp.get("spring.service.name"), "businessService");
            profileName = StringUtils.getString(extentionProp.get("profile.name"), "production");
        }

        start(serverPort, serviceName, profileName);

    }



    public static void start(int port,String serviceName,String profileName) throws InterruptedException {
        // spring
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles(profileName);
        context.register(SpringBeanUtil.class);
        context.register(PropertyPlacheholderConfigurerHelp.class);
        context.scan("com.adatafun.airportshop.order", "com.**.service");
        context.refresh();
        // thrift server
        Server server = new Server(port);
        server.startSingleServer((IBusinessService) context.getBean("businessService"), serviceName);
        while (true) {
            System.out.println("order-service   interface start");
            Thread.sleep(1000000);
        }
    }
}
