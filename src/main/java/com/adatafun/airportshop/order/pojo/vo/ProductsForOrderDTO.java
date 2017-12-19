package com.adatafun.airportshop.order.pojo.vo;

import java.util.List;

/**
 * Created by tiecheng on 2017/12/1.
 */
public class ProductsForOrderDTO {

    private Long specificationId;

    private Long productId;

    private Integer version;

    private List<ProductForOrder> productForOrderDTOList;

    public Long getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Long specificationId) {
        this.specificationId = specificationId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<ProductForOrder> getProductForOrderDTOList() {
        return productForOrderDTOList;
    }

    public void setProductForOrderDTOList(List<ProductForOrder> productForOrderDTOList) {
        this.productForOrderDTOList = productForOrderDTOList;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
