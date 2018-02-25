package com.neo.discovery.web;

/**
 * Created by liuyunpeng1 on 2017/9/14.
 */

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.neo.discovery.service.RaceTeamService;
import com.neo.discovery.service.WaveService;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.vo.ChartVo;
import com.neo.discovery.vo.RaceTeam;
import com.neo.discovery.vo.Wave;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/main")
public class TongJiController {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(TongJiController.class);
    @Resource
    private RaceTeamService raceTeamService;

    @Resource
    private WaveService waveService;

        @RequestMapping("/showMain")
        public ModelAndView toIndex(HttpServletRequest request,Model model){
//            int userId = Integer.parseInt(request.getParameter("id"));

           List<RaceTeam> list  = raceTeamService.selectBaoLeng(null);
            ModelAndView md = new ModelAndView();
            md.addObject("list",list);

            return new ModelAndView("main");
        }
        @RequestMapping("/showZheXian")
        public ModelAndView showZheXian(HttpServletRequest request,Model model){
//            int userId = Integer.parseInt(request.getParameter("id"));

            ModelAndView md = new ModelAndView("main");
            try{
                Wave wave = new Wave();
                wave.setMatchId(28577378);
                List<Wave> list  = waveService.selectWaveList(wave);
                ChartVo chartVo = buildChartVo(list);
                md.addObject("chartVo", JSON.toJSONString(chartVo));
                md.addObject("buy_s1_list", JSON.toJSONString(chartVo.getSeriesList().get("buy_s1_list")));
                md.addObject("buy_p1_list", JSON.toJSONString(chartVo.getSeriesList().get("buy_p1_list")));
                md.addObject("buy_f1_list", JSON.toJSONString(chartVo.getSeriesList().get("buy_f1_list")));
                md.addObject("amountAllList", JSON.toJSONString(chartVo.getSeriesList().get("amountAllList")));


                md.addObject("sale_s1_list", JSON.toJSONString(chartVo.getSeriesList().get("sale_s1_list")));
                md.addObject("sale_p1_list", JSON.toJSONString(chartVo.getSeriesList().get("sale_p1_list")));
                md.addObject("sale_f1_list", JSON.toJSONString(chartVo.getSeriesList().get("sale_f1_list")));
                md.addObject("sale_amountAllList", JSON.toJSONString(chartVo.getSeriesList().get("sale_amountAllList")));
                md.addObject("buy_sale_rate_list", JSON.toJSONString(chartVo.getSeriesList().get("buy_sale_rate_list")));
//                md.addObject("xAxis", JSON.toJSONString(chartVo.getxAxis()));
                md.addObject("xAxis", JSON.toJSONString(chartVo.getxAxis()).replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"",""));
            }catch (Exception e){
                logger.error("",e);
            }
            return md;
        }



       public ChartVo buildChartVo(List<Wave> list){
           ChartVo chartVo = new ChartVo();

           List<String> xTime = new ArrayList<String>();
           List<Map<String,List<Float>>> seriesList = new ArrayList<Map<String,List<Float>>>();
           if(null!=list){

               Map<String,List<Float>> chartMap = new HashMap<String, List<Float>>();
               List<Float> amountBuyAllList = new ArrayList<Float>();
               List<Float> buy_s1_list = new ArrayList<Float>();
               List<Float> buy_p1_list = new ArrayList<Float>();
               List<Float> buy_f1_list  = new ArrayList<Float>();
               List<String>  date_list  = new ArrayList<String>();

               List<Float> sale_amountAllList = new ArrayList<Float>();
               List<Float> sale_s1_list = new ArrayList<Float>();
               List<Float> sale_p1_list = new ArrayList<Float>();
               List<Float> sale_f1_list  = new ArrayList<Float>();

               List<Float> buy_sale_rate_list  = new ArrayList<Float>();
               for(Wave wave:list){
                   try{

                       Float allAmount= wave.getBuy_f1_amount()+wave.getBuy_f2_amount()+wave.getBuy_f3_amount();
                       amountBuyAllList.add(allAmount);
                       buy_f1_list.add(wave.getBuy_f1());
                       buy_p1_list.add(wave.getBuy_p1());
                       buy_s1_list.add(wave.getBuy_s1());



                       Float sale_allAmount= wave.getSale_f1_amount()+wave.getSale_f2_amount()+wave.getSale_f3_amount();
                       sale_amountAllList.add(sale_allAmount);
                       sale_f1_list.add(wave.getSale_f1());
                       sale_p1_list.add(wave.getSale_p1());
                       sale_s1_list.add(wave.getSale_s1());



                       Float buy_sale_rate = allAmount/sale_allAmount;
                       buy_sale_rate_list.add(buy_sale_rate);


                       date_list.add(ParseUtil.parseDate2Str(wave.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                   }catch (Exception e){
                       logger.error("",e);
                   }
               }




               chartMap.put("amountAllList", amountBuyAllList);
               chartMap.put("buy_s1_list", buy_s1_list);
               chartMap.put("buy_p1_list", buy_p1_list);
               chartMap.put("buy_f1_list", buy_f1_list);


               chartMap.put("sale_amountAllList", sale_amountAllList);
               chartMap.put("sale_f1_list", sale_f1_list);
               chartMap.put("sale_p1_list", sale_p1_list);
               chartMap.put("sale_s1_list", sale_s1_list);

               chartMap.put("buy_sale_rate_list", buy_sale_rate_list);

               chartVo.setSeriesList(chartMap);
               chartVo.setxAxis(date_list);
           }


           return chartVo;

       }
}
