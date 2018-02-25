package com.neo.discovery.vo;

/**
 * Created by liuyunpeng1 on 2017/9/25.
 *
 * 必发 各 赔率 总金额 比率
 */
public class TradePieChartBo {


    private Float totalAmount;
    private Float winAmount;
    private Float drawAmount;
    private Float lossAmount;
    private String winPercent;
    private String drawPercent;
    private String lossPercent;

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
}
