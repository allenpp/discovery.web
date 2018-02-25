package com.neo.discovery.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 8/19/2017.
 */
public class Wave {


    private long id;
    private Integer matchId;
    private Float buy_s1;
    private Float buy_p1;
    private Float buy_f1;
    private Float buy_s1_amount;
    private Float buy_p1_amount;
    private Float buy_f1_amount;


    private Float buy_s2;
    private Float buy_p2;
    private Float buy_f2;
    private Float buy_s2_amount;
    private Float buy_p2_amount;
    private Float buy_f2_amount;

    private Float buy_s3;
    private Float buy_p3;
    private Float buy_f3;
    private Float buy_s3_amount;
    private Float buy_p3_amount;
    private Float buy_f3_amount;


    private Float sale_s1;
    private Float sale_p1;
    private Float sale_f1;
    private Float sale_s1_amount;
    private Float sale_p1_amount;
    private Float sale_f1_amount;


    private Float sale_s2;
    private Float sale_p2;
    private Float sale_f2;
    private Float sale_s2_amount;
    private Float sale_p2_amount;
    private Float sale_f2_amount;


    private Float sale_s3;
    private Float sale_p3;
    private Float sale_f3;
    private Float sale_s3_amount;
    private Float sale_p3_amount;
    private Float sale_f3_amount;




    private Date createTime;
    private Date matchDate;
    private String home;
    private String away;
    private String leagueName;
    private String json;


    private Date beginTime;
    private Date endTime;

    public Float getBuy_s1() {
        return buy_s1;
    }

    public void setBuy_s1(Float buy_s1) {
        this.buy_s1 = buy_s1;
    }

    public Float getBuy_p1() {
        return buy_p1;
    }

    public void setBuy_p1(Float buy_p1) {
        this.buy_p1 = buy_p1;
    }

    public Float getBuy_f1() {
        return buy_f1;
    }

    public void setBuy_f1(Float buy_f1) {
        this.buy_f1 = buy_f1;
    }

    public Float getSale_s1() {
        return sale_s1;
    }

    public void setSale_s1(Float sale_s1) {
        this.sale_s1 = sale_s1;
    }

    public Float getSale_p1() {
        return sale_p1;
    }

    public void setSale_p1(Float sale_p1) {
        this.sale_p1 = sale_p1;
    }

    public Float getSale_f1() {
        return sale_f1;
    }

    public void setSale_f1(Float sale_f1) {
        this.sale_f1 = sale_f1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }



    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getCreateTime() {
        if (null != createTime) {
            String temp = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(temp);
            String str = "";
            try {
                str = sdf.format(createTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return createTime;
        } else {
            return null;
        }
    }

    public Float getBuy_s1_amount() {
        return buy_s1_amount;
    }

    public void setBuy_s1_amount(Float buy_s1_amount) {
        this.buy_s1_amount = buy_s1_amount;
    }

    public Float getBuy_p1_amount() {
        return buy_p1_amount;
    }

    public void setBuy_p1_amount(Float buy_p1_amount) {
        this.buy_p1_amount = buy_p1_amount;
    }

    public Float getBuy_f1_amount() {
        return buy_f1_amount;
    }

    public void setBuy_f1_amount(Float buy_f1_amount) {
        this.buy_f1_amount = buy_f1_amount;
    }

    public Float getSale_s1_amount() {
        return sale_s1_amount;
    }

    public void setSale_s1_amount(Float sale_s1_amount) {
        this.sale_s1_amount = sale_s1_amount;
    }

    public Float getSale_p1_amount() {
        return sale_p1_amount;
    }

    public void setSale_p1_amount(Float sale_p1_amount) {
        this.sale_p1_amount = sale_p1_amount;
    }

    public Float getSale_f1_amount() {
        return sale_f1_amount;
    }

    public void setSale_f1_amount(Float sale_f1_amount) {
        this.sale_f1_amount = sale_f1_amount;
    }


    public Float getBuy_s2() {
        return buy_s2;
    }

    public void setBuy_s2(Float buy_s2) {
        this.buy_s2 = buy_s2;
    }

    public Float getBuy_p2() {
        return buy_p2;
    }

    public void setBuy_p2(Float buy_p2) {
        this.buy_p2 = buy_p2;
    }

    public Float getBuy_f2() {
        return buy_f2;
    }

    public void setBuy_f2(Float buy_f2) {
        this.buy_f2 = buy_f2;
    }

    public Float getBuy_s2_amount() {
        return buy_s2_amount;
    }

    public void setBuy_s2_amount(Float buy_s2_amount) {
        this.buy_s2_amount = buy_s2_amount;
    }

    public Float getBuy_p2_amount() {
        return buy_p2_amount;
    }

    public void setBuy_p2_amount(Float buy_p2_amount) {
        this.buy_p2_amount = buy_p2_amount;
    }

    public Float getBuy_f2_amount() {
        return buy_f2_amount;
    }

    public void setBuy_f2_amount(Float buy_f2_amount) {
        this.buy_f2_amount = buy_f2_amount;
    }

    public Float getBuy_s3() {
        return buy_s3;
    }

    public void setBuy_s3(Float buy_s3) {
        this.buy_s3 = buy_s3;
    }

    public Float getBuy_p3() {
        return buy_p3;
    }

    public void setBuy_p3(Float buy_p3) {
        this.buy_p3 = buy_p3;
    }

    public Float getBuy_f3() {
        return buy_f3;
    }

    public void setBuy_f3(Float buy_f3) {
        this.buy_f3 = buy_f3;
    }

    public Float getBuy_s3_amount() {
        return buy_s3_amount;
    }

    public void setBuy_s3_amount(Float buy_s3_amount) {
        this.buy_s3_amount = buy_s3_amount;
    }

    public Float getBuy_p3_amount() {
        return buy_p3_amount;
    }

    public void setBuy_p3_amount(Float buy_p3_amount) {
        this.buy_p3_amount = buy_p3_amount;
    }

    public Float getBuy_f3_amount() {
        return buy_f3_amount;
    }

    public void setBuy_f3_amount(Float buy_f3_amount) {
        this.buy_f3_amount = buy_f3_amount;
    }

    public Float getSale_s2() {
        return sale_s2;
    }

    public void setSale_s2(Float sale_s2) {
        this.sale_s2 = sale_s2;
    }

    public Float getSale_p2() {
        return sale_p2;
    }

    public void setSale_p2(Float sale_p2) {
        this.sale_p2 = sale_p2;
    }

    public Float getSale_f2() {
        return sale_f2;
    }

    public void setSale_f2(Float sale_f2) {
        this.sale_f2 = sale_f2;
    }

    public Float getSale_s2_amount() {
        return sale_s2_amount;
    }

    public void setSale_s2_amount(Float sale_s2_amount) {
        this.sale_s2_amount = sale_s2_amount;
    }

    public Float getSale_p2_amount() {
        return sale_p2_amount;
    }

    public void setSale_p2_amount(Float sale_p2_amount) {
        this.sale_p2_amount = sale_p2_amount;
    }

    public Float getSale_f2_amount() {
        return sale_f2_amount;
    }

    public void setSale_f2_amount(Float sale_f2_amount) {
        this.sale_f2_amount = sale_f2_amount;
    }

    public Float getSale_s3() {
        return sale_s3;
    }

    public void setSale_s3(Float sale_s3) {
        this.sale_s3 = sale_s3;
    }

    public Float getSale_p3() {
        return sale_p3;
    }

    public void setSale_p3(Float sale_p3) {
        this.sale_p3 = sale_p3;
    }

    public Float getSale_f3() {
        return sale_f3;
    }

    public void setSale_f3(Float sale_f3) {
        this.sale_f3 = sale_f3;
    }

    public Float getSale_s3_amount() {
        return sale_s3_amount;
    }

    public void setSale_s3_amount(Float sale_s3_amount) {
        this.sale_s3_amount = sale_s3_amount;
    }

    public Float getSale_p3_amount() {
        return sale_p3_amount;
    }

    public void setSale_p3_amount(Float sale_p3_amount) {
        this.sale_p3_amount = sale_p3_amount;
    }

    public Float getSale_f3_amount() {
        return sale_f3_amount;
    }

    public void setSale_f3_amount(Float sale_f3_amount) {
        this.sale_f3_amount = sale_f3_amount;
    }


    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
