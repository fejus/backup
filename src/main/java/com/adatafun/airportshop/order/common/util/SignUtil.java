package com.adatafun.airportshop.order.common.util;

import java.util.*;

/**
 * desc :签名工具类
 * Created by Lin on 2017/8/8.
 */
public class SignUtil {
    /**
     * ASCII字典序排序
     *
     * @param parameters 参数集合
     * @param key        密钥
     * @return
     */
    public static String ASC2Sort(SortedMap<String, Object> parameters, String key) {
        //parameters参数为请求参数的map映射，不包括key
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        //System.out.println(sb.toString());
        return sb.toString();
    }


    public static void main(String[] args) {
        SortedMap<String, Object> map = new TreeMap<>();
        map.put("a", "b");
        map.put("b", "b");
        map.put("c", "b");
        map.put("d", "b");
        map.put("e", "b");
        map.put("a", "b");

        System.out.println(MD5Util.MD5(ASC2Sort(map, "fengshu2017")));


    }


}
