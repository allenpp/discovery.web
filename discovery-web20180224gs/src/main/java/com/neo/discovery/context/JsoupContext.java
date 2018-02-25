package com.neo.discovery.context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.discovery.service.QueryRaceTeamService;
import com.neo.discovery.service.RaceDataService;
import com.neo.discovery.util.HttpClientUtils;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.vo.RaceData;
import com.neo.discovery.vo.RaceTeam;
import com.neo.discovery.service.RaceTeamService;

import com.neo.discovery.util.RaceTeamUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import sun.rmi.server.InactiveGroupException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @作者 Goofy
 * @邮件 252878950@qq.com
 * @日期 2014-4-2上午10:54:53
 * @描述
 */
public class JsoupContext {

    static ApplicationContext ct=new ClassPathXmlApplicationContext("spring-config.xml");
    static QueryRaceTeamService queryRaceTeamService = (QueryRaceTeamService)ct.getBean("queryRaceTeamService");
    static RaceTeamService raceTeamService = (RaceTeamService)ct.getBean("raceTeamService");
    static RaceDataService raceDataService = (RaceDataService)ct.getBean("raceDataService");
//     static ThreadPoolTaskExecutor asyncThreadPool =(ThreadPoolTaskExecutor) ct.getBean("asyncThreadPool") ;


    public static Logger logger = org.slf4j.LoggerFactory.getLogger(JsoupContext.class);




   public static   void beginXiShu(List<RaceTeam> taskList){
       RaceTeam raceTeam = new RaceTeam();
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
//                               setRangLv(temp, parseDocumentFromUrl(temp.getResultUrl()));
                               final RaceTeam runRaceTeam = temp;
                               final RaceData runRaceData = new RaceData();

//                               JSONObject object =  parseJsonById(runRaceTeam.getResultUrl());
                               JSONObject object =  parseHisJsonById(runRaceTeam.getResultUrl());
                               setHisResult(runRaceTeam, object);
//                               setFirst(runRaceTeam, object);
//                               setZongHf(runRaceTeam, object);
                               raceTeamService.updateRaceTeamHisResult(runRaceTeam);
                               logger.info("Thread " + Thread.currentThread().getName() + " @ update RaceTeam " + JSON.toJSONString(runRaceTeam));


                           }
                       } catch (Exception e) {
                           logger.error(" set RangLv update fail ", e);
                       }
                   }
               }
//       }

   }
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

                               new Thread(){
                                   public void run() {
                                       JSONObject object =  parseJsonById(runRaceTeam.getResultUrl());
                                       setFirst(runRaceTeam, object);
                                       setZongHf(runRaceTeam, object);
                                       JSONObject objectHis =  parseHisJsonById(runRaceTeam.getResultUrl());
                                       setHisResult(runRaceTeam, objectHis);
                                       raceTeamService.updateRaceTeamAllField(runRaceTeam);

//                                       raceTeamService.updateRaceTeamHisResult(runRaceTeam);

                                       logger.info("Thread " + Thread.currentThread().getName() + " @ update RaceTeam " + JSON.toJSONString(runRaceTeam));


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



    public static void main(String[] args){

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
            JsoupContext.beginXiShuYiBu(tasklist);
//            JsoupContext.beginXiShu(tasklist);
//                ThreadPL t =new ThreadPL(tasklist);
//                Thread thread = new Thread(t);
//                threadList.add(thread);
//                thread.start();
                begin=i*limit+1;
        }

//        beginXiShu();
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
                    Thread.sleep(1000);
                    data = HttpClientUtils.doHttpGet("http://i.sporttery.cn/api/fb_match_info/get_pool_rs/?mid=" + id);
                    jsonObject = JSONObject.parseObject(data);
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
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
            logger.error("http://i.sporttery.cn/api/fb_match_info/get_result_his/?mid= "+id+ " ,  parse Data fail !  url="+url+"  data= "+data,e);
        }
        return jsonObject;
    }
    /**
     * 从文件加载
     * @return Document
     */
    public static Document parseDocumentFromFile(){
        File input = new File("/tmp/input.html");
        Document doc=null;
        try {
            //从文件加载Document文档
            doc = Jsoup.parse(input, "UTF-8");
            System.out.println(doc.title());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }



    public  static  void allFlow(String onePageUrl,int page){
        System.out.println("******************************************第"+page+"页");
        Document doc =  parseDocumentFromUrl(onePageUrl);
        parseOnePageDoc(doc);
    }


    /**
     * 解析每一条数据  并入库
     * @param doc
     */
    private static void parseOnePageDoc(Document doc){

        Element ele =doc.select("div.match_list").last();
        List< Node> list = ele.childNode(1).childNodes();
        Node node = list.get(1);
        //所有比赛的信息
        List<Node> results = node.childNodes();
        int i = 0;
        if(null!=results&&results.size()>0){


                for (Node temp : results) {//temp每一场比赛的信息

                    try {
                        Thread.sleep(1000);
                            if (null != temp.attributes() && temp.attributes().size() > 0 && temp.attributes().hasKey("style") && temp.attributes().get("style").equals("background-color:#FFF")) {
                                //当最后一行是 分页的时候 跳过
                                continue;
                            }
                            //tdList 一场比赛的 每项信息
                            List<Node> tdList = temp.childNodes();

                            if (tdList != null && tdList.size() > 0) {
                                StringBuffer tr = new StringBuffer();
                                for (Node tdTemp : tdList) {
                                    if (null != tdTemp.childNodes() && tdTemp.childNodes().size() > 0) {
                                        Node tdNode = tdTemp.childNode(0);

                                        String str = tdTemp.childNode(0).toString();
                                        tr.append(str).append("||");
                                    }
                                }
                                RaceTeam raceTeam = new RaceTeam();
                                String raceDate = null;
                                String raceNo = null;
                                String leagueName = null;
                                String raceHomeVistTeam = null;
                                String raceHalfScore = null;
                                String raceFullScore = null;
                                String raceResult = null;
                                String raceDetail = null;
                                raceDate = tdList.get(1).childNode(0).toString();
//                                raceTeam.setRaceDate(ParseUtil.parseStr2Date(raceDate));
                                raceNo = tdList.get(3).childNode(0).toString();
                                raceTeam.setRaceNo(raceNo);
                                leagueName = tdList.get(5).childNode(0).toString();
                                raceTeam.setLeagueName(leagueName);
                                RaceTeamUtils.parseRaceTeam(tdList.get(7).childNode(0), raceTeam);
                                raceHomeVistTeam = tdList.get(7).childNode(0).toString();
                                if (null != tdList.get(9).childNodes() && tdList.get(9).childNodes().size() > 0 && tdList.get(9).childNode(0).childNodes().size() > 0) {

                                    raceHalfScore = tdList.get(9).childNode(0).childNode(0).toString();
                                    raceTeam.setHalfCourtScore(raceHalfScore);
                                }
                                if (null != tdList.get(11).childNodes() && tdList.get(11).childNodes().size() > 0 && tdList.get(11).childNode(0).childNodes().size() > 0) {
                                    raceFullScore = tdList.get(11).childNode(0).childNode(0).toString();
                                    if (StringUtils.isNotEmpty(raceFullScore) && raceFullScore.indexOf(":") > 0) {
                                        raceTeam.setFullHomeTeamCourtscore(Integer.parseInt(raceFullScore.split(":")[0]));
                                        raceTeam.setFullVisitingTeamCourtscore(Integer.parseInt(raceFullScore.split(":")[1]));
                                    }
                                }
                                if (null != tdList.get(13).childNodes() && tdList.get(13).childNodes().size() > 0) {
                                    raceResult = tdList.get(13).childNode(0).toString();
                                    raceTeam.setRaceStatus(raceResult);
                                }
                                if (null != tdList.get(15).childNodes() && tdList.get(15).childNodes().size() > 0) {
                                    String detailUrl = "http://info.sporttery.cn/football/" + tdList.get(15).childNode(0).attributes().get("href");
                                    raceDetail = tdList.get(15).childNode(0).toString();
                                    setPeilv(raceTeam, detailUrl);

                                    raceTeam.setResultUrl(detailUrl);
                                }
                                System.out.println(JSON.toJSONString(raceTeam));
                                raceTeamService.insert(raceTeam);
        //                    System.out.println(raceDate+"||"+raceNo+"||"+leagueName+"||"+raceHomeVistTeam+"||"+raceHalfScore+"||"+raceFullScore+"||"+raceResult+"||"+raceDetail );
                            }

                    }catch (Exception e){
                        logger.error(" parse Html error ",e);
                    }
                }
        }

    }


    /**
     * 设置让去的赔率
     * @param raceTeam
     * @param doc
     */
    private static void setRangLv(RaceTeam raceTeam,Document doc){
//        Elements elements = doc.select("had_tb");
        Element elements = doc.getElementById("had_tb");
        if(null!=elements){
            try{
//                Element ele = elements.get(1);//让球胜平负的数据
    //            Elements trs = ele.getElementsByClass(".Tr3 Tr_normal");//用class  获取 tr 获取不到 先放放 直接用 tag 获取
                Elements trs = elements.getElementsByTag("tr");
                int trsSize = trs.size();
                Element firstPeilvtrs = trs.get(1);//第一次时间开的赔率
                Elements firsttds =  firstPeilvtrs.getElementsByTag("td");
                String  firstwin = firsttds.get(2).childNode(0).toString();
                String  firstequals = firsttds.get(3).childNode(0).toString();
                String  firstfail = firsttds.get(4).childNode(0).toString();


                Element lastPeilvtrs = trs.get(trsSize - 1);//最后一次时间开的赔率
                Elements tds =  lastPeilvtrs.getElementsByTag("td");
                String  rangQiu = tds.get(1).childNode(0).toString();
                String  win = tds.get(2).childNode(0).toString();
                String  equals = tds.get(3).childNode(0).toString();
                String  fail = tds.get(4).childNode(0).toString();


                raceTeam.setRangScore(Float.parseFloat(rangQiu));
                raceTeam.setRangWin(Float.parseFloat(win));
                raceTeam.setRangEq(Float.parseFloat(equals));
                raceTeam.setRangFail(Float.parseFloat(fail));
            }catch (Exception e){
                logger.error(" set setRangLv fail :win:"+ JSON.toJSONString(raceTeam),e);

            }


        }

    }

    /**
     * 找出这一场的  各种玩法的 赔率
     * @param raceTeam
     * @param detailUrl
     */
    public static void setPeilv(RaceTeam raceTeam,String detailUrl){

        Document detail = parseDocumentFromUrl(detailUrl);
        Elements elements = detail.select(".date6.jf");
        for(Element ele:elements){
            //预留  这次只查 胜平负的
        }
        if(null!=elements&&elements.size()>0){
            Element ele = elements.get(2);//胜平负的数据
//            Elements trs = ele.getElementsByClass(".Tr3 Tr_normal");//用class  获取 tr 获取不到 先放放 直接用 tag 获取
            Elements trs = ele.getElementsByTag("tr");
            int trsSize = trs.size();
            Element lastPeilvtrs = trs.get(trsSize - 1);//最后一次时间开的赔率
            Elements tds =  lastPeilvtrs.getElementsByTag("td");
            String  win = tds.get(1).childNode(0).toString();
            String  equals = tds.get(2).childNode(0).toString();
            String  fail = tds.get(3).childNode(0).toString();
            try{

                raceTeam.setWin(Float.parseFloat(win));
                raceTeam.setEquals(Float.parseFloat(equals));
                raceTeam.setFail(Float.parseFloat(fail));
            }catch (Exception e){
                logger.error(" set PeiLv fail :win:"+win+"equals:"+equals+" fail:"+fail+ JSON.toJSONString(raceTeam),e);

            }


        }



    }

    public static void  setHisResult(RaceTeam raceTeam ,JSONObject jsonObject){
        try{

            if(null!=raceTeam&&null!=jsonObject){

                JSONObject result = (JSONObject)jsonObject.get("result");
                JSONObject total = (JSONObject)result.get("total");
                JSONObject h_total = (JSONObject)result.get("h_total");
                JSONObject a_total = (JSONObject)result.get("a_total");


                try{

                    raceTeam.setZongWin(Float.parseFloat((String)total.get("win").toString()));
                    raceTeam.setZongEq(Float.parseFloat((String) total.get("draw").toString()));
                     raceTeam.setZongFail(Float.parseFloat((String)total.get("lose").toString()));

                      raceTeam.setZhuWin(Float.parseFloat((String)h_total.get("win").toString()));
                      raceTeam.setZhuEq(Float.parseFloat((String)h_total.get("draw").toString()));
                      raceTeam.setZhuFail(Float.parseFloat((String)h_total.get("lose").toString()));

                     raceTeam.setKeWin(Float.parseFloat((String)a_total.get("win").toString()));
                     raceTeam.setKeEq(Float.parseFloat((String)a_total.get("draw").toString()));
                     raceTeam.setKeFail(Float.parseFloat((String)a_total.get("lose").toString()));

                }catch (Exception e){
                    logger.error("",e);
                }
            }
        }catch (Exception e){

        }

    }


    public static void  setFirst(RaceTeam raceTeam ,JSONObject jsonObject){
        try{

            if(null!=raceTeam&&null!=jsonObject){

                    JSONObject result = (JSONObject)jsonObject.get("result");
                    JSONObject odds_list = (JSONObject)result.get("odds_list");
//                    JSONObject pool_rs = (JSONObject)result.get("pool_rs");


                try{
//                    JSONObject hhad = (JSONObject)pool_rs.get("hhad");
//                    String goalline = (String)hhad.get("goalline");
//                    raceTeam.setRangScore(Float.parseFloat(goalline));

                }catch (Exception e){
                    logger.error("",e);
                }



                try {

                    JSONObject had = (JSONObject) odds_list.get("had");
                    JSONObject hhad = (JSONObject) odds_list.get("hhad");
                    JSONArray odds = (JSONArray) had.get("odds");
                    JSONArray oddshhad = (JSONArray) hhad.get("odds");
                    String rangscore = (String) hhad.get("goalline");
                    JSONObject frist = (JSONObject) odds.get(0);
                    JSONObject finalPeiLv = (JSONObject) odds.get(odds.size()-1);
                    JSONObject finalRangPeiLv = (JSONObject) oddshhad.get(oddshhad.size()-1);

                    String win0 = (String) frist.get("h");
                    String equals0 = (String) frist.get("d");
                    String fail0 = (String) frist.get("a");

                    String win = (String) finalPeiLv.get("h");
                    String equals = (String) finalPeiLv.get("d");
                    String fail = (String) finalPeiLv.get("a");

                    String rangWin = (String) finalRangPeiLv.get("h");
                    String rangEq = (String) finalRangPeiLv.get("d");
                    String rangFail = (String) finalRangPeiLv.get("a");

                    raceTeam.setWin0(Float.parseFloat(win0));
                    raceTeam.setEquals0(Float.parseFloat(equals0));
                    raceTeam.setFail0(Float.parseFloat(fail0));

                    raceTeam.setWin(Float.parseFloat(win));
                    raceTeam.setEquals(Float.parseFloat(equals));
                    raceTeam.setFail(Float.parseFloat(fail));

                    raceTeam.setRangScore(Float.parseFloat(rangscore));
                    raceTeam.setRangWin(Float.parseFloat(rangWin));
                    raceTeam.setRangEq(Float.parseFloat(rangEq));
                    raceTeam.setRangFail(Float.parseFloat(rangFail));


                }catch (Exception e) {
                    logger.error("parse win0 fail",e);
                }

                try{
                    JSONObject had = (JSONObject) odds_list.get("had");
                    JSONArray odds = (JSONArray) had.get("odds");
                    JSONObject finalPeiLv = (JSONObject) odds.get(odds.size()-1);


                    String winFinal = (String) finalPeiLv.get("h");
                    String equalsFinal = (String) finalPeiLv.get("d");
                    String failFinal = (String) finalPeiLv.get("a");

                    raceTeam.setWin(Float.parseFloat(winFinal));
                    raceTeam.setEquals(Float.parseFloat(equalsFinal));
                    raceTeam.setFail(Float.parseFloat(failFinal));
                }catch (Exception e){
                    logger.error("",e);
                }






                try{

                    JSONObject crs = (JSONObject)odds_list.get("crs");
                    JSONArray oddsBiFen = (JSONArray)crs.get("odds");
                    JSONObject map = (JSONObject)oddsBiFen.get(oddsBiFen.size()-1);

                    Set set = map.keySet();

                    List<Float> winList = new ArrayList<Float>();
                    List<Float> eqList = new ArrayList<Float>();
                    List<Float> failList = new ArrayList<Float>();
                    for(Object obj:set){
                        String str = (String)obj;
                        String zhu =str.substring(1,2);
                        String ke = str.substring(3, 4);
                        try{
                            Float value = Float.parseFloat((String) map.get(str));
                            if(zhu.equals("1")&&ke.equals("0")){
                                raceTeam.setW10(value);
                                winList.add(value);
                            }else  if(zhu.equals("2")&&ke.equals("0")){
                                raceTeam.setW20(value);
                                winList.add(value);
                            }else  if(zhu.equals("2")&&ke.equals("1")){
                                raceTeam.setW21(value);
                                winList.add(value);
                            }else  if(zhu.equals("3")&&ke.equals("0")){
                                raceTeam.setW30(value);
                                winList.add(value);
                            }else  if(zhu.equals("3")&&ke.equals("1")){
                                raceTeam.setW31(value);
                                winList.add(value);
                            }else  if(zhu.equals("0")&&ke.equals("0")){
                                raceTeam.setE00(value);
                                eqList.add(value);
                            }else  if(zhu.equals("1")&&ke.equals("1")){
                                raceTeam.setE11(value);
                                eqList.add(value);
                            }else  if(zhu.equals("2")&&ke.equals("2")){
                                raceTeam.setE22(value);
                                eqList.add(value);
                            }else  if(zhu.equals("3")&&ke.equals("3")){
                                raceTeam.setE33(value);
                                eqList.add(value);
                            }else  if(zhu.equals("0")&&ke.equals("1")){
                                raceTeam.setF01(value);
                                failList.add(value);
                            }else  if(zhu.equals("0")&&ke.equals("2")){
                                raceTeam.setF02(value);
                                failList.add(value);
                            }else  if(zhu.equals("1")&&ke.equals("2")){
                                raceTeam.setF12(value);
                                failList.add(value);
                            }else  if(zhu.equals("0")&&ke.equals("3")){
                                raceTeam.setF03(value);
                                failList.add(value);
                            }else  if(zhu.equals("1")&&ke.equals("3")){
                                raceTeam.setF13(value);
                                failList.add(value);
                            }
                        }catch (Exception e){

                        }
                    }
                    Collections.sort(winList);
                    Collections.sort(eqList);
                    Collections.sort(failList);
                    raceTeam.setWinmin(winList.get(0));
                    raceTeam.setEqmin(eqList.get(0));
                    raceTeam.setFailmin(failList.get(0));
                }catch (Exception e){
                    e.printStackTrace();
                }


                try{

                    JSONObject rangHhad = (JSONObject)odds_list.get("hhad");
                    JSONArray rangOdds = (JSONArray)rangHhad.get("odds");
                    JSONObject fristRang = (JSONObject)rangOdds.get(0);
                    String rangwin0 = (String)fristRang.get("h");
                    String rangequals0 = (String)fristRang.get("d");
                    String rangfail0 = (String)fristRang.get("a");

                    raceTeam.setRangWin0(Float.parseFloat(rangwin0));
                    raceTeam.setRangEq0(Float.parseFloat(rangequals0));
                    raceTeam.setRangFail0(Float.parseFloat(rangfail0));

                }catch (Exception e){
                    e.printStackTrace();
                }
                try{

                    JSONObject rangHhad = (JSONObject)odds_list.get("hhad");
                    JSONArray rangOdds = (JSONArray)rangHhad.get("odds");
                    JSONObject rangFinal = (JSONObject)rangOdds.get(rangOdds.size()-1);
                    String rangwinFinal = (String)rangFinal.get("h");
                    String rangequalsFinal = (String)rangFinal.get("d");
                    String rangfailFinal = (String)rangFinal.get("a");

                    raceTeam.setRangWin(Float.parseFloat(rangwinFinal));
                    raceTeam.setRangEq(Float.parseFloat(rangequalsFinal));
                    raceTeam.setRangFail(Float.parseFloat(rangfailFinal));

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(raceTeam.getResultUrl()+" jsonObject :"+jsonObject);
        }


        if(raceTeam.getWin0()==null||raceTeam.getEquals0()==null||raceTeam.getFail0()==null){
            raceTeam.setWin0(0f);
            raceTeam.setEquals0(0f);
            raceTeam.setFail0(0f);
        }
        if(raceTeam.getRangWin0()==null||raceTeam.getRangEq0()==null||raceTeam.getRangFail0()==null){
            raceTeam.setRangWin0(0f);
            raceTeam.setRangEq0(0f);
            raceTeam.setRangFail0(0f);
        }

    }
    public static void  setZongHf(RaceTeam raceTeam ,JSONObject jsonObject) {
        try {

            if (null != raceTeam && null != jsonObject) {

                JSONObject result = (JSONObject) jsonObject.get("result");
                JSONObject odds_list = (JSONObject) result.get("odds_list");


                try {


                    List<Float> zongList = new ArrayList<Float>();
                    List<Float> fhList = new ArrayList<Float>();

                    try {

                        JSONObject ttg = (JSONObject) odds_list.get("ttg");
                        JSONArray zongOdds = (JSONArray) ttg.get("odds");
                        JSONObject lastZong = (JSONObject) zongOdds.get(zongOdds.size() - 1);
                        String zong0 = (String) lastZong.get("s0");
                        String zong1 = (String) lastZong.get("s1");
                        String zong2 = (String) lastZong.get("s2");
                        String zong3 = (String) lastZong.get("s3");
                        String zong4 = (String) lastZong.get("s4");
                        String zong5 = (String) lastZong.get("s5");
                        String zong6 = (String) lastZong.get("s6");
                        String zong7 = (String) lastZong.get("s7");

                        raceTeam.setZong0(Float.parseFloat(zong0));
                        raceTeam.setZong1(Float.parseFloat(zong1));
                        raceTeam.setZong2(Float.parseFloat(zong2));
                        raceTeam.setZong3(Float.parseFloat(zong3));
                        raceTeam.setZong4(Float.parseFloat(zong4));
                        raceTeam.setZong5(Float.parseFloat(zong5));
                        raceTeam.setZong6(Float.parseFloat(zong6));
                        raceTeam.setZong7(Float.parseFloat(zong7));

                        zongList.add(Float.parseFloat(zong0));
                        zongList.add(Float.parseFloat(zong1));
                        zongList.add(Float.parseFloat(zong2));
                        zongList.add(Float.parseFloat(zong3));
                        zongList.add(Float.parseFloat(zong4));
                        zongList.add(Float.parseFloat(zong5));
                        zongList.add(Float.parseFloat(zong6));
                        zongList.add(Float.parseFloat(zong7));
                        Collections.sort(zongList);
                        raceTeam.setZongmin(zongList.get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {

                        JSONObject hafu = (JSONObject) odds_list.get("hafu");
                        JSONArray halfFullJinQiuShuArr = (JSONArray) hafu.get("odds");
                        JSONObject halfFullJinQiuShu = (JSONObject) halfFullJinQiuShuArr.get(halfFullJinQiuShuArr.size() - 1);
                        String ss = (String) halfFullJinQiuShu.get("hh");
                        String ps = (String) halfFullJinQiuShu.get("dh");
                        String fs = (String) halfFullJinQiuShu.get("ah");

                        String sp = (String) halfFullJinQiuShu.get("hd");
                        String pp = (String) halfFullJinQiuShu.get("dd");
                        String fp = (String) halfFullJinQiuShu.get("ad");

                        String sf = (String) halfFullJinQiuShu.get("ha");
                        String pf = (String) halfFullJinQiuShu.get("da");
                        String ff = (String) halfFullJinQiuShu.get("aa");


                        raceTeam.setHfss(Float.parseFloat(ss));
                        raceTeam.setHfps(Float.parseFloat(ps));
                        raceTeam.setHffs(Float.parseFloat(fs));
                        raceTeam.setHfsp(Float.parseFloat(sp));
                        raceTeam.setHfpp(Float.parseFloat(pp));
                        raceTeam.setHffp(Float.parseFloat(fp));
                        raceTeam.setHfsf(Float.parseFloat(sf));
                        raceTeam.setHfpf(Float.parseFloat(pf));
                        raceTeam.setHfff(Float.parseFloat(ff));

                        fhList.add(Float.parseFloat(ss));
                        fhList.add(Float.parseFloat(ps));
                        fhList.add(Float.parseFloat(fs));
                        fhList.add(Float.parseFloat(sp));
                        fhList.add(Float.parseFloat(pp));
                        fhList.add(Float.parseFloat(fp));
                        fhList.add(Float.parseFloat(sf));
                        fhList.add(Float.parseFloat(pf));
                        fhList.add(Float.parseFloat(ff));

                        Collections.sort(fhList);
                        raceTeam.setHfmin(fhList.get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(raceTeam.getResultUrl() + " jsonObject :" + jsonObject);
                }
            }

        }catch (Exception e){
            logger.error("",e);
        }
    }
            /**
             * 解析一共有多少页  每一页的url
             * @param doc
             * @return
             */
            private static List<String> getPagesUrl (Document doc){
                List<String> todayTotalPage = new ArrayList<String>();
                Elements hrefs = doc.select(".m-page").select("a[href]");
                if (null != hrefs && hrefs.size() > 0) {
                    for (Element href : hrefs) {
                        if (null != href && null != href.attributes() && href.attr("title").equals("尾页")) {
                            continue;
                        } else {
                            todayTotalPage.add("http://info.sporttery.cn/football/" + href.attr("href").toString());
                        }
                    }
                }
                return todayTotalPage;
            }

        }


class ThreadPL  implements Runnable{

    List<RaceTeam> list = new ArrayList<RaceTeam>();

    public ThreadPL(List<RaceTeam> list){
        this.list = list;
    }
    public void run() {
        JsoupContext.beginXiShu(list);
    }
}