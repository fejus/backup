package com.adatafun.airportshop.order.pojo.dto;

/**
 * desc :
 * Created by Lin on 2017/12/4.
 */
public class EnterpriseInfoDTO {
    /**
     * 自增主键id(企业id)
     * enterprise_id 自增主键id(企业id)
     */
    private Long enterpriseId;
    /**
     * 法人姓名
     * register_name 法人姓名
     */
    private String registerName;
    /**
     * 法人身份证
     * id_card 法人身份证
     */
    private String idCard;
    /**
     * 上传证件
     * id_card_img 上传证件
     */
    private String idCardImg;
    /**
     * 对接人姓名
     * access_name 对接人姓名
     */
    private String accessName;
    /**
     * 对接人电话
     * access_telephone 对接人电话
     */
    private String accessTelephone;
    /**
     * 营业执照号
     * license_number 营业执照号
     */
    private String licenseNumber;
    /**
     * 营业执照
     * license 营业执照
     */
    private String license;

    private String companyName;

    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardImg() {
        return idCardImg;
    }

    public void setIdCardImg(String idCardImg) {
        this.idCardImg = idCardImg;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public String getAccessTelephone() {
        return accessTelephone;
    }

    public void setAccessTelephone(String accessTelephone) {
        this.accessTelephone = accessTelephone;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
