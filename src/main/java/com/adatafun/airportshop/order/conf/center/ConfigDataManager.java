package com.adatafun.airportshop.order.conf.center;

import com.adatafun.airportshop.order.conf.util.ConfigCenterUtils;
import com.wyun.zookeeper.conf.DataChangeHandler;

import java.util.Map;

/**
 * Created by pc-wm on 2017/7/21.
 */
public class ConfigDataManager extends DataChangeHandler {
    @Override
    public void onConfigAdd(String configName, Map<String, String> newProperties) {
        super.onConfigAdd(configName, newProperties);
        // add
        ConfigCenterUtils.add(configName, newProperties);
    }

    @Override
    public void onConfigDelete(String configName, Map<String, String> oldProperties) {
        super.onConfigDelete(configName, oldProperties);
        // delete
        ConfigCenterUtils.delete(configName);
    }

    @Override
    public void onConfigChange(String configName, Map<String, String> oldProperties, Map<String, String> newProperties) {
        super.onConfigChange(configName, oldProperties, newProperties);
        // update
        ConfigCenterUtils.update(configName, newProperties);
    }
}
