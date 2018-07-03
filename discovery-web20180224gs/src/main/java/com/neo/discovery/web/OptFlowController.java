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



}
