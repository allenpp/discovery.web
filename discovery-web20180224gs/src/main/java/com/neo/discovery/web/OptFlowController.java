package com.neo.discovery.web;

/**
 * Created by liuyunpeng1 on 2017/9/14.
 */

import com.neo.discovery.service.OptFlowService;
import com.neo.discovery.service.TongJiService;
import com.neo.discovery.service.WaveService;
import com.neo.discovery.util.OptType;
import com.neo.discovery.vo.OptFlow;
import com.neo.discovery.vo.ShouldDoOpt;
import com.neo.discovery.vo.TongJiDto;
import com.neo.discovery.vo.Wave;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/optFlow")
public class OptFlowController {

    @Resource
    private OptFlowService optFlowService;
    @Resource
    private WaveService waveService;
    @Resource
    private TongJiService tongJiService;
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(OptFlowController.class);



    @RequestMapping(value="/doBet" ,method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insert(HttpServletRequest request,@RequestBody OptFlow optFlow){
        Integer success = 0;
           Integer temp   =  optFlowService.insert(optFlow);
        if(null!=temp){
            success = temp;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doBet",success);
        return  modelAndView;
    }
    @RequestMapping(value="/update" ,method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView update(HttpServletRequest request,@RequestBody OptFlow optFlow){
//    public ModelAndView insert(HttpServletRequest request ){
//            int userId = Integer.parseInt(request.getParameter("id"));

//        Wave wave = buildParam(request);
        optFlowService.updateOptFlowByMatchId(optFlow);
        return  null;
    }

    @RequestMapping(value="/isDoBet" ,method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView isDoBet(HttpServletRequest request,@RequestBody Wave wave){

        OptFlow optFlow = optFlowService.isBettingRecord(null);
        ShouldDoOpt shouldDoOpt = new ShouldDoOpt();
        if(null!=optFlow){
            Wave max = null;
            TongJiDto avgNow = null;
            TongJiDto avgLast = null;
            List<Wave> list = waveService.selectMaxPeiLv(wave);
            if(null!=list && list.size()>0){
                max = list.get(0);
            }
            TongJiDto tongJiDto = new TongJiDto();
            tongJiDto.setMatchId(wave.getMatchId());
            List<TongJiDto> avgList = waveService.selectAvgByGroupTime(tongJiDto);
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
        }else{
            String haveOptType =  optFlow.getOptType();
            float haveOptPeiLv = optFlow.getOptPeiLv();
            if(haveOptType.equals(OptType.BUY_S)){
                if(wave.getSale_s1()-0.1<haveOptPeiLv){
                    logger.info("should sale {},{}",wave.getSale_s1());
                    shouldDoOpt.setOptType(OptType.SALE_S.getOptType());
                }
            }else if(haveOptType.equals(OptType.BUY_P)){
                if(wave.getSale_p1()-0.1<haveOptPeiLv){
                    logger.info("should sale {},{}",wave.getSale_s1());
                    shouldDoOpt.setOptType(OptType.SALE_P.getOptType());
                }
            }else if(haveOptType.equals(OptType.BUY_F)){
                if(wave.getSale_f1()-0.1<haveOptPeiLv){
                    logger.info("should sale {},{}",wave.getSale_s1());
                    shouldDoOpt.setOptType(OptType.SALE_F.getOptType());
                }
            }else if(haveOptType.equals(OptType.SALE_S)){
                if(wave.getBuy_s1()-0.1>haveOptPeiLv){
                    logger.info("should buy {},{}",wave.getSale_s1());
                    shouldDoOpt.setOptType(OptType.BUY_S.getOptType());
                }
            }else if(haveOptType.equals(OptType.SALE_P)){
                if(wave.getBuy_p1()-0.1>haveOptPeiLv){
                    logger.info("should buy {},{}",wave.getSale_s1());
                    shouldDoOpt.setOptType(OptType.BUY_P.getOptType());
                }
            }else if(haveOptType.equals(OptType.SALE_F)){
                if(wave.getBuy_f1() -0.1>haveOptPeiLv){
                    logger.info("should buy {},{}",wave.getSale_s1());
                    shouldDoOpt.setOptType(OptType.BUY_F.getOptType());
                }
            }




        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("shouldDoOpt",shouldDoOpt);

        return  modelAndView;
    }
}
