package com.adatafun.airportshop.order.conf;

 import com.adatafun.airportshop.order.conf.util.ConfigCenterUtils;
 import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Map;
import java.util.Properties;

/**
 * Created by tiecheng on 2017/7/22.
 */
public class PropertyPlacheholderConfigurerHelp extends PropertyPlaceholderConfigurer{

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        Map<String, Map<String, String>> prop = ConfigCenterUtils.getAppProperties().getConfigProperties();
        for (Map.Entry<String, Map<String, String>> entry : prop.entrySet()) {
            for (Map.Entry<String, String> map : entry.getValue().entrySet()) {
                props.setProperty(map.getKey(), map.getValue());
            }
        }
        super.processProperties(beanFactoryToProcess, props);
    }

}
