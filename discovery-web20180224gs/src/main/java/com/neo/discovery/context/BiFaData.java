package com.neo.discovery.context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.discovery.util.HttpClientUtils;
import com.neo.discovery.util.HttpsUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.util.*;

/**
 * Created by liuyunpeng1 on 2018/2/2.
 */
public class BiFaData {


//    public static String cookieStr =  "__utmz=212350934.1517320346.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); _qddaz=QD.q7a31g.c0ugbz.jd1pcw42; pgv_pvi=1778713600; tencentSig=5332117504; ASP.NET_SessionId=fwmvuar0kdpgyxgqdbwioxt2; __utmc=212350934; IESESSION=alive; pgv_si=s1352643584; __utma=212350934.1652295010.1517320346.1517477707.1517541481.6; _qddab=3-lv04we.jd5d0l6z; GuidCookie=guid=034b26ea-e353-4e20-863a-7618a0a84580; UserCookie=UserId=kRqO8DDKHmk=&UserName=Dk8Blqvx8kYx8ocQKhEOZQ==&RoleId=NDHQ7ow2zSQ=&UserState=B0Rg9+PXfW4=&LastActivityDate=c7UFcK5Vg55+p2sBLMYd2BtE+XHQ3289&RegisterDate=1W4mzVOSbLFgMPjdDtmYRJOq7H/4laI4&EndDate=WKH407zFb6VLNPSITq10MoeCb25f+ywD&LastIp=JH186fT4kcsZANNIsBDa+A==&RoleName=9EbFaLqsbtlu10Kr/zDgWw==&Email=c0DNCMqVERZNTAy0xCkOfJ2MQfoZboil&EndDays=TILRNs8kc14=&Referred=cQtL6+/niQk=&NickName=UekhCU2a/lY=; CodeCookie=CodeStr=d8803f50665a70e77a9b93005bb24702";
//    public static String cookieStr =  "ASP.NET_SessionId=ekfl1bcwrp0wf0pawmu1hskc; __utmt=1; __utma=212350934.358740411.1517666800.1517666800.1517666800.1; __utmb=212350934.1.10.1517666800; __utmc=212350934; __utmz=212350934.1517666800.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; IESESSION=alive; _qddaz=QD.ruma5.cvk7sn.jd7fmn8n; pgv_pvi=5828896768; pgv_si=s663606272; _qddamta_800170782=3-0; tencentSig=8820491264; GuidCookie=guid=fdbecaf7-27c2-40a8-9882-bbd4c1e1d2f0; UserCookie=UserId=kRqO8DDKHmk=&UserName=Dk8Blqvx8kYx8ocQKhEOZQ==&RoleId=NDHQ7ow2zSQ=&UserState=B0Rg9+PXfW4=&LastActivityDate=WsrOkykW5ysZtiewe2sweapzFxZwlzmo&RegisterDate=1W4mzVOSbLFgMPjdDtmYRJOq7H/4laI4&EndDate=WKH407zFb6VLNPSITq10MoeCb25f+ywD&LastIp=xzvyLd71ucY7Gq5TGOyE9g==&RoleName=9EbFaLqsbtlu10Kr/zDgWw==&Email=c0DNCMqVERZNTAy0xCkOfJ2MQfoZboil&EndDays=TILRNs8kc14=&Referred=cQtL6+/niQk=&NickName=UekhCU2a/lY=; _qdda=3-1.3temyu; _qddab=3-v5zos5.jd7fmn8p; CodeCookie=CodeStr=3e97a543a8bfcb18ff8a368592d15e64";
//    public static String cookieStr =  "pgv_pvi=5828896768; tencentSig=8820491264; ASP.NET_SessionId=disbor1exwwvxecig4mmsnds; __utmt=1; __utma=212350934.358740411.1517666800.1517670108.1517753881.3; __utmb=212350934.1.10.1517753881; __utmc=212350934; __utmz=212350934.1517753881.3.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; IESESSION=alive; _qddaz=QD.ruma5.cvk7sn.jd7fmn8n; pgv_si=s5644508160; _qddamta_800170782=3-0; GuidCookie=guid=5abf65ca-2122-4f9c-a832-396399aa2ce4; UserCookie=UserId=kRqO8DDKHmk=&UserName=Dk8Blqvx8kYx8ocQKhEOZQ==&RoleId=NDHQ7ow2zSQ=&UserState=B0Rg9+PXfW4=&LastActivityDate=thAZEct8lOwmQEt13+n2J1ZAQobhaV3P&RegisterDate=1W4mzVOSbLFgMPjdDtmYRJOq7H/4laI4&EndDate=WKH407zFb6VLNPSITq10MoeCb25f+ywD&LastIp=xzvyLd71ucahz4A0pvQVWA==&RoleName=9EbFaLqsbtlu10Kr/zDgWw==&Email=c0DNCMqVERZNTAy0xCkOfJ2MQfoZboil&EndDays=TILRNs8kc14=&Referred=cQtL6+/niQk=&NickName=UekhCU2a/lY=; _qdda=3-1.eofs3; _qddab=3-jy837v.jd8vh1tx; CodeCookie=CodeStr=b16bc510a39d8f023f1c89d44e3cdd26";
    public static String cookieStr =  "__utmz=212350934.1517320346.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); _qddaz=QD.q7a31g.c0ugbz.jd1pcw42; pgv_pvi=1778713600; tencentSig=5332117504; ASP.NET_SessionId=ck120puws2nrfjdmp4lqbzmj; __utmc=212350934; IESESSION=alive; pgv_si=s5532570624; __utma=212350934.1652295010.1517320346.1518096345.1518157530.14; __utmt=1; _qdda=3-1.1; _qddab=3-jn50ul.jdfjsnrm; _qddamta_800170782=3-0; GuidCookie=guid=08fc2874-8ad9-404b-8f6c-f0ee400b4e5e; _qddac=3-2-1.1.jn50ul.jdfjsnrm; __utmb=212350934.2.10.1518157530; UserCookie=UserId=kRqO8DDKHmk=&UserName=Dk8Blqvx8kYx8ocQKhEOZQ==&RoleId=NDHQ7ow2zSQ=&UserState=B0Rg9+PXfW4=&LastActivityDate=8T+RsuFaXYXY51opCApMBQbYthiwkSN6&RegisterDate=1W4mzVOSbLFgMPjdDtmYRJOq7H/4laI4&EndDate=WKH407zFb6VLNPSITq10MoeCb25f+ywD&LastIp=JH186fT4kcsAGH0nI93qNQ==&RoleName=9EbFaLqsbtlu10Kr/zDgWw==&Email=c0DNCMqVERZNTAy0xCkOfJ2MQfoZboil&EndDays=TILRNs8kc14=&Referred=cQtL6+/niQk=&NickName=UekhCU2a/lY=; CodeCookie=CodeStr=563065d7e2135a186ea0cf99ca826314";


    public Map<String,String> leagueNameMapping = new HashMap<String, String>();
    public Map<String,String> teamMapping = new HashMap<String, String>();

    public static void initParam(){




    }

    /**
     * ��URL����
     * @return Document
     */
    public static Document parseDocumentFromUrl(String url,Map<String,String> cookies){
        Document doc = null;
        try {
            if(StringUtils.isNotEmpty(url)){
                doc = Jsoup.connect(url).timeout(10000).cookies(cookies).get();
            }else{
                doc = Jsoup.connect("http://info.sporttery.cn/football/match_result.php").data("start_date","2017-11-28","end_date","2017-12-04","search_league","0").timeout(1000000).get();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    /**
     * ��URL����
     * @return Document
     */
    public static Document parseDocumentFromHtml(String html){
        Document doc = null;
        try {

            Jsoup.parse(html);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());

        }
        return doc;
    }




    public static Map<String,String> buildCookie(String str){
        Map<String,String> cookies = new HashMap<String, String>();
        if(StringUtils.isNotBlank(str)){
            String[] arrs = str.split(";");
            if(null!=arrs){
                for(String cookie:arrs){
                    String key = cookie.substring(0, cookie.indexOf("="));
                    String value = cookie.substring(cookie.indexOf("=")+1);
                    cookies.put(key,value);
                }
            }
        }
        return cookies;
    }

    public static  int getPages( ) {
        String url = "http://c.spdex.com/dv_1_0_0_0_0_0";
        Map<String,String> cookies = buildCookie(cookieStr);
        Element pages = null;
        try{

            Document doc = parseDocumentFromUrl(url, cookies);
            Elements manu = doc.select("div.manu");
            pages = (Element) manu.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        int pageInt = 0;
        if(null!=pages){
            pageInt  = pages.childNodeSize() - 2;

        }
        return pageInt;
    }


    public static  Map<String,String> getOneMatch(Document doc){
        Map<String,String> map = new HashMap<String, String>();
        try{
            if(null!=doc){
                Elements elements =doc.select("#dataContainer");
                Elements dataTitle = elements.select("div.datatitle");
                Object[]  objects = dataTitle.toArray();
                if(null!=objects){
                    for(Object obj:objects){
                        try{
                            Element ele = (Element)obj;
                            String id = ele.select("div.datatitle").attr("id");
                            String team_league = ele.select(".team_league").text();
                            if(null!=team_league){
                                team_league = team_league.replace("英超联赛","英超").replace("法国甲级联赛","法甲").replace("荷兰足球甲级联赛","荷甲").replace("西班牙乙级联赛","西乙");
                                team_league = team_league.replace("瑞士超级联赛","瑞超").replace("土耳其超级联赛","土超").replace("意大利乙级联赛","意乙").replace("阿根廷甲级联赛","阿甲");
                                team_league = team_league.replace("法国乙级联赛","法乙");
                            }
                            String team_home = ele.select(".team_home").text();
                            String team_away = ele.select(".team_away").text();
                            String value = team_league+"@"+team_home+"="+team_away;
                            map.put(id,value);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(map));
        return map;
    }
    public static  Map<String,Map<String,Float>>   getBiFaOneMatchPrice(String ids, Map<String,String> idName,Map<String,Map<String,Float>> bifaSpfMap ){
        if(null!=ids){
            String[] idsArr = ids.split(",");
            if(null==idsArr||idsArr.length<1){
                return bifaSpfMap;
            }
            for(String matchId:idsArr){
                String url = "http://c.spdex.com/Match/View/Normal/"+matchId+"?history=";
                Document doc = parseDocumentFromUrl(url,buildCookie(cookieStr));
                try{
                    if(null!=doc){
                        Elements elements =doc.select(".ddvtable");
                        Object[]  objects = elements.toArray();
                        if(null!=objects){
                            for(Object obj:objects){
                                try{
                                    Map<String,Float> map = new HashMap<String, Float>();
                                    Element ele = (Element)obj;
                                    Elements trs = ele.getElementsByTag("tr");
                                    if(null!=trs&&null!=trs.get(2)){
                                        Elements tds = trs.get(2).getAllElements();
                                        if(tds.size()<12){
                                            continue;
                                        }
                                        Element sEle = tds.get(2);
                                        Element pEle = tds.get(7);
                                        Element fEle = tds.get(12);
                                        Float s = Float.parseFloat(sEle.text());
                                        Float p = Float.parseFloat(pEle.text());
                                        Float f = Float.parseFloat(fEle.text());

                                        map.put("s",s);
                                        map.put("p",p);
                                        map.put("f",f);
                                        String team_league = idName.get(matchId);
                                        team_league = replaceLeagueName(team_league);
                                        bifaSpfMap.put(team_league,map);

                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println(JSON.toJSONString(bifaSpfMap));
        }
        return bifaSpfMap;
    }



    public static Map<String,Map<String,Float>>  getAllMatchs(int pages ){
        Map<String,Map<String,Float>> finalMap = new HashMap<String, Map<String,Float>>();
        try{
            Map<String,String> cookies = buildCookie(cookieStr);
            for( int i=1;i<=pages;i++){
                String url = "http://c.spdex.com/dv_"+i+"_0_0_0_0_0";
                Document doc = parseDocumentFromUrl(url, cookies);
                Map<String,String> map = getOneMatch(doc);
                String ids = buildIds(map);
                String json = ajaxBifa(ids);
//                getBiFaOneMatchPrice(ids,map,finalMap);
                parseBiFaJson(json,map,finalMap);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return finalMap;
    }






    public static Float jiSuanBuySale(Float buy,Float buyRate,Float sale,Float saleRate){

        if(null!=buy&&null!=sale&&null!=buyRate&&null!=saleRate){

            Float win = buy*buyRate-sale*saleRate;
            Float fail = buy*buyRate/saleRate-buy;

            System.out.println("buy:"+buy+" sale:"+buy*buyRate/saleRate);
        }
        return null;
    }




    public static void sortByLeague( Map<String,Map<String,Float>> bifaSpfMap, Map<String,Map<String,Float>> jinDouSpfMap){
        Map<String,List<String>> leagueTeamMap = new HashMap<String, List<String>>();
        if(null!=bifaSpfMap&&bifaSpfMap!=null){
            for(Map.Entry  entry:bifaSpfMap.entrySet()){
                String key = entry.getKey().toString();
                String leagueName = key.split("@")[0];
                String teamName = key.split("@")[1];
                teamName +="("+entry.getValue()+")";
                List<String> list = null;
                if(leagueTeamMap.get(leagueName)==null){
                    list = new ArrayList<String>();
                    list.add(teamName);
                    leagueTeamMap.put(leagueName, list);
                }else{
                    list = leagueTeamMap.get(leagueName);
                    list.add(teamName);
                }

            }

            }
        Map<String,List<String>> jinDouleagueTeamMap = new HashMap<String, List<String>>();
        if(null!=jinDouSpfMap&&jinDouSpfMap!=null){
                for(Map.Entry  entry:jinDouSpfMap.entrySet()){
                    String key = entry.getKey().toString();
                    String leagueName = key.split("@")[0];
                    String teamName = key.split("@")[1];
                    teamName +="("+entry.getValue()+")";
                    List<String> list = null;
                    if(jinDouleagueTeamMap.get(leagueName)==null){
                        list = new ArrayList<String>();
                        list.add(teamName);
                        jinDouleagueTeamMap.put(leagueName,list);
                    }else{
                        list = jinDouleagueTeamMap.get(leagueName);
                        list.add(teamName);
                    }

                }
            }


        System.out.println("bifaJson="+JSON.toJSONString(leagueTeamMap));
        System.out.println("jinDouJson="+JSON.toJSONString(jinDouleagueTeamMap));


    }


    public static void compare( Map<String,Map<String,Float>> jinDouSpfMap, Map<String,Map<String,Float>> bifaSpfMap){
        if(null!=jinDouSpfMap&&bifaSpfMap!=null){
            StringBuffer jindouLeagueName = new StringBuffer();
            StringBuffer jindouTeamName = new StringBuffer();
            for(Map.Entry  entry:jinDouSpfMap.entrySet()){
                Map<String,Float> bifa = jinDouSpfMap.get(entry.getKey());
                jindouLeagueName.append(""+entry.getKey().toString().split("@")[0]).append("|");
                jindouTeamName.append(""+entry.getKey().toString().split("@")[1]).append("|");
                if(null!=bifa){
                    Float bifa_s = bifa.get("s");
                    Float bifa_p = bifa.get("p");
                    Float bifa_f = bifa.get("f");

                    Map<String,Float> jindou = jinDouSpfMap.get(entry.getKey());
                    Float jindou_s = jindou.get("s");
                    Float jindou_p = jindou.get("p");
                    Float jindou_f = jindou.get("f");
                    if(bifa_s<jindou_s){
                        System.out.println(" find  the match key:"+entry.getKey()+" bifa:"+JSONObject.toJSONString(bifa)+"  jindou"+JSONObject.toJSONString(jindou));
                    }
                    if(bifa_p<jindou_p){
                        System.out.println(" find  the match key:"+entry.getKey()+" bifa:"+JSONObject.toJSONString(bifa)+"  jindou"+JSONObject.toJSONString(jindou));
                    }
                    if(bifa_f<jindou_f){
                        System.out.println(" find  the match key:"+entry.getKey()+" bifa:"+JSONObject.toJSONString(bifa)+"  jindou"+JSONObject.toJSONString(jindou));
                    }

                }
            }

            StringBuffer bifaLeagueName = new StringBuffer();
            StringBuffer bifaTeaamName = new StringBuffer();
            for(Map.Entry  entry:bifaSpfMap.entrySet()) {
                Map<String, Float> bifa = bifaSpfMap.get(entry.getKey());
                bifaLeagueName.append(entry.getKey().toString().split("@")[0]).append("|");
                bifaTeaamName.append(entry.getKey().toString().split("@")[1]).append("|");
            }
            System.out.println("bifaLeagueName:" + bifaLeagueName.toString());
            System.out.println("jindouLeagueName:" + jindouLeagueName.toString());
            System.out.println("bifaTeamName:" + bifaTeaamName.toString());
            System.out.println("jindouTeamName:" + jindouTeamName.toString());
        }
    }

    public static String buildIds(Map<String,String> map){
        String  ids = "";
        if(null!=map){
            try{

                Set<String> keys = map.keySet();
                for(String str :keys){
                    ids+=str+",";
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ids;
    }


    public static String ajaxBifa(String ids){

//        String url ="https://c.spdex.com/api/Json_Get_EventByIds.aspx?eid=28562315,28563128,28560803,28560801,28560786"+ids;
        String url ="https://c.spdex.com/api/Json_Get_EventByIds.aspx?eid="+ids;
        String response = HttpClientUtils.doHttpsGetWithCookie(url,null,"utf-8",cookieStr);

        System.out.println(response);

        return response;
    }

    public static Map<String,Map<String,Float>> parseBiFaJson(String json,Map<String,String> idName,Map<String,Map<String,Float>> map){
//        Map<String,Map<String,Float>> map = new HashMap<String, Map<String,Float>>();
        System.out.println("json :"+json);

        if(null!=json){
            JSONArray array = (JSONArray) JSONArray.parse(json);
            if(null!=array){
                for(Object obj:array){
                    try{
                        Map<String,Float> spf = new HashMap<String, Float>();
                        JSONObject jsonObject = (JSONObject)obj;
                        String EventId = jsonObject.getString("EventId");
                        String BfOddsHome = jsonObject.getString("BfOddsHome");
                        String BfOddsDraw = jsonObject.getString("BfOddsDraw");
                        String BfOddsAway = jsonObject.getString("BfOddsAway");
                        try{
                                spf.put("s",Float.parseFloat(BfOddsHome));
                                spf.put("p",Float.parseFloat(BfOddsDraw));
                                spf.put("f",Float.parseFloat(BfOddsAway));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            String leagueHomeVisit = idName.get(EventId);
                            map.put(leagueHomeVisit,spf);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return map;
    }



    public static  Map<String,Map<String,Float>>  parseJinDou(){
        Map<String,Map<String,Float>> map = new HashMap<String,Map<String,Float>>();
       String json =  HttpClientUtils.jinDou();

        if(null!=json){
            try{

                JSONObject object = JSONObject.parseObject(json);
                JSONObject data = object.getJSONObject("data");
                JSONArray matchList = data.getJSONArray("matchList");
                JSONObject oddsList = data.getJSONObject("oddsList");

                if(null!=matchList) {
                    Object[] objects = matchList.toArray();
                    if (null != objects) {
                        for (Object obj : objects) {
                            String matchid = null;
                            try{

                                JSONObject match = (JSONObject) obj;
                                  matchid = match.getString("matchid");
                                String home = match.getString("home");
                                String away = match.getString("away");
                                String tournament = match.getString("tournament");

                                 tournament = replaceLeagueName(tournament);

                                JSONObject thematch = oddsList.getJSONObject(matchid);
                                JSONObject spf = thematch.getJSONObject("spf");
                                JSONArray odds = (JSONArray) spf.get("odds");

                                Map<String, Float> spfValue = new HashMap<String, Float>();
                                if (null != odds && odds.size() == 3) {
                                    String s = odds.get(0).toString();
                                    String p = odds.get(1).toString();
                                    String f = odds.get(2).toString();
                                    try {

                                        spfValue.put("s", Float.parseFloat(s));
                                        spfValue.put("p", Float.parseFloat(p));
                                        spfValue.put("f", Float.parseFloat(f));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                String key = tournament + "@" + home + "=" + away;
                                map.put(key, spfValue);
                            }catch (Exception e){
                                e.printStackTrace();
                                System.out.println("matchid:"+matchid+""+e.getStackTrace());
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return map;

    }



    public static String replaceLeagueName(String leagueName){
        String afterReplace = "";
        afterReplace = leagueName.replace("英超联赛","英超").replace("法国甲级联赛","法甲").replace("荷兰足球甲级联赛","荷甲").replace("西班牙乙级联赛","西乙");
        afterReplace = afterReplace.replace("瑞士超级联赛","瑞超").replace("土耳其超级联赛","土超").replace("意大利乙级联赛","意乙").replace("阿根廷甲级联赛", "阿甲");
        afterReplace = afterReplace.replace("法国乙级联赛","法乙").replace("丹麦足球超级联赛","丹超").replace("荷兰乙","荷乙").replace("智利甲","智甲".replace("瑞士甲级联赛","瑞甲"));
        return afterReplace;
    }

    /**
     * 已必发的为标准
     * @param teamName
     * @return
     */
    public static String replaceJinDouTeamName(String teamName){
        String afterReplace = "";
        afterReplace = teamName.replace("西汉姆","西汉姆联").replace("Newell'S Old Boys","纽维尔老男孩").replace("拜仁","拜仁慕尼黑").replace("士柏","费拉拉SPAL");
        afterReplace = afterReplace.replace("昆士兰狮吼","布里斯本狮吼").replace("热刺","托特纳姆热刺").replace("巴拉多利德","巴利亚").replace("毕尔巴鄂", "毕尔巴鄂竞技");
        afterReplace = afterReplace.replace("拉帕马斯", "拉斯帕尔马斯");
        return afterReplace;
    }




    public static void main(String[] args) {
        int pages = getPages();
        Map<String,Map<String,Float>> bifaSpfMap  = getAllMatchs(pages);
        System.out.println("bifaSpfMap"+JSON.toJSON(bifaSpfMap));
        Map<String,Map<String,Float>> jinDouSpfMap = parseJinDou();


        System.out.println("jinDouSpfMap"+JSON.toJSON(jinDouSpfMap));

        sortByLeague(bifaSpfMap,jinDouSpfMap);
//
//        compare(jinDouSpfMap,bifaSpfMap);


//        jiSuanBuySale(30f,1.5f,30f,1.4f);



    }
}
