package com.neo.discovery.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyunpeng1 on 2018/2/2.
 */
public class Mapping {



    public  static Map<String,String> leagueNameMapping = new HashMap<String,String>();
    public  static Map<String,String> teamNameMapping = new HashMap<String,String>();



    public static void initLeagueNameMapping(){

        teamNameMapping.put("����������","");

    }
    public static void initteamNameMapping(){

        leagueNameMapping.put("����","�����䳬������");
        leagueNameMapping.put("�ȼ�","");
        leagueNameMapping.put("����","����͢�׼�����");
        leagueNameMapping.put("Ӣ��","Ӣ������");
        leagueNameMapping.put("����","");
        leagueNameMapping.put("�¼�","");
        leagueNameMapping.put("������","");
        leagueNameMapping.put("Ӣ��","");
        leagueNameMapping.put("����","�����Ҽ�����");
        leagueNameMapping.put("����","�����׼�����");
        leagueNameMapping.put("ī��","");
        leagueNameMapping.put("���","");
        leagueNameMapping.put("�ɼ�","��������׼�����");
        leagueNameMapping.put("����","�������Ҽ�����");
        leagueNameMapping.put("�ĳ�","");
        leagueNameMapping.put("�ϳ�","");
        leagueNameMapping.put("������","");
        leagueNameMapping.put("Ӣ��","");
        leagueNameMapping.put("�ճ�","");
        leagueNameMapping.put("ʥ���޽�","");
        leagueNameMapping.put("����","������Ҽ�����");
        leagueNameMapping.put("����","");
        leagueNameMapping.put("��","��ʿ��������");

    }


}
