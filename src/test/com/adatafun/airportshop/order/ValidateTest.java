package com.adatafun.airportshop.order;

import com.adatafun.airportshop.order.pojo.dto.H5OrderDTO;
import com.adatafun.airportshop.order.pojo.dto.SubOrderDTO;
import com.adatafun.utils.data.BeanValidateUtil;
import org.junit.Test;

import java.util.ArrayList;
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
}
