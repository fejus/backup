package com.adatafun.airportshop.order.conf.util;

import com.adatafun.airportshop.order.conf.center.ConfigDataManager;
import com.adatafun.utils.common.FileUtils;
import com.adatafun.utils.common.StringUtils;
import com.wyun.zookeeper.conf.AppProperties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by pc-wm on 2017/7/21.
 */
public class ConfigCenterUtils {

    private static AppProperties appProperties;

    public static AppProperties getAppProperties() {
        return appProperties;
    }

    public static void connect(String appName) throws InterruptedException {
        String localEnv = System.getenv("local_env");
        if(localEnv != null && localEnv.length() !=0){
            connect();
        }else {
            // 测试配置中心
            ConfigDataManager dataChange = new ConfigDataManager();
            appProperties = new AppProperties(appName);
            appProperties.registerDataChangeHandler(dataChange);
            System.out.println("zookeeper 初始");
            appProperties.init();
            System.out.println("zookeeper 结束");
//            for (Map.Entry<String, Map<String, String>> stringMapEntry : appProperties.getConfigProperties().entrySet()) {
//                for (Map.Entry<String, String> entry : stringMapEntry.getValue().entrySet()) {
//                    System.out.println(entry.getKey() + entry.getValue());
//                }
//            }
        }
    }

    private static void connect() throws InterruptedException {
        appProperties = new AppProperties("local");
        Map<String, Map<String, String>> configProperties = appProperties.getConfigProperties();

        Map<String, Map<String, String>> properties = FileUtils.parsePropertiesForSpringValue("conf.properties");

        configProperties.put("data.source", properties.get("data.source") == null? new HashMap<>():properties.get("data.source"));
        configProperties.put("extention", properties.get("extention") == null ? new HashMap<>():properties.get("extention"));
        configProperties.put("protocol", properties.get("protocol") == null ? new HashMap<>():properties.get("protocol"));
        configProperties.put("custom", properties.get("custom") == null ? new HashMap<>():properties.get("custom"));
    }

    public static void delete(String configName){
        appProperties.getConfigProperties().remove(configName);
    }

    public static void add(String configName,Map<String, String> properties){
        appProperties.getConfigProperties().put(configName, properties);
    }

    public static void update(String configName,Map<String, String> properties){
        appProperties.getConfigProperties().remove(configName);
        appProperties.getConfigProperties().put(configName, properties);
    }

}

