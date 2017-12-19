package com.adatafun.airportshop.order;

import com.adatafun.airportshop.order.pojo.dto.H5OrderDTO;
import com.adatafun.airportshop.order.pojo.dto.SubOrderDTO;
import com.adatafun.utils.common.DateUtils;
import com.adatafun.utils.common.JWTUtil;
import com.adatafun.utils.data.BeanValidateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * desc :
 * Created by Lin on 2017/11/9.
 */
public class ValidateTest {
    @Test
    public void test1() {
        H5OrderDTO orderInfo = new H5OrderDTO();
        orderInfo.setUseNumber(99);
        List<SubOrderDTO> subOrderDTOS = new ArrayList<>();
        SubOrderDTO orderDTO = new SubOrderDTO();
        orderInfo.setSubOrderProList(subOrderDTOS);


        System.out.println("single:"+BeanValidateUtil.getValidateMessage(orderInfo));

        List<String> messages = BeanValidateUtil.getAllValidateMessage(orderInfo);
        for (String message : messages) {
            System.out.println(message);
        }

    }

    @Test
    public void test2() {
        System.out.println(new Date().getTime() / 1000);
    }


    @Test
    public void test3() {
        System.out.println(JWTUtil.createJWT("ac011fffea3d48f49d3a31ef3ca33e34", "", 999999999, "adatafun"));
    }

    @Test
    public void test4() throws ParseException {
        System.out.println(new Date().getTime() / 1000);
        System.out.println((DateUtils.nextDay(DateUtils.getDate("yyyy-MM-dd")).getTime() - new Date().getTime()) / 1000 );

    }

    @Test
    public void test5(){

    }
}
