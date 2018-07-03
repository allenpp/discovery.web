package com.neo.discovery.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 8/19/2017.
 */
public class OptFlow {


    private long id;
    private Integer matchId;
    private String hedgingId;
    private Float optPeiLv;
    private Float optAmount;

    private Date createTime;
    private Date matchDate;
    private String matchDateStr;
    private String home;
    private String away;
    private String leagueName;
    private String json;
    private String dufa;
    private String optType;
    private String betId;
    private String status;

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

    public Float getOptPeiLv() {
        return optPeiLv;
    }

    public void setOptPeiLv(Float optPeiLv) {
        this.optPeiLv = optPeiLv;
    }

    public Float getOptAmount() {
        return optAmount;
    }

    public void setOptAmount(Float optAmount) {
        this.optAmount = optAmount;
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

    public String getDufa() {
        return dufa;
    }

    public void setDufa(String dufa) {
        this.dufa = dufa;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
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


    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getHedgingId() {
        return hedgingId;
    }

    public void setHedgingId(String hedgingId) {
        this.hedgingId = hedgingId;
    }
}
