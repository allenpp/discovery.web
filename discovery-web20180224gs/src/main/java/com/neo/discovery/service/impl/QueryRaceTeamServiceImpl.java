package com.neo.discovery.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.discovery.mapper.QueryRaceTeamMapper;
import com.neo.discovery.mapper.RaceTeamMapper;
import com.neo.discovery.service.QueryRaceTeamService;
import com.neo.discovery.service.RaceTeamService;
import com.neo.discovery.util.AppendToFile;
import com.neo.discovery.util.HttpClientUtils;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.vo.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 8/20/2017.
 */
@Service("queryRaceTeamService")
public class QueryRaceTeamServiceImpl implements QueryRaceTeamService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(QueryRaceTeamServiceImpl.class);
    @Resource
    private   QueryRaceTeamMapper queryRaceTeamMapper;


//    @Override
    public List<RaceTeam> selecttRaceTeamList(RaceTeam raceTeam) {
        return null;
    }
    public List<RaceTeam> selectRaceTeamByRandomRateCount(RaceTeam raceTeam) {
        return queryRaceTeamMapper.selectRaceTeamByRandomRateCount(raceTeam);
    }

    public List<RaceTeam> selectBaoLeng(RaceTeam raceTeam) {
        return null;
    }

    public void tongjiBiFenRate() {
        boolean winjumpOut = true;
        boolean eqjumpOut = true;
        boolean failjumpOut = true;
        boolean bufuJumpOut = true;
        Float win = 1f;
        Float equals = 1f;
        Float fail = 1f;
        Float finalWin =0f;
        Float finalFail =0f;
        Float buFu = 0.1f;
        List<Float> rates =new ArrayList<Float>();
        Map<Float,String> map = new HashMap<Float, String>();


           while(bufuJumpOut){

               if(buFu>3) break;
               buFu+=0.1f;
               win = 1f;
                while(winjumpOut){
                    if(win>5){
                        break;
                    }
                    RaceTeam raceTeam = new RaceTeam();
                    raceTeam.setXiWin(win);
                    raceTeam.setDaWin(win + buFu);
                    win +=buFu;
                    equals = 1.0f;
                    eqjumpOut = true;
                    while(eqjumpOut){
                        raceTeam.setXiEq(equals);
                        raceTeam.setDaEq(equals +buFu);
                        equals +=buFu;
                        if(equals>5) break;

                        failjumpOut = true;
                        fail = 1.0f;
                        while(failjumpOut){
                            raceTeam.setXiFail(fail);
                            raceTeam.setDaFail(fail +buFu);
                            caculateBaolengRate(rates,raceTeam ,map,buFu);
                            fail +=buFu;
                            if(fail>10) break;
                        }

                    }
                }

        }


        Collections.sort(rates, new Comparator<Float>() {
            public int compare(Float o1, Float o2) {
                if ((o2 - o1) > 0) {
                    return 1;            //降序
                } else if ((o2 - o1) == 0) {
                    return 0;
                } else if ((o2 - o1) < 0) {
                    return -1;
                }
                return -1;
//                return o1.para - o2.para;  //升序
//                return o2.para - o1.para;  //降序
            }
        });
        for(int i=0;i<rates.size();i++){

            Float rate = rates.get(i);
            String peilv = map.get(rate);
            AppendToFile.appendMethodA( "\n  rate:"+rate+"  peilv:"+peilv+"  ");
            logger.info("  rate:"+rate+"  peilv:"+peilv);
        }


    }
    public void findYingLiRate() {
        List<Float> rates =new ArrayList<Float>();
        Map<Float,String> map = new HashMap<Float, String>();

        RaceTeam raceTeam = new RaceTeam();
        for(float win = 0;win<5;win+=0.1){
            if(win!=0)    raceTeam.setXiWin(win);

//            for(float daWin = 0;daWin<5;daWin+=0.1){
//                if(daWin!=0) raceTeam.setDaWin(daWin);

//                for(float equals = 0;equals<10;equals+=0.1){
//                    if(equals!=0) raceTeam.setXiEq(equals);
//
//                    for(float daEq = 0;daEq<10;daEq+=0.1){
//                        if(daEq!=0) raceTeam.setDaEq(daEq);
//
//                        for(float fail = 0;fail<10;fail+=0.1){
//                            if(fail!=0) raceTeam.setXiFail(fail);
//
//                            for(float daFail = 0;daFail<10;daFail+=0.1){
//                                if(daFail!=0) raceTeam.setDaFail(daFail);
//
//                                for(float rangWin = 0;rangWin<10;rangWin+=0.1){
//                                    if(rangWin!=0) raceTeam.setRangXiWin(rangWin);
//
//                                    for(float daRangWin = 0;daRangWin<10;daRangWin+=0.1){
//                                        if(daRangWin!=0) raceTeam.setRangDaWin(daRangWin);
//
//                                        for(float rangEq = 0;rangEq<10;rangEq+=0.1){
//                                            if(rangEq!=0) raceTeam.setRangXiEq(rangEq);
//                                            for(float daRangEq = 0;daRangEq<10;daRangEq+=0.1){
//                                                if(daRangEq!=0) raceTeam.setRangDaEq(daRangEq);

                                                for(float rangFail = 0;rangFail<5;rangFail+=0.1){
                                                    if(rangFail!=0) raceTeam.setRangXiFail(rangFail);

                                                    for(float daRangFail = 0;daRangFail<5;daRangFail+=0.1){
                                                        if(daRangFail!=0) raceTeam.setRangDaFail(daRangFail);
                                                        findYingLiRateBySelect(raceTeam);
                                                    }
                                                }
                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
    public void findYingLiRateBySelect() {
        List<Float> rates =new ArrayList<Float>();
        Map<Float,String> map = new HashMap<Float, String>();

        RaceTeam raceTeam = new RaceTeam();
        for(float win = 0;win<10;win+=0.1){
            if(win!=0)    raceTeam.setXiWin(win);

            for(float daWin = 0;daWin<10;daWin+=0.1){
                if(daWin!=0) raceTeam.setDaWin(daWin);

                for(float equals = 0;equals<10;equals+=0.1){
                    if(equals!=0) raceTeam.setXiEq(equals);

                    for(float daEq = 0;daEq<10;daEq+=0.1){
                        if(daEq!=0) raceTeam.setDaEq(daEq);

                        for(float fail = 0;fail<10;fail+=0.1){
                            if(fail!=0) raceTeam.setXiFail(fail);

                            for(float daFail = 0;daFail<10;daFail+=0.1){
                                if(daFail!=0) raceTeam.setDaFail(daFail);

                                for(float rangWin = 0;rangWin<10;rangWin+=0.1){
                                    if(rangWin!=0) raceTeam.setRangXiWin(rangWin);

                                    for(float daRangWin = 0;daRangWin<10;daRangWin+=0.1){
                                        if(daRangWin!=0) raceTeam.setRangDaWin(daRangWin);

                                        for(float rangEq = 0;rangEq<10;rangEq+=0.1){
                                            if(rangEq!=0) raceTeam.setRangXiEq(rangEq);
                                            for(float daRangEq = 0;daRangEq<10;daRangEq+=0.1){
                                                if(daRangEq!=0) raceTeam.setRangDaEq(daRangEq);

                                                for(float rangFail = 0;rangFail<10;rangFail+=0.1){
                                                    if(rangFail!=0) raceTeam.setRangXiFail(rangFail);

                                                    for(float daRangFail = 0;daRangFail<10;daRangFail+=0.1){
                                                        if(daRangFail!=0) raceTeam.setRangDaFail(daRangFail);
                                                        findYingLiRateBySelect(raceTeam);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    public void caculateBaolengRate(List<Float> rates,RaceTeam raceTeam,Map<Float,String> mapping,Float buFu){
        Integer total =  queryRaceTeamMapper.selectCount(raceTeam);
        Integer baoleng =  queryRaceTeamMapper.selectBiFen(raceTeam);
        Float rate = (Float)(baoleng.floatValue()/total.floatValue());

        String temp = "( win>="+new BigDecimal(raceTeam.getXiWin()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue() +" and win<=" + new BigDecimal(raceTeam.getDaWin()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue()+
                " )  and  (  equals>=" + new BigDecimal(raceTeam.getXiEq() ).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue()+" and equals<="+ new BigDecimal(raceTeam.getDaEq() ).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue() +" ) and (  fail>=" +
                raceTeam.getXiFail()+"  and fail<="+ new BigDecimal(raceTeam.getDaFail() ).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue()+")  ; buFu="+buFu+"; total="+total+"; baoleng="+baoleng;
//      if(StringUtils.isNotBlank(value)&&!value.equals(temp)){
          mapping.put(rate, temp);
//      }
        if(total.floatValue()!=0){
            rates.add(rate);
        }
        logger.info("rate:" + rate + " total=" + total + "  baoleng=" + baoleng + " win=" + raceTeam.getXiWin() + "  equals=" + raceTeam.getXiEq() + "   fail=" + raceTeam.getXiFail() + "   buFu;" + buFu);

    }
    public void findYingLiRate(RaceTeam raceTeam){
        logger.info(" findYingLiRate " + JSONObject.toJSONString(raceTeam));
//        RaceTeam temp = new RaceTeam();
//        temp.setRangXiFail(2.5f);
//        temp.setXiFail(4f);
//        temp.setXiEq(3.5f);
        Float yingKui = processRangFailBuyRangFail(raceTeam);
        if(yingKui>(-100f)&&yingKui!=0f){
            String log = "yingKui="+yingKui+" xiWin="+raceTeam.getXiWin()+",daWin="+raceTeam.getDaWin()+";  xiEq="+raceTeam.getXiEq()+"  daEq="+raceTeam.getDaEq()+", xiFail="+raceTeam.getXiFail()+"   daFail="+raceTeam.getDaFail()+
                    " rangXiWin="+raceTeam.getRangXiWin()+",rangDaWin="+raceTeam.getRangDaWin()+";  rangXiEq="+raceTeam.getRangXiEq()+"  rangDaEq="+raceTeam.getRangDaEq()+", rangXifail="+raceTeam.getRangXiFail()+"   rangDaFail="+raceTeam.getRangDaFail();
            AppendToFile.appendMethodA("\n  :" + log);
            logger.info(log);
        }
    }
    public void findYingLiRateBySelect(RaceTeam raceTeam){
//        logger.info(" findYingLiRate " + JSONObject.toJSONString(raceTeam));
//        RaceTeam temp = new RaceTeam();
//        temp.setRangXiFail(2.5f);
//        temp.setXiFail(4f);
//        temp.setXiEq(3.5f);
        Map<String,Float> yingKui = processSelectZuHe(raceTeam);
        if(null!=yingKui&&yingKui.size()>0){
            Float tempTotal = 0f;
            for(Map.Entry<String,Float> entry:yingKui.entrySet()){
                tempTotal+=entry.getValue();
            }
            String log = "yingKui="+tempTotal+" select: "+yingKui+"  xiWin="+raceTeam.getXiWin()+",daWin="+raceTeam.getDaWin()+";  xiEq="+raceTeam.getXiEq()+"  daEq="+raceTeam.getDaEq()+", xiFail="+raceTeam.getXiFail()+"   daFail="+raceTeam.getDaFail()+
                    " rangXiWin="+raceTeam.getRangXiWin()+",rangDaWin="+raceTeam.getRangDaWin()+";  rangXiEq="+raceTeam.getRangXiEq()+"  rangDaEq="+raceTeam.getRangDaEq()+", rangXifail="+raceTeam.getRangXiFail()+"   rangDaFail="+raceTeam.getRangDaFail();
            AppendToFile.appendMethodA("\n  :" + log);
            logger.info(log);
        }
    }

    public  Float    processRangFailBuyRangFail(RaceTeam temp ){
        List<String> days = getRaceByDate(temp);
        Float total =new Float(0f);
        Map<String,Float> totalMap = new HashMap<String, Float>();
        temp.setRaceDates(days);
        List<RaceTeam> list = queryRaceTeamMapper.selectRaceTeamByRandomRate(temp);

        Map<String,List<RaceTeam>>  map = new HashMap<String,List<RaceTeam>>();
        if(null!=list&&list.size()>0){
            SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
            for(RaceTeam one:list){
                String raceDate =sf.format(one.getRaceDate());
                List<RaceTeam> tempList= map.get(raceDate);
                if(null==tempList){
                    tempList = new ArrayList<RaceTeam>();
                    tempList.add(one);
                    map.put(raceDate,tempList);
                }else{
                    if(tempList.size()<6)
                        tempList.add(one);
                }
            }
        }
        for(String date :map.keySet()) {
            List<RaceTeam> dolist = map.get(date);
            Float oneDay = calculateRangFailBuyRangFail(dolist, date, 1f);
            total +=oneDay;
        }
//        logger.info("total yingkui =    "+total);
        return total;
    }
    public   Map<String,Float>    processSelectZuHe(RaceTeam temp ){
        List<String> days = getRaceByDate(temp);
        Float total =new Float(0f);
        Map<String,Float> totalMap = new HashMap<String, Float>();
        if(null!=days&&days.size()>0){
            temp.setRaceDates(days);
        }
        List<RaceTeam> list = queryRaceTeamMapper.selectRaceTeamByRandomRate(temp);

        Map<String,List<RaceTeam>>  map = new HashMap<String,List<RaceTeam>>();
        if(null!=list&&list.size()>0){
            SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
            for(RaceTeam one:list){
                String raceDate =sf.format(one.getRaceDate());
                List<RaceTeam> tempList= map.get(raceDate);
                if(null==tempList){
                    tempList = new ArrayList<RaceTeam>();
                    tempList.add(one);
                    map.put(raceDate,tempList);
                }else{
                    if(tempList.size()<6)
                        tempList.add(one);
                }
            }
        }

        List<Map.Entry<String, List<RaceTeam>>> infoIds =
                new ArrayList<Map.Entry<String, List<RaceTeam>>>(map.entrySet());

        Collections.sort(infoIds, new Comparator<Map.Entry<String, List<RaceTeam>>>() {
            public int compare(Map.Entry<String, List<RaceTeam>> o1, Map.Entry<String, List<RaceTeam>> o2) {
                Date date1 = ParseUtil.parseStr2Date(o1.getKey());
                Date date2 = ParseUtil.parseStr2Date(o2.getKey());
                return date2.compareTo(date1);
            }
        });



        Map<String,Float>  junyun = new HashMap<String,Float>();
        for(Map.Entry<String, List<RaceTeam>> entry :infoIds) {
            List<RaceTeam> dolist = entry.getValue();
            Map<String,Float> oneDay = caculateBySelect(dolist, entry.getKey());
            sumEveryDayZuHe( totalMap , oneDay,junyun);
        }
        temp.setRaceDates(null);
        for(Map.Entry<String, Float> entry :junyun.entrySet()) {
           if(entry.getValue()>-3){
               String log = "junyun yingli "+JSONObject.toJSONString(temp);
               logger.info(log);
               AppendToFile.appendMethodA("\n  :" + log);
           }
        }


//        logger.info("total yingkui =    "+total);
        return findYingLiZuHe(totalMap);
    }



    private void sumEveryDayZuHe(Map<String,Float> totalMap,Map<String,Float> oneDay,Map<String,Float> junyunMap){

        if(null!=totalMap&&null!=oneDay){
            for(Map.Entry<String,Float> entry:oneDay.entrySet()){
               Float tempTotal =  totalMap.get(entry.getKey());
                if(null==tempTotal){
                    totalMap.put(entry.getKey(),entry.getValue());
                }else{
                    totalMap.put(entry.getKey(),tempTotal+entry.getValue());
                }


                //计算连续小于0的  3次的
                if(entry.getValue()>0){
                   Float count =  junyunMap.get(entry.getKey());
                    if(null==count){
                        junyunMap.put(entry.getKey(),0f);
                    }else if(count>-3){
                        junyunMap.put(entry.getKey(),0f);
                    }
                }else{
                    Float count =  junyunMap.get(entry.getKey());
                    if(null!=count){
                        junyunMap.put(entry.getKey(),count-1);
                    }else{
                        junyunMap.put(entry.getKey(),-1f);
                    }
                }
            }
        }
    }
    private Map<String,Float> findYingLiZuHe(Map<String,Float> totalMap){
        Map<String,Float> yingliMap = new HashMap<String,Float>();
        if(null!=totalMap){
            for(Map.Entry<String,Float> entry:totalMap.entrySet()){
               Float tempTotal =  entry.getValue();
                if(null!=tempTotal&&tempTotal>0){
                    yingliMap.put(entry.getKey(),tempTotal);
                }
            }
        }
        return yingliMap;
    }



    public Float calculateRangFailBuyRangFail(List<RaceTeam> list,String date,Float beiShu) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;

            if(beiShu==null){
                beiShu = 1f;
            }

            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;


            for(int k=0;k<list.size();k++){
                RaceTeam raceTeamOne  = list.get(k);
                for(int i=k;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float thisEarn = 0f;
                        if((raceTeamOne.getFullHomeTeamCourtscore()<=raceTeamOne.getFullVisitingTeamCourtscore())){
                            if(null!=raceTeamOne.getRangFail())
                            onePei = raceTeamOne.getRangFail();
                        }
                        if((other.getFullHomeTeamCourtscore()<=other.getFullVisitingTeamCourtscore())){
                            if(other.getRangFail()!=null)
                            twoPei = other.getRangFail();
                        }
                        allEarn += (onePei * twoPei * 2)*beiShu ;
                        allSpend += 2*beiShu ;
                    }
                }
                j++;
            }

            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+"   rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:";
          logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }






    private Map<String,Float>  caculateBySelect(List<RaceTeam> resultList,String date){


        List<String> oneSelect = new ArrayList<String>();
        List<String> twoSelect = new ArrayList<String>();
        initSelect(oneSelect);
        initSelect(twoSelect);
        Map<String,Float> zuheYingKui = new HashMap<String, Float>();
        if(null!=oneSelect&&null!=twoSelect&&null!=resultList){

//            for(int beiShu=1;beiShu<10;beiShu++){
                for(String one:oneSelect){
                    for(String two:twoSelect){

                        Float allSpend = 0.00f;
                        Float allEarn = 0.00f;
                        Float rangAllEarn = 0f;
                        Float beiShu = 1f;
                        Float yingkui = 0f;
                        for(int k=0;k<resultList.size();k++){
                            RaceTeam raceTeamOne  = resultList.get(k);
                            for(int i=k;i<resultList.size();i++) {
                                List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                                RaceTeam other = resultList.get(i);
                                if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                                    Float onePei = 0f;
                                    Float twoPei = 0f;
                                    Float thisEarn = 0f;

                                    try{
                                            if(one.equals("win")){
                                                if((raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore())){
                                                        onePei = raceTeamOne.getWin();
                                                }
                                            }else if(one.equals("equals")){
                                                if((raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore())){
                                                        onePei = raceTeamOne.getEquals();
                                                }

                                            }else if(one.equals("fail")){
                                                if((raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore())){
                                                    onePei = raceTeamOne.getFail();
                                                }

                                            }else if(one.equals("rangWin")){
                                                if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore()>raceTeamOne.getFullVisitingTeamCourtscore())){
                                                    onePei = raceTeamOne.getRangWin();
                                                }
                                            }else if(one.equals("rangEq")){
                                                if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore()==raceTeamOne.getFullVisitingTeamCourtscore())){
                                                    onePei = raceTeamOne.getRangEq();
                                                }
                                            }else if(one.equals("rangFail")){
                                                if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore()<raceTeamOne.getFullVisitingTeamCourtscore())){
                                                    onePei = raceTeamOne.getRangFail();
                                                }
                                            }



                                            if(two.equals("win")){
                                                if((other.getFullHomeTeamCourtscore()>other.getFullVisitingTeamCourtscore())){
                                                    twoPei = other.getWin();
                                                }
                                            }else if(two.equals("equals")){
                                                if((other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore())){
                                                    twoPei = other.getEquals();
                                                }

                                            }else if(two.equals("fail")){
                                                if((other.getFullHomeTeamCourtscore()<other.getFullVisitingTeamCourtscore())){
                                                    twoPei = other.getFail();
                                                }

                                            }else if(two.equals("rangWin")){
                                                if((other.getFullHomeTeamCourtscore()+other.getRangScore()>other.getFullVisitingTeamCourtscore())){
                                                    twoPei = other.getRangWin();
                                                }
                                            }else if(two.equals("rangEq")){
                                                if((other.getFullHomeTeamCourtscore()+other.getRangScore()==other.getFullVisitingTeamCourtscore())){
                                                    twoPei = other.getRangEq();
                                                }
                                            }else if(two.equals("rangFail")){
                                                if((other.getFullHomeTeamCourtscore()+other.getRangScore()<other.getFullVisitingTeamCourtscore())){
                                                    twoPei = other.getRangFail();
                                                }
                                            }
                                    }catch (Exception e){
                                        logger.error("cacualte fail ",e);
                                    }
                                    if(null!=onePei&&null!=twoPei){
                                        allEarn += (onePei * twoPei * 2)*beiShu ;
                                        allSpend += 2*beiShu ;
                                    }
                                }
                            }
                        }

                          yingkui = allEarn-allSpend;
                          zuheYingKui.put(one+"-"+two,yingkui);
                    }
                }
            }
//        }

        return zuheYingKui;

    }






    private  void initSelect(List<String> list){
        if(null==list){
            list = new ArrayList<String>();
        }
        list.add("win");
//        list.add("equals");
//        list.add("fail");
//        list.add("rangWin");
//        list.add("rangEq");
        list.add("rangFail");
    }





    public   List<String> getRaceByDate(RaceTeam temp){
        List<String> days = new ArrayList<String>();
        SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
//        GregorianCalendar gc=new GregorianCalendar();
//        for(int i=0;i<400;i++){
//            try{
//            gc.setTime(new Date());
////            gc.setTime(sf.parse("2016-10-29"));
//            gc.add(5, -i);
//            sf.format(gc.getTime());
////            logger.info(sf.format(gc.getTime()));
//
//                days.add( sf.format(gc.getTime()));
//            }catch (Exception e){
//
//            }
//        }

        List<RaceTeam> list =  queryRaceTeamMapper.selectRaceTeamByRandomRateCount(temp);
        if(null!=list&&list.size()>0){
            for(RaceTeam raceTeam:list){
                days.add(sf.format(raceTeam.getRaceDate()));
            }
        }

        return days;
    }
    public static void main(String[] args){

        RaceTeam raceTeam = new RaceTeam();
        raceTeam.setWin(3.55f);
        raceTeam.setEquals(3.33f);
        raceTeam.setFail(1.53f);
    }


}
