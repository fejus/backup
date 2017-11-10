package com.adatafun.airportshop.order.pojo.vo;

import com.adatafun.airportshop.order.pojo.po.OrdOrder;

import java.util.Date;
import java.util.List;

/**
 * desc  订单详情
 * Created by Lin on 2017/11/7.
 */
public class OrderDetailVO extends OrdOrder {

    private String language;//语言
    private String enterpriseName;//商户名称
    private String storeName;//门店名称
    private List<SubOrderDetailVO> subOrderProList;//子订单列表
    private BillInfoVO billInfo;//发票信息
    private String remarks;//备注
    private String storeRemarks;//门店备注

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<SubOrderDetailVO> getSubOrderProList() {
        return subOrderProList;
    }

    public void setSubOrderProList(List<SubOrderDetailVO> subOrderProList) {
        this.subOrderProList = subOrderProList;
    }

    public BillInfoVO getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(BillInfoVO billInfo) {
        this.billInfo = billInfo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStoreRemarks() {
        return storeRemarks;
    }

    public void setStoreRemarks(String storeRemarks) {
        this.storeRemarks = storeRemarks;
    }
}
