package com.neo.discovery.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.discovery.mapper.RaceDataMapper;
import com.neo.discovery.mapper.RaceTeamMapper;
import com.neo.discovery.service.RaceDataService;
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
@Service("raceDataService")
public class RaceDataServiceImpl implements RaceDataService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(RaceDataServiceImpl.class);
    @Resource
    private RaceDataMapper raceDataMapper;

    public static  void main(String[] args){

        String url = "http://i.sporttery.cn/api/fb_match_info/get_pool_rs/?mid=12312";
        System.out.println(url.substring(url.indexOf("=")+1));
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class,timeout=1,isolation= Isolation.DEFAULT)
    public Integer insert(RaceData raceData,RaceTeam runRaceTeam) {
        try{
            String url = runRaceTeam.getResultUrl();
            String mathcId =url.substring(url.indexOf("=")+1);

            String getPool_result ="http://i.sporttery.cn/api/fb_match_info/get_pool_rs/?mid="+mathcId;
            String getMatchInfoUrl ="http://i.sporttery.cn/api/fb_match_info/get_match_info?mid="+mathcId+"&f_callback=getMatchInfo";
            String getTeamScoreUrl="http://i.sporttery.cn/api/fb_match_info/get_team_score?mid="+mathcId+"&f_callback=getScoreBoardInfoall&order_type=all";
//        String getTeamRecDataUrl=
//        String getFutureMatchesUrl=
            String getResultHisUrl="http://i.sporttery.cn/api/fb_match_info/get_result_his?limit=10&is_ha=all&limit=10&c_id=0&mid="+mathcId+"&ptype[]=three_-1&ptype[]=asia_229&&f_callback=getResultHistoryInfo";
            String getTeamStatisticsUrl="http://i.sporttery.cn/api/fb_match_info/get_team_statistics?mid="+mathcId+"&f_callback=getTeamStatiscsInfo";
            String getScorerUrl="http://i.sporttery.cn/api/fb_match_info/get_scorer?mid="+mathcId+"&f_callback=getScorerInfo";
            String getInjurySuspensionUrl="http://i.sporttery.cn/api/fb_match_info/get_injury_suspension?mid="+mathcId+"&f_callback=getInjurySuspensionInfo";

            String getMatchInfo = "";
            String getTeamScore = "";
            String getResultHis = "";
            String getTeamStatistics = "";
            String getInjurySuspension = "";

            try{

//            String pool_result = HttpClientUtils.doHttpGet(getPool_result);
                  getMatchInfo = HttpClientUtils.doHttpGet(getMatchInfoUrl);
                  getTeamScore = HttpClientUtils.doHttpGet(getTeamScoreUrl);
                  getResultHis = HttpClientUtils.doHttpGet(getResultHisUrl);
                  getTeamStatistics = HttpClientUtils.doHttpGet(getTeamStatisticsUrl);
                  getInjurySuspension = HttpClientUtils.doHttpGet(getInjurySuspensionUrl);
            }catch (Exception e){
                logger.error("",e);
            }


            raceData.setMatchId(Integer.parseInt(mathcId));
            raceData.setGetMatchInfo(getMatchInfo);
            raceData.setGetTeamScore(getTeamScore);
            raceData.setGetResultHis(getResultHis);
            raceData.setGetTeamStatistics(getTeamStatistics);
            raceData.setGetInjurySuspension(getInjurySuspension);

            return  raceDataMapper.insert(raceData);
        }catch (Exception e){
            logger.error("",e);
        }

        return 0;
    }

}
