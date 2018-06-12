package com.neo.discovery.service.impl;

import com.neo.discovery.domain.Constant;
import com.neo.discovery.service.NoticeBase;
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
@Service("noticeSale")
public class NoticeSale extends NoticeBase implements NoticeRule {


    private  Float buy_s1 = 0f;
    private  Float buy_p1 = 0f;
    private  Float buy_f1 = 0f;

    private  Float buy_s1_amount = 25f;
    private  Float buy_p1_amount = 25f;
    private  Float buy_f1_amount = 25f;

    @Override
    public void notice(Wave wave) {
        try{

            String matchName = wave.getLeagueName().replace("\n", "").replace(" ","") +"@"+ wave.getHome().replace("\n", "").replace(" ", "")+"V"+wave.getAway();
            matchName = matchName.replace(" ", "");
            Properties pro = PropertiesFileUtils.getProperties();

            //已经buy
            Float buy_s1 = getValue(pro, matchName+ Constant.BUY_S);
            Float buy_p1 = getValue(pro, matchName+ Constant.BUY_P);
            Float buy_f1 = getValue(pro, matchName+ Constant.BUY_F);

            Float buy_s1_amount = getValue(pro, matchName+ Constant.BUY_S_AMOUNT);
            Float buy_p1_amount = getValue(pro, matchName+ Constant.BUY_P_AMOUNT);
            Float buy_f1_amount = getValue(pro, matchName+ Constant.BUY_F_AMOUNT);

            if(null!=wave){
                if(null!=buy_s1&&wave.getSale_s1()<buy_s1){
                    String content = buildSaleContent(buy_s1,buy_s1_amount,wave.getSale_s1());

                    MailUtil.sendMail(matchName+"need sale s1 ",content);
                }else if(null!=buy_p1&&wave.getSale_p1()<buy_p1){
                    String content = buildSaleContent(buy_p1,buy_p1_amount,wave.getSale_p1());
                    MailUtil.sendMail(matchName+"need sale p1 ",content);
                }else if(null!=buy_f1&&wave.getSale_f1()<buy_f1){
                    String content = buildSaleContent(buy_f1,buy_f1_amount,wave.getSale_f1());
                    MailUtil.sendMail(matchName+"need sale f1 ",content);
                }

                if(null!=buy_s1&&wave.getSale_s1()-0.3>buy_s1){
                    String content = buildSaleContent(buy_s1,buy_s1_amount,wave.getSale_s1());

                    MailUtil.sendMail(matchName+"逆转了  need sale s1 ",content);
                }else if(null!=buy_p1&&wave.getSale_p1()-0.3>buy_p1){
                    String content = buildSaleContent(buy_p1,buy_p1_amount,wave.getSale_p1());
                    MailUtil.sendMail(matchName+"逆转了  need sale p1 ",content);
                }else if(null!=buy_f1&&wave.getSale_f1()-0.3>buy_f1){
                    String content = buildSaleContent(buy_f1,buy_f1_amount,wave.getSale_f1());
                    MailUtil.sendMail(matchName+"逆转了  need sale f1 ",content);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private Float getValue(Properties pro,String key ){
        try{
            String value = pro.getProperty(key);
            Object keyObj = pro.get(key);
            boolean contons =  pro.keySet().contains(key);
            if(!contons){
                FileUtils.appendMethodA(PropertiesFileUtils.filePath, key + "=");
            }
            return Float.parseFloat(value);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String  buildSaleContent(Float rate,Float amount,Float now_sale_rate){

        String hasBuycontent = "has  buy = {"+rate+"*"+amount+"="+rate*amount+"}";
        String needSaleContent =  " ;suggest sale ("+amount+"-"+rate*amount/now_sale_rate+") = {"+now_sale_rate+"*(未)"+(rate*amount)/now_sale_rate+"="+ now_sale_rate*((rate*amount)/now_sale_rate)+"}";

        return hasBuycontent+needSaleContent;
    }
}
