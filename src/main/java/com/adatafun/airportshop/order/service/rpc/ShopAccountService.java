package com.adatafun.airportshop.order.service.rpc;

import com.adatafun.airportshop.order.pojo.dto.StoreInfoDTO;
import com.adatafun.airportshop.order.pojo.dto.UserAccountDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.utils.ByteBufferUtil;
import com.wyun.utils.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by jiangbo on 2017/12/28.
 */
@Service
public class ShopAccountService {

    private static Logger logger = LoggerFactory.getLogger(ShopAccountService.class);

    // 帐号信息thrift服务

    private static MyService.Iface shopAccountClient = (MyService.Iface) SpringBeanUtil.getBean("shopAccountClient");


    public static UserAccountDTO getUserInfo(String userId) {
        if (userId == null) {
            return null;
        }
        try {
            JSONObject param = new JSONObject();
            param.put("userId", Long.parseLong(userId));
            Response response = ClientUtil.clientSendData(shopAccountClient, "businessService", "getUserByUserId", param);
            if (response != null && response.getResponeCode().getValue() == 200) {
                JSONObject jsonObj = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                logger.info("获取帐号信息结果{}", jsonObj.toJSONString());
                if (jsonObj != null && jsonObj.containsKey("data")) {
                    UserAccountDTO user = JSONObject.parseObject(jsonObj.getJSONObject("data").toJSONString(), UserAccountDTO.class);
                    return user;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.info("获取帐号信息失败", e);
        }
        return null;
    }
}
