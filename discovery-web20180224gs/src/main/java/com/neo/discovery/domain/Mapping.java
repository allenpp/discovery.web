package com.neo.discovery.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyunpeng1 on 2018/2/2.
 */
public class Mapping {



    public  static Map<String,String> leagueNameMapping = new HashMap<String,String>();
    public  static Map<String,String> teamNameMapping = new HashMap<String,String>();



    public static void initLeagueNameMapping(){

        teamNameMapping.put("布尔萨体育","");

    }
    public static void initteamNameMapping(){

        leagueNameMapping.put("土超","土耳其超级联赛");
        leagueNameMapping.put("比甲","");
        leagueNameMapping.put("阿甲","阿根廷甲级联赛");
        leagueNameMapping.put("英超","英超联赛");
        leagueNameMapping.put("德乙","");
        leagueNameMapping.put("德甲","");
        leagueNameMapping.put("智利甲","");
        leagueNameMapping.put("英甲","");
        leagueNameMapping.put("法乙","法国乙级联赛");
        leagueNameMapping.put("法甲","法国甲级联赛");
        leagueNameMapping.put("墨超","");
        leagueNameMapping.put("意甲","");
        leagueNameMapping.put("荷甲","荷兰足球甲级联赛");
        leagueNameMapping.put("西乙","西班牙乙级联赛");
        leagueNameMapping.put("澳超","");
        leagueNameMapping.put("葡超","");
        leagueNameMapping.put("荷兰乙","");
        leagueNameMapping.put("英冠","");
        leagueNameMapping.put("苏超","");
        leagueNameMapping.put("圣保罗锦","");
        leagueNameMapping.put("意乙","意大利乙级联赛");
        leagueNameMapping.put("西甲","");
        leagueNameMapping.put("瑞超","瑞士超级联赛");

    }


}
