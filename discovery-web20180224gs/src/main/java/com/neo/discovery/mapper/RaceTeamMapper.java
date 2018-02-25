package com.neo.discovery.mapper;

import com.neo.discovery.vo.BiFaDTo;
import com.neo.discovery.vo.PeiLvDTo;
import com.neo.discovery.vo.RaceTeam;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
@MapperScan
public interface RaceTeamMapper {


    Integer insert(RaceTeam raceTeam);

    Integer insertJdd(RaceTeam raceTeam);

    RaceTeam selectRaceTeam(RaceTeam raceTeam);

    void updateRaceTeamByJdd(RaceTeam raceTeam);

    Integer selectRaceTeamCount(RaceTeam raceTeam);

    List<RaceTeam> selectRaceTeamList(RaceTeam raceTeam);
    List<RaceTeam> selectCountDaYu6(RaceTeam raceTeam);

    List<RaceTeam> selectRaceTeamListByPage(RaceTeam raceTeam);

    List<RaceTeam> selectBaoLeng(RaceTeam raceTeam);

    Integer updateRaceTeam(RaceTeam raceTeam);

    Integer updateRaceTeamPoolResult(RaceTeam raceTeam);

    Integer updateRaceTeamBifen(RaceTeam raceTeam);

    Integer updateRaceTeamFirst(RaceTeam raceTeam);

    Integer updateRaceTeamZongHf(RaceTeam raceTeam);

    Integer updateRaceTeamAllField(RaceTeam raceTeam);

    Integer updateRaceTeamHisResult(RaceTeam raceTeam);


    public Integer insertBiFa(BiFaDTo biFaDTo);


    public Integer insertPeiLvChange(PeiLvDTo peiLvDTo);

}
