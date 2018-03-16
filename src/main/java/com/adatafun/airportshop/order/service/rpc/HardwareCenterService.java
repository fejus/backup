package com.adatafun.airportshop.order.service.rpc;

import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.utils.ByteBufferUtil;
import com.wyun.utils.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by jiangbo on 2018/3/14.
 */
@Service
public class HardwareCenterService {

    private static Logger logger = LoggerFactory.getLogger(HardwareCenterService.class);

    //硬件信息thrift服务

    private static MyService.Iface hardwareCenterClient = (MyService.Iface) SpringBeanUtil.getBean("hardwareCenterClient");

    /**
     * 推送
     * @param request
     * @return
     */
    public JSONObject push(JSONObject request) {
        JSONObject jsonObject = new JSONObject();
        try {
            Response response = ClientUtil.clientSendData(hardwareCenterClient, "businessService", "push", request);
            if (response != null && response.getResponeCode().getValue() == 200) {
                JSONObject jsonObj = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                if (jsonObj != null && jsonObj.containsKey("msg")) {
                    logger.info("推送结果{}", jsonObj.toJSONString());
                    String msg = jsonObj.getString("msg");
                    jsonObject.put("msg", msg);
                    return jsonObject;
                }
            }
        } catch (Exception e) {
            logger.error("推送出错", e);
        }

        jsonObject.put("msg", "推送出错");
        return jsonObject;
    }

}
