package com.neo.discovery.context;


import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.vo.RaceTeam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuyunpeng1 on 2017/9/11.
 */
public class CalculateContext extends JsoupContext{


    /**
     * *gc.add(1,-1)表示年份减一.
     *gc.add(2,-1)表示月份减一.
     *gc.add(3.-1)表示周减一.
     *gc.add(5,-1)表示天减一.
     */
    public static List<String> getRaceByDate(){
        List<String> days = new ArrayList<String>();
        SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
//        GregorianCalendar gc=new GregorianCalendar();
//        for(int i=0;i<400;i++){
//            try{
//            gc.setTime(new Date());
////            gc.setTime(sf.parse("2016-10-29"));
//            gc.add(5, -i);
//            sf.format(gc.getTime());
////            logger.info(sf.format(gc.getTime()));
//
//                days.add( sf.format(gc.getTime()));
//            }catch (Exception e){
//
//            }
//        }

       List<RaceTeam> list =  raceTeamService.selectCountDaYu6(null);
        if(null!=list&&list.size()>0){
            for(RaceTeam temp:list){
                days.add(sf.format(temp.getRaceDate()));
            }
        }

        return days;
    }


    public static void  processById(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);

            StringBuffer sb = new StringBuffer("(");
            int i = 0;
            for(String date :days) {

                sb.append(date);
                if(i!=days.size()-1){
                    sb.append(",");
                }
                        i++;
            }
            sb.append(")");

            RaceTeam temp = new RaceTeam();
            temp.setRaceDates(days);
            List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
            Map<String,List<RaceTeam>>  map = new HashMap<String,List<RaceTeam>>();

            if(null!=list&&list.size()>0){
                SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
                for(RaceTeam one:list){
                    String raceDate =sf.format(one.getRaceDate());
                    List<RaceTeam> tempList= map.get(raceDate);
                    if(null==tempList){
                        tempList = new ArrayList<RaceTeam>();
                        tempList.add(one);
                        map.put(raceDate,tempList);
                    }else{
                        if(tempList.size()<6)
                        tempList.add(one);
                    }
                }
            }

            for(String date :map.keySet()) {
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();

                List<RaceTeam> dolist = map.get(date);
                Float oneDay = raceTeamService.calculateMoneyBySelect(dolist, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processBanQuanChang(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
                Float oneDay = raceTeamService.calculateBanQuanChang(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processPingFuGeYi(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
                Float oneDay = raceTeamService.calculatePingFuGeYi(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processPingHfSheng(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
                Float oneDay = raceTeamService.calculatepingHfSheng(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processBiFen(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
                Float oneDay = raceTeamService.calculateSelectBiFen(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processHunHe(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
                Float oneDay = raceTeamService.calculateHunHe(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processCaiBfGeYi(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(1f);
            Float beishu =new Float(1f);
            int isFanBeiCount = 0;
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
                Float oneDay = raceTeamService.calculateCaiBFGeYi(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, beishu);
//                if(oneDay==0) continue;
//                if((oneDay==-30*beishu)){
//                    if((beishu)<=5){
//                        beishu++;
//                    }else{
//                        beishu=1f;
//                    }
//                }else{
//                    beishu=1f;
//                }
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processFromSheng1BuyRang(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(1f);
            Float beishu =new Float(1f);
            int isFanBeiCount = 0;
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
                Float oneDay = raceTeamService.calculateFromSheng1BuyRang(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, beishu);
                if(oneDay==0) continue;
//                if((oneDay<0)){
//                    if((beishu)<=4){
//                        beishu++;
//                    }else{
//                        beishu=1f;
//                    }
//                }else{
//                    beishu=1f;
//                }
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processcalculateChengDuiJiSuan(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(1f);
            Float beishu =new Float(1f);
            int isFanBeiCount = 0;
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
                Float oneDay = raceTeamService.calculateChengDuiJiSuan(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, beishu);
//                if(oneDay==0) continue;
//                if((oneDay<0)){
//                    if((beishu)<4){
//                        beishu++;
//                    }else{
//                        beishu=1f;
//                    }
//                }else{
//                    beishu=1f;
//                }
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processkui5JiuTing(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);

            StringBuffer sb = new StringBuffer("(");
            RaceTeam temp = new RaceTeam();
           temp.setRaceDates(days);
            List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
            Map<String,List<RaceTeam>>  map = new HashMap<String,List<RaceTeam>>();

            if(null!=list&&list.size()>0){
                SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
                for(RaceTeam one:list){
                    String raceDate =sf.format(one.getRaceDate());
                    List<RaceTeam> tempList= map.get(raceDate);
                    if(null==tempList){
                        tempList = new ArrayList<RaceTeam>();
                        tempList.add(one);
                        map.put(raceDate,tempList);
                    }else{
                        if(tempList.size()<6)
                            tempList.add(one);
                    }
                }
            }

            List<Map.Entry<String, List<RaceTeam>>> infoIds =
                    new ArrayList<Map.Entry<String, List<RaceTeam>>>(map.entrySet());

            Collections.sort(infoIds, new Comparator<Map.Entry<String, List<RaceTeam>>>() {
                public int compare(Map.Entry<String, List<RaceTeam>> o1, Map.Entry<String, List<RaceTeam>> o2) {
                    //return (o2.getValue() - o1.getValue());
                    Date date1 = ParseUtil.parseStr2Date(o1.getKey());
                    Date date2 = ParseUtil.parseStr2Date(o2.getKey());

                    return date2.compareTo(date1);
                }
            });
            for(Map.Entry one :infoIds) {

                String date = one.getKey().toString();
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();

                List dolist = (List)one.getValue();
                Float oneDay = raceTeamService.calculateLiLun5fen3PingMaiRangSheng(dolist, oneSelect, twoSelect, rangOneSelect, rangTwoSelect,date,total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processGeDayBuDan(){
        List<String> days = getRaceByDate();
        int kuiCount = 0;
        int yingCount = 0;
        if(null!=days&&days.size()>0){
            Float total =new Float(1f);
            Float beishu =new Float(10f);
            int isFanBeiCount = 0;
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> listDayOne = raceTeamService.selectBaoLeng(temp);

                GregorianCalendar gc=new GregorianCalendar();
                SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
                gc.setTime(ParseUtil.parseStr2Date(date));
                gc.add(5, -1);
                Date dayTwo = gc.getTime();
                temp.setRaceDate(dayTwo);
                List<RaceTeam> listDayTwo = raceTeamService.selectBaoLeng(temp);

                gc.add(5, -1);
                Date dayThree = gc.getTime();
                temp.setRaceDate(dayThree);
                List<RaceTeam> listDayThree = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
                Float oneDay = raceTeamService.calculateGeDayBuDan(listDayOne, listDayTwo, listDayThree, rangOneSelect, rangTwoSelect, date, beishu);
//                if(oneDay==0) continue;
//                if(isFanBeiCount<100&&oneDay<0){
//                    isFanBeiCount++;
//                    if(isFanBeiCount<6){
//                        beishu =beishu*1.6f;
//                    }
//                }else{
//                    beishu = 10f;
//                    isFanBeiCount=0;
//                }

                total +=oneDay;

            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void queryRaceCaculateBySelect(){

        queryRaceTeamService.findYingLiRateBySelect();
//            logger.info("total yingkui =    "+total);
    }
    public static void  processcalculateShengBuySheng(){
        List<String> days = getRaceByDate();
        int kuiCount = 0;
        int yingCount = 0;
        if(null!=days&&days.size()>0){
            Float total =new Float(1f);
            Float beishu =new Float(1f);
            int isFanBeiCount = 0;
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
                Float oneDay = raceTeamService.calculateShengBuySheng(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, beishu);
                if(oneDay==0) continue;
                if(isFanBeiCount<8&&oneDay<0){
                    isFanBeiCount++;
                    beishu = beishu*2;
                }else{
                    beishu = 1f;
                    isFanBeiCount=0;
                }

                total +=oneDay;

            }
            logger.info("total yingkui =    "+total);
        }
    }

    public static void  processCaiBf(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
//                rangOneSelect.add("让平");
//                rangTwoSelect.add("让平");
//                oneSelect.add("负");
//                oneSelect.add("平");
//                oneSelect.add("胜");
//                twoSelect.add("负");
//                twoSelect.add("平");
//                twoSelect.add("胜");
                Float oneDay = raceTeamService.calculateCaiBf(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
//                Float oneDay = raceTeamService.calculateJinQiuShu(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processHengShuBi(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();
//                rangOneSelect.add("让平");
//                rangTwoSelect.add("让平");
//                oneSelect.add("负");
//                oneSelect.add("平");
//                oneSelect.add("胜");
//                twoSelect.add("负");
//                twoSelect.add("平");
//                twoSelect.add("胜");
                Float oneDay = raceTeamService.calculateHengShuBi(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
//                Float oneDay = raceTeamService.calculateJinQiuShu(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processRangqiu(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();

                Float oneDay = raceTeamService.calculateRangqiu(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
//                Float oneDay = raceTeamService.calculateJinQiuShu(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }
    public static void  processJinQiuShu(){
        List<String> days = getRaceByDate();
        if(null!=days&&days.size()>0){
            Float total =new Float(0f);
            for(String date :days) {
                RaceTeam temp = new RaceTeam();
                temp.setRaceDate(ParseUtil.parseStr2Date(date));
                List<RaceTeam> list = raceTeamService.selectBaoLeng(temp);
                List<String> oneSelect = new ArrayList<String>();
                List<String> twoSelect = new ArrayList<String>();
                List<String> rangOneSelect = new ArrayList<String>();
                List<String> rangTwoSelect = new ArrayList<String>();

                Float oneDay = raceTeamService.calculateJinQiuShu(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
//                Float oneDay = raceTeamService.calculateJinQiuShu(list, oneSelect, twoSelect, rangOneSelect, rangTwoSelect, date, total);
                total +=oneDay;
            }
            logger.info("total yingkui =    "+total);
        }
    }


    public static void process(){

//        List<String> days = getRaceByDate();
//        if(null!=days&&days.size()>0){
//            for(String date :days){
//                RaceTeam raceTeam = new RaceTeam();
//                raceTeam.setRaceDate(date);
//                List<RaceTeam> list = raceTeamService.selectBaoLeng(raceTeam);
//                raceTeamService.calculateMoney(list);
//            }
//
//
//        }



    }



    public static void main(String[] args){
//        queryRaceCaculateBySelect();
        processkui5JiuTing();
//        processGeDayBuDan();
//        processcalculateShengBuySheng();
//        processcalculateChengDuiJiSuan();
//        processkui5JiuTing();
//        processFromSheng1BuyRang();
//        processHunHe();
//        processHunHe();
//        processBiFen();
//        processPingHfSheng();
//        processCaiBfGeYi();----
//        processPingFuGeYi();
//        processRangqiu();
//        processBanQuanChang();
//        processCaiBf();
//        processHengShuBi();
//        processJinQiuShu();
//        processById();
//        process();
//        zuhe(oneList,twoList,threeList);
    }




    public static void  zuhe(List<String> oneList,List<String> twoList ,List<String> threeList){
        if(null!=oneList&&oneList.size()>0&&null!=twoList&&twoList.size()>0){
            for(String one:oneList ){
                for(String two:twoList){
                    for(String three:threeList){
                        logger.info(""+one+"--"+two+"--"+three);

                    }
                }

            }




        }





    }
}
