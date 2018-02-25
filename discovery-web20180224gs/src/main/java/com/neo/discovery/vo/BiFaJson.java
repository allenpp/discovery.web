package com.neo.discovery.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyunpeng1 on 2017/9/25.
 */
public class BiFaJson {

    private TradePieChartBo tradePieChartBo;
    private List<BetFairTradeStatisticsBo> betFairTradeStatisticsBoList = new ArrayList<BetFairTradeStatisticsBo>();


    public TradePieChartBo getTradePieChartBo() {
        return tradePieChartBo;
    }

    public void setTradePieChartBo(TradePieChartBo tradePieChartBo) {
        this.tradePieChartBo = tradePieChartBo;
    }

    public List<BetFairTradeStatisticsBo> getBetFairTradeStatisticsBoList() {
        return betFairTradeStatisticsBoList;
    }

    public void setBetFairTradeStatisticsBoList(List<BetFairTradeStatisticsBo> betFairTradeStatisticsBoList) {
        this.betFairTradeStatisticsBoList = betFairTradeStatisticsBoList;
    }
}
