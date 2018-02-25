package com.neo.discovery.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 8/19/2017.
 */
public class RangSpfChange {


    private long id;
    private Integer matchId;
    private Integer rangscore;
    private Float rangwin;
    private Float rangequals;
    private Float rangfail;
    private Date publishtime;
    private String rangwinfx;
    private String rangequalsfx;
    private String rangfailfx;
    private Date createTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getRangscore() {
        return rangscore;
    }

    public void setRangscore(Integer rangscore) {
        this.rangscore = rangscore;
    }

    public Float getRangwin() {
        return rangwin;
    }

    public void setRangwin(Float rangwin) {
        this.rangwin = rangwin;
    }

    public Float getRangequals() {
        return rangequals;
    }

    public void setRangequals(Float rangequals) {
        this.rangequals = rangequals;
    }

    public Float getRangfail() {
        return rangfail;
    }

    public void setRangfail(Float rangfail) {
        this.rangfail = rangfail;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public String getRangwinfx() {
        return rangwinfx;
    }

    public void setRangwinfx(String rangwinfx) {
        this.rangwinfx = rangwinfx;
    }

    public String getRangequalsfx() {
        return rangequalsfx;
    }

    public void setRangequalsfx(String rangequalsfx) {
        this.rangequalsfx = rangequalsfx;
    }

    public String getRangfailfx() {
        return rangfailfx;
    }

    public void setRangfailfx(String rangfailfx) {
        this.rangfailfx = rangfailfx;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
