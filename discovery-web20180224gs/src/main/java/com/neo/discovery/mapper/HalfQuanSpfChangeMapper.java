package com.neo.discovery.mapper;

import com.neo.discovery.vo.HalfQuanSpfChange;
import com.neo.discovery.vo.RangSpfChange;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
@MapperScan
public interface HalfQuanSpfChangeMapper {


    Integer insert(HalfQuanSpfChange halfQuanSpfChange);


    HalfQuanSpfChange selectHalfQuanSpfChange(HalfQuanSpfChange halfQuanSpfChange);

    List<HalfQuanSpfChange> selectHalfQuanSpfChangeList(HalfQuanSpfChange halfQuanSpfChange);



}
