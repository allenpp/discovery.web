package com.neo.discovery.service.impl;

import com.neo.discovery.mapper.HalfQuanSpfChangeMapper;
import com.neo.discovery.mapper.RangSpfChangeMapper;
import com.neo.discovery.mapper.SpfChangeMapper;
import com.neo.discovery.mapper.WaveMapper;
import com.neo.discovery.service.ChangeService;
import com.neo.discovery.service.NoticeRule;
import com.neo.discovery.service.WaveService;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.vo.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 8/20/2017.
 */
@Service("waveService")
public class WaveServiceImpl implements WaveService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(WaveServiceImpl.class);
    @Resource
    private WaveMapper waveMapper;

    @Autowired
    private DataSourceTransactionManager txManager;

    private List<NoticeRule> noticeList = new ArrayList<NoticeRule>();
    @Autowired
    private NoticeRule noticeSale;
    @Autowired
    private NoticeRule noticeBuy;
    @Autowired
    private NoticeRule noticeMatchDate;
    @Autowired
    private NoticeRule noticeChange;


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

    @Transactional
    public Integer insert(Wave wave) {
        if(null==wave||wave.getBuy_f1()==null){
            return 0;
        }
        Date matchDate = parseMatchDate(wave.getMatchDateStr());
        wave.setMatchDate(matchDate);
        doNotice(wave);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
        TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
        Integer result = null;
        try{
            wave.setCreateTime(new Date());
            result =  waveMapper.insert(wave);
            txManager.commit(status);
        }catch (Exception e){
            logger.error("",e.getMessage());
            txManager.rollback(status);
        }
        return result;
    }

    public Wave selectWave(Wave wave) {
        return null;
    }

    public List<Wave> selectWaveList(Wave wave) {
        List<Wave>  list =  new ArrayList<Wave>();
        try{
            list =  waveMapper.selectWaveList(wave);
        }catch (Exception e){
            logger.error("",e);
        }
        return list;
    }

    public List<Wave> selectGroupByMatchId(Wave wave) {
        List<Wave>  list =  new ArrayList<Wave>();
        try{
            list =  waveMapper.selectGroupByMatchId(wave);
        }catch (Exception e){
            logger.error("",e);
        }
        return list;
    }

    public Integer updateWaveByMatchId(Wave wave) {
        Wave result = waveMapper.selectFirstRecord(wave);
        wave.setId(result.getId());
        waveMapper.updateWaveByMatchId(wave);
        return null;
    }



    public void doNotice(Wave wave){

        if(null!=noticeList&&noticeList.size()<1){
            noticeList.add(noticeSale);
            noticeList.add(noticeBuy);
            noticeList.add(noticeMatchDate);
            noticeList.add(noticeChange);
        }
        if(null!=noticeList&&noticeList.size()>0){
            for(NoticeRule noticeRule:noticeList){
                noticeRule.notice(wave);
            }
        }
    }


    private static Date parseMatchDate(String matchDateStr){

//周六 17 三月<br>23:00
        if(null!=matchDateStr){
            try{
                matchDateStr = matchDateStr.replace("周一","").replace("周二", "").replace("周三","").replace("周四","").replace("周五","").replace("周六","").replace("周日","");
                String [] strArr = matchDateStr.split("<br>");
                String mounthTemp = strArr[0].replace("一月","").replace("一月", "01").replace("二月","02").replace("三月","03").replace("四月","04").replace("五月","05").replace("六月","06").replace("七月","07").replace("八月","08").replace("九月","09").replace("十月","10").replace("十一月","11").replace("十二月","12");

                String mounth = mounthTemp.trim().split(" ")[1];
                String day = mounthTemp.trim().split(" ")[0];
                String hour = strArr[1];

                String mathcDate = "2018"+"-"+mounth+"-"+day+" "+hour+":00";
                Date date = ParseUtil.parseStr2Time(mathcDate);
                return date;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }


    public static  void main(String[] args){

        parseMatchDate("周六 17 三月<br>23:00");
    }
}
