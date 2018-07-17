package com.neo.discovery.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.discovery.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
public class ParseUtil {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ParseUtil.class);


    public static String parseDate2Str(Date date,String format){
        String temp = "yyyy-MM-dd";
        if(StringUtils.isNotEmpty(format)){
            temp = format;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(temp);
        String str = "";
        try {
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    public static Date parseStr2Date(String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static Date formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String temp = null;
        Date result =null;
        try {
            temp = sdf.format(date);
            result = sdf.parse(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Date parseStr2Time(String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static Date parseStr2TimeFormat(String strDate,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



    public static List<RaceTeam> parseJsonStr(String json){

        List<RaceTeam> result = new ArrayList<RaceTeam>();
        JSONArray array =   JSONArray.parseArray(json);
        Object list [] =  array.toArray();
        for(Object obj:list){

           String raceDate = (String) JSONObject.parseObject(obj.toString()).get("issue");
            JSONArray matches =(JSONArray) JSONObject.parseObject(obj.toString()).get("Matches");
            Object matchesArray [] =  matches.toArray();
            for(Object match:matchesArray){
               RaceTeam raceTeam =  parseLiveRaceTeam((JSONObject) match);
                result.add(raceTeam);
            }
        }

        return result;
    }



    public static Date parseMatchDate(String matchDate){
       String matchDateStr =matchDate;
//周六 17 三月<br>23:00
        if(null!=matchDateStr){
            try{
                matchDateStr = matchDateStr.replaceAll("周一", "").replaceAll("周二", "").replaceAll("周三", "").replaceAll("周四", "").replaceAll("周五", "").replaceAll("周六", "").replaceAll("周日", "");
                String [] strArr = matchDateStr.split("<br>");
                String mounthTemp = strArr[0].replaceAll("一月", "01").replaceAll("二月", "02").replaceAll("三月", "03").replaceAll("四月", "04").replaceAll("五月", "05").replaceAll("六月", "06").replaceAll("七月", "07").replaceAll("八月", "08").replaceAll("九月", "09").replaceAll("十月", "10").replaceAll("十一月", "11").replaceAll("十二月", "12");

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


    public static  boolean isBegining(Wave wave,int beginX){
        if(null!=wave){
            Date now = new Date();
            Date matchDate =ParseUtil.parseMatchDate( wave.getMatchDateStr());
            if(null!=matchDate){
               return ParseUtil.ifMatchRunningXMinute(now, matchDate, beginX);

            }
        }
        return false;

    }

    /**
     * 解析当天比赛的数据
     * @param match
     * @return
     */
    public static RaceTeam parseLiveRaceTeam(JSONObject match){

        RaceTeam raceTeam  = new RaceTeam();
        try{

            Date matchStartTime = parseStr2Time((String) match.get("matchStartTime"));

            String spf = (String)match.get("SpSPF");
            String[] temp = spf.split("\\|");
            String SpRQSPF = (String)match.get("SpRQSPF");
            String[] rqspftemp = spf.split("\\|");


            String homeTeam = (String)match.get("HTeam");
            String visitingTeam = (String)match.get("VTeam");
            String leagueName = (String)match.get("NMm");
            String rangQiu = (String)match.get("Rq");
            JSONObject OptionSPF =(JSONObject) match.get("OptionSPF");
            JSONObject OptionRQSPF =(JSONObject) match.get("OptionRQSPF");

            String mid = (String)match.get("MID");
            Integer matchID = (Integer)match.get("MatchID");

            raceTeam.setHomeTeam(homeTeam);
            raceTeam.setVisitingTeam(visitingTeam);

            raceTeam.setRaceDate(matchStartTime);
            raceTeam.setWin(Float.parseFloat(temp[0]));
            raceTeam.setEquals(Float.parseFloat(temp[1]));
            raceTeam.setFail(Float.parseFloat(temp[2]));

            raceTeam.setRangWin(Float.parseFloat(rqspftemp[0]));
            raceTeam.setRangEq(Float.parseFloat(rqspftemp[1]));
            raceTeam.setRangFail(Float.parseFloat(rqspftemp[2]));

            raceTeam.setRangScore(Float.parseFloat(rangQiu));
            raceTeam.setLeagueName(leagueName);

            try{
                raceTeam.setWinRate((BigDecimal) OptionSPF.get("WinRate"));
            }catch (Exception e1){
                logger.error(" setWinRate fail"+match.toJSONString(),e1);
            }
            try{
                raceTeam.setEqRate((BigDecimal) OptionSPF.get("DrawRate"));
            }catch (Exception e2){
                logger.error("setEqRate fail "+match.toJSONString(),e2);
            }
            try{
                raceTeam.setFailRate(((BigDecimal) OptionSPF.get("LossRate")));
            }catch (Exception e3){
                logger.error("setFailRate fail "+match.toJSONString(),e3);
            }

            try{
                raceTeam.setRangWinRate(((BigDecimal) OptionRQSPF.get("WinRate")));
            }catch (Exception e4){
                logger.error("setRangWinRate fail "+match.toJSONString(),e4);
            }
            try{
                raceTeam.setRangEqRate(((BigDecimal) OptionRQSPF.get("DrawRate")));
            }catch (Exception e5){
                logger.error("setRangEqRate fail "+match.toJSONString(),e5);
            }
            try{
                raceTeam.setRangFailRate(((BigDecimal) OptionRQSPF.get("LossRate")));
            }catch (Exception e6){
                logger.error("setRangFailRate fail "+match.toJSONString(),e6);
            }

            raceTeam.setMid(Integer.parseInt(mid));
            raceTeam.setMatchId((matchID));

        }catch (Exception e){
            logger.error(" build RaceTeam fail ",e);
            logger.error(" build RaceTeam fail "+match.toJSONString());

        }
        return raceTeam;

    }
    /**
     * 解析已完成比赛的数据
     * @param match
     * @return
     */
    public static RaceTeam parseBeforeRaceTeam(JSONObject match){


        RaceTeam raceTeam  = new RaceTeam();
        try{

            Date jingCaiDate = parseStr2Date((String) match.get("I"));
            Date matchStartTime = parseStr2TimeFormat((String) match.get("SMD"), "yyyy-MM-dd HH:mm");


            String homeTeam = (String)match.get("HT");
            String visitingTeam = (String)match.get("AT");
            String leagueName = (String)match.get("TABS");
            String raceNo = (String)match.get("NUM");


            Integer homeScore = (Integer)match.get("HS");
            Integer visitingScore = (Integer)match.get("AS");
            Integer  halfHomeScore= (Integer)match.get("HHS");
            Integer halfVisitingScore = (Integer)match.get("AHS");
            Integer matchID = (Integer)match.get("MId");

            Integer homeTeamId = (Integer)match.get("HTD");
            Integer visitingTeamId = (Integer)match.get("ATD");


            raceTeam.setHomeTeamId(homeTeamId);
            raceTeam.setVisitingTeamId(visitingTeamId);

            raceTeam.setMatchId(matchID);
            raceTeam.setFullHomeTeamCourtscore(homeScore);
            raceTeam.setFullVisitingTeamCourtscore(visitingScore);

            raceTeam.setHalfHomeScore(halfHomeScore);
            raceTeam.setHalfVisitingScore(halfVisitingScore);

            raceTeam.setHomeTeam(homeTeam);
            raceTeam.setVisitingTeam(visitingTeam);

            raceTeam.setRaceDate(matchStartTime);


            raceTeam.setLeagueName(leagueName);
            raceTeam.setRaceNo(raceNo);
            raceTeam.setJingCaiDate(jingCaiDate);


        }catch (Exception e){
            logger.error(" build RaceTeam fail ",e);
            logger.error(" build RaceTeam fail "+match.toJSONString());

        }
        return raceTeam;

    }

    /**
     * 解析spf变化
     * @param match
     * @return
     */
    public static PeiLvDTo parseSpfChange(JSONObject match,String raceDate,Integer marchId){
        PeiLvDTo peiLvDTo = new PeiLvDTo();
        try{
//            SpfChangeJson spfChangeJson = JSONObject.toJavaObject(match, SpfChangeJson.class);
//            LastOdds lastOdds = spfChangeJson.getLastOdds();
            JSONObject  lastOdds = (JSONObject)match.get("LastOdds");
            if(null!=lastOdds){
                peiLvDTo.setWin((BigDecimal)(lastOdds.get("Win")));
                peiLvDTo.setDraw((BigDecimal)(lastOdds.get("Draw")));
                peiLvDTo.setLoss((BigDecimal)(lastOdds.get("Loss")));
            }
            peiLvDTo.setMatchId(marchId);
            String str = raceDate+"-"+lastOdds.get("UpdateTime");
            peiLvDTo.setUpdateTime(ParseUtil.parseStr2TimeFormat(str, "yyyy-MM-dd HH:mm"));
        }catch (Exception e){
            logger.error(" parseSpfChange fail ",e);
            logger.error(" parseSpfChange fail "+match.toJSONString());
        }
        return peiLvDTo;
    }
    /**
     * 解析让spf变化
     * @param
     * @return
     */
    public static PeiLvDTo parseRangSpfPeiLv(JSONArray matchs, PeiLvDTo peiLvDTo){
        try{
            if(null!=matchs&&null!=peiLvDTo&&matchs.size()>0){
                JSONObject  jingcai = (JSONObject)matchs.get(0);
                if(null!=jingcai&&(Integer)jingcai.get("BookMakerId")==1000){
                    peiLvDTo.setRangWin((BigDecimal)(jingcai.get("LastWin")));
                    peiLvDTo.setRangEq((BigDecimal)(jingcai.get("LastDraw")));
                    peiLvDTo.setRangFail((BigDecimal) (jingcai.get("LastLoss")));
                }
            }
        }catch (Exception e){
            logger.error(" parseRangSpfPeiLv fail ",e);
        }
        return peiLvDTo;
    }
    /**
     * 解析必发
     * @param match
     * @return
     */
    public static BiFaDTo parseBiFa(JSONObject match,String raceDate,Integer marchId){
        BiFaDTo biFaDTo = new BiFaDTo();
        try{
            BiFaJson biFaJson = JSONObject.toJavaObject(match, BiFaJson.class);
            TradePieChartBo tradePieChartBo = biFaJson.getTradePieChartBo();
            if(null!=tradePieChartBo){
                biFaDTo.setTotalAmount(tradePieChartBo.getTotalAmount());
                biFaDTo.setWinAmount(tradePieChartBo.getWinAmount());
                biFaDTo.setDrawAmount(tradePieChartBo.getDrawAmount());
                biFaDTo.setLossAmount(tradePieChartBo.getLossAmount());
                biFaDTo.setWinPercent(tradePieChartBo.getWinPercent());
                biFaDTo.setDrawPercent(tradePieChartBo.getDrawPercent());
                biFaDTo.setLossPercent(tradePieChartBo.getLossPercent());
            }
            biFaDTo.setMarchId(marchId);
           List<BetFairTradeStatisticsBo> list =  biFaJson.getBetFairTradeStatisticsBoList();
            if(null!=list&&list.size()==3){
                for(BetFairTradeStatisticsBo bo :list){
                    if(bo.getTypeId().equals("0")){//负
                        biFaDTo.setOfail(Float.parseFloat(bo.getPrice()));
                        biFaDTo.setOfail_bankerProfit(Integer.parseInt(bo.getBankerProfit()));
                    }else  if(bo.getTypeId().equals("1")){//平
                        biFaDTo.setOeq(Float.parseFloat(bo.getPrice()));
                        biFaDTo.setOeq_bankerProfit(Integer.parseInt(bo.getBankerProfit()));
                    }else  if(bo.getTypeId().equals("3")){//胜
                        biFaDTo.setOwin(Float.parseFloat(bo.getPrice()));
                        biFaDTo.setOwin_bankerProfit(Integer.parseInt(bo.getBankerProfit()));
                    }
                }
            }
        }catch (Exception e){
            logger.error(" parseSpfChange fail ",e);
            logger.error(" parseSpfChange fail "+match.toJSONString());
        }
        return biFaDTo;
    }


    /**
     * 1 date1 >date2
     * -1 date1<date2
     * 0  date1=date2
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compareDateStr(String DATE1, String DATE2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    /**
     * 1 date1 >date2
     * -1 date1<date2
     * 0  date1=date2
     * @param dt1
     * @param dt2
     * @return
     */
    public static int compareDate(Date dt1, Date dt2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();

        }
        return 0;
    }

    /**
     * 比赛是否已经开始X 分钟
     * @param now  当前时间
     * @param matchDate  比赛时间
     * @param haveBeginX
     * @return true:比赛已经开始x分钟   false:比赛还没开始x分钟; 默认返回 true
     */
    public static boolean ifMatchRunningXMinute(Date now, Date matchDate,int haveBeginX) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        if(null!=now&&null!=matchDate){
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(matchDate);
//            rightNow.add(Calendar.YEAR,-1);//日期减1年
//            rightNow.add(Calendar.MONTH,3);//日期加3个月
            rightNow.add(Calendar.MINUTE,haveBeginX);//日期加x 分钟
            Date runingTime=rightNow.getTime();
            try {
                if (now.getTime() >= runingTime.getTime()) {
                    System.out.println("dt1 在dt2前");
                    return true;
                } else if (now.getTime() < runingTime.getTime()) {
                    System.out.println("dt1在dt2后");
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();

            }
        }
        return true;
    }


    public static void main(String[] args ){



        boolean  is  = ifMatchRunningXMinute(parseStr2Time("2018-01-01 00:00:03"), parseStr2Time("2018-01-01 00:00:02"),0);

        System.out.print(is );
        try{

//            Date aa =  parseMatchDate(new String("周三 11 七月<br>02:00".getBytes("UTF-8"), "UTF-8"));

        }catch (Exception e){

        }

    }


}
