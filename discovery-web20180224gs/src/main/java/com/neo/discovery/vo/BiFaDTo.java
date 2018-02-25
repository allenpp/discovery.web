package com.neo.discovery.vo;

/**
 * Created by liuyunpeng1 on 2017/9/25.
 */
public class BiFaDTo {


    private Integer id;
    private Integer marchId;
    private Float totalAmount;
    private Float winAmount;
    private Float drawAmount;
    private Float lossAmount;
    private String winPercent;
    private String drawPercent;
    private String lossPercent;
    private Float owin;// 欧胜赔
    private Float oeq;// 欧平赔
    private Float ofail;// 欧负赔
    private Integer owin_bankerProfit;//欧赔 胜盈亏
    private Integer oeq_bankerProfit;//欧赔 平盈亏
    private Integer ofail_bankerProfit;//欧赔 负盈亏
    private String benzhan_win;
    private String benzhan_eq;
    private String benzhan_fail;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMarchId() {
        return marchId;
    }

    public void setMarchId(Integer marchId) {
        this.marchId = marchId;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(Float winAmount) {
        this.winAmount = winAmount;
    }

    public Float getDrawAmount() {
        return drawAmount;
    }

    public void setDrawAmount(Float drawAmount) {
        this.drawAmount = drawAmount;
    }

    public Float getLossAmount() {
        return lossAmount;
    }

    public void setLossAmount(Float lossAmount) {
        this.lossAmount = lossAmount;
    }

    public String getWinPercent() {
        return winPercent;
    }

    public void setWinPercent(String winPercent) {
        this.winPercent = winPercent;
    }

    public String getDrawPercent() {
        return drawPercent;
    }

    public void setDrawPercent(String drawPercent) {
        this.drawPercent = drawPercent;
    }

    public String getLossPercent() {
        return lossPercent;
    }

    public void setLossPercent(String lossPercent) {
        this.lossPercent = lossPercent;
    }

    public Float getOwin() {
        return owin;
    }

    public void setOwin(Float owin) {
        this.owin = owin;
    }

    public Float getOeq() {
        return oeq;
    }

    public void setOeq(Float oeq) {
        this.oeq = oeq;
    }

    public Float getOfail() {
        return ofail;
    }

    public void setOfail(Float ofail) {
        this.ofail = ofail;
    }

    public Integer getOwin_bankerProfit() {
        return owin_bankerProfit;
    }

    public void setOwin_bankerProfit(Integer owin_bankerProfit) {
        this.owin_bankerProfit = owin_bankerProfit;
    }

    public Integer getOeq_bankerProfit() {
        return oeq_bankerProfit;
    }

    public void setOeq_bankerProfit(Integer oeq_bankerProfit) {
        this.oeq_bankerProfit = oeq_bankerProfit;
    }

    public Integer getOfail_bankerProfit() {
        return ofail_bankerProfit;
    }

    public void setOfail_bankerProfit(Integer ofail_bankerProfit) {
        this.ofail_bankerProfit = ofail_bankerProfit;
    }

    public String getBenzhan_win() {
        return benzhan_win;
    }

    public void setBenzhan_win(String benzhan_win) {
        this.benzhan_win = benzhan_win;
    }

    public String getBenzhan_eq() {
        return benzhan_eq;
    }

    public void setBenzhan_eq(String benzhan_eq) {
        this.benzhan_eq = benzhan_eq;
    }

    public String getBenzhan_fail() {
        return benzhan_fail;
    }

    public void setBenzhan_fail(String benzhan_fail) {
        this.benzhan_fail = benzhan_fail;
    }
}
