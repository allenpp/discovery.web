package com.neo.discovery.service;

import com.neo.discovery.vo.RaceTeam;

import java.util.List;

/**
 * Created by Administrator on 8/20/2017.
 */
public interface QueryRaceTeamService {



    public List<RaceTeam> selecttRaceTeamList(RaceTeam raceTeam);

    public List<RaceTeam> selectRaceTeamByRandomRateCount(RaceTeam raceTeam);


    public List<RaceTeam>  selectBaoLeng(RaceTeam raceTeam);


    public void findYingLiRate();
    public void findYingLiRateBySelect();
    public void tongjiBiFenRate();



}
