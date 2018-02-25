package com.neo.discovery.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyunpeng1 on 2017/9/25.
 */
public class SpfChangeJson {


    private String BookMakerId ;
    private String BookMakerName;

    private  LastOdds LastOdds;

    private List<LastOdds> ChangeOddsList = new ArrayList<com.neo.discovery.vo.LastOdds>();

    public String getBookMakerId() {
        return BookMakerId;
    }

    public void setBookMakerId(String bookMakerId) {
        BookMakerId = bookMakerId;
    }

    public String getBookMakerName() {
        return BookMakerName;
    }

    public void setBookMakerName(String bookMakerName) {
        BookMakerName = bookMakerName;
    }

    public LastOdds getLastOdds() {
        return LastOdds;
    }

    public void setLastOdds(LastOdds lastOdds) {
        LastOdds = lastOdds;
    }

    public List<LastOdds> getChangeOddsList() {
        return ChangeOddsList;
    }

    public void setChangeOddsList(List<LastOdds> changeOddsList) {
        ChangeOddsList = changeOddsList;
    }
}
