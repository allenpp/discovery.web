package com.neo.discovery.task;

import com.neo.discovery.service.TongJiService;
import com.neo.discovery.service.WaveService;
import com.neo.discovery.util.ParseUtil;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by liuyunpeng1 on 2018/6/19.
 */

@Controller
public class Schedule {

    private static Logger logger = Logger.getLogger(Schedule.class);


    @Resource
    private TongJiService tongJiService;


    /**
     * 定时任务，每天晚上1点删除数据表t_tempClob中的所有记录
     */
    @Scheduled(cron= "0/10 * *  * * ? ")  //每10秒执行一次
    public void deleteAllTempClob(){

        try {

            tongJiService.selectAvgByGroupTime();
            System.err.println("---->>begin  execute task:" + ParseUtil.parseDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }catch (Exception e){
            logger.error(e);
        }
    }



}
