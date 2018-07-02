package com.neo.discovery.util;

/**
 * Created by liuyunpeng1 on 2018/7/2.
 */
public enum OptStatus {



    PLACE("2", "成交中"),//未支付等
    OK("1", "成功");




    private  String optStatus;
    private  String desc;


    private OptStatus(String optStatus, String desc) {
        this.optStatus = optStatus;
        this.desc = desc;
    }

    public String getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(String optStatus) {
        this.optStatus = optStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
