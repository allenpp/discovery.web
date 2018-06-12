package com.neo.discovery.service.impl;

import com.neo.discovery.service.NoticeRule;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.util.mail.MailUtil;
import com.neo.discovery.vo.Wave;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 3/10/2018.
 */
@Service("noticeMatchDate")
public class NoticeMatchDate implements NoticeRule {

    private Map<String,Integer> count = new HashMap<String,Integer>();
    @Override
    public void notice(Wave wave) {

        try{

            if(null!=wave.getMatchDate()){
                String matchName = wave.getLeagueName().replace("\n", "").replace(" ","") +"@"+ wave.getHome()+"V"+wave.getAway();
                String matchId = wave.getMatchId().toString();
                GregorianCalendar gc=new GregorianCalendar();
                try {
                    gc.setTime( wave.getMatchDate());
                    gc.add(java.util.Calendar.HOUR_OF_DAY, -2);

                    Date matchDate = gc.getTime();
                    Date now = new Date();
                    if(now.after(matchDate)&&now.before(wave.getMatchDate())){
                        if(sendNoticeMail(count,wave.getMatchId().toString())){
                            MailUtil.sendMail(matchName + " matchDate is close  "+ParseUtil.parseDate2Str(wave.getMatchDate(),"yyyy-MM-dd HH:mm:ss"), " match is begin ");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private boolean sendNoticeMail(Map<String,Integer> map,String matchId){
        if(null==map.get(matchId)){
            map.put(matchId,new Integer(1));
            return false;
        }else{
            Integer nowCount = map.get(matchId)+1;
            if(nowCount>40){
                map.put(matchId,new Integer(1));
                return true;
            }else{
                map.put(matchId,nowCount);
                return false;
            }
        }
    }


    public static  void main(String[] args){

        GregorianCalendar gc=new GregorianCalendar();
        try {
            gc.setTime( new Date());
            gc.add(java.util.Calendar.HOUR_OF_DAY,-1);
            Date now =  gc.getTime();
            Date matchDate = ParseUtil.parseStr2Time("2018-03-16 01:00:00");
            if(now.before(matchDate)){

                System.out.print("aaaaaa");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
