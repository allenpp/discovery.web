package com.neo.discovery.service;

import com.neo.discovery.vo.*;

import java.util.List;

/**
 * Created by Administrator on 8/20/2017.
 */
public interface WaveService {

    Integer insert(Wave wave);


    Wave selectWave(Wave wave);

    List<Wave> selectWaveList(Wave wave);

    List<TongJiDto> selectAvgByGroupTime(TongJiDto wave);

    List<Wave> selectGroupByMatchId(Wave wave);


    List<Wave> selectMaxPeiLv(Wave wave);


    Integer updateWaveByMatchId(Wave wave);




}
