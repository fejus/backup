package com.adatafun.airportshop.order.pojo.dto;


/**
 * Created by jiangbo on 2017/12/4.
 */
public class StoreInfoDTO {
    /**
     * 门店名称
     * store_name 门店名称
     */
    private String storeName;
    /**
     * 所属公司
     */
    private String companyName;

    /**
     * 菜系
     * cuisine 菜系
     */
    private String cuisine;
    /**
     * 出菜时间
     * gen_food_time 出菜时间
     */
    private String genFoodTime;
    /**
     * 人均
     * per_capita 人均
     */
    private String perCapita;
    /**
     * 位置信息
     * location_info 位置信息
     */
    private String locationInfo;

    /**
     * 餐厅类型
     * store_type 餐厅类型
     */
    private String storeType;
    /**
     * 所在机场
     * airport 所在机场
     */
    private String airport;
    /**
     * 广告语
     * ad 广告语
     */
    private String ad;
    /**
     * 主键自增id
     * store_id 主键自增id
     */
    private Long storeId;
    /**
     * 企业id
     * enterprise_id 企业id
     */
    private Long enterpriseId;
    /**
     * 营业开始时间
     * office_start_time 营业开始时间
     */
    private String officeStartTime;
    /**
     * 营业结束时间
     * office_end_time 营业结束时间
     */
    private String officeEndTime;
    /**
     * 位置指引图
     * location_point_img 位置指引图
     */
    private String locationPointImg;
    /**
     * 最近登机口
     * near_gate 最近登机口
     */
    private String nearGate;
    /**
     * 适用登机口
     * apply_gate 适用登机口
     */
    private String applyGate;
    /**
     * 货币类型
     * coinType 货币类型
     */
    private String coinType;
    /**
     * 客服电话
     * customServiceTelephone 客服电话
     */
    private String customServiceTelephone;
    /**
     * 所在层数
     * floor 所在层数
     */
    private String floor;
    /**
     * 是否支持发票
     * bill 是否支持发票 0 : 是 1：否
     */
    private int isBill;
    /**
     * 状态
     * status 状态 0 : 已开通 1：未开通
     */
    private int status;
    /**
     * 堂食开关
     * eat_switch 0 : 开 1：关
     */
    private int eatSwitch;
    /**
     * 美食街模式
     * cateStreetModel 美食街模式 0 : 是 1：否
     */
    private int isCateStreetModel;
    /**
     * 支持产品
     * support_product 支持产品
     */
    private String supportProduct;
    /**
     * 门店背景图图片
     * store_background_img 门店背景图图片
     */
    private String storeBackgroundImg;
    /**
     * 门店logo
     * store_logo 门店logo
     */
    private String storeLogo;
    /**
     * 小程序背景图
     * small_backgroundImg 小程序背景图
     */
    private String smallBackgroundImg;
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getGenFoodTime() {
        return genFoodTime;
    }

    public void setGenFoodTime(String genFoodTime) {
        this.genFoodTime = genFoodTime;
    }

    public String getPerCapita() {
        return perCapita;
    }

    public void setPerCapita(String perCapita) {
        this.perCapita = perCapita;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getOfficeStartTime() {
        return officeStartTime;
    }

    public void setOfficeStartTime(String officeStartTime) {
        this.officeStartTime = officeStartTime;
    }

    public String getOfficeEndTime() {
        return officeEndTime;
    }

    public void setOfficeEndTime(String officeEndTime) {
        this.officeEndTime = officeEndTime;
    }

    public String getLocationPointImg() {
        return locationPointImg;
    }

    public void setLocationPointImg(String locationPointImg) {
        this.locationPointImg = locationPointImg;
    }

    public String getNearGate() {
        return nearGate;
    }

    public void setNearGate(String nearGate) {
        this.nearGate = nearGate;
    }

    public String getApplyGate() {
        return applyGate;
    }

    public void setApplyGate(String applyGate) {
        this.applyGate = applyGate;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getCustomServiceTelephone() {
        return customServiceTelephone;
    }

    public void setCustomServiceTelephone(String customServiceTelephone) {
        this.customServiceTelephone = customServiceTelephone;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getIsBill() {
        return isBill;
    }

    public void setIsBill(int isBill) {
        this.isBill = isBill;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEatSwitch() {
        return eatSwitch;
    }

    public void setEatSwitch(int eatSwitch) {
        this.eatSwitch = eatSwitch;
    }

    public int getIsCateStreetModel() {
        return isCateStreetModel;
    }

    public void setIsCateStreetModel(int isCateStreetModel) {
        this.isCateStreetModel = isCateStreetModel;
    }

    public String getSupportProduct() {
        return supportProduct;
    }

    public void setSupportProduct(String supportProduct) {
        this.supportProduct = supportProduct;
    }

    public String getStoreBackgroundImg() {
        return storeBackgroundImg;
    }

    public void setStoreBackgroundImg(String storeBackgroundImg) {
        this.storeBackgroundImg = storeBackgroundImg;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getSmallBackgroundImg() {
        return smallBackgroundImg;
    }

    public void setSmallBackgroundImg(String smallBackgroundImg) {
        this.smallBackgroundImg = smallBackgroundImg;
    }
}

