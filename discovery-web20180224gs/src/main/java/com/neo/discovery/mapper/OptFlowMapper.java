package com.neo.discovery.mapper;

import com.neo.discovery.vo.OptFlow;
import com.neo.discovery.vo.TongJiDto;
import com.neo.discovery.vo.Wave;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
@MapperScan
public interface OptFlowMapper {

    Integer insert(OptFlow optFlow);

    OptFlow selectOptFlow(OptFlow optFlow);

    OptFlow isBettingRecord(OptFlow optFlow);

    List<OptFlow> selectOptFlowList(OptFlow optFlow);

    List<OptFlow> selectGroupByMatchId(OptFlow optFlow);

    Integer updateOptFlowByBetId(OptFlow optFlow);

    Integer updateOptFlow(OptFlow optFlow);



}
