package com.neo.discovery.util;

/**
 * Created by liuyunpeng1 on 2018/7/2.
 */
public enum OptStatus {



    PLACE("2", "�ɽ���"),//δ֧����
    OK("1", "�ɹ�");




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
