package com.neo.discovery.util;

import com.neo.discovery.vo.RaceTeam;
import org.jsoup.nodes.Node;

import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
public class RaceTeamUtils {


    /**
     * ���� ���� �Ͷ� ����
     * @param tdNode
     * @param raceTeam
     */
    public static void parseRaceTeam(Node tdNode,RaceTeam raceTeam){
        if(null!=tdNode){

            if(null!=tdNode.childNodes()&&tdNode.childNodes().size()>0){
                List<Node> spanList = tdNode.childNodes();
                Node homeTeam = spanList.get(0).childNode(0);
                Node visitTeam = spanList.get(2).childNode(0);
                String homeTeamStr = homeTeam.toString();
                if(homeTeamStr.indexOf("(")>1){
                    raceTeam.setHomeTeam(homeTeamStr.substring(0,homeTeamStr.indexOf("(")));
                }
                raceTeam.setVisitingTeam(visitTeam.toString());
            }
        }
    }


}
