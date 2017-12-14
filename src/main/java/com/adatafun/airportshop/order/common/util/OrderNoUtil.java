package com.adatafun.airportshop.order.common.util;

import com.adatafun.airportshop.order.common.enums.ChannelType;
import com.adatafun.airportshop.order.common.enums.OrderType;
import com.adatafun.utils.common.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * desc :
 * Created by Lin on 2017/11/27.
 */
public class OrderNoUtil {
    private static Logger logger = LoggerFactory.getLogger(OrderNoUtil.class);

    /**
     * 生成订单编码 时间戳（10位）+订单类型（1）+下单渠道（1）+随机数（4）
     *
     * @param orderType
     * @param channelType
     * @return
     */
    public static String createOrderNo(OrderType orderType, ChannelType channelType) {
        StringBuilder sb = new StringBuilder();
        sb.append(new Date().getTime() / 1000);
        sb.append(orderType.value());
        sb.append(channelType.value());
        sb.append(createRandomNumber(4));
        return sb.toString();
    }


    /**
     * 生成取餐编码
     * 规则：每个商户 一天内不重复的随机四位数
     *
     * @param enterpriseId
     * @return
     */
    public static String createGetFoodNumber(String enterpriseId) {
        String getFoodNumber = null;
        try {
            getFoodNumber = createRandomNumber(4);
            while (true) {
                String lockValue = null;
                try {
                    //先获得分布式锁 锁最多持有10秒
                    lockValue = RedisUtil.getLock(enterpriseId + ":" + getFoodNumber, 10);
                    if (null != lockValue) {
                        //redis set 判断是否重复
                        if (RedisUtil.sismember(enterpriseId, getFoodNumber)) {
                            System.out.println(getFoodNumber + "已存在");
                            //重复则重新生成
                            getFoodNumber = createRandomNumber(4);
                        } else {
                            //放入集合中
                            RedisUtil.sadd(enterpriseId, getFoodNumber);
                            //长度为1 则设置过期时间为当天有效
                            if (1 == RedisUtil.scard(enterpriseId)) {
                                //计算key的过期时间
                                Long nowTime = new Date().getTime();
                                Long nextDayTime = DateUtils.nextDay(DateUtils.getDate("yyyy-MM-dd")).getTime();
                                int timeout = Long.valueOf((nextDayTime - nowTime) / 1000).intValue();
                                RedisUtil.expire(enterpriseId, timeout);
                            }
                            break;
                        }
                    } else {
                        //没有拿到锁 重新生成随机数
                        getFoodNumber = createRandomNumber(4);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != lockValue) {
                        //释放锁
                        RedisUtil.releaseLock(enterpriseId + ":" + getFoodNumber, lockValue);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取取餐码失败", e);
        }
        return getFoodNumber;
    }

    /**
     * 生成n位的随机数
     *
     * @param n
     * @return
     */
    public static String createRandomNumber(int n) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();

    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            //System.out.println(createOrderNo(OrderType.TS_ORDER, ChannelType.MINI_PROGRAM));
        }

        //并发测试
        Set sets = Collections.synchronizedSet(new HashSet());
        List<String> list = Collections.synchronizedList(new ArrayList());
        String enterpriseId = Math.random() + "";
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String getFoodNumber = createGetFoodNumber(enterpriseId);
                    if (null != getFoodNumber) {
                        sets.add(getFoodNumber);
                        list.add(getFoodNumber);
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(30 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("set size:" + sets.size());
        System.out.println("list size:" + list.size());

    }
}
