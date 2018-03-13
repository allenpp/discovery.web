package com.neo.discovery.mapper;

import com.neo.discovery.vo.SpfChange;
import com.neo.discovery.vo.Wave;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
@MapperScan
public interface WaveMapper {


    Integer insert(Wave wave);


    Wave selectWave(Wave wave);

    List<Wave> selectWaveList(Wave wave);

    List<Wave> selectGroupByMatchId(Wave wave);



}
