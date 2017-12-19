package com.adatafun.airportshop.order.service.rpc;

import com.adatafun.airportshop.order.pojo.dto.StoreInfoDTO;
import com.adatafun.airportshop.order.pojo.vo.ProductForOrder;
import com.adatafun.airportshop.order.pojo.vo.ProductsForOrderDTO;
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
 * desc :菜品服务
 * Created by Lin on 2017/12/1.
 */
@Service
public class ShopProductService {
    private static Logger logger = LoggerFactory.getLogger(ShopProductService.class);

    //菜品信息thrift服务

    private static MyService.Iface shopProductClient = (MyService.Iface) SpringBeanUtil.getBean("shopProductClient");


    /**
     * 根据specificationId查询菜品规格信息(多语言)
     *{"msg":"操作成功","data":[{"specificationId":3,"productForOrderDTOList":[{"specificationId":3,"remnantInventory":100,"specificationPrice":35,"discountPrice":35,"discount":100,"language":"zh_cn","productCoverImg":"http://img2.imgtn.bdimg.com/it/u=1373277966,300441290&fm=27&gp=0.jpg","productName":"气吞山河龙虾饭【泰咖喱】","specificationName":"中份"},{"specificationId":3,"remnantInventory":100,"specificationPrice":35,"discountPrice":35,"discount":100,"language":"en","productCoverImg":"http://img2.imgtn.bdimg.com/it/u=1373277966,300441290&fm=27&gp=0.jpg","productName":"Thai curry lobster dinner [] grandeur","specificationName":"In a"}],"productId":5,"version":1},{"specificationId":4,"productForOrderDTOList":[{"specificationId":4,"remnantInventory":0,"specificationPrice":25,"discountPrice":22,"discount":100,"language":"zh_cn","productCoverImg":"http://img2.imgtn.bdimg.com/it/u=1373277966,300441290&fm=27&gp=0.jpg","productName":"鸡腿饭","specificationName":"小份"},{"specificationId":4,"remnantInventory":0,"specificationPrice":25,"discountPrice":22,"discount":100,"language":"en","productCoverImg":"http://img2.imgtn.bdimg.com/it/u=1373277966,300441290&fm=27&gp=0.jpg","productName":"Rice with Stewed Chicken Leg","specificationName":"In a"}],"productId":6,"version":2}],"status":10200}

     *{
     "productForOrderDTOList": [
     {
     "specificationId": 3,
     "remnantInventory": 100,
     "specificationPrice": 35,
     "discountPrice": 35,
     "discount": 100,
     "language": "zh_cn",
     "productCoverImg": "http://img2.imgtn.bdimg.com/it/u=1373277966,300441290&fm=27&gp=0.jpg",
     "productName": "气吞山河龙虾饭【泰咖喱】",
     "specificationName": "中份"
     },
     {
     "specificationId": 3,
     "remnantInventory": 100,
     "specificationPrice": 35,
     "discountPrice": 35,
     "discount": 100,
     "language": "en",
     "productCoverImg": "http://img2.imgtn.bdimg.com/it/u=1373277966,300441290&fm=27&gp=0.jpg",
     "productName": "Thai curry lobster dinner [] grandeur",
     "specificationName": "In a"
     }
     ]
     }
     *
     *
     *
     * @param specificationIds
     * @return
     */
    public List<ProductsForOrderDTO> getProductInfo(String specificationIds) {
        List<ProductsForOrderDTO> productForOrderLanguages = null;
        try {
            JSONObject param = new JSONObject();
            param.put("specificationIds", specificationIds);
            Response response = ClientUtil.clientSendData(shopProductClient, "businessService", "thrift-orderProductFullInfo", param);
            if (response != null && response.getResponeCode().getValue() == 200) {
                JSONObject jsonObj = ByteBufferUtil.convertByteBufferToJSON(response.getResponseJSON());
                logger.info("获取菜品信息结果{}", jsonObj.toJSONString());
                if (jsonObj != null && jsonObj.containsKey("data")) {
                    JSONArray array = jsonObj.getJSONArray("data");
                    productForOrderLanguages = new ArrayList<>();
                    for (int i = 0; i < array.size(); i++) {
                        ProductsForOrderDTO productsForOrderDTO = JSONObject.toJavaObject(array.getJSONObject(i), ProductsForOrderDTO.class);
                        productForOrderLanguages.add(productsForOrderDTO);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取菜品信息结果出错", e);
        }
        return productForOrderLanguages;
    }



//    public JSONObject
}
