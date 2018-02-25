package com.neo.discovery.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by liuyunpeng1 on 2018/2/23.
 */
public class ChartVo {


    private Map<String,List<Float>> seriesList;

    private List<String> xAxis ;


    public Map<String, List<Float>> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(Map<String, List<Float>> seriesList) {
        this.seriesList = seriesList;
    }

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }
}
