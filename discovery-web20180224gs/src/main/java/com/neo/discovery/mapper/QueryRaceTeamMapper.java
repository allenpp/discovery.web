package com.neo.discovery.mapper;

import com.neo.discovery.vo.BiFaDTo;
import com.neo.discovery.vo.PeiLvDTo;
import com.neo.discovery.vo.RaceTeam;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
@MapperScan
public interface QueryRaceTeamMapper {




    Integer selectCount(RaceTeam raceTeam);

    List<RaceTeam> selectRaceTeamByRandomRateCount(RaceTeam raceTeam);

    Integer selectBiFen(RaceTeam raceTeam);

    List<RaceTeam> selectRaceTeamByRandomRate(RaceTeam raceTeam);

}
