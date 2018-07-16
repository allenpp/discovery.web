package com.neo.discovery.web;

/**
 * Created by liuyunpeng1 on 2017/9/14.
 */

import com.alibaba.fastjson.JSON;
import com.neo.discovery.service.OptFlowService;
import com.neo.discovery.service.RaceTeamService;
import com.neo.discovery.service.TongJiService;
import com.neo.discovery.service.WaveService;
import com.neo.discovery.util.OptStatus;
import com.neo.discovery.util.OptType;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
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
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(OptFlowController.class);
    @Resource
    private WaveService waveService;

    @Resource
    private OptFlowService optFlowService;
    @Resource
    private TongJiService tongJiService;

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
    public String  insert(HttpServletRequest request,@RequestBody Wave wave){
//    public ModelAndView insert(HttpServletRequest request ){
//            int userId = Integer.parseInt(request.getParameter("id"));

//        Wave wave = buildParam(request);
        Integer success = waveService.insert(wave);
        return  "{\"success\":"+success+"}";
    }
    @RequestMapping(value="/update" ,method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView update(HttpServletRequest request,@RequestBody Wave wave){
//    public ModelAndView insert(HttpServletRequest request ){
//            int userId = Integer.parseInt(request.getParameter("id"));

//        Wave wave = buildParam(request);
        waveService.updateWaveByMatchId(wave);
        return  null;
    }

    @RequestMapping(value="/canDoBet" ,method = RequestMethod.POST)
    @ResponseBody
    public String canDoBet(HttpServletRequest request,@RequestBody OptFlow optFlow){
//            int userId = Integer.parseInt(request.getParameter("id"));

//        Wave temp = buildParam(request);
        List<OptFlow> result =  optFlowService.findNoHedgOptFlow(optFlow);
        String canDoBet = "false";
        if(null==result||result.size()<1){
            canDoBet  = "true";
        }
//        canDoBet = "false";
        return  "{\"canDoBet\":"+canDoBet+"}";
    }


    @RequestMapping(value="/doBet" ,method = RequestMethod.POST)
    @ResponseBody
    public String doBet(HttpServletRequest request,@RequestBody OptFlow optFlow){
        Integer success = 0;
        Integer temp   =  optFlowService.insert(optFlow);
        if(null!=temp){
            success = temp;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doBet", success);
        return  "{\"success\":"+success+"}";
    }
    @RequestMapping(value="/updateBet" ,method = RequestMethod.POST)
    @ResponseBody
    public String updateBet(HttpServletRequest request,@RequestBody OptFlow optFlow){
        Integer success = 0;
        Integer temp   =  optFlowService.updateOptFlowByBetId(optFlow);
        if(null!=temp){
            success = temp;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("success", success);
        return  "{\"success\":"+success+"}";
    }

    @RequestMapping(value="/isDoBet" ,method = RequestMethod.POST)
    @ResponseBody
    public String isDoBet(HttpServletRequest request,@RequestBody Wave wave){
        ShouldDoOpt shouldDoOpt = new ShouldDoOpt();
        shouldDoOpt.setOptType(OptType.NOTHING.getOptType());
        OptFlow optFlow =  null;
        OptFlow status0 = new OptFlow();
        status0.setMatchId(wave.getMatchId());
        status0.setStatus(OptStatus.PLACE.getOptStatus());
        optFlow = optFlowService.isBettingRecord(status0);
        if(null!=optFlow){
            shouldDoOpt.setOptType(OptType.CONFIRM_STATUS.getOptType());
            shouldDoOpt.setBetId(optFlow.getBetId());
            shouldDoOpt.setHedgingId(optFlow.getHedgingId());
            return JSON.toJSONString(shouldDoOpt);
        }

        status0.setStatus(OptStatus.OK.getOptStatus());
        optFlow = optFlowService.isBettingRecord(status0);
        if(null==optFlow){
            if(ParseUtil.isBegining(wave)){
                return JSON.toJSONString(shouldDoOpt);
            }
            TongJiDto max = null;
            TongJiDto avgNow = null;
            TongJiDto avgLast = null;
            TongJiDto param = new TongJiDto();
            param.setMatchId(wave.getMatchId());

            List<TongJiDto> list = waveService.selectMaxPeiLv(param);
            if(null!=list && list.size()>0){
                max = list.get(0);
            }
            TongJiDto tongJiDto = new TongJiDto();
            tongJiDto.setMatchId(wave.getMatchId());
            List<TongJiDto> avgList = waveService.selectAvgByGroupTime(tongJiDto);
            if(null!=avgList&&avgList.size()>=6){
                avgNow = avgList.get(0);
                avgLast = avgList.get(avgList.size()-1);
                if(avgNow.getBuy_s1_avg()<avgLast.getBuy_s1_avg()&&avgNow.getSale_s1_avg()<avgLast.getSale_s1_avg()){//现在的 buy 和 sale 都小于 一个小时前  则 先 buy
                    shouldDoOpt.setOptType(OptType.BUY_S.getOptType());
                }else if(avgNow.getBuy_p1_avg()<avgLast.getBuy_p1_avg()&&avgNow.getSale_p1_avg()<avgLast.getSale_p1_avg()) {//现在的 buy 和 sale 都小于 一个小时前  则 先 buy
                    shouldDoOpt.setOptType(OptType.BUY_P.getOptType());
                }else if(avgNow.getBuy_f1_avg()<avgLast.getBuy_f1_avg()&&avgNow.getSale_f1_avg()<avgLast.getSale_f1_avg()){//现在的 buy 和 sale 都小于 一个小时前  则 先 buy
                    shouldDoOpt.setOptType(OptType.BUY_F.getOptType());
                } else if(avgNow.getBuy_s1_avg()>avgLast.getBuy_s1_avg()&&avgNow.getSale_s1_avg()>avgLast.getSale_s1_avg()){//现在的 buy 和 sale 都小于 一个小时前  则 先 sale
                    shouldDoOpt.setOptType(OptType.SALE_S.getOptType());
                }else if(avgNow.getBuy_p1_avg()>avgLast.getBuy_p1_avg()&&avgNow.getSale_p1_avg()>avgLast.getSale_p1_avg()) {//现在的 buy 和 sale 都小于 一个小时前  则 先 sale
                    shouldDoOpt.setOptType(OptType.SALE_P.getOptType());
                }else if(avgNow.getBuy_f1_avg()>avgLast.getBuy_f1_avg()&&avgNow.getSale_f1_avg()>avgLast.getSale_f1_avg()){//现在的 buy 和 sale 都小于 一个小时前  则 先 sale
                    shouldDoOpt.setOptType(OptType.SALE_F.getOptType());
                }
            }
        }else {
            shouldDoOpt.setBetId(optFlow.getBetId());
            if(optFlow.getStatus().equals(OptStatus.OK.getOptStatus())){

                String haveOptType =  optFlow.getOptType();
                float haveOptPeiLv = optFlow.getOptPeiLv();
                if(haveOptType.equals(OptType.BUY_S.getOptType())){
                    if(wave.getSale_s1()<haveOptPeiLv-0.1|| ParseUtil.isBegining(wave)){
                        logger.info("should sale {},{}",wave.getSale_s1());
                        shouldDoOpt.setOptType(OptType.SALE_S.getOptType());
                        shouldDoOpt.setStatus(OptStatus.HEDGING.getOptStatus());
                        shouldDoOpt.setHedgingId(optFlow.getId()+"");
                    }
                }else if(haveOptType.equals(OptType.BUY_P.getOptType())){
                    if(wave.getSale_p1()<haveOptPeiLv-0.1|| ParseUtil.isBegining(wave)){
                        logger.info("should sale {},{}",wave.getSale_s1());
                        shouldDoOpt.setOptType(OptType.SALE_P.getOptType());
                        shouldDoOpt.setStatus(OptStatus.HEDGING.getOptStatus());
                        shouldDoOpt.setHedgingId(optFlow.getId() + "");
                    }
                }else if(haveOptType.equals(OptType.BUY_F.getOptType())){
                    if(wave.getSale_f1()<haveOptPeiLv-0.1|| ParseUtil.isBegining(wave)){
                        logger.info("should sale {},{}",wave.getSale_s1());
                        shouldDoOpt.setOptType(OptType.SALE_F.getOptType());
                        shouldDoOpt.setStatus(OptStatus.HEDGING.getOptStatus());
                        shouldDoOpt.setHedgingId(optFlow.getId() + "");
                    }
                }else if(haveOptType.equals(OptType.SALE_S.getOptType())){
                    if(wave.getBuy_s1()-0.1>haveOptPeiLv|| ParseUtil.isBegining(wave)){//这里注意 修改回去***********************************************************
                        logger.info("should buy {},{}",wave.getSale_s1());
                        shouldDoOpt.setOptType(OptType.BUY_S.getOptType());
                        shouldDoOpt.setStatus(OptStatus.HEDGING.getOptStatus());
                        shouldDoOpt.setHedgingId(optFlow.getId() + "");
                    }
                }else if(haveOptType.equals(OptType.SALE_P.getOptType())){
                    if(wave.getBuy_p1()-0.1>haveOptPeiLv|| ParseUtil.isBegining(wave)){
                        logger.info("should buy {},{}",wave.getSale_s1());
                        shouldDoOpt.setOptType(OptType.BUY_P.getOptType());
                        shouldDoOpt.setStatus(OptStatus.HEDGING.getOptStatus());
                        shouldDoOpt.setHedgingId(optFlow.getId() + "");
                    }
                }else if(haveOptType.equals(OptType.SALE_F.getOptType())){
                    if(wave.getBuy_f1() -0.11>haveOptPeiLv|| ParseUtil.isBegining(wave)){
                        logger.info("should buy {},{}",wave.getSale_s1());
                        shouldDoOpt.setOptType(OptType.BUY_F.getOptType());
                        shouldDoOpt.setStatus(OptStatus.HEDGING.getOptStatus());
                        shouldDoOpt.setHedgingId(optFlow.getId() + "");
                    }
                }
            }else{


            }



        }


        return JSON.toJSONString(shouldDoOpt);
    }

}