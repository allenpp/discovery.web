package com.neo.discovery.util.mail;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by cuizhixiang on 2017/5/31.
 * 过去资源文件数据
 **/
@Component
public class MailConfig {
    private static final String PROPERTIES_DEFAULT = "mailConfig.properties";
    public static String host="smtp.163.com";
    public static Integer port = 25;
    public static String userName = "13691177451@163.com";
    public static String passWord ="liu710927120";
    public static String emailForm ="13691177451@163.com";
    public static String timeout ="25000";
    public static String personal ="aaa";
    public static Properties properties;
    static{
        init();
    }

    /**
     * 初始化
     *
     * #服务器
     mailHost=smtp.163.com
     #端口号
     mailPort=25
     #邮箱账号
     mailUsername=15953185712@163.com
     #邮箱授权码
     mailPassword=czxsqm521
     #时间延迟
     mailTimeout=25000
     #发送人
     mailFrom=15953185712@163.com
     */
    private static void init() {
//        properties = new Properties();
//        try{
//            InputStream inputStream = MailConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_DEFAULT);
//            properties.load(inputStream);
//            inputStream.close();
//            //properties.setProperty("mailFrom","cuizhixiang@feitu.biz");
////            host = properties.getProperty("mailHost");
////            port = Integer.parseInt(properties.getProperty("mailPort"));
////            userName = properties.getProperty("mailUsername");
////            passWord = properties.getProperty("mailPassword");
////            emailForm = properties.getProperty("mailFrom");
////            timeout = properties.getProperty("mailTimeout");
////            personal = "墨裔";
//        } catch(IOException e){
//            e.printStackTrace();
//        }
    }
}
