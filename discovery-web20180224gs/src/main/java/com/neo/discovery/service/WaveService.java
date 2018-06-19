package com.neo.discovery.service;

import com.neo.discovery.vo.HalfQuanSpfChange;
import com.neo.discovery.vo.RangSpfChange;
import com.neo.discovery.vo.SpfChange;
import com.neo.discovery.vo.Wave;

import java.util.List;

/**
 * Created by Administrator on 8/20/2017.
 */
public interface WaveService {

    Integer insert(Wave wave);


    Wave selectWave(Wave wave);

    List<Wave> selectWaveList(Wave wave);

    List<Wave> selectGroupByMatchId(Wave wave);


    Integer updateWaveByMatchId(Wave wave);




}
