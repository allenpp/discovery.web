package com.neo.discovery.service.impl;

import com.neo.discovery.mapper.OptFlowMapper;
import com.neo.discovery.mapper.WaveMapper;
import com.neo.discovery.service.NoticeRule;
import com.neo.discovery.service.OptFlowService;
import com.neo.discovery.service.WaveService;
import com.neo.discovery.util.OptStatus;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.vo.OptFlow;
import com.neo.discovery.vo.RaceTeam;
import com.neo.discovery.vo.Wave;
import org.apache.commons.lang3.StringUtils;
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
@Service("optFlowService")
public class OptFlowServiceImpl implements OptFlowService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(OptFlowServiceImpl.class);
    @Resource
    private OptFlowMapper optFlowMapper;

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



    @Transactional
    public Integer insert(OptFlow optFlow) {

        Date matchDate = parseMatchDate(optFlow.getMatchDateStr());
        optFlow.setMatchDate(matchDate);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
        TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
        Integer result = null;
        try{

            OptFlow param = new OptFlow();
            param.setMatchId(optFlow.getMatchId());
            param.setHedgingId(optFlow.getHedgingId());
//            param.setStatus(OptStatus.PLACE.getOptStatus());
            OptFlow place = optFlowMapper.selectOptFlow(param);
            if(null!=place){
                return 0;
            }

            if(StringUtils.isEmpty(optFlow.getHedgingId())){
//                String hedgingId = ParseUtil.parseDate2Str(new Date(),"yyyyMMddHHmmss");
//                optFlow.setHedgingId(hedgingId);
            }
            optFlow.setCreateTime(new Date());
            result =  optFlowMapper.insert(optFlow);
            txManager.commit(status);
        }catch (Exception e){
            logger.error("",e.getMessage());
            txManager.rollback(status);
        }
        return result;
    }

    public OptFlow selectOptFlow(OptFlow optFlow) {
        return optFlowMapper.selectOptFlow(optFlow);
    }
    public OptFlow isBettingRecord(OptFlow optFlow) {
        return optFlowMapper.isBettingRecord(optFlow);
    }

    public List<OptFlow> selectOptFlowList(OptFlow optFlow) {
        return optFlowMapper.selectOptFlowList(optFlow);
    }

    public OptFlow selectWave(OptFlow wave) {
        return null;
    }

    public List<OptFlow> selectWaveList(OptFlow wave) {
        List<OptFlow>  list =  new ArrayList<OptFlow>();
        try{
            list =  optFlowMapper.selectOptFlowList(wave);
        }catch (Exception e){
            logger.error("",e);
        }
        return list;
    }

    public List<OptFlow> selectGroupByMatchId(OptFlow optFlow) {
        List<OptFlow>  list =  new ArrayList<OptFlow>();
        try{
            list =  optFlowMapper.selectGroupByMatchId(optFlow);
        }catch (Exception e){
            logger.error("",e);
        }
        return list;
    }

    /**
     * 更新 一个hedging的状态
     * @param optFlow
     * @return
     */
    public Integer updateOneHedgingOptFlow(OptFlow optFlow) {
        Integer result = 0;
        OptFlow param = new OptFlow();
        param.setBetId(optFlow.getBetId());
        param.setMatchId(optFlow.getMatchId());
        OptFlow optFlowResult = optFlowMapper.selectOptFlow(param);//第二个bet的信息  根据 betId查询出来 该bet的状态信息应该为 0
        OptFlow bet1 = new OptFlow();
        bet1.setId(Long.parseLong(optFlow.getHedgingId()));
        OptFlow hedgingBet = optFlowMapper.selectOptFlow(bet1);//根据第二个bet的 hedgingId查询出第一个 bet的信息  该bet的 状态应该为 1
        if(null!=optFlowResult&&null!=optFlow.getHedgingId()&&hedgingBet.getStatus().equals(OptStatus.OK.getOptStatus())&&null!=optFlowResult&&optFlowResult.getStatus().equals(OptStatus.PLACE.getOptStatus())&&optFlow.getStatus().equals(OptStatus.OK.getOptStatus())){
            OptFlow hedging = new OptFlow();
            hedging.setStatus(OptStatus.HEDGING.getOptStatus());
            hedging.setId(Long.parseLong(optFlowResult.getHedgingId()));
            hedging.setBetId(optFlow.getBetId());
            hedging.setMatchId(optFlow.getMatchId());
            result =   optFlowMapper.updateOneHedgingOptFlow(hedging);
        }
        return result;
    }

    /**
     * 正常更新 一个bet的状态
     * @param optFlow
     * @return
     */
    public Integer updateOptFlowByBetId(OptFlow optFlow) {
        Integer result = 0;
        if(null!=optFlow&&StringUtils.isNotBlank(optFlow.getHedgingId())){//更新 hedging
            result = updateOneHedgingOptFlow(optFlow);
        }else{
            OptFlow param = new OptFlow();
            param.setBetId(optFlow.getBetId());
            param.setMatchId(optFlow.getMatchId());
            OptFlow optFlowResult = optFlowMapper.selectOptFlow(param);
            if(null!=optFlowResult&&optFlow.getStatus().equals(OptStatus.OK.getOptStatus())){
                result =   optFlowMapper.updateOptFlowByBetId(optFlow);
            }
        }


        return result;
    }



    public void doNotice(OptFlow wave){

//        if(null!=noticeList&&noticeList.size()<1){
//            noticeList.add(noticeSale);
//            noticeList.add(noticeBuy);
//            noticeList.add(noticeMatchDate);
//            noticeList.add(noticeChange);
//        }
//        if(null!=noticeList&&noticeList.size()>0){
//            for(NoticeRule noticeRule:noticeList){
//                noticeRule.notice(wave);
//            }
//        }
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
