package com.neo.discovery.util;

/**
 * Created by liuyunpeng1 on 2018/7/2.
 */
public enum OptType {



    BUY_S("buy_s", "��ʼ"),//δ֧����
    BUY_P("buy_p", "��ʼ"),//δ֧����
    BUY_F("buy_f", "��ʼ"),//δ֧����
    SALE_S("sale_s", "��ʼ"),//δ֧����
    SALE_P("sale_p", "��ʼ"),//δ֧����
    SALE_F("sale_f", "��ʼ");//δ֧����





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
