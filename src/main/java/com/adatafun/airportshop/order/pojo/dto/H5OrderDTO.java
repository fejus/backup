package com.adatafun.airportshop.order.pojo.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * desc : 小程序下单参数
 * Created by Lin on 2017/11/6.
 */
public class H5OrderDTO implements Serializable {
    @NotNull(message = "无语言标识")
    private String language;//语言
    @NotNull(message = "无商户标识")
    private String enterpriseId;//商户编码
    @NotNull(message = "无门店标识")
    private String storeId;//门店编码
    @NotNull(message = "无渠道标示")
    private String channelType;
    @NotNull(message = "桌号不能为空")
    private String deskNumber;//桌号
    @NotNull(message = "使用人数不能为空")
    @Max(value = 10, message = "最大人数不能超多10人")
    private Integer useNumber;//使用人数
    @Min(value = 1, message = "至少选中一个菜品")
    private Integer subOrderNumber;//子订单数量
    private String formId;//小程序表单formID
    private List<SubOrderDTO> subOrderProList;//子订单列表
    private OrderBillInfoDTO billInfo;//发票信息
    private String remarks;//备注


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDeskNumber() {
        return deskNumber;
    }

    public void setDeskNumber(String deskNumber) {
        this.deskNumber = deskNumber;
    }

    public Integer getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(Integer useNumber) {
        this.useNumber = useNumber;
    }

    public Integer getSubOrderNumber() {
        return subOrderNumber;
    }

    public void setSubOrderNumber(Integer subOrderNumber) {
        this.subOrderNumber = subOrderNumber;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<SubOrderDTO> getSubOrderProList() {
        return subOrderProList;
    }

    public void setSubOrderProList(List<SubOrderDTO> subOrderProList) {
        this.subOrderProList = subOrderProList;
    }

    public OrderBillInfoDTO getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(OrderBillInfoDTO billInfo) {
        this.billInfo = billInfo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
