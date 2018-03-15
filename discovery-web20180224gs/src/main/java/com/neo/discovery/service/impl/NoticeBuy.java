package com.neo.discovery.service.impl;

import com.neo.discovery.domain.Constant;
import com.neo.discovery.service.NoticeRule;
import com.neo.discovery.util.FileUtils;
import com.neo.discovery.util.PropertiesFileUtils;
import com.neo.discovery.util.mail.MailUtil;
import com.neo.discovery.vo.Wave;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by Administrator on 3/10/2018.
 */
@Service("noticeBuy")
public class NoticeBuy implements NoticeRule {





    @Override
    public void notice(Wave wave) {
        String matchName = wave.getLeagueName().replace("\n", "").replace(" ","") +"@"+ wave.getHome()+"V"+wave.getAway();
        matchName = matchName.replace(" ","");
        Properties pro = PropertiesFileUtils.getProperties();

        //已经卖了
        Float sale_s1 = getValue(pro, matchName+Constant.SALE_S);
        Float sale_p1 = getValue(pro, matchName+Constant.SALE_P);
        Float sale_f1 = getValue(pro, matchName+Constant.SALE_F);

        Float sale_s1_amount = getValue(pro, matchName+Constant.SALE_S_AMOUNT);
        Float sale_p1_amount = getValue(pro, matchName+Constant.SALE_P_AMOUNT);
        Float sale_f1_amount = getValue(pro, matchName+Constant.SALE_F_AMOUNT);

        if(null!=wave){
            if(null!=sale_s1&&wave.getBuy_s1()>sale_s1){
                String content = builBuyContent(sale_s1, sale_s1_amount, wave.getBuy_s1());
                MailUtil.sendMail(matchName + "need buy s1 ", content);
            }else if(null!=sale_p1&&wave.getBuy_p1()>sale_p1){
                String content = builBuyContent(sale_p1, sale_p1_amount, wave.getBuy_p1());
                MailUtil.sendMail(matchName+"need buy p1 ",content);
            }else if(null!=sale_f1&&wave.getBuy_f1()>sale_f1){
                String content = builBuyContent(sale_f1,sale_f1_amount,wave.getBuy_f1());
                MailUtil.sendMail(matchName+"need buy f1 ",content);
            }
        }
    }

    private Float getValue(Properties pro,String key ){
        try{
            String value = pro.getProperty(key);
            Object keyObj = pro.get(key);
            boolean contons =  pro.keySet().contains(key);
            if(!contons){
                FileUtils.appendMethodA(PropertiesFileUtils.filePath,key+"=");
            }
            return Float.parseFloat(value);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    private String  builBuyContent(Float rate,Float amount,Float now_sale_rate){

        String hasSaleContent = "has sale = {"+rate+"*"+amount+"="+rate*amount+"}";
        String needBuyContent =  " ;suggest buy ("+(rate*amount)/now_sale_rate+"-"+amount+")  = {"+now_sale_rate+"*(未)"+(rate*amount)/now_sale_rate+"="+ now_sale_rate*((rate*amount)/now_sale_rate)+"}";

        return hasSaleContent+needBuyContent;
    }
}
