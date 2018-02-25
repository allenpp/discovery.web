package com.neo.discovery.service;

import com.neo.discovery.vo.HalfQuanSpfChange;
import com.neo.discovery.vo.RaceTeam;
import com.neo.discovery.vo.RangSpfChange;
import com.neo.discovery.vo.SpfChange;

import java.util.List;

/**
 * Created by Administrator on 8/20/2017.
 */
public interface ChangeService {


    public Integer insertSpfChange(SpfChange spfChange);

    public Integer insertRangSpfChange(RangSpfChange rangSpfChange);

    public Integer insertHalfQuanChange(HalfQuanSpfChange halfQuanSpfChange);


    public SpfChange selectSpfChange(SpfChange spfChange);

    public  RangSpfChange selectRangSpfChange(RangSpfChange rangSpfChange);

    public  HalfQuanSpfChange  selectHalfQuanSpfChange(HalfQuanSpfChange halfQuanSpfChange);

    public List<SpfChange> selectSpfChangeList(SpfChange spfChange);

    public List<RangSpfChange> selectRangSpfChangeList(RangSpfChange rangSpfChange);

    public List<HalfQuanSpfChange> selectHalfQuanSpfChangeList(HalfQuanSpfChange halfQuanSpfChange);






}
