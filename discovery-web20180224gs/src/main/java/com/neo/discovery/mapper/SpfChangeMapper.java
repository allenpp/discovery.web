package com.neo.discovery.mapper;

import com.neo.discovery.vo.BiFaDTo;
import com.neo.discovery.vo.PeiLvDTo;
import com.neo.discovery.vo.RaceTeam;
import com.neo.discovery.vo.SpfChange;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
@MapperScan
public interface SpfChangeMapper {


    Integer insert(SpfChange spfChange);


    SpfChange selectSpfChange(SpfChange spfChange);

    List<SpfChange> selectSpfChangeList(SpfChange spfChange);



}
