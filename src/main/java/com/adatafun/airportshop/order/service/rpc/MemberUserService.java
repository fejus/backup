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
 * desc :
 * Created by Lin on 2017/11/17.
 */
@Service
public class MemberUserService {
    private static Logger logger = LoggerFactory.getLogger(MemberUserService.class);

    //会员信息thrift服务

    private static MyService.Iface memberCenterClient = (MyService.Iface) SpringBeanUtil.getBean("memberCenterClient");

    /**
     * 保存微信会员
     *
     * @param userInfo
     * @return
     */
    public JSONObject saveMemberUser(JSONObject userInfo) {

        try {
            Response response = ClientUtil.clientSendData(memberCenterClient, "businessService", "/memberUser/saveMemberUser", userInfo);
            if (response != null && response.getResponeCode().getValue() == 200) {
                JSONObject jsonObj = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                if (jsonObj != null && jsonObj.containsKey("data")) {
                    logger.info("保存微信会员结果{}", jsonObj.toJSONString());
                    return jsonObj.getJSONObject("data");
                }
            }
        } catch (Exception e) {
            logger.error("保存微信会员出错", e);
        }
        return null;
    }


    /**
     * 根据openId查询会员信息
     *
     * @param openId
     * @return
     */
    public JSONObject getMemberUserByOpenId(String openId) {
        try {
            JSONObject param = new JSONObject();
            param.put("openId", openId);
            Response response = ClientUtil.clientSendData(memberCenterClient, "businessService", "/memberUser/getMemberUserByOpenId", param);
            if (response != null && response.getResponeCode().getValue() == 200) {
                JSONObject jsonObj = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                logger.info("获取微信会员结果{}", jsonObj.toJSONString());
                if (jsonObj != null && jsonObj.containsKey("data")) {
                    return jsonObj;
                }
            }
        } catch (Exception e) {
            logger.error("获取微信会员结果出错", e);
        }
        return null;
    }


    /**
     * 根据openId查询会员信息
     *
     * @param userId
     * @return
     */
    public JSONObject getMemberUserByUserId(String userId) {
        try {
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            Response response = ClientUtil.clientSendData(memberCenterClient, "businessService", "/memberUser/getMemberUserByUserId", param);
            if (response != null && response.getResponeCode().getValue() == 200) {
                JSONObject jsonObj = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                if (jsonObj != null && jsonObj.containsKey("data")) {
                    logger.info("获取微信会员结果{}", jsonObj.toJSONString());
                    return jsonObj.getJSONObject("data");
                }
            }
        } catch (Exception e) {
            logger.error("获取微信会员结果出错", e);
        }
        return null;
    }
}
