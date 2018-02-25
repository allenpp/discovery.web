package com.neo.discovery.context;


import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.vo.RaceTeam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by liuyunpeng1 on 2017/9/11.
 */
public class TongJiContext extends JsoupContext{


    /**
     * *gc.add(1,-1)表示年份减一.
     *gc.add(2,-1)表示月份减一.
     *gc.add(3.-1)表示周减一.
     *gc.add(5,-1)表示天减一.
     */
    public static List<String> getRaceByDate(){
        List<String> days = new ArrayList<String>();
        SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc=new GregorianCalendar();
        for(int i=0;i<60;i++){
            gc.setTime(new Date());
            gc.add(5, -i);
            sf.format(gc.getTime());
            logger.info(sf.format(gc.getTime()));
            try{
                days.add( sf.format(gc.getTime()));
            }catch (Exception e){

            }
        }
        return days;
    }





    public static void main(String[] args){

        queryRaceTeamService.findYingLiRate();
//        queryRaceTeamService.tongjiBiFenRate();

    }




    public static void  zuhe(List<String> oneList,List<String> twoList ,List<String> threeList){
        if(null!=oneList&&oneList.size()>0&&null!=twoList&&twoList.size()>0){
            for(String one:oneList ){
                for(String two:twoList){
                    for(String three:threeList){
                        logger.info(""+one+"--"+two+"--"+three);

                    }
                }

            }
        }
    }
}
