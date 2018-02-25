package com.neo.discovery.vo;

/**
 * Created by liuyunpeng1 on 2017/9/19.
 */
public class LocalFloat implements Comparable {


    public String spf;
    public Float value;
    public String raceDate;
    public String oneRaceNo;
    public String twoRaceNo;
    public String oneLeagueName;
    public String twoLeagueName;
    public String oneHomeTeam;
    public String oneVisitingTeam;
    public String twoHomeTeam;
    public String twoVisitingTeam;
    public String oneRaceResult;
    public String twoRaceResult;

    public Integer oneFullHomeTeamCourtscore;
    public Integer oneFullVisitingTeamCourtscore;

    public Integer twoFullHomeTeamCourtscore;
    public Integer twoFullVisitingTeamCourtscore;



    public LocalFloat(){

    }


    public LocalFloat(Float value,String spf){
        this.value = value;
        this.spf = spf;
    }




//    @Override
    public int compareTo(Object o) {
        if(this.value>((LocalFloat)o).value){
            return 1;
        }else if(this.value==((LocalFloat)o).value){
            return 0;
        }else if(this.value<((LocalFloat)o).value){
            return -1;
        }else{
            return -2;
        }
    }


    public String getSpf() {
        return spf;
    }

    public void setSpf(String spf) {
        this.spf = spf;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(String raceDate) {
        this.raceDate = raceDate;
    }

    public String getOneRaceNo() {
        return oneRaceNo;
    }

    public void setOneRaceNo(String oneRaceNo) {
        this.oneRaceNo = oneRaceNo;
    }

    public String getTwoRaceNo() {
        return twoRaceNo;
    }

    public void setTwoRaceNo(String twoRaceNo) {
        this.twoRaceNo = twoRaceNo;
    }

    public String getOneLeagueName() {
        return oneLeagueName;
    }

    public void setOneLeagueName(String oneLeagueName) {
        this.oneLeagueName = oneLeagueName;
    }

    public String getTwoLeagueName() {
        return twoLeagueName;
    }

    public void setTwoLeagueName(String twoLeagueName) {
        this.twoLeagueName = twoLeagueName;
    }

    public String getOneHomeTeam() {
        return oneHomeTeam;
    }

    public void setOneHomeTeam(String oneHomeTeam) {
        this.oneHomeTeam = oneHomeTeam;
    }

    public String getOneVisitingTeam() {
        return oneVisitingTeam;
    }

    public void setOneVisitingTeam(String oneVisitingTeam) {
        this.oneVisitingTeam = oneVisitingTeam;
    }

    public String getTwoHomeTeam() {
        return twoHomeTeam;
    }

    public void setTwoHomeTeam(String twoHomeTeam) {
        this.twoHomeTeam = twoHomeTeam;
    }

    public String getTwoVisitingTeam() {
        return twoVisitingTeam;
    }

    public void setTwoVisitingTeam(String twoVisitingTeam) {
        this.twoVisitingTeam = twoVisitingTeam;
    }

    public Integer getOneFullHomeTeamCourtscore() {
        return oneFullHomeTeamCourtscore;
    }

    public void setOneFullHomeTeamCourtscore(Integer oneFullHomeTeamCourtscore) {
        this.oneFullHomeTeamCourtscore = oneFullHomeTeamCourtscore;
    }

    public Integer getOneFullVisitingTeamCourtscore() {
        return oneFullVisitingTeamCourtscore;
    }

    public void setOneFullVisitingTeamCourtscore(Integer oneFullVisitingTeamCourtscore) {
        this.oneFullVisitingTeamCourtscore = oneFullVisitingTeamCourtscore;
    }

    public Integer getTwoFullHomeTeamCourtscore() {
        return twoFullHomeTeamCourtscore;
    }

    public void setTwoFullHomeTeamCourtscore(Integer twoFullHomeTeamCourtscore) {
        this.twoFullHomeTeamCourtscore = twoFullHomeTeamCourtscore;
    }

    public Integer getTwoFullVisitingTeamCourtscore() {
        return twoFullVisitingTeamCourtscore;
    }

    public void setTwoFullVisitingTeamCourtscore(Integer twoFullVisitingTeamCourtscore) {
        this.twoFullVisitingTeamCourtscore = twoFullVisitingTeamCourtscore;
    }


    public String getOneRaceResult() {
        return oneRaceResult;
    }

    public void setOneRaceResult(String oneRaceResult) {
        this.oneRaceResult = oneRaceResult;
    }

    public String getTwoRaceResult() {
        return twoRaceResult;
    }

    public void setTwoRaceResult(String twoRaceResult) {
        this.twoRaceResult = twoRaceResult;
    }
}
