package com.neo.discovery.web;

/**
 * Created by liuyunpeng1 on 2017/9/14.
 */

import com.neo.discovery.service.RaceTeamService;
import com.neo.discovery.service.WaveService;
import com.neo.discovery.vo.RaceTeam;
import com.neo.discovery.vo.Wave;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/wave")
public class WaveController {

    @Resource
    private WaveService waveService;

    private Wave buildParam(HttpServletRequest request){

        Wave wave = new Wave();

        try{

            String    id  =  request.getParameter("");
            String  matchId   =  request.getParameter("matchId");
            String   buy_s1  =  request.getParameter("buy_s1");
            String   buy_p1  =  request.getParameter("buy_p1");
            String   buy_f1  =  request.getParameter("buy_f1");
            String  buy_s1_amount   =  request.getParameter("buy_s1_amount");
            String   buy_p1_amount  =  request.getParameter("buy_p1_amount");
            String   buy_f1_amount  =  request.getParameter("buy_f1_amount");
            String   sale_s1  =  request.getParameter("sale_s1");
            String   sale_p1  =  request.getParameter("sale_p1");
            String   sale_f1  =  request.getParameter("sale_f1");
            String   sale_s1_amount  =  request.getParameter("sale_s1_amount");
            String   sale_p1_amount  =  request.getParameter("sale_p1_amount");
            String   sale_f1_amount  =  request.getParameter("sale_f1_amount");
            String   createTime  =  request.getParameter("reateTime");
            String   matchDate  =  request.getParameter("matchDate");
            String    home =  request.getParameter("home");
            String    away =  request.getParameter("away");
            String   leagueName  =  request.getParameter("leagueName");
            String   json  = request.getParameter("json");


            wave.setAway(away);
            wave.setMatchId(Integer.parseInt(matchId));
            wave.setBuy_s1(Float.parseFloat(buy_s1));
            wave.setBuy_p1(Float.parseFloat(buy_p1));
            wave.setBuy_f1(Float.parseFloat(buy_f1));
            wave.setBuy_s1_amount(Float.parseFloat(buy_s1_amount));
            wave.setBuy_p1_amount(Float.parseFloat(buy_p1_amount));
            wave.setBuy_f1_amount(Float.parseFloat(buy_f1_amount));
            wave.setSale_s1(Float.parseFloat(sale_s1));
            wave.setSale_p1(Float.parseFloat(sale_p1));
            wave.setSale_f1(Float.parseFloat(sale_f1));

            wave.setSale_s1_amount(Float.parseFloat(sale_s1_amount));
            wave.setSale_p1_amount(Float.parseFloat(sale_p1_amount));
            wave.setSale_f1_amount(Float.parseFloat(sale_f1_amount));

            wave.setCreateTime(new Date());
            wave.setMatchDate(new Date());
            wave.setHome(home);

            wave.setLeagueName(leagueName);
        }catch (Exception e){
            e.printStackTrace();
        }

        return wave;
    }


    @RequestMapping(value="/insert" ,method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insert(HttpServletRequest request,@RequestBody Wave wave){
//    public ModelAndView insert(HttpServletRequest request ){
//            int userId = Integer.parseInt(request.getParameter("id"));

//        Wave wave = buildParam(request);
        waveService.insert(wave);
        return  null;
    }

    @RequestMapping(value="/insertGet" ,method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView insertGet(HttpServletRequest request,@RequestBody Wave wave){
//            int userId = Integer.parseInt(request.getParameter("id"));

//        Wave temp = buildParam(request);
        waveService.insert(wave);
        return  null;
    }
}
