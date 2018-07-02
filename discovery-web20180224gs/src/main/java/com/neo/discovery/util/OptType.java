package com.neo.discovery.util;

/**
 * Created by liuyunpeng1 on 2018/7/2.
 */
public enum OptType {



    BUY_S("buy_s", "初始"),//未支付等
    BUY_P("buy_p", "初始"),//未支付等
    BUY_F("buy_f", "初始"),//未支付等
    SALE_S("sale_s", "初始"),//未支付等
    SALE_P("sale_p", "初始"),//未支付等
    SALE_F("sale_f", "初始");//未支付等





    private  String optType;
    private  String desc;


    private OptType(String optType, String desc) {
        this.optType = optType;
        this.desc = desc;
    }


    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
