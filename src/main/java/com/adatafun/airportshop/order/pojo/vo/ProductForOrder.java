package com.adatafun.airportshop.order.pojo.vo;

/**
 * desc :
 * Created by Lin on 2017/12/1.
 */
public class ProductForOrder {
    private Long productId;

    private Long specificationId;

    private String specificationName;

    private String productName;

    private Integer specificationPrice;

    private Integer discount;

    private Integer discountPrice;

    private Integer remnantInventory;

    private String productCoverImg;

    private String language;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Long specificationId) {
        this.specificationId = specificationId;
    }

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSpecificationPrice() {
        return specificationPrice;
    }

    public void setSpecificationPrice(Integer specificationPrice) {
        this.specificationPrice = specificationPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getRemnantInventory() {
        return remnantInventory;
    }

    public void setRemnantInventory(Integer remnantInventory) {
        this.remnantInventory = remnantInventory;
    }

    public String getProductCoverImg() {
        return productCoverImg;
    }

    public void setProductCoverImg(String productCoverImg) {
        this.productCoverImg = productCoverImg;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
