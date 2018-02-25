package com.neo.discovery.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liuyunpeng1 on 2017/9/25.
 */
public class PeiLvDTo {


    private  Integer id;
    private  Integer matchId;
    private  BigDecimal win;
    private  BigDecimal draw;
    private  BigDecimal loss;
    private  BigDecimal rangWin;
    private  BigDecimal rangEq;
    private  BigDecimal rangFail;
    private  Integer changeWin;
    private  Integer changeDraw;
    private  Integer changeLoss;
    private  Integer firstOrLastOdds;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public BigDecimal getWin() {
        return win;
    }

    public void setWin(BigDecimal win) {
        this.win = win;
    }

    public BigDecimal getDraw() {
        return draw;
    }

    public void setDraw(BigDecimal draw) {
        this.draw = draw;
    }

    public BigDecimal getLoss() {
        return loss;
    }

    public void setLoss(BigDecimal loss) {
        this.loss = loss;
    }

    public Integer getChangeWin() {
        return changeWin;
    }

    public void setChangeWin(Integer changeWin) {
        this.changeWin = changeWin;
    }

    public Integer getChangeDraw() {
        return changeDraw;
    }

    public void setChangeDraw(Integer changeDraw) {
        this.changeDraw = changeDraw;
    }

    public Integer getChangeLoss() {
        return changeLoss;
    }

    public void setChangeLoss(Integer changeLoss) {
        this.changeLoss = changeLoss;
    }

    public Integer getFirstOrLastOdds() {
        return firstOrLastOdds;
    }

    public void setFirstOrLastOdds(Integer firstOrLastOdds) {
        this.firstOrLastOdds = firstOrLastOdds;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getRangWin() {
        return rangWin;
    }

    public void setRangWin(BigDecimal rangWin) {
        this.rangWin = rangWin;
    }

    public BigDecimal getRangEq() {
        return rangEq;
    }

    public void setRangEq(BigDecimal rangEq) {
        this.rangEq = rangEq;
    }

    public BigDecimal getRangFail() {
        return rangFail;
    }

    public void setRangFail(BigDecimal rangFail) {
        this.rangFail = rangFail;
    }
}
