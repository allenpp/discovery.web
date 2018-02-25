package com.neo.discovery.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 8/19/2017.
 */
public class HalfQuanSpfChange {


    private long id;
    private Integer matchId;
    private Float ss;
    private Float ps;
    private Float fs;
    private Float sp;
    private Float pp;
    private Float fp;
    private Float sf;
    private Float pf;
    private Float ff;
    private Date publishtime;
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

    public Float getSs() {
        return ss;
    }

    public void setSs(Float ss) {
        this.ss = ss;
    }

    public Float getPs() {
        return ps;
    }

    public void setPs(Float ps) {
        this.ps = ps;
    }

    public Float getFs() {
        return fs;
    }

    public void setFs(Float fs) {
        this.fs = fs;
    }

    public Float getSp() {
        return sp;
    }

    public void setSp(Float sp) {
        this.sp = sp;
    }

    public Float getPp() {
        return pp;
    }

    public void setPp(Float pp) {
        this.pp = pp;
    }

    public Float getFp() {
        return fp;
    }

    public void setFp(Float fp) {
        this.fp = fp;
    }

    public Float getSf() {
        return sf;
    }

    public void setSf(Float sf) {
        this.sf = sf;
    }

    public Float getPf() {
        return pf;
    }

    public void setPf(Float pf) {
        this.pf = pf;
    }

    public Float getFf() {
        return ff;
    }

    public void setFf(Float ff) {
        this.ff = ff;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
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
