package com.neo.discovery.service.impl;

import com.neo.discovery.mapper.HalfQuanSpfChangeMapper;
import com.neo.discovery.mapper.RangSpfChangeMapper;
import com.neo.discovery.mapper.SpfChangeMapper;
import com.neo.discovery.mapper.WaveMapper;
import com.neo.discovery.service.ChangeService;
import com.neo.discovery.service.NoticeRule;
import com.neo.discovery.service.WaveService;
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


    public void doNotice(Wave wave){

        if(null!=noticeList&&noticeList.size()<1){
            noticeList.add(noticeSale);
            noticeList.add(noticeBuy);
        }
        if(null!=noticeList&&noticeList.size()>0){
            for(NoticeRule noticeRule:noticeList){
                noticeRule.notice(wave);
            }
        }
    }
}
