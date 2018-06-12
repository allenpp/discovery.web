package com.neo.discovery.service.impl;

import com.alibaba.fastjson.JSON;
import com.neo.discovery.service.NoticeRule;
import com.neo.discovery.util.mail.MailUtil;
import com.neo.discovery.vo.Wave;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 3/10/2018.
 */
@Service("noticeChange")
public class NoticeChange implements NoticeRule {
    @Override
    public void notice(Wave wave) {

        try{

            if(1==1)  return ;
            GregorianCalendar gc=new GregorianCalendar();
            try {

                 Date now = new Date();
                if (now.after( wave.getMatchDate())) {
                    return ;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            String matchName = wave.getLeagueName().replace("\n", "").replace(" ","") +"@"+ wave.getHome()+"V"+wave.getAway();
            Float buy_s_AllAmount = wave.getBuy_s1_amount()+wave.getBuy_s2_amount()+wave.getBuy_s3_amount();
            Float buy_p_AllAmount = wave.getBuy_p1_amount()+wave.getBuy_p2_amount()+wave.getBuy_p3_amount();
            Float buy_f_AllAmount = wave.getBuy_f1_amount()+wave.getBuy_f2_amount()+wave.getBuy_f3_amount();

            Float sale_s_AllAmount = wave.getSale_s1_amount()+wave.getSale_s2_amount()+wave.getSale_s3_amount();
            Float sale_p_AllAmount = wave.getSale_p1_amount()+wave.getSale_p2_amount()+wave.getSale_p3_amount();
            Float sale_f_AllAmount = wave.getSale_f1_amount()+wave.getSale_f2_amount()+wave.getSale_f3_amount();

            if(buy_s_AllAmount/sale_s_AllAmount>2){
                MailUtil.sendMail(matchName + " S   buy/sale >2  current buy"+wave.getBuy_s1()+"  sale ="+wave.getSale_s1(), JSON.toJSONString(wave));
            } if(buy_p_AllAmount/sale_p_AllAmount>2){
                MailUtil.sendMail(matchName + " P   buy/sale >2  current buy"+wave.getBuy_p1()+"  sale ="+wave.getSale_p1(), JSON.toJSONString(wave) );
            } if(buy_f_AllAmount/sale_f_AllAmount>2){
                MailUtil.sendMail(matchName + " F   buy/sale >2  current buy"+wave.getBuy_f1()+"  sale ="+wave.getSale_f1(), JSON.toJSONString(wave));

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
