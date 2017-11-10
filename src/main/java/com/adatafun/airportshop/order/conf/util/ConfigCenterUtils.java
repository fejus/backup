package com.adatafun.airportshop.order.conf.util;

import com.adatafun.airportshop.order.conf.center.ConfigDataManager;
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
            appProperties.init();
        }
    }

    private static void connect() throws InterruptedException {
        appProperties = new AppProperties("local");
        Map<String, Map<String, String>> configProperties = appProperties.getConfigProperties();
        String defaultPath = "conf.properties";
        Properties props = new Properties();
        try (InputStream in = ConfigCenterUtils.class.getClassLoader().getResourceAsStream(defaultPath)) {
            props.load(new InputStreamReader(in, "UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // data.source
        Map<String, String> database = new HashMap<>();
        Map<String, String> extention = new HashMap<>();
        Map<String, String> protocol = new HashMap<>();
        Map<String, String> custom = new HashMap<>();

        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            if (String.valueOf(entry.getKey()).contains("data.source")) {
                database.put(String.valueOf(entry.getKey()).replaceFirst("data.source.",""), String.valueOf(entry.getValue()));
                continue;
            }
            if (String.valueOf(entry.getKey()).contains("extention")) {
                extention.put(String.valueOf(entry.getKey()).replaceFirst("extention.",""), String.valueOf(entry.getValue()));
                continue;
            }
            if (String.valueOf(entry.getKey()).contains("protocol")) {
                protocol.put(String.valueOf(entry.getKey()).replaceFirst("protocol.",""), String.valueOf(entry.getValue()));
                continue;
            }
            if (String.valueOf(entry.getKey()).contains("custom")) {
                custom.put(String.valueOf(entry.getKey()).replaceFirst("custom.",""), String.valueOf(entry.getValue()));
                continue;
            }
        }

        configProperties.put("data.source", database);
        configProperties.put("extention", extention);
        configProperties.put("protocol", protocol);
        configProperties.put("custom", custom);
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
