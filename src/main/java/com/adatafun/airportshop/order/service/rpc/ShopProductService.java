package com.adatafun.airportshop.order.service.rpc;

import com.adatafun.airportshop.order.pojo.vo.ProductForOrder;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.utils.ByteBufferUtil;
import com.wyun.utils.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * desc :菜品服务
 * Created by Lin on 2017/12/1.
 */
@Service
public class ShopProductService {
    private static Logger logger = LoggerFactory.getLogger(ShopProductService.class);

    //菜品信息thrift服务

    private static MyService.Iface shopProductClient = (MyService.Iface) SpringBeanUtil.getBean("shopProductClient");


    /**
     * 根据specificationId查询菜品规格信息
     *
     * @param specificationIds
     * @return
     */
    public List<List<ProductForOrder>> getProductInfo(String specificationIds) {
        List<List<ProductForOrder>> lists = null;
        try {
            JSONObject param = new JSONObject();
            param.put("specificationIds", specificationIds);
            Response response = ClientUtil.clientSendData(shopProductClient, "businessService", "orderProductFullInfo", param);
            if (response != null && response.getResponeCode().getValue() == 200) {
                JSONObject jsonObj = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                logger.info("获取菜品信息结果{}", jsonObj.toJSONString());
                if (jsonObj != null && jsonObj.containsKey("data")) {
                }
            }
        } catch (Exception e) {
            logger.error("获取菜品信息结果出错", e);
        }
        return lists;
    }



//    public JSONObject
}
