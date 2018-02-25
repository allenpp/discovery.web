package com.neo.discovery.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.discovery.mapper.RaceTeamMapper;
import com.neo.discovery.service.RaceTeamService;
import com.neo.discovery.util.HttpClientUtils;
import com.neo.discovery.util.ParseUtil;
import com.neo.discovery.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.ejb.Local;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 8/20/2017.
 */
@Service("raceTeamService")
public class RaceTeamServiceImpl implements RaceTeamService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(RaceTeamServiceImpl.class);
    @Resource
    private RaceTeamMapper raceTeamMapper;

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class,timeout=1,isolation= Isolation.DEFAULT)
    public Integer insert(RaceTeam raceTeam) {
         RaceTeam hasInsert = new RaceTeam();
        hasInsert.setRaceNo(raceTeam.getRaceNo());
        hasInsert.setRaceDate(raceTeam.getRaceDate());
        RaceTeam result =   raceTeamMapper.selectRaceTeam(hasInsert);
       if(null!=result){
           raceTeamMapper.updateRaceTeamBifen(raceTeam);
       }
        return raceTeamMapper.insert(raceTeam);
    }

//    @Override
    public Integer selectRaceTeamCount(RaceTeam raceTeam) {
        return raceTeamMapper.selectRaceTeamCount(raceTeam);
    }
//    @Override
    public List<RaceTeam> selecttRaceTeamList(RaceTeam raceTeam) {
        return raceTeamMapper.selectRaceTeamList(raceTeam);
    }
//    @Override
    public List<RaceTeam> selectRaceTeamListByPage(RaceTeam raceTeam) {
        return raceTeamMapper.selectRaceTeamListByPage(raceTeam);
    }

    public   Integer  updateRaceTeam(RaceTeam raceTeam) {
        try{
            return raceTeamMapper.updateRaceTeam(raceTeam);
        }catch (Exception e){
            logger.error("  updateRaceTeam fail ",e);
        }
        return null;
    }
    public   Integer  updateRaceTeamFirst(RaceTeam raceTeam) {
        try{
            return raceTeamMapper.updateRaceTeamFirst(raceTeam);
        }catch (Exception e){
            logger.error("  updateRaceTeam fail ",e);
        }
        return null;
    }
    public   Integer  updateRaceTeamZongHf(RaceTeam raceTeam) {
        try{
            return raceTeamMapper.updateRaceTeamZongHf(raceTeam);
        }catch (Exception e){
            logger.error("  updateRaceTeam fail ",e);
        }
        return null;
    }
    public   Integer  updateRaceTeamAllField(RaceTeam raceTeam) {
        try{
            return raceTeamMapper.updateRaceTeamAllField(raceTeam);
        }catch (Exception e){
            logger.error("  updateRaceTeam fail ",e);
        }
        return null;
    }
    public   Integer  updateRaceTeamPoolResult(RaceTeam raceTeam) {
        try{
            return raceTeamMapper.updateRaceTeamPoolResult(raceTeam);
        }catch (Exception e){
            logger.error("  updateRaceTeam fail ",e);
        }
        return null;
    }
    public   Integer  updateRaceTeamHisResult(RaceTeam raceTeam) {
        try{
            return raceTeamMapper.updateRaceTeamHisResult(raceTeam);
        }catch (Exception e){
            logger.error("  updateRaceTeam fail ",e);
        }
        return null;
    }


    /**
     * 排序 s  p  f
     * @param raceTeam
     * @return
     */
    private static List<LocalFloat>  sortLocalFloat(RaceTeam raceTeam){
        List<LocalFloat> list =new ArrayList<LocalFloat>();
        list.add(new LocalFloat(raceTeam.getWin(), BaseConstant.WIN));
        list.add(new LocalFloat(raceTeam.getEquals(), BaseConstant.EQ));
        list.add(new LocalFloat(raceTeam.getFail(), BaseConstant.FAIL));
        Collections.sort(list);
//        logger.info(list.get(0)+"  ,"+list.get(1)+"  ,"+list.get(2));
        return list;
    }
    private static List<Float>  sortFloat(RaceTeam raceTeam){
        List<Float> list =new ArrayList<Float>();
        list.add(raceTeam.getWin());
        list.add(raceTeam.getEquals());
        list.add(raceTeam.getFail());
        Collections.sort(list);
//        logger.info(list.get(0)+"  ,"+list.get(1)+"  ,"+list.get(2));
        return list;
    }

    public static void main(String[] args){

        RaceTeam raceTeam = new RaceTeam();
        raceTeam.setWin(3.55f);
        raceTeam.setEquals(3.33f);
        raceTeam.setFail(1.53f);
    }

    public void calculateMoney(List<RaceTeam> list) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float allOwn  =0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            for(RaceTeam raceTeamOne:list){
//                for(RaceTeam other:list){
                    for(int i=j;i<list.size();i++){
                        RaceTeam other = list.get(i);
                    if(!raceTeamOne.getHomeTeam().equals(other.getHomeTeam())&&raceTeamOne.getRaceDate().compareTo(other.getRaceDate())==0){
                        hasCalculate.add(other);
                        StringBuffer sb = new StringBuffer();
                        Float winwin = raceTeamOne.getWin()*other.getWin()*2;
                        Float wineq = raceTeamOne.getWin()*other.getEquals()*2;
                        Float winfail = raceTeamOne.getWin()*other.getFail()*2;

                        Float eqwin = raceTeamOne.getEquals()*other.getWin()*2;
                        Float eqeq = raceTeamOne.getEquals()*other.getEquals()*2;
                        Float eqfail = raceTeamOne.getEquals()*other.getFail()*2;

                        Float failwin = raceTeamOne.getFail()*other.getWin()*2;
                        Float faileq = raceTeamOne.getFail()*other.getEquals()*2;
                        Float failfail = raceTeamOne.getFail()*other.getFail()*2;

                        //rr
                        Float rwinrwin = raceTeamOne.getRangWin()*other.getRangWin()*2;
                        Float rwinreq = raceTeamOne.getRangWin()*other.getRangEq()*2;
                        Float rwinrfail = raceTeamOne.getRangWin()*other.getRangFail()*2;

                        Float reqrwin = raceTeamOne.getRangEq()*other.getRangWin()*2;
                        Float reqreq = raceTeamOne.getRangEq()*other.getRangEq()*2;
                        Float reqrfail = raceTeamOne.getRangEq()*other.getRangFail()*2;

                        Float rfailrwin = raceTeamOne.getRangFail()*other.getRangWin()*2;
                        Float rfailreq = raceTeamOne.getRangFail()*other.getRangEq()*2;
                        Float rfailrfail = raceTeamOne.getRangFail()*other.getRangFail()*2;

                        String raceTeamOneResult = "";
                        String otherResult = "";
                        String raceOneRangResult = "";
                        String otherRangResult = "";
                        if(raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceTeamOneResult = "胜";
                        }else if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceTeamOneResult = "平";
                        }else if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceTeamOneResult = "负";
                        }
                        if(other.getFullHomeTeamCourtscore()>other.getFullVisitingTeamCourtscore()){
                            otherResult = "胜";
                        }else if(other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore()){
                            otherResult = "平";
                        }else if(other.getFullHomeTeamCourtscore()<other.getFullVisitingTeamCourtscore()){
                            otherResult = "负";
                        }

                        if(raceTeamOneResult.equals("胜")&&otherResult.equals("胜")){
                            allOwn = winwin;
                        }else if(raceTeamOneResult.equals("胜")&&otherResult.equals("平")){
                            allOwn = wineq;
                        }else if(raceTeamOneResult.equals("胜")&&otherResult.equals("负")){
                            allOwn = winfail;
                        }else if(raceTeamOneResult.equals("平")&&otherResult.equals("胜")){
                            allOwn = eqwin;
                        }else if(raceTeamOneResult.equals("平")&&otherResult.equals("平")){
                            allOwn = eqeq;
                        }else if(raceTeamOneResult.equals("平")&&otherResult.equals("负")){
                            allOwn = eqfail;
                        }else if(raceTeamOneResult.equals("负")&&otherResult.equals("胜")){
                            allOwn = failwin;
                        }else if(raceTeamOneResult.equals("负")&&otherResult.equals("平")){
                            allOwn = faileq;
                        }else if(raceTeamOneResult.equals("负")&&otherResult.equals("负")){
                            allOwn = failfail;
                        }


                        if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore())>raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceOneRangResult = "让胜";
                        }else if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore())==raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceOneRangResult = "让平";
                        }else if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore())<raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceOneRangResult = "让负";
                        }
                        if((other.getFullHomeTeamCourtscore()+other.getRangScore())>other.getFullVisitingTeamCourtscore()){
                            otherRangResult = "让胜";
                        }else if((other.getFullHomeTeamCourtscore()+other.getRangScore())==other.getFullVisitingTeamCourtscore()){
                            otherRangResult = "让平";
                        }else if((other.getFullHomeTeamCourtscore()+other.getRangScore())<other.getFullVisitingTeamCourtscore()){
                            otherRangResult = "让负";
                        }

                        if(raceOneRangResult.equals("让胜")&&otherRangResult.equals("让胜")){
                            rangAllOwn = rwinrwin;
                        }else if(raceOneRangResult.equals("让胜")&&otherRangResult.equals("让平")){
                            rangAllOwn = rwinreq;
                        }else if(raceOneRangResult.equals("让胜")&&otherRangResult.equals("让负")){
                            rangAllOwn = rwinrfail;
                        }else if(raceOneRangResult.equals("让平")&&otherRangResult.equals("让胜")){
                            rangAllOwn = reqrwin;
                        }else if(raceOneRangResult.equals("让平")&&otherRangResult.equals("让平")){
                            rangAllOwn = reqreq;
                        }else if(raceOneRangResult.equals("让平")&&otherRangResult.equals("让负")){
                            rangAllOwn = reqrfail;
                        }else if(raceOneRangResult.equals("让负")&&otherRangResult.equals("让胜")){
                            rangAllOwn = rfailrwin;
                        }else if(raceOneRangResult.equals("让负")&&otherRangResult.equals("让平")){
                            rangAllOwn = rfailreq;
                        }else if(raceOneRangResult.equals("让负")&&otherRangResult.equals("让负")){
                            rangAllOwn = rfailrfail;
                        }


                        allSpend+=2;

                        Float dada = getDazhoxi(raceTeamOne,other,"dada");
                        Float dazho = getDazhoxi(raceTeamOne,other,"dazho");
                        Float zhoda = getDazhoxi(raceTeamOne,other,"zhoda");
                        LocalFloat fufu =  getTheResult(raceTeamOne, other, ("负负"));
                        LocalFloat fuping =  getTheResult(raceTeamOne, other, ("负平"));
                        LocalFloat pingfu =  getTheResult(raceTeamOne,other,("平负"));
                        String jieguo = "";
                        Float zhechangzhong = 0f;
                        if(Math.abs(dada-fufu.value)==0){
                            allEarn +=fufu.value;
                            jieguo =fufu.spf;
                            zhechangzhong = fufu.value;
                        }else if(Math.abs(dazho-fuping.value)==0){
                            allEarn +=fuping.value;
                            jieguo = fuping.spf;
                            zhechangzhong=fuping.value;
                        }else if(Math.abs(zhoda-pingfu.value)==0){
                            allEarn +=pingfu.value;
                            jieguo =pingfu.spf;
                            zhechangzhong = pingfu.value;
                        }
//                        sb.append(raceTeamOne.getBaoLeng()+"[SPF=" + raceTeamOneResult + ":" + otherResult +" "+other.getBaoLeng()+ " earn=" + allOwn +"],[Rang :"+raceOneRangResult+":"+otherRangResult+ ",rangAllOwn="+rangAllOwn+"] RangOne("+raceTeamOne.getRangScore()+")=["+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+"] RangTwo("+other.getRangScore()+")=["+other.getFullVisitingTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore()+"] ss=" + winwin + ",sp=" + wineq + ",sf=" + winfail + ",ps=" + eqwin + ",pp=" + eqeq + ",pf=" + eqfail + ",fw=" + failwin + ",fp=" + faileq + ",ff=" + failfail);
//                        sb.append("raceOneRang="+raceOneRangResult+":otherRang="+otherRangResult+"  earn:"+rangAllOwn+" , rss="+rwinrwin+",rsp="+rwinreq+",rsf="+rwinrfail+",rps="+reqrwin+",rpp="+reqreq+",rpf="+reqrfail+",rfw="+rfailrwin+",rfp="+rfailreq+",rff="+rfailrfail);
                      sb.append("allSpend:"+allSpend+" ,allEarn:"+allEarn+";  jieguo:"+jieguo+" zhechangzhong:"+zhechangzhong+"   "+raceTeamOne.getHomeTeam()+":"+raceTeamOne.getVisitingTeam()+"="+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+";---"+raceTeamOneResult+";   ");
                      sb.append(other.getHomeTeam()+":"+other.getVisitingTeam()+"="+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore()+";---"+otherResult);
                        logger.info(sb.toString());
                    }
                }

                j++;
            }
        }
    }

    public void calculateMoneyBySelect(List<RaceTeam> list, List<String> oneSelect, List<String> twoSelect) {

    }


    public Float calculateMoneyBySelect(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            int totalChang = 0;
            int shengCount = 0;
            int pingCount = 0;
            int fuCount = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6)return  0f;
            for(RaceTeam raceTeamOne:list){

                totalChang++;
                if(raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore()){
                    shengCount++;
                }
                if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                    pingCount++;
                }
                if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                    fuCount++;
                }

                for(int i=j;i<list.size();i++){
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if(!raceTeamOne.getHomeTeam().equals(other.getHomeTeam())&&raceTeamOne.getRaceDate().compareTo(other.getRaceDate())==0){
                        hasCalculate.add(other);
                        Float winwin = raceTeamOne.getWin()*other.getWin()*2;
                        Float wineq = raceTeamOne.getWin()*other.getEquals()*2;
                        Float winfail = raceTeamOne.getWin()*other.getFail()*2;

                        Float eqwin = raceTeamOne.getEquals()*other.getWin()*2;
                        Float eqeq = raceTeamOne.getEquals()*other.getEquals()*2;
                        Float eqfail = raceTeamOne.getEquals()*other.getFail()*2;

                        Float failwin = raceTeamOne.getFail()*other.getWin()*2;
                        Float faileq = raceTeamOne.getFail()*other.getEquals()*2;
                        Float failfail = raceTeamOne.getFail()*other.getFail()*2;



                        //rr
                        Float rwinrwin = raceTeamOne.getRangWin()*other.getRangWin()*2;
                        Float rwinreq = raceTeamOne.getRangWin()*other.getRangEq()*2;
                        Float rwinrfail = raceTeamOne.getRangWin()*other.getRangFail()*2;

                        Float reqrwin = raceTeamOne.getRangEq()*other.getRangWin()*2;
                        Float reqreq = raceTeamOne.getRangEq()*other.getRangEq()*2;
                        Float reqrfail = raceTeamOne.getRangEq()*other.getRangFail()*2;

                        Float rfailrwin = raceTeamOne.getRangFail()*other.getRangWin()*2;
                        Float rfailreq = raceTeamOne.getRangFail()*other.getRangEq()*2;
                        Float rfailrfail = raceTeamOne.getRangFail()*other.getRangFail()*2;

                        String raceTeamOneResult = "";
                        String otherResult = "";
                        String raceOneRangResult = "";
                        String otherRangResult = "";
                        if(raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceTeamOneResult = "胜";
                        }else if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceTeamOneResult = "平";
                        }else if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceTeamOneResult = "负";
                        }
                        if(other.getFullHomeTeamCourtscore()>other.getFullVisitingTeamCourtscore()){
                            otherResult = "胜";
                        }else if(other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore()){
                            otherResult = "平";
                        }else if(other.getFullHomeTeamCourtscore()<other.getFullVisitingTeamCourtscore()){
                            otherResult = "负";
                        }


                        if(oneSelect.contains("胜")&&twoSelect.contains("胜")){
                            LocalFloat oneZuHe = new LocalFloat();
                            oneZuHe.setValue(winwin);
                            oneZuHe.setSpf("胜胜");
                            setLocalFloat(oneZuHe, raceTeamOne, other, raceTeamOneResult, otherResult);
                            hasSelect.add(oneZuHe);
                        }  if(oneSelect.contains("胜")&&twoSelect.contains("平")){
                            LocalFloat oneZuHe = new LocalFloat();
                            oneZuHe.setValue(wineq);
                            oneZuHe.setSpf("胜平");
                            setLocalFloat(oneZuHe, raceTeamOne, other, raceTeamOneResult, otherResult);
                            hasSelect.add(oneZuHe);
                        }  if(oneSelect.contains("胜")&&twoSelect.contains("负")){
                            LocalFloat oneZuHe = new LocalFloat();
                            oneZuHe.setValue(winfail);
                            oneZuHe.setSpf("胜负");
                            setLocalFloat(oneZuHe, raceTeamOne, other, raceTeamOneResult, otherResult);
                            hasSelect.add(oneZuHe);
                        }  if(oneSelect.contains("负")&&twoSelect.contains("胜")){
                            LocalFloat oneZuHe = new LocalFloat();
                            oneZuHe.setValue(failwin);
                            oneZuHe.setSpf("负胜");
                            setLocalFloat(oneZuHe, raceTeamOne, other, raceTeamOneResult, otherResult);
                            hasSelect.add(oneZuHe);
                        }  if(oneSelect.contains("负")&&twoSelect.contains("平")){
                            LocalFloat oneZuHe = new LocalFloat();
                            oneZuHe.setValue(faileq);
                            oneZuHe.setSpf("负平");
                            setLocalFloat(oneZuHe, raceTeamOne, other, raceTeamOneResult,otherResult);
                            hasSelect.add(oneZuHe);
                        }  if(oneSelect.contains("负")&&twoSelect.contains("负")){
                            LocalFloat oneZuHe = new LocalFloat();
                            oneZuHe.setValue(failfail);
                            oneZuHe.setSpf("负负");
                            setLocalFloat(oneZuHe, raceTeamOne, other, raceTeamOneResult,otherResult);
                            hasSelect.add(oneZuHe);
                        }  if(oneSelect.contains("平")&&twoSelect.contains("胜")){
                            LocalFloat oneZuHe = new LocalFloat();
                            oneZuHe.setValue(eqwin);
                            oneZuHe.setSpf("平胜");
                            setLocalFloat(oneZuHe, raceTeamOne, other, raceTeamOneResult, otherResult);
                            hasSelect.add(oneZuHe);
                        }  if(oneSelect.contains("平")&&twoSelect.contains("平")){
                            LocalFloat oneZuHe = new LocalFloat();
                            oneZuHe.setValue(eqeq);
                            oneZuHe.setSpf("平平");
                            hasSelect.add(oneZuHe);
                            setLocalFloat(oneZuHe, raceTeamOne, other, raceTeamOneResult, otherResult);
                        }  if(oneSelect.contains("平")&&twoSelect.contains("负")){
                            LocalFloat oneZuHe = new LocalFloat();
                            oneZuHe.setValue(eqfail);
                            oneZuHe.setSpf("平负");
                            setLocalFloat(oneZuHe,raceTeamOne,other,raceTeamOneResult,otherResult);
                            hasSelect.add(oneZuHe);
                        }


                        if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore())>raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceOneRangResult = "让胜";
                        }else if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore())==raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceOneRangResult = "让平";
                        }else if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore())<raceTeamOne.getFullVisitingTeamCourtscore()){
                            raceOneRangResult = "让负";
                        }
                        if((other.getFullHomeTeamCourtscore()+other.getRangScore())>other.getFullVisitingTeamCourtscore()){
                            otherRangResult = "让胜";
                        }else if((other.getFullHomeTeamCourtscore()+other.getRangScore())==other.getFullVisitingTeamCourtscore()){
                            otherRangResult = "让平";
                        }else if((other.getFullHomeTeamCourtscore()+other.getRangScore())<other.getFullVisitingTeamCourtscore()){
                            otherRangResult = "让负";
                        }

                        List<LocalFloat> rangJieGuo = null;
                        if(raceOneRangResult.equals("让胜")&&otherRangResult.equals("让胜")){
                            rangAllOwn = rwinrwin;
                            rangJieGuo= getRangEarnBySelect(rangOneSelect,rangTwoSelect,raceTeamOne,other,raceOneRangResult,otherRangResult,rwinrwin);
                        }else if(raceOneRangResult.equals("让胜")&&otherRangResult.equals("让平")){
                            rangAllOwn = rwinreq;
                            rangJieGuo= getRangEarnBySelect(rangOneSelect,rangTwoSelect,raceTeamOne,other,raceOneRangResult,otherRangResult,rwinreq);
                        }else if(raceOneRangResult.equals("让胜")&&otherRangResult.equals("让负")){
                            rangAllOwn = rwinrfail;
                            rangJieGuo= getRangEarnBySelect(rangOneSelect,rangTwoSelect,raceTeamOne,other,raceOneRangResult,otherRangResult,rwinrfail);
                        }else if(raceOneRangResult.equals("让平")&&otherRangResult.equals("让胜")){
                            rangAllOwn = reqrwin;
                            rangJieGuo= getRangEarnBySelect(rangOneSelect,rangTwoSelect,raceTeamOne,other,raceOneRangResult,otherRangResult,reqrwin);
                        }else if(raceOneRangResult.equals("让平")&&otherRangResult.equals("让平")){
                            rangAllOwn = reqreq;
                            rangJieGuo= getRangEarnBySelect(rangOneSelect,rangTwoSelect,raceTeamOne,other,raceOneRangResult,otherRangResult,reqreq);
                        }else if(raceOneRangResult.equals("让平")&&otherRangResult.equals("让负")){
                            rangAllOwn = reqrfail;
                            rangJieGuo= getRangEarnBySelect(rangOneSelect,rangTwoSelect,raceTeamOne,other,raceOneRangResult,otherRangResult,reqrfail);
                        }else if(raceOneRangResult.equals("让负")&&otherRangResult.equals("让胜")){
                            rangAllOwn = rfailrwin;
                            rangJieGuo= getRangEarnBySelect(rangOneSelect,rangTwoSelect,raceTeamOne,other,raceOneRangResult,otherRangResult,rfailrwin);
                        }else if(raceOneRangResult.equals("让负")&&otherRangResult.equals("让平")){
                            rangAllOwn = rfailreq;
                            rangJieGuo= getRangEarnBySelect(rangOneSelect,rangTwoSelect,raceTeamOne,other,raceOneRangResult,otherRangResult,rfailreq);
                        }else if(raceOneRangResult.equals("让负")&&otherRangResult.equals("让负")){
                            rangAllOwn = rfailrfail;
                            rangJieGuo= getRangEarnBySelect(rangOneSelect,rangTwoSelect,raceTeamOne,other,raceOneRangResult,otherRangResult,rfailrfail);
                        }


                        if(null!=hasSelect&&hasSelect.size()>0){
                            for(LocalFloat temp:hasSelect){
                                StringBuffer sb = new StringBuffer();
                                Float zhechangzhong = 0f;
                                if(temp.spf.equals(raceTeamOneResult+otherResult)){
                                    allEarn+=temp.value;
                                    zhechangzhong = temp.value;

                                    sb.append("allSpend:"+allSpend+" ,allEarn:"+allEarn+";  jieguo:"+raceTeamOneResult+otherResult+"  所选结果:"+temp.spf+"  zhechangzhong:"+zhechangzhong+"   "+temp.getOneHomeTeam()+":"+temp.getOneVisitingTeam()+"="+temp.getOneFullHomeTeamCourtscore()+":"+temp.getOneFullVisitingTeamCourtscore()+"=---"+raceTeamOneResult+";   ");
                                    sb.append(" "+temp.getTwoHomeTeam()+":"+temp.getTwoVisitingTeam()+"="+temp.getTwoFullHomeTeamCourtscore()+":"+temp.getTwoFullVisitingTeamCourtscore()+otherResult);
//                                logger.info(sb.toString());
                                }
                                allSpend +=2;

                            }
                        }
                        if(null!=rangJieGuo&&rangJieGuo.size()>0){
                            for(LocalFloat temp:rangJieGuo){
                                StringBuffer sb = new StringBuffer();
                                Float zhechangzhong = 0f;
                                if(temp.spf.equals(raceOneRangResult+otherRangResult)){
                                    rangAllEarn+=temp.value;
                                    zhechangzhong = temp.value;
                                }
                                rangAllspend +=2;
                                sb.append("allRangSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";  jieguo:"+raceOneRangResult+otherRangResult+"  所选结果:"+temp.spf+"  zhechangzhong:"+zhechangzhong+"   "+temp.getOneHomeTeam()+":"+temp.getOneVisitingTeam()+"="+temp.getOneFullHomeTeamCourtscore()+":"+temp.getOneFullVisitingTeamCourtscore()+"=---"+raceOneRangResult+";   ");
                                sb.append(" "+temp.getTwoHomeTeam()+":"+temp.getTwoVisitingTeam()+"="+temp.getTwoFullHomeTeamCourtscore()+":"+temp.getTwoFullVisitingTeamCourtscore()+otherRangResult);

//                                logger.info(sb.toString());
                            }
                        }




                    }
                }

                j++;
            }
            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
//            String oneDay ="YingKui=   "+yingkui+"; Date="+date+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
//            logger.info(oneDay);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+"  共:"+totalChang+" 胜:"+shengCount+" 平:"+pingCount+"  负:"+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);

            return  yingkui;
        }
        return 0f;
    }
    public Float calculateJinQiuShu(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            for(RaceTeam raceTeamOne:list){
                for(int i=j;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);


                        Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                        Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float thisEarn = 0f;
                        if (1 == oneTotal) {
//                            onePei = 4f;
                        } else if (2 == oneTotal) {
                            onePei = 4f;
                        } else if (3 == oneTotal) {
//                            onePei = 4f;
                        }
                        if (1 == twoTotal) {
//                            twoPei = 4f;
                        } else if (2 == twoTotal) {
//                            twoPei = 4f;
                        } else if (3 == twoTotal) {
//                            twoPei = 4f;
                        }
                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend += 2;


                    }
                }

                j++;
            }
            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }
    public Float calculatePingFuGeYi_20171016gs(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;

            for(int k=0;k<list.size();k++){
                List<RaceTeam> temp = new ArrayList<RaceTeam>();


            }
            int pingCount = 0;
            int fuCount = 0;
            int totalChang = 0;

            for(RaceTeam raceTeamOne:list){
                totalChang++;
                if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                    pingCount++;
                }else  if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                    fuCount++;
                }
                for(int i=j;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                        Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float thisEarn = 0f;
                        StringBuffer log = new StringBuffer();
                        log.append( date+ "="+raceTeamOne.getHomeTeam()+":"+raceTeamOne.getVisitingTeam()+","+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+ " || "+other.getHomeTeam()+":"+other.getVisitingTeam()+","+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore());
                      if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                              onePei = raceTeamOne.getEquals();
                          log.append("****************  one 平："+onePei);
                      }


                      if(raceTeamOne.getFullHomeTeamCourtscore()==1&&raceTeamOne.getFullVisitingTeamCourtscore()==0){
                              onePei = raceTeamOne.getW10();
                          log.append("****************  one 负：" + onePei);
                          if(other.getFullHomeTeamCourtscore()==2&&other.getFullVisitingTeamCourtscore()==1){
                              twoPei = other.getW21();
                              log.append("**************** two 平："+twoPei).append("  earn=" + onePei*twoPei*2);
//                              logger.info("$$$$$$$$$$$$$$$$$$$$"+date+ "="+raceTeamOne.getRaceNo()+":"+other.getRaceNo()+" "+onePei+"*"+twoPei+"="+onePei*twoPei*2);
                          }else{
                              onePei =0f;
                          }

                      }

                        if(other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore()){
                            twoPei = other.getEquals()*2;
//                            log.append("**************** two 平："+twoPei).append("  earn=" + onePei*twoPei*2);
                        }

                        String   str = ( date+ "=( "+raceTeamOne.getRaceNo()+":"+other.getRaceNo()+"),( "+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+ ") || ("+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore())+") earn=" +onePei+"*"+twoPei+" = "+onePei*twoPei*2;

//                        logger.info(str);
                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend +=6;

//                        logger.info(log.toString());
                    }
                }

                j++;
            }
            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+"  共:"+totalChang+" 平:"+pingCount+"  负:"+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }


    public Float calculatePingFuGeYi(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;

            for(int k=0;k<list.size();k++){
                List<RaceTeam> temp = new ArrayList<RaceTeam>();


            }
            int pingCount = 0;
            int fuCount = 0;
            int totalChang = 0;

            for(RaceTeam raceTeamOne:list){
                totalChang++;
                if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                    pingCount++;
                }else  if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                    fuCount++;
                }
                for(int i=j;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                        Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float thisEarn = 0f;
                        StringBuffer log = new StringBuffer();
                        log.append( date+ "="+raceTeamOne.getHomeTeam()+":"+raceTeamOne.getVisitingTeam()+","+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+ " || "+other.getHomeTeam()+":"+other.getVisitingTeam()+","+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore());
                        if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                            onePei = raceTeamOne.getEquals();
                            log.append("****************  one 平："+onePei);
                        }
//                      if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
//                              onePei = raceTeamOne.getFail();
//                          log.append("****************  one 负：" + onePei);
//
//                      }
                        if(raceTeamOne.getFullHomeTeamCourtscore()==1&&raceTeamOne.getFullVisitingTeamCourtscore()==0){
                            onePei = raceTeamOne.getW10();
                            log.append("****************  one 负：" + onePei);
                            if(other.getFullHomeTeamCourtscore()==2&&other.getFullVisitingTeamCourtscore()==1){
                                twoPei = other.getW21();
                                log.append("**************** two 平："+twoPei).append("  earn=" + onePei*twoPei*2);
//                              logger.info("$$$$$$$$$$$$$$$$$$$$"+date+ "="+raceTeamOne.getRaceNo()+":"+other.getRaceNo()+" "+onePei+"*"+twoPei+"="+onePei*twoPei*2);
                            }else{
                                onePei =0f;
                            }
                        }

                        if(other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore()){
                            twoPei = other.getEquals()*2;
                            log.append("**************** two 平："+twoPei).append("  earn=" + onePei*twoPei*2);
                        }

                        if(onePei!=0&&twoPei!=0){
//                            logger.info("*************"+date+ "  "+raceTeamOne.getRaceNo()+":"+other.getRaceNo()+" "+onePei+"*"+twoPei+"="+onePei*twoPei*2);
                        }

//                      if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
//                          onePei = raceTeamOne.getEquals();
//                      }
//                      if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
//                          onePei = raceTeamOne.getFail();
//                      }
//                      if(other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore()){
//                          twoPei = other.getEquals()*2;
//                      }
//                     if(other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore()&&raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore()){
//                         twoPei = twoPei*2;
//                     }
                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend +=6;

//                        logger.info(log.toString());
                    }
                }

                j++;
            }
            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+"共:"+totalChang+" 平:"+pingCount+"  负:"+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }


    public Float calculatepingHfSheng(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;

            int pingCount = 0;
            int fuCount = 0;

            for(RaceTeam raceTeamOne:list){
                if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                    pingCount++;
                }else  if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                    fuCount++;
                }
                for(int i=j;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                        Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float thisEarn = 0f;
                        StringBuffer log = new StringBuffer();
                        log.append( date+ "="+raceTeamOne.getHomeTeam()+":"+raceTeamOne.getVisitingTeam()+","+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+ " || "+other.getHomeTeam()+":"+other.getVisitingTeam()+","+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore());
                      if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                              onePei = raceTeamOne.getEquals();
                          log.append("****************  one 平："+onePei);
                      }
                      if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
//                              onePei = raceTeamOne.getFail();
//                          log.append("****************  one 负："+onePei);
                      }
                        if(other.getFullHomeTeamCourtscore()>other.getFullVisitingTeamCourtscore()){
                            if(other.getHalfHomeScore()>other.getHalfVisitingScore()){
                                twoPei = other.getHfss();
                            }else  if(other.getHalfHomeScore()==other.getHalfVisitingScore()){
                                twoPei = other.getHfps();
                            }
                            if(null==twoPei)
                                twoPei=3f;

                            log.append("**************** two 平："+twoPei).append("  earn="+onePei*twoPei*2);
                        }

//                      if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
//                          onePei = raceTeamOne.getEquals();
//                      }
//                      if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
//                          onePei = raceTeamOne.getFail();
//                      }
//                      if(other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore()){
//                          twoPei = other.getEquals()*2;
//                      }
//                     if(other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore()&&raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore()){
//                         twoPei = twoPei*2;
//                     }
                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend +=4;

//                        logger.info(log.toString());
                    }
                }

                j++;
            }
            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+" 平："+pingCount+"  负："+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }
    public Float calculateSelectBiFen(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;

            int pingCount = 0;
            int fuCount = 0;

            for(RaceTeam raceTeamOne:list){
                if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                    pingCount++;
                }else  if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                    fuCount++;
                }
                for(int i=0;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                        Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float thisEarn = 0f;
                        StringBuffer log = new StringBuffer();
                        log.append( date+ "="+raceTeamOne.getHomeTeam()+":"+raceTeamOne.getVisitingTeam()+","+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+ " || "+other.getHomeTeam()+":"+other.getVisitingTeam()+","+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore());
                      if(raceTeamOne.getFullHomeTeamCourtscore()==1&&raceTeamOne.getFullVisitingTeamCourtscore()==0){
                              onePei = raceTeamOne.getW10();
                      }
                        if(raceTeamOne.getFullHomeTeamCourtscore()==2&&raceTeamOne.getFullVisitingTeamCourtscore()==1){
                            onePei = raceTeamOne.getW21();
                            log.append("****************  one 平："+onePei);
                        }
//
                        if(other.getFullHomeTeamCourtscore()==1&&other.getFullVisitingTeamCourtscore()==1){
                                twoPei=other.getE11();
                        }

                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend +=4;

//                        logger.info(log.toString());
                    }
                }

                j++;
            }
            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+" 平："+pingCount+"  负："+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }
    public Float calculateHunHe(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;

            int pingCount = 0;
            int fuCount = 0;

            for(RaceTeam raceTeamOne:list){
                if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                    pingCount++;
                }else  if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                    fuCount++;
                }
                for(int i=j;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                        Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                        Float onePei = 0f;
                        Float onePei2 = 0f;
                        Float onePei3 = 0f;
                        Float onePei4 = 0f;
                        Float onePei5 = 0f;
                        Float twoPei = 0f;
                        Float twoPei2 = 0f;
                        Float twoPei3 = 0f;
                        Float twoPei4 = 0f;
                        Float thisEarn = 0f;
                        StringBuffer log = new StringBuffer();
                        log.append( date+ "="+raceTeamOne.getHomeTeam()+":"+raceTeamOne.getVisitingTeam()+","+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+ " || "+other.getHomeTeam()+":"+other.getVisitingTeam()+","+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore());
                      if(raceTeamOne.getFullHomeTeamCourtscore()==1&&raceTeamOne.getFullVisitingTeamCourtscore()==0){
                              onePei = raceTeamOne.getW10();
                      }

                        if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                            onePei = raceTeamOne.getEquals();
                        }
                        if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                            onePei = raceTeamOne.getFail();
                        }




                        if(other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore()){
                                twoPei=other.getEquals();
                        }
                        if(other.getFullHomeTeamCourtscore()<other.getFullVisitingTeamCourtscore()){
                                twoPei=other.getFail();
                        }
                        if(other.getFullHomeTeamCourtscore()==1&&other.getFullVisitingTeamCourtscore()==0){
                                twoPei=other.getW10();
                        }



                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend +=50;

                        onePei = 0f;
                        twoPei = 0f;
                        if(raceTeamOne.getFullHomeTeamCourtscore()==1&&raceTeamOne.getFullVisitingTeamCourtscore()==1){
                            onePei =3.5f;
                            if(other.getFullHomeTeamCourtscore()==0&&other.getFullVisitingTeamCourtscore()==2){
                                twoPei =3.5f;
                            }
                            if(other.getFullHomeTeamCourtscore()==1&&other.getFullVisitingTeamCourtscore()==2){
                                twoPei =3.5f;
                            }
                            if(other.getFullHomeTeamCourtscore()==0&&other.getFullVisitingTeamCourtscore()==3){
                                twoPei =3.5f;
                            }
                            thisEarn += onePei * twoPei * 2;

                        }

                        if(raceTeamOne.getFullHomeTeamCourtscore()==0&&raceTeamOne.getFullVisitingTeamCourtscore()==2){
                            onePei =3.5f;
                            if(other.getFullHomeTeamCourtscore()==0&&other.getFullVisitingTeamCourtscore()==2){
                                twoPei =3.5f;
                            }
                            if(other.getFullHomeTeamCourtscore()==1&&other.getFullVisitingTeamCourtscore()==2){
                                twoPei =3.5f;
                            }
                            if(other.getFullHomeTeamCourtscore()==0&&other.getFullVisitingTeamCourtscore()==3){
                                twoPei =3.5f;
                            }
                            thisEarn += onePei * twoPei * 2;
                        }
                        if(raceTeamOne.getFullHomeTeamCourtscore()==1&&raceTeamOne.getFullVisitingTeamCourtscore()==2){
                            onePei =3.5f;
                            if(other.getFullHomeTeamCourtscore()==0&&other.getFullVisitingTeamCourtscore()==2){
                                twoPei =3.5f;
                            }
                            if(other.getFullHomeTeamCourtscore()==1&&other.getFullVisitingTeamCourtscore()==2){
                                twoPei =3.5f;
                            }
                            if(other.getFullHomeTeamCourtscore()==0&&other.getFullVisitingTeamCourtscore()==3){
                                twoPei =3.5f;
                            }
                            thisEarn += onePei * twoPei * 2;
                        }
                        if(raceTeamOne.getFullHomeTeamCourtscore()==0&&raceTeamOne.getFullVisitingTeamCourtscore()==3){
                            onePei =3.5f;
                            if(other.getFullHomeTeamCourtscore()==0&&other.getFullVisitingTeamCourtscore()==2){
                                twoPei =3.5f;
                            }
                            if(other.getFullHomeTeamCourtscore()==1&&other.getFullVisitingTeamCourtscore()==2){
                                twoPei =3.5f;
                            }
                            if(other.getFullHomeTeamCourtscore()==0&&other.getFullVisitingTeamCourtscore()==3){
                                twoPei =3.5f;
                            }
                            thisEarn += onePei * twoPei * 2;
                        }



//                        logger.info(log.toString());
                    }
                }

                j++;
            }
            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+" 平："+pingCount+"  负："+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }



    private Float  getPeiLvByBiFen(RaceTeam raceTeam){

        String zhu =  raceTeam.getFullHomeTeamCourtscore().toString();
        String ke = raceTeam.getFullVisitingTeamCourtscore().toString();
        Float winmin = raceTeam.getWinmin();
        Float eqmin = raceTeam.getEqmin();

        if(winmin<eqmin){
                if( (zhu+":"+ke).equals("1:0")){
                   if(winmin==raceTeam.getW10()){
                       return  winmin;
                   }
                }else if( (zhu+":"+ke).equals("2:0")){
                    if(winmin==raceTeam.getW20()){
                        return  winmin;
                    }
                }else if( (zhu+":"+ke).equals("3:0")){
                    if(winmin==raceTeam.getW30()){
                        return  winmin;
                    }
                }
                else if( (zhu+":"+ke).equals("2:1")){
                    if(winmin==raceTeam.getW21()){
                        return  winmin;
                    }
                }
                else if( (zhu+":"+ke).equals("3:1")){
                    if(winmin==raceTeam.getW31()){
                        return  winmin;
                    }
                }
        }else if(  winmin>eqmin){
              if( (zhu+":"+ke).equals("0:0")){
                if(winmin==raceTeam.getE00()){
                    return  eqmin;
                }
            }else if( (zhu+":"+ke).equals("1:1")){
                if(winmin==raceTeam.getE11()){
                    return  eqmin;
                }
            }else if( (zhu+":"+ke).equals("2:2")){
                if(winmin==raceTeam.getE22()){
                    return  eqmin;
                }
            }else if( (zhu+":"+ke).equals("3:3")){
                if(winmin==raceTeam.getE33()){
                    return  eqmin;
                }
            }
        }
        return 0f;

    }



    public Float calculateCaiBFGeYi(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float beiShu) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;


            Float totalChang = 0f;
            Float pingCount = 0f;
            Float fuCount = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;
            for(RaceTeam raceTeamOne:list){
                totalChang++;
                if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                    pingCount++;
                }
                if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                    fuCount++;
                }

                for(int i=j;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                        Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float thisEarn = 0f;
                      if(raceTeamOne.getFullHomeTeamCourtscore()==1&&raceTeamOne.getFullVisitingTeamCourtscore()==0){
                          onePei = raceTeamOne.getW10();
                      }

                      if(other.getFullHomeTeamCourtscore()==1&&other.getFullVisitingTeamCourtscore()==0){
                          twoPei = other.getW10();
                      }
                     String   log = ( date+ "=( "+raceTeamOne.getRaceNo()+":"+other.getRaceNo()+"),( "+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+ ") || ("+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore())+") earn=" +onePei+"*"+twoPei+"*"+beiShu+" = "+onePei*twoPei*2;

//                        logger.info(log);
                        thisEarn = onePei * twoPei * 2*beiShu;
                        allEarn += thisEarn;
                        allSpend += 2*beiShu;


                    }
                }

                j++;
            }
            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+"  共:"+totalChang+" 平:"+pingCount+"  负:"+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }
    public Float calculateFromSheng1BuyRang(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float beiShu) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;


            Float totalChang = 0f;
            Float pingCount = 0f;
            Float shengCount = 0f;
            Float fuCount = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;
            for(RaceTeam raceTeamOne:list){
                totalChang++;
                if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                    pingCount++;
                }
                if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
                    fuCount++;
                }
                if(raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore()){
                    shengCount++;
                }

                for(int i=0;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                        Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float thisEarn = 0f;

                      if((raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore())){
                          onePei = raceTeamOne.getWin();
                      }
//                      if((other.getFullHomeTeamCourtscore()+other.getRangScore())>other.getFullVisitingTeamCourtscore()){
//                          twoPei = other.getRangWin();
//                      }
                      if((other.getFullHomeTeamCourtscore()+other.getRangScore())<other.getFullVisitingTeamCourtscore()){
                          twoPei = other.getRangFail();
                      }


//                        if((other.getFullHomeTeamCourtscore()<other.getFullVisitingTeamCourtscore())){
//                            twoPei = other.getFail();
//                            allEarn += (onePei * twoPei * 2*beiShu) ;
//                            String   log = ( date+ "=( "+raceTeamOne.getRaceNo()+":"+other.getRaceNo()+"),( "+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+ ") || ("+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore())+") earn=" +onePei+"*"+twoPei+""+beiShu+" = "+onePei*twoPei*2*beiShu;
////                            logger.info(log);
//                        }
//                      if((other.getFullHomeTeamCourtscore()+other.getRangScore())<other.getFullVisitingTeamCourtscore()){
//                          twoPei = other.getRangFail();
//                      }
                     String   log = ( date+ "=( "+raceTeamOne.getRaceNo()+":"+other.getRaceNo()+"),( "+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+ ") || ("+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore())+") earn=" +onePei+"*"+twoPei+""+beiShu+" = "+onePei*twoPei*2*beiShu;
//                        logger.info(log);
                        thisEarn = (onePei * twoPei * 2*beiShu) ;
                        allEarn += thisEarn;
                        allSpend += 2*beiShu ;
                    }
                }

                j++;
            }
            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+"  共:"+totalChang+" 胜:"+shengCount+" 平:"+pingCount+"  负:"+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }
    public Float calculateLiLun5fen3PingMaiRangSheng(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float beiShu) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;


            int totalChang = 0;
            int pingCount = 0;
            int shengCount = 0;
            int fuCount = 0;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
//            if(list.size()<6) return 0f;

            beiShu=1f;
            String jieguo = "";
            float quankui = 1f;
            for(int k=0;k<list.size();k++) {
                RaceTeam raceTeamOne = list.get(k);
                totalChang++;
                if (raceTeamOne.getFullHomeTeamCourtscore() == raceTeamOne.getFullVisitingTeamCourtscore()) {
                    pingCount++;
                    jieguo+="F" ;
                    quankui = quankui*raceTeamOne.getRangFail();
                }
                if (raceTeamOne.getFullHomeTeamCourtscore() < raceTeamOne.getFullVisitingTeamCourtscore()) {
                    fuCount++;
                    jieguo+="F" ;
                    quankui = quankui*raceTeamOne.getRangFail();
                }
                if (raceTeamOne.getFullHomeTeamCourtscore() > raceTeamOne.getFullVisitingTeamCourtscore()) {
                    shengCount++;
                    jieguo+="S" ;
                    quankui = quankui*raceTeamOne.getWin();
                }
            }

            for(int k=0;k<list.size();k++) {
                RaceTeam raceTeamOne = list.get(k);
                for (int i = k; i < list.size(); i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float threePei = 0f;
                        Float fourPei = 0f;
                        Float thisEarn = 0f;
                        if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                            hasCalculate.add(other);

                                if ((raceTeamOne.getFullHomeTeamCourtscore() <=raceTeamOne.getFullVisitingTeamCourtscore())) {
                                    onePei = raceTeamOne.getRangFail();
                                }

                                if ((other.getFullHomeTeamCourtscore() <=other.getFullVisitingTeamCourtscore())) {
                                    twoPei = other.getRangFail();
                                }

//                                if ((raceTeamOne.getFullHomeTeamCourtscore() -1==raceTeamOne.getFullVisitingTeamCourtscore())) {
//                                    threePei = raceTeamOne.getRangEq();
//                                }
//                                if ((other.getFullHomeTeamCourtscore() -1==other.getFullVisitingTeamCourtscore())) {
//                                    fourPei = other.getRangEq();
//                                }
                            String log = (date + "()=( " + raceTeamOne.getRaceNo() + ":" + other.getRaceNo() + "),( " + raceTeamOne.getFullHomeTeamCourtscore() + ":" + raceTeamOne.getFullVisitingTeamCourtscore() + ")(" + raceTeamOne.getRangScore() + ") || (" + other.getFullHomeTeamCourtscore() + ":" + other.getFullVisitingTeamCourtscore()) + ")(" + other.getRangScore() + ") earn=" + onePei + "*" + twoPei + "" + beiShu + " = " + onePei * twoPei * 2 * beiShu;
//                        logger.info(log);
                            if(null!=onePei&&twoPei!=null){
                                allEarn += (onePei * twoPei * 2) * beiShu;
//                                allEarn += (threePei * fourPei * 2) * beiShu;
                                allSpend += 2 * beiShu;
                            }
                        }
                    }
                    j++;
                }

            }


//            if(null!=list&&list.size()>=6){
//                for(int i=0;i<list.size();i+=2){
//                    if((i+1)>=list.size()) break;
//                    RaceTeam one = list.get(i);
//                    RaceTeam two = list.get(i+1);
//                    Float thisEarn = 0f;
//                    Float onePeilv = 0f;
//                    Float twoPeilv = 0f;
//                    if(one.getFullHomeTeamCourtscore()>one.getFullVisitingTeamCourtscore()){
//                        onePeilv = one.getWin();
//                    }
//
//                    if(two.getFullHomeTeamCourtscore()>two.getFullVisitingTeamCourtscore()){
//                        twoPeilv = two.getWin();
//                    }
//                    thisEarn = onePeilv*twoPeilv*2;
//                    allSpend+=2;
//                    allEarn+=thisEarn;
//
//                }
//            }


            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+"  ("+jieguo+") 共:"+totalChang+" 胜:"+shengCount+" 平:"+pingCount+"  负:"+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }
    public Float calculateGeDayBuDan(List<RaceTeam> oneList,List<RaceTeam> twoList,List<RaceTeam> threeList,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float beiShu) {
        if(null!=oneList&& oneList.size()>=2&&null!=twoList&& twoList.size()>=2&&null!=threeList&& threeList.size()>=2){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;


            int totalChang = 0;
            int pingCount = 0;
            int shengCount = 0;
            int fuCount = 0;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
//            if(list.size()<6) return 0f;
            Float onePei = 0f;
            Float twoPei = 0f;
            Float otherOnePei = 0f;
            Float otherTwoPei = 0f;
            Float thisEarn = 0f;


               RaceTeam one =   oneList.get(0);
               RaceTeam other =   oneList.get(1);
               RaceTeam two =   twoList.get(0);
               RaceTeam otherTwo =   twoList.get(1);
               RaceTeam three =   threeList.get(0);
               RaceTeam otherThree =   threeList.get(1);


            //win
            if(one.getFullHomeTeamCourtscore()>one.getFullVisitingTeamCourtscore()){
                if(two.getFullHomeTeamCourtscore()>two.getFullVisitingTeamCourtscore()){
                    onePei = one.getWin();
                    twoPei = two.getWin();
                }
            }else{//rangfail
                if(three.getFullHomeTeamCourtscore()<=three.getFullVisitingTeamCourtscore()){
                    onePei = one.getRangFail();
                    twoPei = three.getWin();
                }

            }


            //otherWIn
            if(other.getFullHomeTeamCourtscore()>other.getFullVisitingTeamCourtscore()){
                if(otherTwo.getFullHomeTeamCourtscore()>otherTwo.getFullVisitingTeamCourtscore()){
                    otherOnePei = other.getWin();
                    otherTwoPei = otherTwo.getWin();
                }
            }else{//other rangfail
                if(otherThree.getFullHomeTeamCourtscore()<=otherThree.getFullVisitingTeamCourtscore()){
                    otherOnePei = other.getRangFail();
                    otherTwoPei = otherThree.getWin();
                }
            }




            if(otherThree.getFullHomeTeamCourtscore()>otherThree.getFullVisitingTeamCourtscore()||three.getFullHomeTeamCourtscore()>three.getFullVisitingTeamCourtscore()){
                   allSpend+=2;

                if(otherThree.getFullHomeTeamCourtscore()>otherThree.getFullVisitingTeamCourtscore()&&three.getFullHomeTeamCourtscore()>three.getFullVisitingTeamCourtscore()){

                    thisEarn+=three.getWin()*otherThree.getWin()* 2;
                }

            }



            List<RaceTeam> oneLine = new ArrayList<RaceTeam>();
            oneLine.addAll(oneList);
            oneLine.addAll(twoList);
            oneLine.addAll(threeList);
            String jieguo = "";
            for(int k=0;k<oneLine.size();k++) {
                RaceTeam raceTeamOne = oneLine.get(k);
                totalChang++;
                if (raceTeamOne.getFullHomeTeamCourtscore() == raceTeamOne.getFullVisitingTeamCourtscore()) {
                    pingCount++;
                    jieguo+="F" ;
                }
                if (raceTeamOne.getFullHomeTeamCourtscore() < raceTeamOne.getFullVisitingTeamCourtscore()) {
                    fuCount++;
                    jieguo+="F" ;
                }
                if (raceTeamOne.getFullHomeTeamCourtscore() > raceTeamOne.getFullVisitingTeamCourtscore()) {
                    shengCount++;
                    jieguo+="S" ;
                }
            }
            List<RaceTeam> twoLine = new ArrayList<RaceTeam>();
            twoLine.addAll(oneList);
            twoLine.addAll(twoList);
            twoLine.addAll(threeList);
            jieguo += "  twoLine= ";
            for(int k=0;k<twoLine.size();k++) {
                RaceTeam raceTeamOne = twoLine.get(k);
                totalChang++;
                if (raceTeamOne.getFullHomeTeamCourtscore() == raceTeamOne.getFullVisitingTeamCourtscore()) {
                    pingCount++;
                    jieguo+="F" ;
                }
                if (raceTeamOne.getFullHomeTeamCourtscore() < raceTeamOne.getFullVisitingTeamCourtscore()) {
                    fuCount++;
                    jieguo+="F" ;
                }
                if (raceTeamOne.getFullHomeTeamCourtscore() > raceTeamOne.getFullVisitingTeamCourtscore()) {
                    shengCount++;
                    jieguo+="S" ;
                }
            }



            thisEarn += onePei*twoPei*2;
            thisEarn += otherOnePei*otherTwoPei*2;
            allEarn += thisEarn;
            allSpend += 8;


            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+"  ("+jieguo+") 共:"+totalChang+" 胜:"+shengCount+" 平:"+pingCount+"  负:"+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }
    public Float calculateShengBuySheng(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float beiShu) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;


            int totalChang = 0;
            int pingCount = 0;
            int shengCount = 0;
            int fuCount = 0;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;

            String jieguo = "";
            for(int k=0;k<list.size();k++) {
                RaceTeam raceTeamOne = list.get(k);
                totalChang++;
                if (raceTeamOne.getFullHomeTeamCourtscore() == raceTeamOne.getFullVisitingTeamCourtscore()) {
                    pingCount++;
                    jieguo+="F" ;
                }
                if (raceTeamOne.getFullHomeTeamCourtscore() < raceTeamOne.getFullVisitingTeamCourtscore()) {
                    fuCount++;
                    jieguo+="F" ;
                }
                if (raceTeamOne.getFullHomeTeamCourtscore() > raceTeamOne.getFullVisitingTeamCourtscore()) {
                    shengCount++;
                    jieguo+="S" ;
                }
            }

            for(int k=0;k<1;k++){
                RaceTeam raceTeamOne  = list.get(k);
                for(int i=j;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                        Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float thisEarn = 0f;

                            if((raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore())){
                                onePei = raceTeamOne.getWin();
                            }

                            if((other.getFullHomeTeamCourtscore()>other.getFullVisitingTeamCourtscore())){
                                twoPei = other.getWin();
                            }


                     String   log = ( date+ "()=( "+raceTeamOne.getRaceNo()+":"+other.getRaceNo()+"),( "+raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore()+ ")("+raceTeamOne.getRangScore()+") || ("+other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore())+")("+other.getRangScore()+") earn=" +onePei+"*"+twoPei+""+beiShu+" = "+onePei*twoPei*2*beiShu;
//                        logger.info(log);
                        allEarn += (onePei * twoPei * 2)*beiShu ;
                        allSpend += 2*beiShu ;
                    }
                }

                j++;
            }




            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+"  ("+jieguo+") 共:"+totalChang+" 胜:"+shengCount+" 平:"+pingCount+"  负:"+fuCount+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }
    public Float calculateChengDuiJiSuan(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float beiShu) {
        if (null != list && list.size() > 0) {
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn = 0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;


            int totalChang = 0;
            int pingCount = 0;
            int shengCount = 0;
            int fuCount = 0;
            int zhong = 0;
            List<RaceTeam> hasCalculate = new ArrayList<RaceTeam>();
            int j = 0;
            List<String> logList = new ArrayList<String>();
            if (list.size() < 6) return 0f;

            String jieguo = "";
            for (int k = 0; k < list.size(); k++) {
                if(k>=3){
//                    break;
//                    jieguo+="--";
                }
                RaceTeam raceTeamOne = list.get(k);
                totalChang++;
                if (raceTeamOne.getFullHomeTeamCourtscore() == raceTeamOne.getFullVisitingTeamCourtscore()) {
                    pingCount++;
                    jieguo += "F";
                }
                if (raceTeamOne.getFullHomeTeamCourtscore() < raceTeamOne.getFullVisitingTeamCourtscore()) {
                    fuCount++;
                    jieguo += "F";
                }
                if (raceTeamOne.getFullHomeTeamCourtscore() > raceTeamOne.getFullVisitingTeamCourtscore()) {
                    shengCount++;
                    jieguo += "S";
                }
            }

            RaceTeam one1 = list.get(0);
            RaceTeam one2 = list.get(1);
            RaceTeam one3 = list.get(2);
            RaceTeam two1 = list.get(3);
            RaceTeam two2 = list.get(4);
            RaceTeam two3 = list.get(5);

            Float onePei = 0f;
            Float twoPei = 0f;
            if ((one1.getFullHomeTeamCourtscore() > one1.getFullVisitingTeamCourtscore())) {
                if ((two1.getFullHomeTeamCourtscore() > two1.getFullVisitingTeamCourtscore())) {
                    onePei = one1.getWin();
                    twoPei = two1.getWin();
                    allEarn += (onePei * twoPei * 2)*beiShu;
                }
            }
//            if ((one2.getFullHomeTeamCourtscore() > one2.getFullVisitingTeamCourtscore())) {
//                if ((two2.getFullHomeTeamCourtscore() > two2.getFullVisitingTeamCourtscore())) {
//                    onePei = one2.getWin();
//                    twoPei = two2.getWin();
//                    allEarn += (onePei * twoPei * 2)*beiShu;
//                }
//            }
//            if ((one3.getFullHomeTeamCourtscore() > one3.getFullVisitingTeamCourtscore())) {
//                if ((two3.getFullHomeTeamCourtscore() > two3.getFullVisitingTeamCourtscore())) {
//                    onePei = one3.getWin();
//                    twoPei = two3.getWin();
//                    allEarn += (onePei * twoPei * 2)*beiShu;
//                }
//            }
//            if ((two1.getFullHomeTeamCourtscore() > two1.getFullVisitingTeamCourtscore())) {
//                if ((one1.getFullHomeTeamCourtscore() <= one1.getFullVisitingTeamCourtscore())) {
//                    twoPei = two1.getWin();
//                    onePei = one1.getRangFail();
//                    allEarn += (onePei * twoPei * 2)*beiShu;
//                }
//            }
//            if ((two2.getFullHomeTeamCourtscore() > two2.getFullVisitingTeamCourtscore())) {
//                if ((one2.getFullHomeTeamCourtscore() <= one2.getFullVisitingTeamCourtscore())) {
//                    twoPei = two2.getWin();
//                    onePei = one2.getRangFail();
//                    allEarn += (onePei * twoPei * 2)*beiShu;
//                }
//            }
//            if ((two3.getFullHomeTeamCourtscore() > two3.getFullVisitingTeamCourtscore())) {
//                if ((one3.getFullHomeTeamCourtscore() <= one3.getFullVisitingTeamCourtscore())) {
//                    twoPei = two3.getWin();
//                    onePei = one3.getRangFail();
//                    allEarn += (onePei * twoPei * 2)*beiShu;
//                }
//            }

            allSpend += 12*beiShu;


            Float yingkui = (rangAllEarn + allEarn) - (rangAllspend + allSpend);
            String oneDay = "YingKui=   " + yingkui + "; Date=" + date + "  (" + jieguo + ") 共:" + totalChang + " 胜:" + shengCount + " 平:" + pingCount + "  负:" + fuCount + " rangAllSpend:" + rangAllspend + " ,rangAllEarn:" + rangAllEarn + ";" + "allSpend:" + allSpend + " ,allEarn:" + allEarn + "  zhong:" + zhong;
            logger.info(oneDay);
            return yingkui;
        }else{
            return 0f;
        }
    }


    public Float calculateCaiBfTemp(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            for(RaceTeam raceTeamOne:list){
                for(int i=j;i<list.size();i++) {
                    List<LocalFloat> hasSelect = new ArrayList<LocalFloat>();
                    RaceTeam other = list.get(i);
                    if (!raceTeamOne.getHomeTeam().equals(other.getHomeTeam()) && raceTeamOne.getRaceDate().compareTo(other.getRaceDate()) == 0) {
                        hasCalculate.add(other);
                        Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                        Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                        Float onePei = 0f;
                        Float twoPei = 0f;
                        Float thisEarn = 0f;



                        if(raceTeamOne.getFullHomeTeamCourtscore()==1&&raceTeamOne.getFullVisitingTeamCourtscore()==1){
                            onePei = 7f;
                        }
                        if(raceTeamOne.getFullHomeTeamCourtscore()==1&&raceTeamOne.getFullVisitingTeamCourtscore()==0){
//                            onePei = 7f;
                        }
                        if(other.getFullHomeTeamCourtscore()==1&&other.getFullVisitingTeamCourtscore()==1){
                            twoPei = 7f;
                        }
                        if(other.getFullHomeTeamCourtscore()==1&&other.getFullVisitingTeamCourtscore()==0){
//                            twoPei = 7f;
                        }

                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend += 2;

                    }
                }

                j++;
            }
            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }


    public Float calculateMoneyByRandomSelect(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();




            if(null!=list&&list.size()>=6){
                for(int i=0;i<list.size();i+=2){
                    if((i+1)>=list.size()) break;
                    RaceTeam one = list.get(i);
                    RaceTeam two = list.get(i+1);
                    Float thisEarn = 0f;
                    Float onePeilv = 0f;
                    Float twoPeilv = 0f;
                    if(one.getFullVisitingTeamCourtscore()==one.getFullHomeTeamCourtscore()){
                        onePeilv = one.getEquals();
                    }
                    if(one.getFullVisitingTeamCourtscore()>one.getFullHomeTeamCourtscore()){
                        onePeilv = one.getFail();
                    }
                    if(two.getFullVisitingTeamCourtscore()==two.getFullHomeTeamCourtscore()){
                        twoPeilv = two.getEquals();
                    }
                    thisEarn = onePeilv*twoPeilv*2;
                    allSpend+=4;
                    allEarn+=thisEarn;

                }
            }

            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
            String oneDay ="YingKui=   "+yingkui+"; Date="+date+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
            logger.info(oneDay);
            return  yingkui;
        }
        return 0f;
    }



    public Float calculateHengShuBi(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;
            for(RaceTeam raceTeamOne:list){
                RaceTeam other = null;
                if((j+1)>=list.size()) {
                    other = list.get(0);
                }else{
                    other = list.get(j+1);
                }

                Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                Float onePei = 0f;
                Float twoPei = 0f;
                Float thisEarn = 0f;



                        if(raceTeamOne.getFullHomeTeamCourtscore()==1&&raceTeamOne.getFullVisitingTeamCourtscore()==1){
//                            onePei = 6f;
                        }
                        if(raceTeamOne.getFullHomeTeamCourtscore()==2&&raceTeamOne.getFullVisitingTeamCourtscore()==0){
                            onePei = 13f;
                        }
                        if(twoTotal==1||twoTotal==2||twoTotal==3){
                            twoPei = 3.5f;
                        }


                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend += 6;

                j++;
            }

            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
        String oneDay ="YingKui=   "+yingkui+"; Date="+date+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
        logger.info(oneDay);
        return  yingkui;
        }
        return 0f;
    }
    public Float calculateBanQuanChang(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;
            for(int i=0;i<list.size();i+=2){
                RaceTeam raceTeamOne = list.get(i);
                RaceTeam other = null;
                if((i+1)>=list.size()) {
                    break;
                }else{
                    other = list.get(i+1);
                }

                Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                Float onePei = 0f;
                Float twoPei = 0f;
                Float thisEarn = 0f;



                        if(raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore()||raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
                            onePei = 4f;
                        }
                        if(other.getFullHomeTeamCourtscore()>other.getFullVisitingTeamCourtscore()||other.getFullHomeTeamCourtscore()==other.getFullVisitingTeamCourtscore()){
                            twoPei = 4f;
                        }
                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend += 32;

                j++;
            }

            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
        String oneDay ="YingKui=   "+yingkui+"; Date="+date+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
        logger.info(oneDay);
        return  yingkui;
        }
        return 0f;
    }
    public Float calculateRangqiu(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;
            for(int i=0;i<list.size();i+=2){
                RaceTeam raceTeamOne = list.get(i);
                RaceTeam other = null;
                if((i+1)>=list.size()) {
                    break;
                }else{
                    other = list.get(i+1);
                }

                Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                Float onePei = 0f;
                Float twoPei = 0f;
                Float thisEarn = 0f;



                        if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore())>raceTeamOne.getFullVisitingTeamCourtscore()){
                            onePei = raceTeamOne.getRangWin();
                        }
                        if((raceTeamOne.getFullHomeTeamCourtscore()+raceTeamOne.getRangScore())==raceTeamOne.getFullVisitingTeamCourtscore()){
                            onePei = raceTeamOne.getRangEq();
                        }
                        if((other.getFullHomeTeamCourtscore()+other.getRangScore())>other.getFullVisitingTeamCourtscore()){
                            twoPei = raceTeamOne.getRangWin();
                        }
                        if((other.getFullHomeTeamCourtscore()+other.getRangScore())==other.getFullVisitingTeamCourtscore()){
                            twoPei = raceTeamOne.getRangEq();
                        }

                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend += 8;

                j++;
            }

            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
        String oneDay ="YingKui=   "+yingkui+"; Date="+date+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
        logger.info(oneDay);
        return  yingkui;
        }
        return 0f;
    }

    public Float calculateCaiBf(List<RaceTeam> list,List<String> oneSelect,List<String> twoSelect,List<String> rangOneSelect,List<String> rangTwoSelect,String date,Float total) {
        if(null!=list&& list.size()>0){
            Float allSpend = 0.00f;
            Float allEarn = 0.00f;
            Float rangAllSpend = 0.00f;
            Float rangAllOwn  =0.00f;
            Float rangAllspend = 0f;
            Float rangAllEarn = 0f;
            int zhong = 0;
            List<RaceTeam>  hasCalculate = new ArrayList<RaceTeam>();
            int j=0;
            List<String> logList = new ArrayList<String>();
            if(list.size()<6) return 0f;
            for(int i=0;i<list.size();i+=2){
                RaceTeam raceTeamOne = list.get(i);
                RaceTeam other = null;
                if((i+1)>=list.size()) {
                    break;
                }else{
                    other = list.get(i+1);
                }

                Integer oneTotal = raceTeamOne.getFullHomeTeamCourtscore() + raceTeamOne.getFullVisitingTeamCourtscore();
                Integer twoTotal = other.getFullVisitingTeamCourtscore() + other.getFullHomeTeamCourtscore();

                String oneScore = raceTeamOne.getFullHomeTeamCourtscore()+":"+raceTeamOne.getFullVisitingTeamCourtscore();
                String twoScore = other.getFullHomeTeamCourtscore()+":"+other.getFullVisitingTeamCourtscore();

                Float onePei = 0f;
                Float twoPei = 0f;
                Float thisEarn = 0f;


                String yuCeResultOne = "";
                String yuCeResultTwo = "";

                Float winmin = raceTeamOne.getWinmin();
                Float eqmin = raceTeamOne.getEqmin();
                Float failmin = raceTeamOne.getFailmin();
                List<Float> temp = new ArrayList<Float>();
                temp.add(winmin);
                temp.add(eqmin);
                temp.add(failmin);
                Collections.sort(temp);

                if(winmin==temp.get(0)){
                    yuCeResultOne="胜";
                }
                if(eqmin==temp.get(0)){
                    yuCeResultOne="胜";
                }
                if(failmin==temp.get(0)){
                    yuCeResultOne="胜";
                }
                if(winmin==temp.get(1)){
                    yuCeResultTwo ="平";
                }
                if(eqmin==temp.get(1)){
                    yuCeResultTwo ="平";
                }
                if(failmin==temp.get(1)){
                    yuCeResultTwo ="平";
                }


                List oneBiFenSelect = getSelectBifen(raceTeamOne);
                List twoBiFenSelect = getSelectBifen(other);


                if(oneBiFenSelect.contains(oneScore)){
                    if(oneScore.equals("0:0")){
                        onePei = raceTeamOne.getE00();
                    }else  if(oneScore.equals("1:1")){
                        onePei = raceTeamOne.getE11();
                    }else  if(oneScore.equals("2:2")){
                        onePei = raceTeamOne.getE22();
                    }else  if(oneScore.equals("3:3")){
                        onePei = raceTeamOne.getE33();
                    }else  if(oneScore.equals("1:0")){
                        onePei = raceTeamOne.getW10();
                    }else  if(oneScore.equals("2:0")){
                        onePei = raceTeamOne.getW20();
                    }else  if(oneScore.equals("3:0")){
                        onePei = raceTeamOne.getW30();
                    }else  if(oneScore.equals("2:1")){
                        onePei = raceTeamOne.getW21();
                    }else  if(oneScore.equals("3:1")){
                        onePei = raceTeamOne.getW31();
                    }else  if(oneScore.equals("0:1")){
                        onePei = raceTeamOne.getF01();
                    }else  if(oneScore.equals("0:2")){
                        onePei = raceTeamOne.getF02();
                    }else  if(oneScore.equals("0:3")){
                        onePei = raceTeamOne.getF03();
                    }else  if(oneScore.equals("1:2")){
                        onePei = raceTeamOne.getF12();
                    }else  if(oneScore.equals("1:3")){
                        onePei = raceTeamOne.getF13();
                    }
                }
                if(twoBiFenSelect.contains(twoScore)){
                    if(twoScore.equals("0:0")){
                        twoPei = other.getE00();
                    }else  if(twoScore.equals("1:1")){
                        twoPei = other.getE11();
                    }else  if(twoScore.equals("2:2")){
                        twoPei = other.getE22();
                    }else  if(twoScore.equals("3:3")){
                        twoPei = other.getE33();
                    }else  if(twoScore.equals("1:0")){
                        twoPei = other.getW10();
                    }else  if(twoScore.equals("2:0")){
                        twoPei = other.getW20();
                    }else  if(twoScore.equals("3:0")){
                        twoPei = other.getW30();
                    }else  if(twoScore.equals("2:1")){
                        twoPei = other.getW21();
                    }else  if(twoScore.equals("3:1")){
                        twoPei = other.getW31();
                    }else  if(twoScore.equals("0:1")){
                        twoPei = other.getF01();
                    }else  if(twoScore.equals("0:2")){
                        twoPei = other.getF02();
                    }else  if(twoScore.equals("0:3")){
                        twoPei = other.getF03();
                    }else  if(twoScore.equals("1:2")){
                        twoPei = other.getF12();
                    }else  if(twoScore.equals("1:3")){
                        twoPei = other.getF13();
                    }
                }
                        thisEarn = onePei * twoPei * 2;
                        allEarn += thisEarn;
                        allSpend +=128 ;

                j++;
            }

            Float yingkui = (rangAllEarn+allEarn)-(rangAllspend+allSpend);
        String oneDay ="YingKui=   "+yingkui+"; Date="+date+" rangAllSpend:"+rangAllspend+" ,rangAllEarn:"+rangAllEarn+";"+"allSpend:"+allSpend+" ,allEarn:"+allEarn+"  zhong:"+zhong;
        logger.info(oneDay);
        return  yingkui;
        }
        return 0f;
    }


    /**
     * get sheng  and fu  minest peilv
     * @param raceTeam
     * @return
     */
    private List<String> getSelectBifen(RaceTeam raceTeam){

        Float w10 =raceTeam.getW10();
        Float w20 =raceTeam.getW20();
        Float w30 =raceTeam.getW30();
        Float w21 =raceTeam.getW21();
        Float w31 =raceTeam.getW31();

        Float e00 = raceTeam.getE00();
        Float e11 = raceTeam.getE11();
        Float e22 = raceTeam.getE22();
        Float e33 = raceTeam.getE33();


        Map<Float,String> winTemp = new HashMap< Float,String>();
        Map<Float,String> eqTemp = new HashMap< Float,String>();
        List<Float> winSelectFloat = new ArrayList<Float>();
        List<Float> eqSelectFloat = new ArrayList<Float>();

        List<String> result = new ArrayList<String>();
        eqTemp.put(e00,"0:0");
        eqTemp.put(e11,"1:1");
        eqTemp.put(e22,"2:2");
        eqTemp.put(e33,"3:3");

        winTemp.put(w10,"1:0");
        winTemp.put(w20,"2:0");
        winTemp.put(w30,"3:0");
        winTemp.put(w21,"2:1");
        winTemp.put(w31, "3:1");

        winSelectFloat.add(w10);
        winSelectFloat.add(w20);
        winSelectFloat.add(w30);
        winSelectFloat.add(w21);
        winSelectFloat.add(w31);

        eqSelectFloat.add(e00);
        eqSelectFloat.add(e11);
        eqSelectFloat.add(e22);
        eqSelectFloat.add(e33);

        Collections.sort(winSelectFloat);
        Collections.sort(eqSelectFloat);

        for(int i=0;i<4;i++){
            result.add(winTemp.get(winSelectFloat.get(i)));
        }
        for(int i=0;i<4;i++){
            result.add(eqTemp.get(eqSelectFloat.get(i)));
        }

        return result;
    }



    /**
     * 组装 让球的 earn
     * @param oneSelect
     * @param twoSelect
     * @param raceTeamOne
     * @param other
     * @param rangOneReuslt
     * @param rangOtherResult
     * @param earn
     * @return
     */
    private  List<LocalFloat>  getRangEarnBySelect(List<String> oneSelect,List<String> twoSelect ,RaceTeam raceTeamOne,RaceTeam other,String rangOneReuslt,String rangOtherResult,Float earn){

        List<LocalFloat>  hasSelect = new ArrayList<LocalFloat>();
        if(oneSelect.contains("让胜")&&twoSelect.contains("让胜")){
            LocalFloat oneZuHe = new LocalFloat();
            oneZuHe.setValue(earn);
            oneZuHe.setSpf("让胜让胜");
            setLocalFloat(oneZuHe, raceTeamOne, other, rangOneReuslt, rangOtherResult);
            hasSelect.add(oneZuHe);
        }  if(oneSelect.contains("让胜")&&twoSelect.contains("让平")){
            LocalFloat oneZuHe = new LocalFloat();
            oneZuHe.setValue(earn);
            oneZuHe.setSpf("让胜让平");
            setLocalFloat(oneZuHe, raceTeamOne, other, rangOneReuslt, rangOtherResult);
            hasSelect.add(oneZuHe);
        }  if(oneSelect.contains("让胜")&&twoSelect.contains("让负")){
            LocalFloat oneZuHe = new LocalFloat();
            oneZuHe.setValue(earn);
            oneZuHe.setSpf("让胜让负");
            setLocalFloat(oneZuHe, raceTeamOne, other, rangOneReuslt, rangOtherResult);
            hasSelect.add(oneZuHe);
        }  if(oneSelect.contains("让负")&&twoSelect.contains("让胜")){
            LocalFloat oneZuHe = new LocalFloat();
            oneZuHe.setValue(earn);
            oneZuHe.setSpf("让负让胜");
            setLocalFloat(oneZuHe, raceTeamOne, other, rangOneReuslt, rangOtherResult);
            hasSelect.add(oneZuHe);
        }  if(oneSelect.contains("让负")&&twoSelect.contains("让平")){
            LocalFloat oneZuHe = new LocalFloat();
            oneZuHe.setValue(earn);
            oneZuHe.setSpf("让负让平");
            setLocalFloat(oneZuHe, raceTeamOne, other, rangOneReuslt,rangOtherResult);
            hasSelect.add(oneZuHe);
        }  if(oneSelect.contains("让负")&&twoSelect.contains("让负")){
            LocalFloat oneZuHe = new LocalFloat();
            oneZuHe.setValue(earn);
            oneZuHe.setSpf("让负让负");
            setLocalFloat(oneZuHe, raceTeamOne, other, rangOneReuslt,rangOtherResult);
            hasSelect.add(oneZuHe);
        }  if(oneSelect.contains("让平")&&twoSelect.contains("让胜")){
            LocalFloat oneZuHe = new LocalFloat();
            oneZuHe.setValue(earn);
            oneZuHe.setSpf("让平让胜");
            setLocalFloat(oneZuHe, raceTeamOne, other, rangOneReuslt, rangOtherResult);
            hasSelect.add(oneZuHe);
        }  if(oneSelect.contains("让平")&&twoSelect.contains("让平")){
            LocalFloat oneZuHe = new LocalFloat();
            oneZuHe.setValue(earn);
            oneZuHe.setSpf("让平让平");
            hasSelect.add(oneZuHe);
            setLocalFloat(oneZuHe, raceTeamOne, other, rangOneReuslt,rangOtherResult);
        }  if(oneSelect.contains("让平")&&twoSelect.contains("让负")){
            LocalFloat oneZuHe = new LocalFloat();
            oneZuHe.setValue(earn);
            oneZuHe.setSpf("让平让负");
            setLocalFloat(oneZuHe,raceTeamOne,other,rangOneReuslt,rangOtherResult);
            hasSelect.add(oneZuHe);
        }

        return hasSelect;
    }



    /**
     * 设置一个组合的值
     * @param localFloat
     * @param one
     * @param two
     * @param oneResult
     * @param twoResult
     */
    public  static void setLocalFloat(LocalFloat localFloat,RaceTeam one,RaceTeam two,String oneResult,String twoResult){
        localFloat.setOneFullHomeTeamCourtscore(one.getFullHomeTeamCourtscore());
        localFloat.setOneFullVisitingTeamCourtscore(one.getFullVisitingTeamCourtscore());
        localFloat.setOneHomeTeam(one.getHomeTeam());
        localFloat.setOneVisitingTeam(one.getVisitingTeam());
        localFloat.setOneLeagueName(one.getLeagueName());
        localFloat.setOneRaceNo(one.getRaceNo());
        localFloat.setOneRaceResult(oneResult);

        localFloat.setTwoFullHomeTeamCourtscore(two.getFullHomeTeamCourtscore());
        localFloat.setTwoFullVisitingTeamCourtscore(two.getFullVisitingTeamCourtscore());
        localFloat.setTwoHomeTeam(two.getHomeTeam());
        localFloat.setTwoVisitingTeam(two.getVisitingTeam());
        localFloat.setTwoLeagueName(two.getLeagueName());
        localFloat.setTwoRaceNo(two.getRaceNo());
        localFloat.setTwoRaceResult(twoResult);
    }


    public static void printLn(List<LocalFloat> localFloats,String jieguo,Float allSpend,Float allEarn){

         getAllEarn(localFloats,jieguo,allSpend,allEarn);
    }

    public static Float getAllEarn(List<LocalFloat> localFloats,String jieguo,Float allSpend,Float allEarn){
        StringBuffer sb = new StringBuffer();
        if(null!=localFloats&&localFloats.size()>0){
            for(LocalFloat temp:localFloats){
                Float zhechangzhong = 0f;
                if(temp.spf.equals(jieguo)){
                    allEarn+=temp.value;
                    zhechangzhong = temp.value;
                }
                allSpend +=2;
                sb.append("allSpend:"+allSpend+" ,allEarn:"+allEarn+";  jieguo:"+jieguo+" zhechangzhong:"+zhechangzhong+"   "+temp.getOneHomeTeam()+":"+temp.getOneHomeTeam()+"="+temp.getOneFullHomeTeamCourtscore()+":"+temp.getOneFullVisitingTeamCourtscore()+"=---"+temp.getOneRaceResult()+";   ");
                logger.info(sb.toString());
            }
            return allEarn;
        }
            return allEarn;

    }



    /**
     *
     * @param raceTeamOne
     * @param raceTeamTwo
     * @return
     */
    private static String getResult (RaceTeam raceTeamOne,RaceTeam raceTeamTwo){
        String oneResult = "";
        String twoResult = "";
        if(raceTeamOne.getFullHomeTeamCourtscore()>raceTeamOne.getFullVisitingTeamCourtscore()){
            oneResult = "胜";
        }else if(raceTeamOne.getFullHomeTeamCourtscore()==raceTeamOne.getFullVisitingTeamCourtscore()){
            oneResult = "平";
        }else if(raceTeamOne.getFullHomeTeamCourtscore()<raceTeamOne.getFullVisitingTeamCourtscore()){
            oneResult = "负";
        }
        if(raceTeamTwo.getFullHomeTeamCourtscore()>raceTeamTwo.getFullVisitingTeamCourtscore()){
            twoResult = "胜";
        }else if(raceTeamTwo.getFullHomeTeamCourtscore()==raceTeamTwo.getFullVisitingTeamCourtscore()){
            twoResult = "平";
        }else if(raceTeamTwo.getFullHomeTeamCourtscore()<raceTeamTwo.getFullVisitingTeamCourtscore()){
            twoResult = "负";
        }
        return oneResult+twoResult;
    }



    private  static Float getEarn(String twoRaceResult,RaceTeam raceTeamOne,RaceTeam other){


        Float allOwn = null;


        Float winwin = raceTeamOne.getWin()*other.getWin()*2;
        Float wineq = raceTeamOne.getWin()*other.getEquals()*2;
        Float winfail = raceTeamOne.getWin()*other.getFail()*2;

        Float eqwin = raceTeamOne.getEquals()*other.getWin()*2;
        Float eqeq = raceTeamOne.getEquals()*other.getEquals()*2;
        Float eqfail = raceTeamOne.getEquals()*other.getFail()*2;

        Float failwin = raceTeamOne.getFail()*other.getWin()*2;
        Float faileq = raceTeamOne.getFail()*other.getEquals()*2;
        Float failfail = raceTeamOne.getFail()*other.getFail()*2;

        //rr
        Float rwinrwin = raceTeamOne.getRangWin()*other.getRangWin()*2;
        Float rwinreq = raceTeamOne.getRangWin()*other.getRangEq()*2;
        Float rwinrfail = raceTeamOne.getRangWin()*other.getRangFail()*2;

        Float reqrwin = raceTeamOne.getRangEq()*other.getRangWin()*2;
        Float reqreq = raceTeamOne.getRangEq()*other.getRangEq()*2;
        Float reqrfail = raceTeamOne.getRangEq()*other.getRangFail()*2;

        Float rfailrwin = raceTeamOne.getRangFail()*other.getRangWin()*2;
        Float rfailreq = raceTeamOne.getRangFail()*other.getRangEq()*2;
        Float rfailrfail = raceTeamOne.getRangFail()*other.getRangFail()*2;


        if(twoRaceResult.equals("胜胜")){
            allOwn = winwin;
        }else if(twoRaceResult.equals("胜平")){
            allOwn = wineq;
        }else if(twoRaceResult.equals("胜负")){
            allOwn = winfail;
        }else if(twoRaceResult.equals("平胜")){
            allOwn = eqwin;
        }else if(twoRaceResult.equals("平平")){
            allOwn = eqeq;
        }else if(twoRaceResult.equals("平负")){
            allOwn = eqfail;
        }else if(twoRaceResult.equals("负胜")){
            allOwn = failwin;
        }else if(twoRaceResult.equals("负平")){
            allOwn = faileq;
        }else if(twoRaceResult.equals("负负")){
            allOwn = failfail;
        }
        return allOwn;

    }

    /**
     * 获取指定的结果
     * @param one
     * @param two
     */
    private static LocalFloat getTheResult(RaceTeam one,RaceTeam two,String which){
        if(null!=one&&null!=two){
            List<LocalFloat> oneList = sortLocalFloat(one);
            List<LocalFloat> twoList = sortLocalFloat(two);
            LocalFloat oneDa = oneList.get(2);
            LocalFloat oneZho = oneList.get(1);
            LocalFloat oneXi = oneList.get(0);

            LocalFloat twoDa = twoList.get(2);
            LocalFloat twoZho = twoList.get(1);
            LocalFloat twoXi = twoList.get(0);

            List<LocalFloat> temp = new ArrayList<LocalFloat>();
            LocalFloat dada = new LocalFloat(oneDa.value*twoDa.value*2,oneDa.spf+twoDa.spf );
            LocalFloat dazho = new LocalFloat(oneDa.value*twoZho.value*2,oneDa.spf+twoZho.spf);
            LocalFloat daxi =new LocalFloat(oneDa.value*twoXi.value*2,oneDa.spf+twoXi.spf);

            LocalFloat zhoda = new LocalFloat(oneZho.value*twoDa.value*2,oneZho.spf+twoDa.spf);
            LocalFloat zhozho =new LocalFloat( oneZho.value*twoZho.value*2,oneZho.spf+twoZho.spf);
            LocalFloat zhoxi = new LocalFloat(oneZho.value*twoXi.value*2,oneZho.spf+twoXi.spf);

            LocalFloat xida = new LocalFloat(oneXi.value*twoDa.value*2,oneXi.spf+twoDa.spf);
            LocalFloat xizho = new LocalFloat(oneXi.value*twoZho.value*2,oneXi.spf+twoZho.spf);
            LocalFloat xixi = new LocalFloat(oneXi.value*twoXi.value*2,oneXi.spf+twoXi.spf);

            temp.add(dada);temp.add(dazho);temp.add(daxi);temp.add(zhoda);temp.add(zhozho);temp.add(zhoxi);temp.add(xida);temp.add(xizho);temp.add(xixi);
            return getEarnMoney(temp,which);
        }
        return null;
    }


    /**
     * 根据大中小来计算
     * @param one
     * @param two
     */
    private static Float getDazhoxi(RaceTeam one,RaceTeam two,String which){
        if(null!=one&&null!=two){
            List<Float> oneList = sortFloat(one);
            List<Float> twoList = sortFloat(two);
            Float oneDa = oneList.get(2);
            Float oneZho = oneList.get(1);
            Float oneXi = oneList.get(0);

            Float twoDa = twoList.get(2);
            Float twoZho = twoList.get(1);
            Float twoXi = twoList.get(0);

            Float dada = oneDa*twoDa*2;
            Float dazho = oneDa*twoDa*2;
            Float daxi =oneDa*twoXi*2;

            Float zhoda = oneZho*twoDa*2;
            Float zhozho = oneZho*twoZho*2;
            Float zhoxi = oneZho*twoXi*2;

            Float xida = oneXi*twoDa*2;
            Float xizho = oneXi*twoZho*2;
            Float xixi = oneXi*twoXi*2;

            if("dada".equals(which)){
                return dada;
            }else if("dazho".equals(which)){
                return dazho;
            }else if("daxi".equals(which)){
                return daxi;
            }else if("zhoda".equals(which)){
                return zhoda;
            }else if("zhozho".equals(which)){
                return zhozho;
            }else if("zhoxi".equals(which)){
                return zhoxi;
            }else if("xida".equals(which)){
                return xida;
            }else if("xizho".equals(which)){
                return xizho;
            }else if("xixi".equals(which)){
                return xixi;
            }else{
                return 0f;
            }
        } return 0f;
    }

    /**
     * 根据两场结果获取金额
     * @param list
     * @param spf
     * @return
     */
    public static LocalFloat getEarnMoney(List<LocalFloat> list,String spf){
        if(null!=list&&list.size()>0){
             for(LocalFloat value:list){
                if(value.spf.equals(spf)){
                    return value;
                }
            }
        }
            return new LocalFloat(0f,"未中");
    }


    public List<RaceTeam> selectBaoLeng(RaceTeam raceTeam) {
        try{
            return raceTeamMapper.selectBaoLeng(raceTeam);
        }catch (Exception e){
            logger.error("  selectBaoLeng fail ",e);
        }
        return null;
    }
    public List<RaceTeam> selectCountDaYu6(RaceTeam raceTeam) {
        try{
            return raceTeamMapper.selectCountDaYu6(raceTeam);
        }catch (Exception e){
            logger.error("  selectCountDaYu6 fail ",e);
        }
        return null;
    }

    public void updateRaceTeamByJdd(List<RaceTeam> list) {
        if(null!=list&&list.size()>0){
            for(RaceTeam raceTeam:list){
//                Date date = ParseUtil.formatDate(raceTeam.getRaceDate());
              RaceTeam raceTeam1 =   raceTeamMapper.selectRaceTeam(raceTeam);
                if(null!=raceTeam1){
                    raceTeam.setId(raceTeam1.getId());
                    raceTeamMapper.updateRaceTeamByJdd(raceTeam);
                }else{
                    raceTeamMapper.insertJdd(raceTeam);
                }
            }
        }
    }


    public void selectHasDoneRaceTeamByJdd() {
        List<String> allBeforDays = getRaceByDate();
        if(null!=allBeforDays&&allBeforDays.size()>0){
            for(String dateStr:allBeforDays){
                try{
                    String response = HttpClientUtils.doHttpsGet("http://dcds.jdd.com/Api.Soccer.V3/Ajax/Live.ashx?action=qlive&&issue="+dateStr+"&lotteryid=90&playid=9001&pts=0",null,null);
                    JSONObject obj =     JSONObject.parseObject(response);
                    if(null!=obj){
                       JSONArray jsonArray = (JSONArray) obj.get("MI");
                        JSONArray mrachs = (JSONArray) ((JSONObject)jsonArray.get(0)).get("MS");
                        if(null!=mrachs&&mrachs.size()>0){
                            for(Object temp:mrachs){
                                RaceTeam raceTeam = insertRaceTeamJdd(temp);
                                insertBiFa(raceTeam, raceTeam.getMatchId());
                                insertPeiLvChange(raceTeam,raceTeam.getMatchId());
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error("",e);
                }

            }


        }


    }


    private RaceTeam insertRaceTeamJdd(Object temp){
        RaceTeam raceTeam = ParseUtil.parseBeforeRaceTeam((JSONObject) temp);
        try{
            raceTeamMapper.insertJdd(raceTeam);
        }catch (Exception e){
            logger.error("",e);
        }
        return raceTeam;
    }


    /**
     * 插入必发的数据
     * @param raceTeam
     * @param matchid
     */
    private void insertBiFa(RaceTeam raceTeam,Integer matchid){
        try {
            String response = HttpClientUtils.doHttpsGet("https://smc.jdddata.com/api/betfair/getbftradeinfo?lotteryId=90&matchid=" + matchid + "&pcode=h5&pts=0&type=1&version=v2.1", null, null);
            JSONObject obj =     JSONObject.parseObject(response);
            Object data = obj.get("data");
            BiFaDTo biFaDTo = ParseUtil.parseBiFa((JSONObject) data, "", matchid);
            raceTeamMapper.insertBiFa(biFaDTo);
        }catch (Exception e){
            logger.error("",e);
        }
    }

    /**
     * 插入赔率变动
     * @param raceTeam
     * @param matchid
     */
    private void insertPeiLvChange(RaceTeam raceTeam,Integer matchid){
        PeiLvDTo peiLvDTo = null;
        try{
            String response = HttpClientUtils.doHttpsGet("http://saishi.jdd.com/Ajax/Soccer.ashx?action=europoddsdetail&bookid=1000&matchid="+matchid+"&pcode=h5", null, null);
            if(StringUtils.isNotEmpty(response)){
                if(response.indexOf("(")>=0&&response.indexOf(")")>=0){
                    response =   response.substring(1,response.length()-1);
                }
                JSONObject obj =     JSONObject.parseObject(response);
                Object data = obj.get("Data");
                peiLvDTo = ParseUtil.parseSpfChange((JSONObject) data, ParseUtil.parseDate2Str(raceTeam.getRaceDate(), "yyyy"), matchid);
            }
        }catch (Exception e){
            logger.error("",e);
        }
        try{
            String rangResponse = HttpClientUtils.doHttpsGet("http://saishi.jdd.com/Ajax/Soccer.ashx?action=handicap&matchid="+matchid+"&pcode=h5", null, null);
            if(StringUtils.isNotEmpty(rangResponse)){
                if(rangResponse.indexOf("(")>=0&&rangResponse.indexOf(")")>=0){
                    rangResponse =   rangResponse.substring(1,rangResponse.length()-1);
                }
                JSONObject obj =     JSONObject.parseObject(rangResponse);
                Object data = obj.get("Data");
                ParseUtil.parseRangSpfPeiLv((JSONArray) data,peiLvDTo );
            }
        }catch (Exception e){
            logger.error("",e);
        }
        try {
            raceTeamMapper.insertPeiLvChange(peiLvDTo);
        }catch (Exception e){
            logger.error("",e);
        }
    }



    /**
     * *gc.add(1,-1)表示年份减一.
     *gc.add(2,-1)表示月份减一.
     *gc.add(3.-1)表示周减一.
     *gc.add(5,-1)表示天减一.
     */
    public static List<String> getRaceByDate(){
        List<String> days = new ArrayList<String>();
        SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc=new GregorianCalendar();
        for(int i=1;i<60;i++){
            gc.setTime(new Date());
            gc.add(5, -i);
            sf.format(gc.getTime());
            logger.info(sf.format(gc.getTime()));
            try{
                days.add( sf.format(gc.getTime()));
            }catch (Exception e){

            }
        }
        return days;
    }
}
