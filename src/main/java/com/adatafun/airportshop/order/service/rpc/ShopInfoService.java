package com.adatafun.airportshop.order.service.rpc;

import com.adatafun.airportshop.order.pojo.dto.EnterpriseInfoDTO;
import com.adatafun.airportshop.order.pojo.dto.StoreInfoDTO;
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
import java.util.List;

/**
 * desc :
 * Created by Lin on 2017/12/4.
 */
@Service
public class ShopInfoService {
    private static Logger logger = LoggerFactory.getLogger(ShopInfoService.class);

    //商户信息thrift服务

    private static MyService.Iface shopInfoClient = (MyService.Iface) SpringBeanUtil.getBean("shopInfoClient");


//    getEnterpriseInfoByIdToOrder 获取企业 getStoreInfoByStoreIdToOrder 获取门店

    /**
     * 根据enterpriseId查询企业信息
     *
     * @param enterpriseId
     * @return
     */
    public List<EnterpriseInfoDTO> getEnterpriseInfo(String enterpriseId) {
        List<EnterpriseInfoDTO> enterpriseInfoLanguages = null;
        try {
            JSONObject param = new JSONObject();
            param.put("enterpriseId", enterpriseId);
            Response response = ClientUtil.clientSendData(shopInfoClient, "businessService", "getEnterpriseInfoByIdToOrder", param);
            if (response != null && response.getResponeCode().getValue() == 200) {
                JSONObject jsonObj = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                logger.info("获取企业信息结果{}", jsonObj.toJSONString());
                if (jsonObj != null && jsonObj.containsKey("data")) {
                    enterpriseInfoLanguages = new ArrayList<>();
                    JSONArray array = jsonObj.getJSONArray("data");
                    for (int i = 0; i < array.size(); i++) {
                        enterpriseInfoLanguages.add(JSONObject.toJavaObject(array.getJSONObject(i), EnterpriseInfoDTO.class));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取微信会员结果出错", e);
        }
        return enterpriseInfoLanguages;
    }

    /**
     * 根据storeId查询门店信息
     *
     * @param storeId
     * @return
     */
    public List<StoreInfoDTO> getStoreInfo(String storeId) {
        List<StoreInfoDTO> storeInfoLanguages = null;
        try {
            JSONObject param = new JSONObject();
            param.put("storeId", storeId);
            Response response = ClientUtil.clientSendData(shopInfoClient, "businessService", "getStoreInfoByStoreIdToOrder", param);
            if (response != null && response.getResponeCode().getValue() == 200) {
                JSONObject jsonObj = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                logger.info("获取门店信息结果{}", jsonObj.toJSONString());
                if (jsonObj != null && jsonObj.containsKey("data")) {
                    storeInfoLanguages = new ArrayList<>();
                    JSONArray array = jsonObj.getJSONArray("data");
                    for (int i = 0; i < array.size(); i++) {
                        storeInfoLanguages.add(JSONObject.toJavaObject(array.getJSONObject(i), StoreInfoDTO.class));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取门店信息结果出错", e);
        }
        return storeInfoLanguages;
    }

}
