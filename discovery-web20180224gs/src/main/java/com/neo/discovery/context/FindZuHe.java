package com.neo.discovery.context;

import com.neo.discovery.vo.OneRace;
import org.apache.ibatis.annotations.One;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyunpeng1 on 2017/11/29.
 */
public class FindZuHe {

    public static Logger logger = org.slf4j.LoggerFactory.getLogger(JsoupContext.class);


    private Map<String,Integer>  action = new HashMap<String, Integer>();
    private List<String> races = new ArrayList<String>();


    private void init(){

        action.put("0",0);//wu
        action.put("1",1);//win
        action.put("2",1);//fail
        action.put("3",2);//winandfail


        races.add("1");
        races.add("2");
        races.add("3");
//        races.add("12");
//        races.add("13");
//        races.add("23");
//        races.add("123");

    }



    public static  void main(String[] args){

        raceJieguo();
//        calcuateZuhe();
//        List<OneRace> list = initRaceNoSelect();
//        youhuaSelect2(list.get(0), list.get(1), list.get(2));
    }


    private static List<OneRace>    initRaceNoSelect(){
        List<OneRace> list  = new ArrayList<OneRace>();
        List<String> select = new ArrayList<String>();
        select.add("win");
        select.add("rangFail");
        select.add("none");
        for(int i=1;i<4;i++){
                OneRace one = new OneRace();
                one.setWin(1.2f);
                one.setRangFail(3f);
                one.setRaceNo("No"+i);
                one.setSelectList(select);
                list.add(one);
        }
        return list;
    }


    private static void raceJieguo(){
        List<String> aa = new ArrayList<String>();
        aa.add("win");
        aa.add("rangFail");
        aa.add("none");
        List<String> bb = new ArrayList<String>();
        bb.add("win");
        bb.add("rangFail");
        bb.add("none");
        List<String> cc = new ArrayList<String>();
        cc.add("win");
        cc.add("rangFail");
        cc.add("none");

        for(int a=0;a<aa.size();a++){
            for(int b=0;b<bb.size();b++){
                for(int c=0;c<cc.size();c++){

                    List<String> temp = new ArrayList<String>();
                    temp.add(aa.get(a));temp.add(bb.get(b));temp.add(cc.get(c));
                    for(int i=0;i<temp.size();i++){
                        for(int j=i+1;j<temp.size();j++){
                            StringBuffer sb = new StringBuffer();
                            sb.append(temp.get(i)).append("+").append(temp.get(j));
                            if(sb.indexOf("none")<0){
                                logger.info(sb.toString());
                            }
                        }
                    }


                }
            }
        }
    }



    private static void calcuateZuhe(){
        List<OneRace> list = initRaceNoSelect();
        Map<String,List> map =youhuaSelect2(list.get(0), list.get(1), list.get(2));
        for(Map.Entry<String,List> entry:map.entrySet()){
            List<OneRace> temp = entry.getValue();
            float tempEarn = 1f;
            for(OneRace oneRace:temp){
                tempEarn = tempEarn*oneRace.getCurrPeiLv();
            }
            logger.info(entry.getKey()+" get:"+tempEarn);
        }

    }

    private static void youhuaSelect3(OneRace one,OneRace two,OneRace three){
        for(int i=1;i<8;i++){
            for(int a=0;a<one.getSelectList().size();a++){
                for(int b=0;b<two.getSelectList().size();b++){
                    for(int c=0;c<three.getSelectList().size();c++){
                        StringBuffer sb = new StringBuffer();
                        sb.append(one.getRaceNo()+"="+one.getSelectList().get(a)).append(",");
                        sb.append(two.getRaceNo()+"="+two.getSelectList().get(b)).append(",");
                        sb.append(three.getRaceNo()+"="+three.getSelectList().get(c)).append(",");
                        System.out.println(sb.toString());
                    }
                }
            }
        }
    }
    private static  Map<String,List> youhuaSelect2(OneRace one,OneRace two,OneRace three){
        Map<String,List> map = new HashMap<String, List>();
            for(int a=0;a<one.getSelectList().size();a++){
                for(int b=0;b<two.getSelectList().size();b++){
                    for(int c=0;c<three.getSelectList().size();c++){
                        String aSelect =one.getSelectList().get(a);
                        String bSelect =two.getSelectList().get(b);
                        String cSelect =three.getSelectList().get(c);
                        boolean iscontinue = isContinue(aSelect,bSelect,cSelect);
                        if(iscontinue) continue;

                        List<OneRace> list = new ArrayList<OneRace>();
                        StringBuffer sb = new StringBuffer();
                        if(!aSelect.equals("none")){
                            sb.append(one.getRaceNo()+"="+aSelect).append(",");
                            if(aSelect.equals("win")){
                                one.setCurrPeiLv(one.getWin());
                            }else if(aSelect.equals("rangFail")){
                                one.setCurrPeiLv(one.getRangFail());
                            }
                            OneRace temp = new OneRace();
                            BeanUtils.copyProperties(one,temp);
                            list.add(temp);
                        }
                        if(!bSelect.equals("none")){
                            sb.append(two.getRaceNo()+"="+bSelect).append(",");
                            if(bSelect.equals("win")){
                                two.setCurrPeiLv(two.getWin());
                            }else if(bSelect.equals("rangFail")){
                                two.setCurrPeiLv(two.getRangFail());
                            }
                            OneRace temp = new OneRace();
                            BeanUtils.copyProperties(two, temp);
                            list.add(temp);
                        }
                        if(!cSelect.equals("none")){
                            sb.append(three.getRaceNo()+"="+cSelect).append(",");
                            if(cSelect.equals("win")){
                                three.setCurrPeiLv(three.getWin());
                            }else if(cSelect.equals("rangFail")){
                                three.setCurrPeiLv(three.getRangFail());
                            }
                            OneRace temp = new OneRace();
                            BeanUtils.copyProperties(three, temp);
                            list.add(temp);
                        }
                        map.put(sb.toString(),list);
                        System.out.println(sb.toString());
                    }
                }
            }
        return map;
    }

    private static boolean isContinue(String a,String b,String c){
        List<String> isContinueList = new ArrayList<String>();
        if(a.equals("none")){
            isContinueList.add(a);
        }
        if( b.equals("none") ){
            isContinueList.add(b);
        }
        if(c.equals("none")){
            isContinueList.add(c);
        }
        if(isContinueList.size()>1){
            return true;
        }else{
            return false;
        }
    }



    private List<String> getALlZuhe(String currRace){
        List<String> allzhuhe = new ArrayList<String>();
        for(Map.Entry<String,Integer> entry:action.entrySet()){
            for(String race:races){
                if(!currRace.equals(race)){
                     for(Map.Entry<String,Integer> raceEntry:action.entrySet()){
                         //1@1buy1@1
                        String zuhe = currRace+"@"+entry.getKey()+"buy"+race+"@"+raceEntry.getKey();
                        allzhuhe.add(zuhe);
                    }
                }
            }
        }
        return allzhuhe;
    }


    private void findZuhe(){
       List<String> aa = getALlZuhe("1");
       List<String> bb = getALlZuhe("2");
       List<String> cc = getALlZuhe("3");


        for(int a=0;a<aa.size();a++){
            for(int b=0;b<bb.size();b++){
                for(int c=0;c<cc.size();c++){
                    Float aaspend = CalcuateSpend(aa.get(a));
                    Float bbspend = CalcuateSpend(bb.get(b));
                    Float ccspend = CalcuateSpend(cc.get(c));
                }
            }
        }
    }

    //1@1buy2@1
    private Float CalcuateSpend(String aaStr){
        String[] aa = aaStr.split("buy");
        String firstSpend = aa[0].split("@")[1];
        String secondSpend = aa[1].split("@")[1];
        return Float.parseFloat(firstSpend)*Float.parseFloat(secondSpend);
    }
    //1@1buy2@1
    private Float CalcuateJiang(String aaStr,Integer oneResult ,Integer twoResult){
        float win = 1.2f;
        float fail = 3f;
        String[] aa = aaStr.split("buy");
        String firstSelect = aa[0].split("@")[1];
        String secondSelect = aa[1].split("@")[1];
        if(firstSelect.equals("3")||secondSelect.equals("3")){
            if(firstSelect.equals("3")&&!secondSelect.equals("3")){
                if(secondSelect.equals(twoResult)){
                    return 1.2f*3f;
                }
            }
        }
        return Float.parseFloat(firstSelect)*Float.parseFloat(secondSelect);
    }


}
