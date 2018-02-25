package com.neo.discovery.context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.discovery.service.RaceTeamService;
import com.neo.discovery.util.HttpClientUtils;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.util.RaceTeamUtils;
import com.neo.discovery.vo.RaceTeam;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @作者 Goofy
 * @邮件 252878950@qq.com
 * @日期 2014-4-2上午10:54:53
 * @描述
 */
public class JsoupTodayLive {

    static ApplicationContext ct=new ClassPathXmlApplicationContext("spring-config.xml");
    static RaceTeamService raceTeamService = (RaceTeamService)ct.getBean("raceTeamService");

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(JsoupTodayLive.class);


    /**
     * 从URL加载
     * @return Document
     */
    public static Document parseDocumentFromUrl(String url){
        Document doc = null;
        try {

            if(StringUtils.isNotEmpty(url)){
                doc = Jsoup.connect(url).timeout(10000000).get();
            }else{
                doc = Jsoup.connect("http://info.sporttery.cn/livescore/fb_livescore.html").timeout(1000000).get();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }


    public static void main(String[] args){

        JSONObject obj =  parseMatchLive(null);
        setHisResult(obj);

//        List<String> todayTotalPage = new ArrayList<String>();
//        Document doc =  parseDocumentFromUrl(null);
//        todayTotalPage = getPagesUrl(doc);
//        parseOnePageDoc(doc);
//        int page = 1;
//        if(todayTotalPage.size()>0){
//            for(String onePage:todayTotalPage){
//                page++;
//                allFlow(onePage,page);
//            }
//        }
    }

    /**
     * 从URL加载
     * @return Document
     */
    public static JSONObject parseMatchLive(String url){
        JSONObject jsonObject = null;
        String data = "";
        String id = "";
        try {

                    data = HttpClientUtils.doHttpGet("http://i.sporttery.cn/api/match_live_2/get_match_list?callback=?&_=1511014699489");
            if(StringUtils.isNotBlank(data)&&data.indexOf("var match_list = ")>=0){
               data =  data.substring(data.indexOf("var match_list = ")+17,data.lastIndexOf(";"));
            }
            jsonObject = JSONArray.parseObject(data);


        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("http://i.sporttery.cn/api/fb_match_info/get_result_his/?mid= "+id+ " ,  parse Data fail !  url="+url+"  data= "+data,e);
        }
        return jsonObject;
    }


    public static void  setHisResult(JSONObject jsonObject){
        try{

            if(null!=jsonObject&&jsonObject.size()>0){
                RaceTeam raceTeam = new RaceTeam();

                try{
                    for(Map.Entry entry:jsonObject.entrySet()){
                        String key = (String)entry.getKey();
                        JSONObject value = (JSONObject)entry.getValue();


                        raceTeam.setRaceDate(ParseUtil.parseStr2Date(value.getString("date_cn")));
                        raceTeam.setRaceNo(value.getString("match_num"));
                        raceTeam.setLeagueName(value.getString("l_cn_abbr"));
                       raceTeam.setHomeTeam(value.getString("h_cn"));
                       raceTeam.setVisitingTeam(value.getString("a_cn"));

                        if(!org.springframework.util.StringUtils.isEmpty(value.getString("fs_h"))){
//                            raceTeam.setFullHomeTeamCourtscore(Integer.parseInt(value.getString("fs_h")));
//                            raceTeam.setFullVisitingTeamCourtscore(Integer.parseInt(value.getString("fs_a")));
                        }

                        String mid = value.getString("m_id").toString();
                       raceTeam.setResultUrl("http://info.sporttery.cn/football/pool_result.php?id="+mid);
                       raceTeam.setMatchId(Integer.parseInt(value.getString("m_id")));


                        JSONObject his = parseHisJsonById(mid);

                        if(null!=his){

                            JSONObject result = (JSONObject)his.get("result");
                            JSONObject total = (JSONObject)result.get("total");
                            JSONObject h_total = (JSONObject)result.get("h_total");
                            JSONObject a_total = (JSONObject)result.get("a_total");

                            raceTeam.setZongWin(Float.parseFloat((String) total.get("win").toString()));
                            raceTeam.setZongEq(Float.parseFloat((String) total.get("draw").toString()));
                            raceTeam.setZongFail(Float.parseFloat((String) total.get("lose").toString()));

                            raceTeam.setZhuWin(Float.parseFloat((String) h_total.get("win").toString()));
                            raceTeam.setZhuEq(Float.parseFloat((String) h_total.get("draw").toString()));
                            raceTeam.setZhuFail(Float.parseFloat((String) h_total.get("lose").toString()));

                            raceTeam.setKeWin(Float.parseFloat((String) a_total.get("win").toString()));
                            raceTeam.setKeEq(Float.parseFloat((String) a_total.get("draw").toString()));
                            raceTeam.setKeFail(Float.parseFloat((String) a_total.get("lose").toString()));


                        }

                        JSONObject peilv = parseJsonById(mid);

                        if(null!=peilv){
                            setFirst(raceTeam,peilv);
                        }

                        raceTeamService.insert(raceTeam);
                        logger.info(" raceTeam live = "+ JSON.toJSONString(raceTeam));

                    }
                }catch (Exception e){
                    logger.error("",e);
                }
            }
        }catch (Exception e){

            logger.info("", e);
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
            logger.error(raceTeam.getResultUrl() + " jsonObject :" + jsonObject);
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

    public static JSONObject parseHisJsonById(String mid){
        JSONObject jsonObject = null;
        String data = "";
        String id = "";
        try {
            if(StringUtils.isNotEmpty(mid)){
                    data = HttpClientUtils.doHttpGet("http://i.sporttery.cn/api/fb_match_info/get_result_his?limit=100&is_ha=all&limit=100&c_id=0&mid="+mid+"&p");
                    jsonObject = JSONObject.parseObject(data);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 从URL加载
     * @return Document
     */
    public static JSONObject parseJsonById(String mid){
        JSONObject jsonObject = null;
        String data = "";
        String id = "";
        try {
            if(StringUtils.isNotEmpty(mid)){
                    data = HttpClientUtils.doHttpGet("http://i.sporttery.cn/api/fb_match_info/get_pool_rs/?mid=" + mid);
                    jsonObject = JSONObject.parseObject(data);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
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
                               raceTeam.setRaceDate(ParseUtil.parseStr2Date(raceDate));
                                raceNo = tdList.get(3).childNode(0).toString();
                                raceTeam.setRaceNo(raceNo);
                                leagueName = tdList.get(5).childNode(0).toString();
                                raceTeam.setLeagueName(leagueName);
                                RaceTeamUtils.parseRaceTeam(tdList.get(7).childNode(0), raceTeam);
                                raceHomeVistTeam = tdList.get(7).childNode(0).toString();
                                if (null != tdList.get(9).childNodes() && tdList.get(9).childNodes().size() > 0 && tdList.get(9).childNode(0).childNodes().size() > 0) {

                                    raceHalfScore = tdList.get(9).childNode(0).childNode(0).toString();
                                    raceTeam.setHalfCourtScore(raceHalfScore);
                                    if(StringUtils.isNotEmpty(raceHalfScore)){
                                        String[] arr = raceHalfScore.split(":");
                                        if(null!=arr&&arr.length>0){
                                            raceTeam.setHalfHomeScore(Integer.parseInt(arr[0]));
                                            raceTeam.setHalfVisitingScore(Integer.parseInt(arr[1]));
                                        }
                                    }
                                }
                                if (null != tdList.get(11).childNodes() && tdList.get(11).childNodes().size() > 0 && tdList.get(11).childNode(0).childNodes().size() > 0) {
                                    raceFullScore = tdList.get(11).childNode(0).childNode(0).toString();
                                    if (StringUtils.isNotEmpty(raceFullScore) && raceFullScore.indexOf(":") > 0) {
                                        raceTeam.setFullHomeTeamCourtscore(Integer.parseInt(raceFullScore.split(":")[0]));
                                        raceTeam.setFullVisitingTeamCourtscore(Integer.parseInt(raceFullScore.split(":")[1]));
                                        if(Integer.parseInt(raceFullScore.split(":")[0])>Integer.parseInt(raceFullScore.split(":")[1])){
                                            raceTeam.setResult("胜");
                                        }
                                        if(Integer.parseInt(raceFullScore.split(":")[0])==Integer.parseInt(raceFullScore.split(":")[1])){
                                            raceTeam.setResult("平");
                                        }
                                        if(Integer.parseInt(raceFullScore.split(":")[0])<Integer.parseInt(raceFullScore.split(":")[1])){
                                            raceTeam.setResult("负");
                                        }
                                    }
                                }
                                if (null != tdList.get(19).childNodes() && tdList.get(19).childNodes().size() > 0) {
                                    raceResult = tdList.get(19).childNode(0).toString();
                                    raceTeam.setRaceStatus(raceResult);
                                }
                                String detailUrl = "http://info.sporttery.cn/football/";
                                if (null != tdList.get(21).childNodes() && tdList.get(21).childNodes().size() > 0) {
                                     detailUrl = "http://info.sporttery.cn/football/" + tdList.get(21).childNode(0).attributes().get("href");
                                    raceDetail = tdList.get(21).childNode(0).toString();
                                    setPeilv(raceTeam, detailUrl);

                                    if(StringUtils.isNotBlank(detailUrl)&&!detailUrl.equals("http://info.sporttery.cn/football/")){
                                        raceTeam.setResultUrl(detailUrl);
                                    }
                                }
                                System.out.println(JSON.toJSONString(raceTeam));
                                if(StringUtils.isNotBlank(detailUrl)&&!detailUrl.equals("http://info.sporttery.cn/football/")){
                                    raceTeamService.insert(raceTeam);
                                }
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
        Elements elements = doc.select(".date6.jf");
        if(null!=elements&&elements.size()>0){
            try{
                Element rangele = elements.get(1);//让球胜平负的数据
                Elements rangtrs = rangele.getElementsByTag("tr");
                int rangtrsSize = rangtrs.size();
                Element ranglastPeilvtrs = rangtrs.get(rangtrsSize - 1);//最后一次时间开的赔率
                Elements rangtds =  ranglastPeilvtrs.getElementsByTag("td");
                String  rangQiu = rangtds.get(1).childNode(0).toString();
                String  rangWin = rangtds.get(2).childNode(0).toString();
                String  rangEquals = rangtds.get(3).childNode(0).toString();
                String  rangFail = rangtds.get(4).childNode(0).toString();


                raceTeam.setRangScore(Float.parseFloat(rangQiu));
                raceTeam.setRangWin(Float.parseFloat(rangWin));
                raceTeam.setRangEq(Float.parseFloat(rangEquals));
                raceTeam.setRangFail(Float.parseFloat(rangFail));
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
        if(null==detail) return ;
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


            Element rangele = elements.get(1);//让球胜平负的数据
            Elements rangtrs = rangele.getElementsByTag("tr");
            int rangtrsSize = rangtrs.size();
            Element ranglastPeilvtrs = rangtrs.get(rangtrsSize - 1);//最后一次时间开的赔率
            Elements rangtds =  ranglastPeilvtrs.getElementsByTag("td");
            String  rangQiu = rangtds.get(1).childNode(0).toString();
            String  rangWin = rangtds.get(2).childNode(0).toString();
            String  rangEquals = rangtds.get(3).childNode(0).toString();
            String  rangFail = rangtds.get(4).childNode(0).toString();

            raceTeam.setRangScore(Float.parseFloat(rangQiu));
            raceTeam.setRangWin(Float.parseFloat(rangWin));
            raceTeam.setRangEq(Float.parseFloat(rangEquals));
            raceTeam.setRangFail(Float.parseFloat(rangFail));

            try{

                raceTeam.setWin(Float.parseFloat(win));
                raceTeam.setEquals(Float.parseFloat(equals));
                raceTeam.setFail(Float.parseFloat(fail));
            }catch (Exception e){
                logger.error(" set PeiLv fail :win:"+win+"equals:"+equals+" fail:"+fail+ JSON.toJSONString(raceTeam),e);

            }


        }



    }



    /**
     * 解析一共有多少页  每一页的url
     * @param doc
     * @return
     */
    private static List<String> getPagesUrl(Document doc){
        List<String> todayTotalPage = new ArrayList<String>();
        Elements hrefs =  doc.select(".m-page").select("a[href]");
        if(null!=hrefs&&hrefs.size()>0){
            for(Element href :hrefs){
                if(null!=href&&null!=href.attributes()&&href.attr("title").equals("尾页")){
                    continue;
                }else{
                    todayTotalPage.add("http://info.sporttery.cn/football/"+href.attr("href").toString());
                }
            }
        }
        return todayTotalPage;
    }

}