package com.neo.discovery.vo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
public class SpfChange {


    private long id;
    private Integer matchId;
    private Float win;
    private Float equals;
    private Float fail;
    private Date publishtime;
    private String winfx;
    private String equalsfx;
    private String failfx;
    private Date createTime;


    public long getId() {
        return id;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Float getWin() {
        return win;
    }

    public void setWin(Float win) {
        this.win = win;
    }

    public Float getEquals() {
        return equals;
    }

    public void setEquals(Float equals) {
        this.equals = equals;
    }

    public Float getFail() {
        return fail;
    }

    public void setFail(Float fail) {
        this.fail = fail;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public String getWinfx() {
        return winfx;
    }

    public void setWinfx(String winfx) {
        this.winfx = winfx;
    }

    public String getEqualsfx() {
        return equalsfx;
    }

    public void setEqualsfx(String equalsfx) {
        this.equalsfx = equalsfx;
    }

    public String getFailfx() {
        return failfx;
    }

    public void setFailfx(String failfx) {
        this.failfx = failfx;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        if (null != createTime) {
            String temp = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(temp);
            String str = "";
            try {
                str = sdf.format(createTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return createTime;
        } else {
            return null;
        }
    }

}
