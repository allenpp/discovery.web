package com.neo.discovery.vo;

import java.util.Date;

/**
 * Created by Administrator on 8/19/2017.
 */
public class ShouldDoOpt {


    private long id;
    private Integer matchId;
    private Float optPeiLv;
    private Float optAmount;

    private String optType;


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

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }
}
