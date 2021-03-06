package com.neo.discovery.service;

import com.neo.discovery.vo.OptFlow;
import com.neo.discovery.vo.Wave;

import java.util.List;

/**
 * Created by Administrator on 8/20/2017.
 */
public interface OptFlowService {

    Integer insert(OptFlow optFlow);

    OptFlow selectOptFlow(OptFlow optFlow);

    List<OptFlow> findNoHedgOptFlow(OptFlow optFlow);

    List<OptFlow> selectOptFlowList(OptFlow optFlow);

    List<OptFlow> selectGroupByMatchId(OptFlow optFlow);

    Integer updateOptFlowByBetId(OptFlow optFlow);

    OptFlow isBettingRecord(OptFlow optFlow);


}
