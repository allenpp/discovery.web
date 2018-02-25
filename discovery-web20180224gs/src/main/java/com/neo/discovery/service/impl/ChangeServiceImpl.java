package com.neo.discovery.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.discovery.mapper.HalfQuanSpfChangeMapper;
import com.neo.discovery.mapper.RaceTeamMapper;
import com.neo.discovery.mapper.RangSpfChangeMapper;
import com.neo.discovery.mapper.SpfChangeMapper;
import com.neo.discovery.service.ChangeService;
import com.neo.discovery.service.RaceTeamService;
import com.neo.discovery.util.HttpClientUtils;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 8/20/2017.
 */
@Service("changeService")
public class ChangeServiceImpl implements ChangeService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ChangeServiceImpl.class);
    @Resource
    private SpfChangeMapper spfChangeMapper;
    @Resource
    private RangSpfChangeMapper rangSpfChangeMapper;
    @Resource
    private HalfQuanSpfChangeMapper halfQuanSpfChangeMapper;

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class,timeout=1,isolation= Isolation.DEFAULT)
    public Integer insert(RaceTeam raceTeam) {
//         RaceTeam hasInsert = new RaceTeam();
//        hasInsert.setRaceNo(raceTeam.getRaceNo());
//        hasInsert.setRaceDate(raceTeam.getRaceDate());
//        RaceTeam result =   raceTeamMapper.selectRaceTeam(hasInsert);
//       if(null!=result){
//           raceTeamMapper.updateRaceTeamBifen(raceTeam);
//       }
//        return raceTeamMapper.insert(raceTeam);
        return null;
    }

    public Integer insertSpfChange(SpfChange spfChange) {

        return spfChangeMapper.insert(spfChange);
    }

    public Integer insertRangSpfChange(RangSpfChange rangSpfChange) {

        return rangSpfChangeMapper.insert(rangSpfChange);
    }

    public Integer insertHalfQuanChange(HalfQuanSpfChange halfQuanSpfChange) {
        return halfQuanSpfChangeMapper.insert(halfQuanSpfChange);
    }

    public SpfChange selectSpfChange(SpfChange spfChange) {
        return spfChangeMapper.selectSpfChange(spfChange);
    }

    public RangSpfChange selectRangSpfChange(RangSpfChange rangSpfChange) {
        return rangSpfChangeMapper.selectRangSpfChange(rangSpfChange);
    }

    public HalfQuanSpfChange selectHalfQuanSpfChange(HalfQuanSpfChange halfQuanSpfChange) {
        return halfQuanSpfChangeMapper.selectHalfQuanSpfChange(halfQuanSpfChange);
    }

    public List<SpfChange> selectSpfChangeList(SpfChange spfChange) {
        List<SpfChange> list  = null;
        try{
            list =  spfChangeMapper.selectSpfChangeList(spfChange);

        }catch (Exception e){
            logger.error("",e);
        }
        return list;
    }

    public List<RangSpfChange> selectRangSpfChangeList(RangSpfChange rangSpfChange) {
        List<RangSpfChange> list  = null;
        try{
            list =  rangSpfChangeMapper.selectRangSpfChangeList(rangSpfChange);

        }catch (Exception e){
            logger.error("",e);
        }
        return list;
    }

    public List<HalfQuanSpfChange> selectHalfQuanSpfChangeList(HalfQuanSpfChange HalfQuanSpfChange) {
        List<HalfQuanSpfChange> list  = null;
        try{
            list =  halfQuanSpfChangeMapper.selectHalfQuanSpfChangeList(HalfQuanSpfChange);

        }catch (Exception e){
            logger.error("",e);
        }
        return list;
    }
}
