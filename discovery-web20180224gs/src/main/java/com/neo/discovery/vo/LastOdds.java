package com.neo.discovery.vo;

import java.util.Date;

/**
 * Created by liuyunpeng1 on 2017/9/25.
 */
public class LastOdds {


    private Float Win;
    private Float Draw;
    private Float Loss;
    private Integer ChangeWin;
    private Integer ChangeDraw;
    private Integer ChangeLoss;
    private Integer FirstOrLastOdds;
    private Date UpdateTime;

    public Float getWin() {
        return Win;
    }

    public void setWin(Float win) {
        Win = win;
    }

    public Float getDraw() {
        return Draw;
    }

    public void setDraw(Float draw) {
        Draw = draw;
    }

    public Float getLoss() {
        return Loss;
    }

    public void setLoss(Float loss) {
        Loss = loss;
    }

    public Integer getChangeWin() {
        return ChangeWin;
    }

    public void setChangeWin(Integer changeWin) {
        ChangeWin = changeWin;
    }

    public Integer getChangeDraw() {
        return ChangeDraw;
    }

    public void setChangeDraw(Integer changeDraw) {
        ChangeDraw = changeDraw;
    }

    public Integer getChangeLoss() {
        return ChangeLoss;
    }

    public void setChangeLoss(Integer changeLoss) {
        ChangeLoss = changeLoss;
    }

    public Integer getFirstOrLastOdds() {
        return FirstOrLastOdds;
    }

    public void setFirstOrLastOdds(Integer firstOrLastOdds) {
        FirstOrLastOdds = firstOrLastOdds;
    }

    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }
}
