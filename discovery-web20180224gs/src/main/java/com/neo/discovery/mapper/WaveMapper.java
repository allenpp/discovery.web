package com.neo.discovery.mapper;

import com.neo.discovery.vo.SpfChange;
import com.neo.discovery.vo.TongJiDto;
import com.neo.discovery.vo.Wave;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
@MapperScan
public interface WaveMapper {


    Integer insert(Wave wave);

    Integer updateWaveByMatchId(Wave wave);


    Wave selectWave(Wave wave);

    Wave selectFirstRecord(Wave wave);

    List<Wave> selectWaveList(Wave wave);

    List<Wave> selectGroupByMatchId(Wave wave);

    List<TongJiDto> selectAvgByGroupTime(TongJiDto wave);

    List<TongJiDto> selectMaxPeiLv(TongJiDto tongJiDto);


    List<TongJiDto> selectAvgByGroupTime();



}
