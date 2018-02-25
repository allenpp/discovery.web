package com.neo.discovery.context;

import com.alibaba.fastjson.JSON;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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

/**
 * @作者 Goofy
 * @邮件 252878950@qq.com
 * @日期 2014-4-2上午10:54:53
 * @描述
 */
public class JsoupRacing {

    static ApplicationContext ct=new ClassPathXmlApplicationContext("spring-config.xml");
    static RaceTeamService raceTeamService = (RaceTeamService)ct.getBean("raceTeamService");

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(JsoupRacing.class);


    /**
     * 从URL加载
     * @return Document
     */
    public static String parseDocumentFromUrl(String url){
        try {

                String str =   HttpClientUtils.doHttpsPost("https://bd-api.jdd.com/basedata/public/securityMobileHandler.do",null,"utf-8");

                return  str;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void main(String[] args){
//        action();


//        List<String> todayTotalPage = new ArrayList<String>();
        String  json =  parseDocumentFromUrl(null);
//        String json ="";

//        List<RaceTeam> list = ParseUtil.parseJsonStr(json);
//        raceTeamService.selectHasDoneRaceTeamByJdd();

    }



}