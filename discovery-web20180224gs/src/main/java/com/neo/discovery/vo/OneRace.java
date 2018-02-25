package com.neo.discovery.vo;

import java.util.List;

/**
 * Created by liuyunpeng1 on 2017/11/30.
 */
public class OneRace {


    private Float win;
    private Float rangFail;
    private Float currPeiLv;
    private String raceNo;
    private List<String> selectList;
//    private List<OneRace> selectRaces;

    private Integer result;

    public Float getWin() {
        return win;
    }

    public void setWin(Float win) {
        this.win = win;
    }

    public Float getRangFail() {
        return rangFail;
    }

    public void setRangFail(Float rangFail) {
        this.rangFail = rangFail;
    }

    public List<String> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<String> selectList) {
        this.selectList = selectList;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }


    public String getRaceNo() {
        return raceNo;
    }

    public void setRaceNo(String raceNo) {
        this.raceNo = raceNo;
    }

    public Float getCurrPeiLv() {
        return currPeiLv;
    }

    public void setCurrPeiLv(Float currPeiLv) {
        this.currPeiLv = currPeiLv;
    }
}
