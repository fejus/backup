/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.adatafun.airportshop.order.thrift;

import com.adatafun.utils.api.ResUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.server.business.IBusinessService;
import com.wyun.utils.SpringBeanUtil;
import com.adatafun.airportshop.order.conf.ApiHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author zhangpengfei
 * @since 2017-07-08 11:09
 */
@Component
public class BusinessService implements IBusinessService {
    private static Logger logger = LoggerFactory.getLogger(BusinessService.class);

    @Autowired
    private ApiHandler getawayApiHandler;


    @Override
    public JSONObject handle(String operation, JSONObject request) {
        String result = null;
        logger.info("operation:{},request body :{}", operation, request.toJSONString());
        try {
            Map<String, Object> operationMap = getawayApiHandler.getApiMap().get(operation);

            if (null != operationMap) {
                //获得调用的controller
                Object controller = SpringBeanUtil.getBean(operationMap.get("beanName").toString());

                //  进行反射
                Method method = controller.getClass().getMethod(operationMap.get("methodName").toString(),
                        (Class<?>[]) operationMap.get("parameterTypes"));

                LinkedList<Object> arrgs = new LinkedList<>();
                //参数处理
                   /* Parameter[] parameters = method.getParameters();
                    for (Parameter p : parameters) {
                        ApiParam apiParam = p.getAnnotation(ApiParam.class);
                        Object o = null;
                        if (null != apiParam) {
                            o = request.get(apiParam.value());
                        } else {
                            o = request.get(p.getName());
                        }
                        arrgs.add(o);
                    }*/

                //参数处理待扩展
                arrgs.add(request);
                result = (String) method.invoke(controller, arrgs.toArray());

            } else {
                result = JSON.toJSONString(ResUtils.result(500, "无此接口"));
            }


        } catch (Exception e) {
            logger.error("{} error ", operation, e);
            result = JSON.toJSONString(ResUtils.result(500, "接口调用出错"));
        } finally {
            logger.info("response :{} ", result);
            return JSONObject.parseObject(result);
        }
    }

}
