package com.neo.discovery.mapper;

import com.neo.discovery.vo.RangSpfChange;
import com.neo.discovery.vo.SpfChange;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
@MapperScan
public interface RangSpfChangeMapper {


    Integer insert(RangSpfChange rangSpfChange);


    RangSpfChange selectRangSpfChange(RangSpfChange rangSpfChange);

    List<RangSpfChange> selectRangSpfChangeList(RangSpfChange rangSpfChange);



}
