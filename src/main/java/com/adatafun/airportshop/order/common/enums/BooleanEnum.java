package com.adatafun.airportshop.order.common.enums;

/**
 * desc :
 * Created by Lin on 2017/7/27.
 */
public enum  BooleanEnum {
    YES(1,"是"),
    NO(0,"否");

    private Integer value;
    private String display;

    public Integer value() {
        return this.value;
    }

    public String display() {
        return this.display;
    }

    private BooleanEnum(Integer value, String display) {
        this.value = value;
        this.display = display;
    }

    public static String getDisplayByValue(Integer value) {
        BooleanEnum booleanEnum = getEnumByValue(value);
        return booleanEnum !=null ? booleanEnum.display() : null;
    }
    public static BooleanEnum getEnumByValue(Integer value) {

        for(BooleanEnum booleanEnum :  values()){
            if(booleanEnum.value()== value){
                return  booleanEnum;
            }
        }
        return null;
    }
}
