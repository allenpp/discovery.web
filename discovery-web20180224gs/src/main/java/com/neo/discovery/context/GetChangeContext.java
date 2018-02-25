package com.neo.discovery.context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.discovery.service.ChangeService;
import com.neo.discovery.service.QueryRaceTeamService;
import com.neo.discovery.service.RaceDataService;
import com.neo.discovery.service.RaceTeamService;
import com.neo.discovery.util.HttpClientUtils;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.util.RaceTeamUtils;
import com.neo.discovery.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @作者 Goofy
 * @邮件 252878950@qq.com
 * @日期 2014-4-2上午10:54:53
 * @描述
 */
public class GetChangeContext {

    static ApplicationContext ct=new ClassPathXmlApplicationContext("spring-config.xml");
    static QueryRaceTeamService queryRaceTeamService = (QueryRaceTeamService)ct.getBean("queryRaceTeamService");
    static RaceTeamService raceTeamService = (RaceTeamService)ct.getBean("raceTeamService");
    static RaceDataService raceDataService = (RaceDataService)ct.getBean("raceDataService");
    static ChangeService changeService = (ChangeService)ct.getBean("changeService");
//     static ThreadPoolTaskExecutor asyncThreadPool =(ThreadPoolTaskExecutor) ct.getBean("asyncThreadPool") ;


    public static Logger logger = org.slf4j.LoggerFactory.getLogger(GetChangeContext.class);


    static ConcurrentMap<String,Integer> map = new ConcurrentHashMap<String,Integer>();



   public static   void beginXiShuYiBu(List<RaceTeam> taskList){
       long i = 0;
//           while(count()) {
//               logger.info("-----i="+i++);
////               if(1==1) continue;
//               List<RaceTeam> list = raceTeamService.selecttRaceTeamList(raceTeam);
               logger.info(" Thread " + Thread.currentThread().getName() + "  list :"+taskList.size() );
               if (null != taskList && taskList.size() > 0) {
                   for (RaceTeam temp : taskList) {
                       try {

                           if (StringUtils.isNotEmpty(temp.getResultUrl())) {
                               final RaceTeam runRaceTeam = temp;
                               final RaceData runRaceData = new RaceData();

                               while(null!= map.get("threads")&& map.get("threads")>20){
                                   Thread.sleep(1000);
                                   logger.info("threads is "+map.get("threads"));
                               }
                               new Thread(){
                                   public void run() {
                                       int current = 0;
                                       if(null!= map.get("threads")){
                                           current =  map.get("threads");
                                       }
                                       map.put("threads",current+1);
                                       JSONObject object =  parseJsonById(runRaceTeam.getResultUrl());
                                       String marchId = runRaceTeam.getResultUrl().substring(runRaceTeam.getResultUrl().indexOf("=") + 1);
                                       setSpfAndRangSpf(Integer.parseInt(marchId), object);

                                       setHalfQuanChange(Integer.parseInt(marchId), object);

                                       logger.info("Thread " + Thread.currentThread().getName() + " @ update RaceTeam " + JSON.toJSONString(runRaceTeam));
                                       int aa = 0;
                                       if(null!= map.get("threads")){
                                           aa =  map.get("threads");
                                       }
                                       map.put("threads",aa-1);

//                                       if(null!=object){
//                                           runRaceData.setPool_result(object.toString());
//                                       }
//                                       new Thread(){
//                                           public void run() {
//                                               raceDataService.insert(runRaceData,runRaceTeam);
//                                               logger.info("Thread " + Thread.currentThread().getName() + " @ raceDataService.insert(null,runRaceTeam)" + JSON.toJSONString(runRaceTeam));
//                                           }
//                                       }.start();

                                   }
                               }.start();

                           }
                       } catch (Exception e) {
                           logger.error(" set RangLv update fail ", e);
                       }
                   }
               }
//       }

   }
   public static   void beginGetSumXiao1(List<RaceTeam> taskList){
       long i = 0;
//           while(count()) {
//               logger.info("-----i="+i++);
////               if(1==1) continue;
//               List<RaceTeam> list = raceTeamService.selecttRaceTeamList(raceTeam);
               logger.info(" Thread " + Thread.currentThread().getName() + "  list :"+taskList.size() );
               if (null != taskList && taskList.size() > 0) {
                   for (RaceTeam temp : taskList) {
                       try {
                           HalfQuanSpfChange halfQuanSpfChange = new HalfQuanSpfChange();
                           String url = temp.getResultUrl();
                           Integer matchId = null;
                           if(null!=url&&url.indexOf("http://info.sporttery.cn/football/pool_result.php?id=")>=0){

                               String mathcIdStr = url.substring(53);
                               if(null!=mathcIdStr)
                                   matchId = Integer.parseInt(mathcIdStr);
                               halfQuanSpfChange.setMatchId(matchId);
                           }

                           SpfChange spfChange = new SpfChange();
                           spfChange.setMatchId(matchId);
                           RangSpfChange rangSpfChange = new RangSpfChange();
                           rangSpfChange.setMatchId(matchId);

                           List<HalfQuanSpfChange> list = changeService.selectHalfQuanSpfChangeList(halfQuanSpfChange);
                           List<SpfChange> spfChangeList = changeService.selectSpfChangeList(spfChange);
                           List<RangSpfChange> rangSpfChangeList = changeService.selectRangSpfChangeList(rangSpfChange);
                           if(null!=list){

                               if(null!=temp.getRangScore()&&temp.getRangScore()==-1f&&null!=rangSpfChangeList){

                                   for(RangSpfChange rangSpfChangeObj:rangSpfChangeList){
                                       Float tempPei = rangSpfChangeObj.getRangfail();
                                       for(HalfQuanSpfChange obj:list){
                                           Float ss = obj.getSs();
                                           Float ps = obj.getPs();
                                           Float fs = obj.getFs();
                                           if(0!=tempPei&&0!=ss&&0!=ps&&0!=fs){
                                               Float sum = 1/ss+1/ps+1/fs+1/tempPei;
                                               if(sum<=1){
                                                   logger.info("temp:"+temp.getMatchId()+"   HalfQuanSpfChange ="+obj.getId());
                                               }
                                           }
                                       }
                                   }

                               }else  if(null!=temp.getRangScore()&&temp.getRangScore()==1f&&null!=rangSpfChangeList){
                                   for(RangSpfChange rangSpfChangeObj:rangSpfChangeList) {
                                       Float tempPei = rangSpfChangeObj.getRangwin();
                                       for(HalfQuanSpfChange obj:list){
                                           Float sf = obj.getSf();
                                           Float pf = obj.getPf();
                                           Float ff = obj.getFf();
                                           if(0!=tempPei&&0!=sf&&0!=pf&&0!=ff){
                                               Float sum = 1/ff+1/pf+1/sf+1/tempPei;
                                               if(sum<=1){
                                                   logger.info("temp:"+temp.getMatchId()+"   HalfQuanSpfChange ="+obj.getId());
                                               }
                                           }
                                       }
                                   }



                               }

                           }

                       } catch (Exception e) {
                           logger.error(" set RangLv update fail ", e);
                       }
                   }
               }
//       }

   }



    public static void main(String[] args){



//        xiShu();

        getChangPeiXiaoYu1();
    }



    public   static void xiShu(){
        int workers = 10;
        int limit = 10;

        RaceTeam param = new RaceTeam();
        List<RaceTeam> list = null;
        List<Thread> threadList = new ArrayList<Thread>();
        int i = 0;
        int begin = 0;
        while(count()){
            i++;
            List<RaceTeam> tasklist =  getWorkers(begin,limit);
            GetChangeContext.beginXiShuYiBu(tasklist);
//            JsoupContext.beginXiShu(tasklist);
//                ThreadPL t =new ThreadPL(tasklist);
//                Thread thread = new Thread(t);
//                threadList.add(thread);
//                thread.start();
            begin=i*limit+1;
        }


    }
    public   static void getChangPeiXiaoYu1(){
        int workers = 10;
        int limit = 10;

        RaceTeam param = new RaceTeam();
        List<RaceTeam> list = null;
        List<Thread> threadList = new ArrayList<Thread>();
        int i = 0;
        int begin = 0;
        while(count()){
            i++;
            List<RaceTeam> tasklist =  getWorkers(begin, limit);
            GetChangeContext.beginGetSumXiao1(tasklist);
//            JsoupContext.beginXiShu(tasklist);
//                ThreadPL t =new ThreadPL(tasklist);
//                Thread thread = new Thread(t);
//                threadList.add(thread);
//                thread.start();
            begin=i*limit+1;
        }


    }




    /**
     * 简单起见 不写单独统计的sql 了
     * @return
     */
    private static   boolean count(){
//        if(1==1)  return true;

       Integer count =    raceTeamService.selectRaceTeamCount(null);
        if(null!=count&&count>0){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 简单起见 不写单独统计的sql 了
     * @return
     */
    private static   List<RaceTeam> getWorkers(Integer begin,Integer end){
        RaceTeam param = new RaceTeam();
        List<RaceTeam> list = null;
            param.setBegin(begin);
            param.setEnd(end);
            list  =  raceTeamService.selectRaceTeamListByPage(param);
        return  list;
    }


    /**
     * 从URL加载
     * @return Document
     */
    public static  synchronized   Document   parseDocumentFromUrl(String url){
        Document doc = null;
        try {
            if(StringUtils.isNotEmpty(url)){
                doc = Jsoup.connect(url).timeout(1000000).get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 从URL加载
     * @return Document
     */
    public static JSONObject parseJsonById(String url){
        JSONObject jsonObject = null;
        String data = "";
        String id = "";
        try {
            if(StringUtils.isNotEmpty(url)){
                if(url.indexOf("=")>0){
                    id = url.substring(url.indexOf("=") + 1);
//                    Thread.sleep(1000);
                    data = HttpClientUtils.doHttpGet("http://i.sporttery.cn/api/fb_match_info/get_pool_rs/?mid=" + id);
                    jsonObject = JSONObject.parseObject(data);
                    try{

                        RaceTeam raceTeam =  new RaceTeam();
                        raceTeam.setMatchId(Integer.parseInt(id));
                        raceTeam.setPool_result(data);
                        raceTeamService.updateRaceTeamPoolResult(raceTeam);
                    }catch (Exception e){
                        logger.error("",e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("http://i.sporttery.cn/api/fb_match_info/get_pool_rs/?mid= " + id + " ,  parse Data fail !  url=" + url + "  data= " + data, e);
        }
        return jsonObject;
    }
    /**
     * 从URL加载
     * @return Document
     */
    public static JSONObject parseHisJsonById(String url){
        JSONObject jsonObject = null;
        String data = "";
        String id = "";
        try {
            if(StringUtils.isNotEmpty(url)){
                if(url.indexOf("=")>0){
                    id = url.substring(url.indexOf("=") + 1);
                    data = HttpClientUtils.doHttpGet("http://i.sporttery.cn/api/fb_match_info/get_result_his?limit=100&is_ha=all&limit=100&c_id=0&mid="+id+"&p");
                    jsonObject = JSONObject.parseObject(data);
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("http://i.sporttery.cn/api/fb_match_info/get_result_his/?mid= " + id + " ,  parse Data fail !  url=" + url + "  data= " + data, e);
        }
        return jsonObject;
    }






    public static void  setSpfAndRangSpf(Integer marchId ,JSONObject jsonObject){

            if(null!=marchId&&null!=jsonObject) {

                JSONObject result = (JSONObject) jsonObject.get("result");
                JSONObject odds_list = (JSONObject) result.get("odds_list");


                try {
                    JSONObject had = (JSONObject) odds_list.get("had");
                    JSONObject hhad = (JSONObject) odds_list.get("hhad");
                    JSONArray odds = (JSONArray) had.get("odds");

                    if (null != odds && odds.size() > 0) {
                        List<SpfChange> spfChangeList = new ArrayList<SpfChange>();
                        for (Object obj : odds) {
                            try{

                                JSONObject spf = (JSONObject) obj;
                                String win = (String) spf.get("h");
                                String equals = (String) spf.get("d");
                                String fail = (String) spf.get("a");
                                String day = (String) spf.get("date");
                                String time = (String) spf.get("time");


                                SpfChange spfChange = new SpfChange();
                                spfChange.setWin(Float.parseFloat(win));
                                spfChange.setEquals(Float.parseFloat(equals));
                                spfChange.setFail(Float.parseFloat(fail));
                                spfChange.setMatchId(marchId);
                                spfChange.setPublishtime(ParseUtil.parseStr2Time(day + " " + time));
                                spfChange.setCreateTime(new Date());
                                spfChangeList.add(spfChange);
                                changeService.insertSpfChange(spfChange);
                            }catch (Exception e){
                                logger.error("",e);
                            }
                        }
                    }


                    JSONArray oddshhad = (JSONArray) hhad.get("odds");
                    if (null != oddshhad && oddshhad.size() > 0) {
                        String rangscore = (String) hhad.get("goalline");
                        List<RangSpfChange> rangSpfChangeList = new ArrayList<RangSpfChange>();
                        for (Object obj : oddshhad) {
                            try{

                                JSONObject rangSpf = (JSONObject) obj;
                                String rangWin = (String) rangSpf.get("h");
                                String rangEq = (String) rangSpf.get("d");
                                String rangFail = (String) rangSpf.get("a");
                                String day = (String) rangSpf.get("date");
                                String time = (String) rangSpf.get("time");

                                RangSpfChange rangSpfChange = new RangSpfChange();

                                rangSpfChange.setRangwin(Float.parseFloat(rangWin));
                                rangSpfChange.setRangequals(Float.parseFloat(rangEq));
                                rangSpfChange.setRangfail(Float.parseFloat(rangFail));
                                rangSpfChange.setMatchId(marchId);
                                rangSpfChange.setCreateTime(new Date());
                                rangSpfChange.setPublishtime(ParseUtil.parseStr2Time(day + " " + time));
//                                rangSpfChange.setRangscore(Integer.parseInt(rangscore));
                                rangSpfChangeList.add(rangSpfChange);
                                changeService.insertRangSpfChange(rangSpfChange);
                            }catch (Exception e){
                                logger.error("",e);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
            }


    }
    public static void  setHalfQuanChange(Integer marchId  ,JSONObject jsonObject) {
        try {
            if (null != marchId && null != jsonObject) {

                JSONObject result = (JSONObject) jsonObject.get("result");
                JSONObject odds_list = (JSONObject) result.get("odds_list");
                try {

                    List<Float> zongList = new ArrayList<Float>();
                    List<Float> fhList = new ArrayList<Float>();

                    List<HalfQuanSpfChange> list = new ArrayList<HalfQuanSpfChange>();

                    try {
                        JSONObject hafu = (JSONObject) odds_list.get("hafu");
                        JSONArray halfFullJinQiuShuArr = (JSONArray) hafu.get("odds");
                        if(null!=halfFullJinQiuShuArr&&halfFullJinQiuShuArr.size()>0){

                            for(Object obj :halfFullJinQiuShuArr){
                                try{

                                    HalfQuanSpfChange halfQuanSpfChange = new HalfQuanSpfChange();
                                    JSONObject halfFullJinQiuShu = (JSONObject) obj;
                                    String ss = (String) halfFullJinQiuShu.get("hh");
                                    String ps = (String) halfFullJinQiuShu.get("dh");
                                    String fs = (String) halfFullJinQiuShu.get("ah");

                                    String sp = (String) halfFullJinQiuShu.get("hd");
                                    String pp = (String) halfFullJinQiuShu.get("dd");
                                    String fp = (String) halfFullJinQiuShu.get("ad");

                                    String sf = (String) halfFullJinQiuShu.get("ha");
                                    String pf = (String) halfFullJinQiuShu.get("da");
                                    String ff = (String) halfFullJinQiuShu.get("aa");

                                    String day = (String) halfFullJinQiuShu.get("date");
                                    String time = (String) halfFullJinQiuShu.get("time");

                                    halfQuanSpfChange.setSs(Float.parseFloat(ss));
                                    halfQuanSpfChange.setPs(Float.parseFloat(ps));
                                    halfQuanSpfChange.setFs(Float.parseFloat(fs));
                                    halfQuanSpfChange.setSp(Float.parseFloat(sp));
                                    halfQuanSpfChange.setPp(Float.parseFloat(pp));
                                    halfQuanSpfChange.setFp(Float.parseFloat(fp));
                                    halfQuanSpfChange.setSf(Float.parseFloat(sf));
                                    halfQuanSpfChange.setPf(Float.parseFloat(pf));
                                    halfQuanSpfChange.setFf(Float.parseFloat(ff));
                                    halfQuanSpfChange.setMatchId(marchId);
                                    halfQuanSpfChange.setCreateTime(new Date());
                                    halfQuanSpfChange.setPublishtime(ParseUtil.parseStr2Time(day + " " + time));
                                    list.add(halfQuanSpfChange);
                                    changeService.insertHalfQuanChange(halfQuanSpfChange);
                                }catch (Exception e){
                                    logger.error("",e);
                                }
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error( " jsonObject :" + jsonObject);
                }
            }

        }catch (Exception e){
            logger.error("",e);
        }
    }

 }


