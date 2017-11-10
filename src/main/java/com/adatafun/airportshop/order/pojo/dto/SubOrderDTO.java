package com.adatafun.airportshop.order.pojo.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * desc : 子订单参数
 * Created by Lin on 2017/11/6.
 */
public class SubOrderDTO {
    @NotNull(message = "无菜品标识")
    private String productId;//产品编码
    @NotNull(message = "无规格标识")
    private String specificationId;//规格编码
    @NotNull(message = "数量不能为空")
    @Max(value = 20, message = "单品数量不能超过20")
    private Integer productNumber;//数量

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(String specificationId) {
        this.specificationId = specificationId;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }
}
