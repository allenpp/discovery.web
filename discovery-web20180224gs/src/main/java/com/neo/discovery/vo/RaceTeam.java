package com.neo.discovery.vo;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 8/19/2017.
 */
public class RaceTeam {



    private long id;


    /**
     * 竞彩期数  日期
     */
    private Date jingCaiDate;
    /**
     * ��������
     */
    private Date raceDate;

    /**
     * ���±��
     */
    private String raceNo;
    /**
     * ����name
     */
    private String leagueName;
    /**
     * ����
     */
    private String homeTeam;
    /**
     * �Ͷ�
     */
    private String visitingTeam;
    /**
     * �볡�ȷ�
     */
    private String halfCourtScore;
    /**
     * ȫ�����ӵ÷�
     */
    private Integer fullHomeTeamCourtscore;
    /**
     * ȫ���Ͷӵ÷�
     */
    private Integer fullVisitingTeamCourtscore;
    /**
     * ����״̬
     */
    private String raceStatus;
    /**
     * �������������url Ŀǰ�Ȳ�����ֻ��url
     */
    private String resultUrl;

    /**
     * �������
     */
    private Float rangScore;

    /**
     * 胜
     */
    private Float win;
    /**
     * 平
     */
    private Float equals;

    /**
     * 负
     */
    private Float fail;

    /**
     * 让球胜 赔率
     */
    private Float rangWin;
    /**
     * 让球相当
     */
    private Float rangEq;
    /**
     * 让球负
     */
    private Float rangFail;

    private String baoLeng;

    /**
     * 半场主得分
     */
    private Integer halfHomeScore;

    /**
     * 半场客得分
     */
    private Integer halfVisitingScore;

    /**
     * 比赛结果
     */
    private String result;


    /**
     * 胜 投注比例
     */
    private BigDecimal winRate;
    /**
     * 平投注比例
     */
    private BigDecimal eqRate;
    /**
     * 负投注比例
     */
    private BigDecimal failRate;

    /**
     * 胜 投注比例
     */
    private BigDecimal rangWinRate;
    /**
     * 平投注比例
     */
    private BigDecimal rangEqRate;
    /**
     * 负投注比例
     */
    private BigDecimal rangFailRate;


    /**
     * 主队id
     */
    private Integer HTeam;
    /**
     * 客队id
     */
    private Integer VTeamID;


    /**
     * 半全场
     */
    private  String SpBQC;

    /**
     *猜比分
     * @return
     */
    private String SpCBF;
    /**
     *总进球
     * @return
     */
    private String SpZJQ;



    private Integer mid;

    private Integer matchId;
    private Integer homeTeamId;
    private Integer visitingTeamId;



    private Float daWin;
    private Float xiWin;
    private Float daEq;
    private Float xiEq;
    private Float daFail;
    private Float xiFail;

    private Float rangDaWin;
    private Float rangXiWin;
    private Float rangDaEq;
    private Float rangXiEq;
    private Float rangDaFail;
    private Float rangXiFail;

    private Float win0;
    private Float equals0;
    private Float fail0;
    private Float rangWin0;
    private Float rangEq0;
    private Float rangFail0;


    private Integer begin ;
    private Integer end ;


    private Float winmin;
    private Float eqmin;
    private Float failmin;
    private Float w10;
    private Float w20;
    private Float w21;
    private Float w30;
    private Float w31;
    private Float e00;
    private Float e11;
    private Float e22;
    private Float e33;
    private Float f01;
    private Float f02;
    private Float f12;
    private Float f03;
    private Float f13;





    private String pool_result;
    private String getMatchInfo;
    private String getTeamScore;
    private String getTeamRecData;
    private String getFutureMatches;
    private String getResultHis;
    private String getTeamStatistics;
    private String getScorer;
    private String getInjurySuspension;


    private Float zong0;
    private Float zong1;
    private Float zong2;
    private Float zong3;
    private Float zong4;
    private Float zong5;
    private Float zong6;
    private Float zong7;


    private Float hfss;
    private Float hfps;
    private Float hffs;
            ;

    private Float hfsp;
    private Float hfpp;
    private Float hffp;

    private Float hfff;
    private Float hfpf;
    private Float hfsf;

    private Float zongmin;
    private Float hfmin;

    private List<String> raceDates;

    private Float zongWin;
    private Float zongEq;
    private Float zongFail;

    private Float zhuWin;
    private Float zhuEq;
    private Float zhuFail;

    private Float keWin;
    private Float keEq;
    private Float keFail;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getRaceDate() {
        if(null!=raceDate){

            String temp = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(temp);
            String str = "";
            try {
                str = sdf.format(raceDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return raceDate;
        }else{
            return null;
        }
    }

    public void setRaceDate(Date raceDate) {
        this.raceDate = raceDate;
    }

    public String getRaceNo() {
        return raceNo;
    }

    public void setRaceNo(String raceNo) {
        this.raceNo = raceNo;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(String visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public String getHalfCourtScore() {
        return halfCourtScore;
    }

    public void setHalfCourtScore(String halfCourtScore) {
        this.halfCourtScore = halfCourtScore;
    }


    public String getRaceStatus() {
        return raceStatus;
    }

    public void setRaceStatus(String raceStatus) {
        this.raceStatus = raceStatus;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public Integer getFullHomeTeamCourtscore() {
        return fullHomeTeamCourtscore;
    }

    public void setFullHomeTeamCourtscore(Integer fullHomeTeamCourtscore) {
        this.fullHomeTeamCourtscore = fullHomeTeamCourtscore;
    }

    public Integer getFullVisitingTeamCourtscore() {
        return fullVisitingTeamCourtscore;
    }

    public void setFullVisitingTeamCourtscore(Integer fullVisitingTeamCourtscore) {
        this.fullVisitingTeamCourtscore = fullVisitingTeamCourtscore;
    }

    public Float getRangScore() {
        return rangScore;
    }

    public void setRangScore(Float rangScore) {
        this.rangScore = rangScore;
    }

    public Float getWin() {
        return win;
    }

    public void setWin(Float win) {
        this.win = win;
    }

    public Float getEquals() {
        return equals;
    }

    public void setEquals(Float equals) {
        this.equals = equals;
    }

    public Float getFail() {
        return fail;
    }

    public void setFail(Float fail) {
        this.fail = fail;
    }

    public Float getRangWin() {
        return rangWin;
    }

    public void setRangWin(Float rangWin) {
        this.rangWin = rangWin;
    }

    public Float getRangEq() {
        return rangEq;
    }

    public void setRangEq(Float rangEq) {
        this.rangEq = rangEq;
    }

    public Float getRangFail() {
        return rangFail;
    }

    public void setRangFail(Float rangFail) {
        this.rangFail = rangFail;
    }

    public String getBaoLeng() {
        return baoLeng;
    }

    public void setBaoLeng(String baoLeng) {
        this.baoLeng = baoLeng;
    }



    public Integer getHalfHomeScore() {
        return halfHomeScore;
    }

    public void setHalfHomeScore(Integer halfHomeScore) {
        this.halfHomeScore = halfHomeScore;
    }

    public Integer getHalfVisitingScore() {
        return halfVisitingScore;
    }

    public void setHalfVisitingScore(Integer halfVisitingScore) {
        this.halfVisitingScore = halfVisitingScore;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BigDecimal getWinRate() {
        return winRate;
    }

    public void setWinRate(BigDecimal winRate) {
        this.winRate = winRate;
    }

    public BigDecimal getEqRate() {
        return eqRate;
    }

    public void setEqRate(BigDecimal eqRate) {
        this.eqRate = eqRate;
    }

    public BigDecimal getFailRate() {
        return failRate;
    }

    public void setFailRate(BigDecimal failRate) {
        this.failRate = failRate;
    }

    public BigDecimal getRangWinRate() {
        return rangWinRate;
    }

    public void setRangWinRate(BigDecimal rangWinRate) {
        this.rangWinRate = rangWinRate;
    }

    public BigDecimal getRangEqRate() {
        return rangEqRate;
    }

    public void setRangEqRate(BigDecimal rangEqRate) {
        this.rangEqRate = rangEqRate;
    }

    public BigDecimal getRangFailRate() {
        return rangFailRate;
    }

    public void setRangFailRate(BigDecimal rangFailRate) {
        this.rangFailRate = rangFailRate;
    }

    public Integer getHTeam() {
        return HTeam;
    }

    public void setHTeam(Integer HTeam) {
        this.HTeam = HTeam;
    }

    public Integer getVTeamID() {
        return VTeamID;
    }

    public void setVTeamID(Integer VTeamID) {
        this.VTeamID = VTeamID;
    }

    public String getSpBQC() {
        return SpBQC;
    }

    public void setSpBQC(String spBQC) {
        SpBQC = spBQC;
    }

    public String getSpCBF() {
        return SpCBF;
    }

    public void setSpCBF(String spCBF) {
        SpCBF = spCBF;
    }

    public String getSpZJQ() {
        return SpZJQ;
    }

    public void setSpZJQ(String spZJQ) {
        SpZJQ = spZJQ;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Date getJingCaiDate() {
        return jingCaiDate;
    }

    public void setJingCaiDate(Date jingCaiDate) {
        this.jingCaiDate = jingCaiDate;
    }

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Integer getVisitingTeamId() {
        return visitingTeamId;
    }

    public void setVisitingTeamId(Integer visitingTeamId) {
        this.visitingTeamId = visitingTeamId;
    }

    public Float getDaWin() {
        return daWin;
    }

    public void setDaWin(Float daWin) {
        this.daWin = daWin;
    }

    public Float getXiWin() {
        return xiWin;
    }

    public void setXiWin(Float xiWin) {
        this.xiWin = xiWin;
    }

    public Float getDaEq() {
        return daEq;
    }

    public void setDaEq(Float daEq) {
        this.daEq = daEq;
    }

    public Float getXiEq() {
        return xiEq;
    }

    public void setXiEq(Float xiEq) {
        this.xiEq = xiEq;
    }

    public Float getDaFail() {
        return daFail;
    }

    public void setDaFail(Float daFail) {
        this.daFail = daFail;
    }

    public Float getXiFail() {
        return xiFail;
    }

    public void setXiFail(Float xiFail) {
        this.xiFail = xiFail;
    }


    public Float getWin0() {
        return win0;
    }

    public void setWin0(Float win0) {
        this.win0 = win0;
    }

    public Float getEquals0() {
        return equals0;
    }

    public void setEquals0(Float equals0) {
        this.equals0 = equals0;
    }

    public Float getFail0() {
        return fail0;
    }

    public void setFail0(Float fail0) {
        this.fail0 = fail0;
    }

    public Float getRangWin0() {
        return rangWin0;
    }

    public void setRangWin0(Float rangWin0) {
        this.rangWin0 = rangWin0;
    }

    public Float getRangEq0() {
        return rangEq0;
    }

    public void setRangEq0(Float rangEq0) {
        this.rangEq0 = rangEq0;
    }

    public Float getRangFail0() {
        return rangFail0;
    }

    public void setRangFail0(Float rangFail0) {
        this.rangFail0 = rangFail0;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }


    public Float getWinmin() {
        return winmin;
    }

    public void setWinmin(Float winmin) {
        this.winmin = winmin;
    }

    public Float getEqmin() {
        return eqmin;
    }

    public void setEqmin(Float eqmin) {
        this.eqmin = eqmin;
    }

    public Float getFailmin() {
        return failmin;
    }

    public void setFailmin(Float failmin) {
        this.failmin = failmin;
    }

    public Float getW10() {
        return w10;
    }

    public void setW10(Float w10) {
        this.w10 = w10;
    }

    public Float getW20() {
        return w20;
    }

    public void setW20(Float w20) {
        this.w20 = w20;
    }

    public Float getW21() {
        return w21;
    }

    public void setW21(Float w21) {
        this.w21 = w21;
    }

    public Float getW30() {
        return w30;
    }

    public void setW30(Float w30) {
        this.w30 = w30;
    }

    public Float getW31() {
        return w31;
    }

    public void setW31(Float w31) {
        this.w31 = w31;
    }

    public Float getE00() {
        return e00;
    }

    public void setE00(Float e00) {
        this.e00 = e00;
    }

    public Float getE11() {
        return e11;
    }

    public void setE11(Float e11) {
        this.e11 = e11;
    }

    public Float getE22() {
        return e22;
    }

    public void setE22(Float e22) {
        this.e22 = e22;
    }

    public Float getE33() {
        return e33;
    }

    public void setE33(Float e33) {
        this.e33 = e33;
    }

    public Float getF01() {
        return f01;
    }

    public void setF01(Float f01) {
        this.f01 = f01;
    }

    public Float getF02() {
        return f02;
    }

    public void setF02(Float f02) {
        this.f02 = f02;
    }

    public Float getF12() {
        return f12;
    }

    public void setF12(Float f12) {
        this.f12 = f12;
    }

    public Float getF03() {
        return f03;
    }

    public void setF03(Float f03) {
        this.f03 = f03;
    }

    public Float getF13() {
        return f13;
    }

    public void setF13(Float f13) {
        this.f13 = f13;
    }


    public String getPool_result() {
        return pool_result;
    }

    public void setPool_result(String pool_result) {
        this.pool_result = pool_result;
    }

    public Float getZong0() {
        return zong0;
    }

    public void setZong0(Float zong0) {
        this.zong0 = zong0;
    }

    public Float getZong1() {
        return zong1;
    }

    public void setZong1(Float zong1) {
        this.zong1 = zong1;
    }

    public Float getZong2() {
        return zong2;
    }

    public void setZong2(Float zong2) {
        this.zong2 = zong2;
    }

    public Float getZong3() {
        return zong3;
    }

    public void setZong3(Float zong3) {
        this.zong3 = zong3;
    }

    public Float getZong4() {
        return zong4;
    }

    public void setZong4(Float zong4) {
        this.zong4 = zong4;
    }

    public Float getZong5() {
        return zong5;
    }

    public void setZong5(Float zong5) {
        this.zong5 = zong5;
    }

    public Float getZong6() {
        return zong6;
    }

    public void setZong6(Float zong6) {
        this.zong6 = zong6;
    }

    public Float getZong7() {
        return zong7;
    }

    public void setZong7(Float zong7) {
        this.zong7 = zong7;
    }

    public Float getHfss() {
        return hfss;
    }

    public void setHfss(Float hfss) {
        this.hfss = hfss;
    }

    public Float getHfps() {
        return hfps;
    }

    public void setHfps(Float hfps) {
        this.hfps = hfps;
    }

    public Float getHffs() {
        return hffs;
    }

    public void setHffs(Float hffs) {
        this.hffs = hffs;
    }

    public Float getHfsp() {
        return hfsp;
    }

    public void setHfsp(Float hfsp) {
        this.hfsp = hfsp;
    }

    public Float getHfpp() {
        return hfpp;
    }

    public void setHfpp(Float hfpp) {
        this.hfpp = hfpp;
    }

    public Float getHffp() {
        return hffp;
    }

    public void setHffp(Float hffp) {
        this.hffp = hffp;
    }

    public Float getHfff() {
        return hfff;
    }

    public void setHfff(Float hfff) {
        this.hfff = hfff;
    }

    public Float getHfpf() {
        return hfpf;
    }

    public void setHfpf(Float hfpf) {
        this.hfpf = hfpf;
    }

    public Float getHfsf() {
        return hfsf;
    }

    public void setHfsf(Float hfsf) {
        this.hfsf = hfsf;
    }

    public String getGetMatchInfo() {
        return getMatchInfo;
    }

    public void setGetMatchInfo(String getMatchInfo) {
        this.getMatchInfo = getMatchInfo;
    }

    public String getGetTeamScore() {
        return getTeamScore;
    }

    public void setGetTeamScore(String getTeamScore) {
        this.getTeamScore = getTeamScore;
    }

    public String getGetTeamRecData() {
        return getTeamRecData;
    }

    public void setGetTeamRecData(String getTeamRecData) {
        this.getTeamRecData = getTeamRecData;
    }

    public String getGetFutureMatches() {
        return getFutureMatches;
    }

    public void setGetFutureMatches(String getFutureMatches) {
        this.getFutureMatches = getFutureMatches;
    }

    public String getGetResultHis() {
        return getResultHis;
    }

    public void setGetResultHis(String getResultHis) {
        this.getResultHis = getResultHis;
    }

    public String getGetTeamStatistics() {
        return getTeamStatistics;
    }

    public void setGetTeamStatistics(String getTeamStatistics) {
        this.getTeamStatistics = getTeamStatistics;
    }

    public String getGetScorer() {
        return getScorer;
    }

    public void setGetScorer(String getScorer) {
        this.getScorer = getScorer;
    }

    public String getGetInjurySuspension() {
        return getInjurySuspension;
    }

    public void setGetInjurySuspension(String getInjurySuspension) {
        this.getInjurySuspension = getInjurySuspension;
    }

    public Float getZongmin() {
        return zongmin;
    }

    public void setZongmin(Float zongmin) {
        this.zongmin = zongmin;
    }

    public Float getHfmin() {
        return hfmin;
    }

    public void setHfmin(Float hfmin) {
        this.hfmin = hfmin;
    }


    public Float getRangDaWin() {
        return rangDaWin;
    }

    public void setRangDaWin(Float rangDaWin) {
        this.rangDaWin = rangDaWin;
    }

    public Float getRangXiWin() {
        return rangXiWin;
    }

    public void setRangXiWin(Float rangXiWin) {
        this.rangXiWin = rangXiWin;
    }

    public Float getRangDaEq() {
        return rangDaEq;
    }

    public void setRangDaEq(Float rangDaEq) {
        this.rangDaEq = rangDaEq;
    }

    public Float getRangXiEq() {
        return rangXiEq;
    }

    public void setRangXiEq(Float rangXiEq) {
        this.rangXiEq = rangXiEq;
    }

    public Float getRangDaFail() {
        return rangDaFail;
    }

    public void setRangDaFail(Float rangDaFail) {
        this.rangDaFail = rangDaFail;
    }

    public Float getRangXiFail() {
        return rangXiFail;
    }

    public void setRangXiFail(Float rangXiFail) {
        this.rangXiFail = rangXiFail;
    }

    public List<String> getRaceDates() {
        return raceDates;
    }

    public void setRaceDates(List<String> raceDates) {
        this.raceDates = raceDates;
    }


    public Float getZongWin() {
        return zongWin;
    }

    public void setZongWin(Float zongWin) {
        this.zongWin = zongWin;
    }

    public Float getZongEq() {
        return zongEq;
    }

    public void setZongEq(Float zongEq) {
        this.zongEq = zongEq;
    }

    public Float getZongFail() {
        return zongFail;
    }

    public void setZongFail(Float zongFail) {
        this.zongFail = zongFail;
    }

    public Float getZhuWin() {
        return zhuWin;
    }

    public void setZhuWin(Float zhuWin) {
        this.zhuWin = zhuWin;
    }

    public Float getZhuEq() {
        return zhuEq;
    }

    public void setZhuEq(Float zhuEq) {
        this.zhuEq = zhuEq;
    }

    public Float getZhuFail() {
        return zhuFail;
    }

    public void setZhuFail(Float zhuFail) {
        this.zhuFail = zhuFail;
    }

    public Float getKeWin() {
        return keWin;
    }

    public void setKeWin(Float keWin) {
        this.keWin = keWin;
    }

    public Float getKeEq() {
        return keEq;
    }

    public void setKeEq(Float keEq) {
        this.keEq = keEq;
    }

    public Float getKeFail() {
        return keFail;
    }

    public void setKeFail(Float keFail) {
        this.keFail = keFail;
    }
}
