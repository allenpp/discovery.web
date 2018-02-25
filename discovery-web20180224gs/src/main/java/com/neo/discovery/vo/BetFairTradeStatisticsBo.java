package com.neo.discovery.vo;

/**
 * Created by liuyunpeng1 on 2017/9/25.
 *
 * 必发各赔率 投注率 盈亏
 */
public class BetFairTradeStatisticsBo {

    private String typeId;
    private String price;
    private String amount;
    private String caloricIndex;
    private String bankerProfit;


    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCaloricIndex() {
        return caloricIndex;
    }

    public void setCaloricIndex(String caloricIndex) {
        this.caloricIndex = caloricIndex;
    }

    public String getBankerProfit() {
        return bankerProfit;
    }

    public void setBankerProfit(String bankerProfit) {
        this.bankerProfit = bankerProfit;
    }
}
