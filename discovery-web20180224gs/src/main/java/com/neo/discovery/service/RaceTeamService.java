package com.neo.discovery.service;

import com.neo.discovery.vo.RaceTeam;

import java.util.List;

/**
 * Created by Administrator on 8/20/2017.
 */
public interface RaceTeamService {


    public Integer insert(RaceTeam raceTeam);


    public List<RaceTeam> selecttRaceTeamList(RaceTeam raceTeam);

    public Integer selectRaceTeamCount(RaceTeam raceTeam);

    public List<RaceTeam> selectRaceTeamListByPage(RaceTeam raceTeam);


    public Integer  updateRaceTeam(RaceTeam raceTeam);

    public Integer  updateRaceTeamFirst(RaceTeam raceTeam);
    public Integer  updateRaceTeamZongHf(RaceTeam raceTeam);
    public Integer  updateRaceTeamAllField(RaceTeam raceTeam);
    public Integer  updateRaceTeamHisResult(RaceTeam raceTeam);
    public Integer  updateRaceTeamPoolResult(RaceTeam raceTeam);


    public void calculateMoney(List<RaceTeam> list);

    public void calculateMoneyBySelect(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect);

    public Float calculateMoneyBySelect(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateJinQiuShu(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateCaiBf(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateHengShuBi(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateMoneyByRandomSelect(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateBanQuanChang(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateRangqiu(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculatePingFuGeYi(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateCaiBFGeYi(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculatepingHfSheng(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateSelectBiFen(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateHunHe(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);

    public Float calculateFromSheng1BuyRang(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateLiLun5fen3PingMaiRangSheng(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateChengDuiJiSuan(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateShengBuySheng(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);
    public Float calculateGeDayBuDan(List<RaceTeam> onelist,List<RaceTeam> twoList,List<RaceTeam> threeList,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total);


        public List<RaceTeam>  selectBaoLeng(RaceTeam raceTeam);
        public List<RaceTeam>  selectCountDaYu6(RaceTeam raceTeam);


    public void updateRaceTeamByJdd(List<RaceTeam> list);


    /**
     * 获取jdd 已完成的比赛
     */
    public void selectHasDoneRaceTeamByJdd();




}
