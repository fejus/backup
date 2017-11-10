package com.adatafun.airportshop.order.conf;

import com.adatafun.airportshop.order.common.annotation.ApiPath;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * desc :
 * Created by Lin on 2017/8/1.
 */
@Component
public class ApiHandler implements BeanPostProcessor {
    //api集合
    private Map<String,Map<String,Object>> apiMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (methods != null) {
            for (Method method : methods) {
                //扫描ApiPath注解 并放到一个map中
                ApiPath apiPath = AnnotationUtils.findAnnotation(method, ApiPath.class);
                if(apiPath != null){
                    Map<String,Object> api = new HashMap<>();
                    api.put("beanName",beanName);//controller实例名
                    api.put("methodName",method.getName());//执行的方法名
                    api.put("parameterTypes",method.getParameterTypes());//参数类型集合
                    apiMap.put(apiPath.value(),api);
                }
             }
        }
        return bean;
    }

    public Map<String, Map<String, Object>> getApiMap() {
        return apiMap;
    }

    public void setApiMap(Map<String, Map<String, Object>> apiMap) {
        this.apiMap = apiMap;
    }
}
