package com.neo.discovery.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 8/19/2017.
 */
public class TongJiDto {


    private long id;
    private Integer matchId;
    private Float buy_s1_avg;
    private Float buy_p1_avg;
    private Float buy_f1_avg;

    private Float sale_s1_avg;
    private Float sale_p1_avg;
    private Float sale_f1_avg;


    private String timeZone;
    private Date createTime;
    private Date matchDate;
    private String matchDateStr;
    private String home;
    private String away;
    private String leagueName;
    private String json;


    private Date beginTime;
    private Date endTime;

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

    public Float getBuy_s1_avg() {
        return buy_s1_avg;
    }

    public void setBuy_s1_avg(Float buy_s1_avg) {
        this.buy_s1_avg = buy_s1_avg;
    }

    public Float getBuy_p1_avg() {
        return buy_p1_avg;
    }

    public void setBuy_p1_avg(Float buy_p1_avg) {
        this.buy_p1_avg = buy_p1_avg;
    }

    public Float getBuy_f1_avg() {
        return buy_f1_avg;
    }

    public void setBuy_f1_avg(Float buy_f1_avg) {
        this.buy_f1_avg = buy_f1_avg;
    }

    public Float getSale_s1_avg() {
        return sale_s1_avg;
    }

    public void setSale_s1_avg(Float sale_s1_avg) {
        this.sale_s1_avg = sale_s1_avg;
    }

    public Float getSale_p1_avg() {
        return sale_p1_avg;
    }

    public void setSale_p1_avg(Float sale_p1_avg) {
        this.sale_p1_avg = sale_p1_avg;
    }

    public Float getSale_f1_avg() {
        return sale_f1_avg;
    }

    public void setSale_f1_avg(Float sale_f1_avg) {
        this.sale_f1_avg = sale_f1_avg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchDateStr() {
        return matchDateStr;
    }

    public void setMatchDateStr(String matchDateStr) {
        this.matchDateStr = matchDateStr;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
