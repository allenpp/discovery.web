package com.neo.discovery.util;

import com.sun.net.ssl.X509TrustManager;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.security.cert.X509Certificate;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.util.*;

/**
 * Created by liuyunpeng1 on 2017/9/22.
 */
public class HttpClientUtils {


    /**
     *
     * https://ws.888.qq.com/node_platform/?d=ball&c=ball&m=loadMatchById&ajax=true&cms_where=1367&vb2ctag=4_2061_3_1866&bc_web=662052694&reportUin=085e9858e60c0f50ce94f4aed%40wx.tenpay.com&_=1517376691978 HTTP/1.1
     *
     *
     * @param url
     * @param openid
     * @return
     */
    public static String httpPost(String url,String openid){
        String result="";
        try {
            // 根据地址获取请求
            HttpPost request = new HttpPost(url);//这里发送get请求
            request.setHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
            request.setHeader("Accept","application/json, text/plain, */*");
            request.setHeader("Accept-Encoding","gzip, deflate, br");
            request.setHeader("Accept-Language","zh-CN,zh;q=0.8");
            request.setHeader("Connection","keep-alive");
//            request.setHeader("Content-Length","616");
            request.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            request.setHeader("Host","bd-api.jdd.com");
            request.setHeader("Referer","http://h5.jdd.com/");

//设置请求的报文头部的编码
//            request.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));

//设置期望服务端返回的编码
//            request.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));

            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);

            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"US-ASCII");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
        //....result是用户信息,站内业务以及具体的json转换这里也不写了...
    }

    /**
     *
     * https://ws.888.qq.com/node_platform/?d=ball&c=ball&m=loadMatchById&ajax=true&cms_where=1367&vb2ctag=4_2061_3_1866&bc_web=662052694&reportUin=085e9858e60c0f50ce94f4aed%40wx.tenpay.com&_=1517376691978 HTTP/1.1
     *
     *
     * @param url
     * @param openid
     * @return
     */
    public static String httpGet(String url,String openid){
        String result="";
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);//这里发送get请求
            request.setHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
            request.setHeader("Accept","application/json, text/plain, */*");
            request.setHeader("Accept-Encoding","gzip, deflate, br");
            request.setHeader("Accept-Language","zh-CN,zh;q=0.8");
            request.setHeader("Connection","keep-alive");
//            request.setHeader("Content-Length","616");
            request.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
//            request.setHeader("Host","bd-api.jdd.com");
//            request.setHeader("Referer","http://h5.jdd.com/");

//设置请求的报文头部的编码
//            request.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));

//设置期望服务端返回的编码
//            request.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));

            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);

            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"US-ASCII");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
        //....result是用户信息,站内业务以及具体的json转换这里也不写了...
    }


    public static  String doHttpsPost(String url,Map<String,String> map,String charset){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new DefaultHttpClient();
//            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            //设置参数
//            List<NameValuePair> list = new ArrayList<NameValuePair>();
//            Iterator iterator = map.entrySet().iterator();
//            while(iterator.hasNext()){
//                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
//                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
//            }
//            if(list.size() > 0){
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
//                httpPost.setEntity(entity);
//            }
            StringEntity se = new StringEntity("vU9OZmOQo5fYC6i26WbVO7ZJd8HiDT10PVmGMQReiuuLXArPauFmBGykowb7HvhLJklMAXd2WO61pHD/7ybLkcXYMBeux8pphGuEf1t/3Sf1189ZF3XiH0PQJQWU6eQl2ZBdxONicWnbzjR2Iw9zUTJrxl3IrYxlg+H81+HCm2tx99tw8SWr/VrPBwUHeLpCOhpP0GHwXcwAF4xJsstEpk60WgEgehKd9d2aYtF/aHiaf4DK9vT5qjmcM1TIhAMNvwlgmJ+CH2V/6LcIBD3miQ==");
//            se.setContentType("application/x-www-form-urlencoded;charset=UTF-8");

            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    public static  String doHttpsPostWithCookie(String url,Map<String,String> map,String charset,String cookies,StringEntity stringEntity){
        CookieStore cookieStore = new BasicCookieStore();
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
//            httpClient = new SSLClient();
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            httpPost.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
            httpPost.setHeader("Accept-Encoding","gzip, deflate, br");
            httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
            httpPost.setHeader("Connection","keep-alive");
            httpPost.setHeader("Host","135.84.237.201");
            httpPost.setHeader("Cookie",cookies);
            httpPost.setHeader("X-CSRF-TOKEN","4b46c8c54f6bd59fd6f91cd48bfd8e722d");
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat QBCore/3.43.691.400 QQBrowser/9.0.2524.400");
            //设置参数
//            List<NameValuePair> list = new ArrayList<NameValuePair>();
//            Iterator iterator = map.entrySet().iterator();
//            while(iterator.hasNext()){
//                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
//                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
//            }
//            if(list.size() > 0){
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
//                httpPost.setEntity(entity);
//            }
//            StringEntity se = new StringEntity("vb2ctag=104_205; vb2ctag2=104_205; pgv_pvid=8721643510; pgv_info=ssid=s5304437022; ts_uid=6858575136; pgv_pvid_new=085e9858e60c0f50ce94f4aed@wx.tenpay.com_b853e5011f; 1876809700=html; 1940794343=html; bc_web=16976632c08");
//            se.setContentType("application/json;charset=UTF-8");

//            httpPost.setEntity(se);

            httpPost.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
    public static  String doHttpsGet(String url,Map<String,String> map,String charset){
        if(null == charset){
            charset = "utf-8";
        }
        HttpClient httpClient = null;
        HttpGet httpGet= null;
        String result = null;

        try {
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);

            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(result);
        return result;
    }
    public static  String doHttpsGetWithCookie(String url,Map<String,String> map,String charset,String cookie){
        if(null == charset){
            charset = "utf-8";
        }
        HttpClient httpClient = null;
        HttpGet httpGet= null;
        String result = null;

        try {
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);

//            httpGet.setHeader("cookie",cookie);
//            httpGet.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
//            httpGet.setHeader("Accept-Encoding","gzip, deflate, sdch");
//            httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9");
            httpGet.setHeader("Connection","keep-alive");
//            httpGet.setHeader("Host","www.fun172.com");
//            httpGet.setHeader("RA-Ver","3.0.7");
//            httpGet.setHeader("Referer","https://www.fun172.com/zh-cn/exchange/main.htm/sport/group/1575634906");
            httpGet.setHeader("X-Requested-With","XMLHttpRequest");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.108 Safari/537.36");

//            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
            // 读取超时
//            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(result);
        return result;
    }
    public static  String doHttpGet(String url){

        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    result = EntityUtils.toString(entity);
                    // 打印响应内容
//                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return result;
    }


    /**
     * http://odds.zgzcw.com/odds/oyzs_ajax.action
     * @return
     */
    public static String  jinDou(){
        String url = "https://ws.888.qq.com/";

        //全部比赛
        String tail = "node_platform/?d=ball&c=ball&m=getInitInfo&ajax=true&cms_where=1367&vb2ctag=4_2061_3_1866&bc_web=1018451640&reportUin=085e9858e60c0f50ce94f4aed%40wx.tenpay.com&_=1517404851899";
        String cookies = "vb2ctag=104_205; vb2ctag2=104_205; pgv_pvid=8721643510; pgv_info=ssid=s5304437022; ts_uid=6858575136; pgv_pvid_new=085e9858e60c0f50ce94f4aed@wx.tenpay.com_b853e5011f; 1940794343=html; bc_web=16976632c08; wcp_uid=1317176614; wcp_qlskey=wx3749d0c504fe6d16_fcdecef1d9e3c05116089e5760489d4a; wcp_qluin=085e9858e60c0f50ce94f4aed@wx.tenpay.com; wcp_qlappid=wx3749d0c504fe6d16; wcp_qq_logtype=16; openid=oTTG3jiDFUKE8iNbZbXXNsY8Ef7M; wcp_nickname=%25E7%25A2%25B0%25E7%25A2%25B0; wcp_pictureUrl=http";
        //单场比赛
//        String tail = "node_platform/?d=ball&c=ball&m=loadMatchById&ajax=true&cms_where=1367&vb2ctag=4_2061_3_1866&bc_web=1018451640&reportUin=085e9858e60c0f50ce94f4aed%40wx.tenpay.com&_=359924954371";
//        String cookies = "vb2ctag=104_205; vb2ctag2=104_205; 1940794343=html; bc_web=16976632c08; wcp_uid=1317176614; wcp_qlskey=wx3749d0c504fe6d16_09a37b522f4be6a16b7a996322d060b2; wcp_qluin=085e9858e60c0f50ce94f4aed@wx.tenpay.com; wcp_qlappid=wx3749d0c504fe6d16; wcp_qq_logtype=16; openid=oTTG3jiDFUKE8iNbZbXXNsY8Ef7M; wcp_nickname=%25E7%25A2%25B0%25E7%25A2%25B0; wcp_pictureUrl=http%3A//wx.qlogo.cn/mmopen/bNqVZcia7iaByzWCSdZhfr5Pxp561xoDStib2qjz804bqvicY81lOgSLks80oCrFCezobQ0wtaicZialNVep5Lt88Xibw/132; qlskey=wx3749d0c504fe6d16_09a37b522f4be6a16b7a996322d060b2; qluin=085e9858e60c0f50ce94f4aed@wx.tenpay.com; qlappid=wx3749d0c504fe6d16; qq_logtype=16; is_encode=1; wcp_newlogin=1; access_token=6_aQpxOqZlN0zfRv-0qkdQJs2axv3BoCVvBzFErwglpYKg7silAOgRWna7KV7tbqMbAATU9cRyxM5q0CMHt5RfAQ; wcp_rcode=1517404505796; ts_last=/m_wx/index.html; pgv_pvid=8721643510; pgv_info=ssid=s5304437022; ts_uid=6858575136; ts_sid=9201961840; pgv_pvid_new=085e9858e60c0f50ce94f4aed@wx.tenpay.com_b853e5011f; 1876809700=html";
        url = url+tail;
        return doHttpsPostWithCookie(url,null,"utf-8",cookies,null);
    }


    public static String bifaZhiShu(){

//        String url= "http://c.spdex.com/dv_1_0_0_0_0_0";
        String url= "https://www.fun172.com/zh-cn/exchange/main.htm/ws/multiple-market-prices/329/qoivcqkm/xhr?t=1518172508636";


        String cookies = "CultureInfo=zh-CN; cook88=2204543168.20480.0000; PlayerPreference=General; intro_BeforeLogin=1; _ga=GA1.1.1786535346.1518144744; _gid=GA1.1.1338613481.1518144744; ASP.NET_SessionId=t1pzbxid0hsb3l4cvxntaacf; _pk_ses.4.37b9=*; comm100_guid2_100014005=vaekRBU5cUSVU-r73OI9QA; BIAB_LANGUAGE=zh_CN; i18next=zh_CN; BIAB_TZ=-480; BIAB_SHOW_TOOLTIPS=false; _pk_id.4.37b9=69ad0a1b95b2157c.1518144744.2.1518171261.1518170979.; CSRF-TOKEN=cea8f7a7d8f83a90b3b3409c9c1fae6f15\n" +
                "Host:www.fun172.com";
        return doHttpsGetWithCookie(url, null, "utf-8", cookies);

    }
    public static String fun88(){

//        String url= "http://c.spdex.com/dv_1_0_0_0_0_0";
//        String url= "https://www.fun172.com/zh-cn/exchange/main.htm/ws/multiple-market-prices/329/qoivcqkm/xhr?t="+new Date().getTime();
        String url= "https://www.fun172.com/zh-cn/exchange/main.htm/ws/multiple-market-prices/099/rjtucqsl/xhr?t="+new Date().getTime();


        String cookies = "CultureInfo=zh-CN; cook88=2204543168.20480.0000; PlayerPreference=General; intro_BeforeLogin=1; _ga=GA1.1.1786535346.1518144744; _gid=GA1.1.1338613481.1518144744; ASP.NET_SessionId=t1pzbxid0hsb3l4cvxntaacf; _pk_ses.4.37b9=*; comm100_guid2_100014005=vaekRBU5cUSVU-r73OI9QA; BIAB_LANGUAGE=zh_CN; i18next=zh_CN; BIAB_TZ=-480; BIAB_SHOW_TOOLTIPS=false; _pk_id.4.37b9=69ad0a1b95b2157c.1518144744.2.1518171261.1518170979.; CSRF-TOKEN=cea8f7a7d8f83a90b3b3409c9c1fae6f15";
        return doHttpsPostWithCookie(url, null, "utf-8", cookies,null);

    }
    public static String fun88HttpsGet(){

//        String url= "http://c.spdex.com/dv_1_0_0_0_0_0";
//        String url= "https://www.fun172.com/zh-cn/exchange/main.htm/ws/multiple-market-prices/329/qoivcqkm/xhr?t="+new Date().getTime();
//        String url= "https://www.baidu.com";
//        String url= "https://www.jd.com";
//        String url= "https://www.fun172.com/zh-cn/home.htm";
//        String url= "https://www.fun172.com/Exchange/customer/api/group/matchOdds/1575634906";
        String url= "https://www.betfair.com.au/www/sports/exchange/readonly/v1/bymarket?_ak=nzIFcwyWhrlwYMrh&alt=json&marketIds=1.139185491,1.139493976,1.139185657,1.139496339,1.139751383,1.139493514,1.139493794,1.139496889,1.140014852,1.140031881,1.139849950,1.140044837,1.139849952,1.139857395,1.140049338,1.140049337,1.140049330,1.140049336,1.139790477,1.139790085,1.139790141,1.139790253,1.140042377,1.140041905,1.140042220,1.140042685,1.139591744,1.140037838,1.140037803,1.139591919&types=MARKET_STATE";


//        String cookies = "LangKey=cs; _ga=GA1.2.34661574.1516547766; OddsType_SPONUUS01001=2; _pk_ref.3.37b9=%5B%22%22%2C%22%22%2C1517184672%2C%22http%3A%2F%2Fwww.fun88.com%2Fzh-CN%2FExchange%2Fsport%2Fevent%2F2022802%22%5D; _pk_id.3.37b9=0a36474e797f8547.1516546239.4.1517184843.1517184672.; ASP.NET_SessionId=1khjilk3shd3tkey20x2ofdz; CultureInfo=zh-CN; PlayerPreference=General; intro_BeforeLogin=1; BIAB_LANGUAGE=zh_CN; _ga=GA1.1.251109361.1516546237; _gid=GA1.1.1554929236.1518224672; _pk_id.4.37b9=a27bce561ebe857c.1518224672.1.1518224690.1518224672.; _pk_ses.4.37b9=*; i18next=zh_CN; BIAB_TZ=-480; comm100_guid2_100014005=TacBDGzY-0KSGitVAz4X4g; CSRF-TOKEN=da348d4cb307204b631739537d1a5db713; BIAB_SHOW_TOOLTIPS=false; cook88=2338760896.20480.0000";
        return doHttpsGetWithCookie(url, null, "utf-8", "");
//        return httpGet(url ,"");

    }
    public static String fun88HttpsPost(){

//        String url= "http://c.spdex.com/dv_1_0_0_0_0_0";
//        String url= "https://www.fun172.com/zh-cn/exchange/main.htm/ws/multiple-market-prices/329/qoivcqkm/xhr?t="+new Date().getTime();
//        String url= "https://www.baidu.com";
//        String url= "https://www.jd.com";
//        String url= "https://www.fun172.com/zh-cn/home.htm";
//        String url= "https://www.fun172.com/Exchange/customer/api/group/matchOdds/1575634906";
        String url= "https://www.fun173.com/Exchange/customer/api/placeBets";


//        String cookies = "LangKey=cs; _ga=GA1.2.34661574.1516547766; OddsType_SPONUUS01001=2; _pk_ref.3.37b9=%5B%22%22%2C%22%22%2C1517184672%2C%22http%3A%2F%2Fwww.fun88.com%2Fzh-CN%2FExchange%2Fsport%2Fevent%2F2022802%22%5D; _pk_id.3.37b9=0a36474e797f8547.1516546239.4.1517184843.1517184672.; ASP.NET_SessionId=1khjilk3shd3tkey20x2ofdz; CultureInfo=zh-CN; PlayerPreference=General; intro_BeforeLogin=1; BIAB_LANGUAGE=zh_CN; _ga=GA1.1.251109361.1516546237; _gid=GA1.1.1554929236.1518224672; _pk_id.4.37b9=a27bce561ebe857c.1518224672.1.1518224690.1518224672.; _pk_ses.4.37b9=*; i18next=zh_CN; BIAB_TZ=-480; comm100_guid2_100014005=TacBDGzY-0KSGitVAz4X4g; CSRF-TOKEN=da348d4cb307204b631739537d1a5db713; BIAB_SHOW_TOOLTIPS=false; cook88=2338760896.20480.0000";
//        return doHttpsPost(url, null, "utf-8", "");
        return httpGet(url ,"");

    }

    public static void main(String[] args) {

//        jinDou();
//        bifaZhiShu();
//        fun88HttpsGet();
        buyhttpsPost();
    }


    private static void buyhttpsPost(){

        String cookie = "cook88=2321983680.31775.0000; AFF=100003; MPR=New/Not Qualify 1; MemberLevel=Gold; PLLC=Continuing; LTDCN=Lifetime Sm.50; intro_AfterLoginliu710927120=1; _ga=GA1.1.2089036263.1530015520; BIAB_HOW_TO_LINKS=true; i18next=zh_CN; BIAB_TZ=-480; BIAB_SHOW_TOOLTIPS=true; ASP.NET_SessionId=dza0vvpvif0u24otaxs45amd; isLogin=1; MC=liu710927120; BIAB_LANGUAGE=zh_CN; CultureInfo=zh-CN; warmSession=3DE098C9888E6CF718E47B553265A81EAB7AE22D367D48AE66C908873E971FBBCF6C9BC1E24EE45B35526AF622614A61A0FD06911D003E6C4909E0A046B8AB88D7F03D9F4A8BB62E4386EB96EFC9798F4EF3D75BD021B2F8C40CCB8F354471BF92E8E732E7678CA2D1C6F154E6C2D9CE; PlayerPreference=General; _gid=GA1.1.2054176041.1530501051; _pk_ses.4.1e6e=*; coldSession=0E2C9D497EE9ACEF3ED4E593019172FF83B1C44D33479692A7DF957EA8A0C65DCAB0049763F6FD141EB13815CEB5FB759CB26C580965EAC00C91890EF63594B01D24F9E8D98716E150CEE2C3658FC12A84734B3065C5983187437C5C92617DD6CD54E5A1D824B9AE04D020163E1A8314; CSRF-TOKEN=4b46c8c54f6bd59fd6f91cd48bfd8e722d; BIAB_CUSTOMER=A9C9EB726C86AEB0E482C9F0106EB16EDF9E3A9B085CF44A1FD6A947187D1298619E4F72A55DB6237959D9D8D5A3439DDC2C002AC4FECFA2; _gat=1; _pk_id.4.1e6e=0a36474e797f8547.1530015520.4.1530516417.1530501051.";
        String url = "http://135.84.237.201/Exchange/customer/api/placeBets";
        Map<String,String> map = new HashMap<String,String>();

        StringEntity stringEntity = new StringEntity("{\"1.145000241\":[{\"selectionId\":1408,\"side\":\"BACK\",\"size\":25,\"price\":1.52,\"persistenceType\":\"LAPSE\",\"handicap\":\"0\",\"eachWayData\":{}}]}","UTF-8");

        try{
            String body = doHttpsPostWithCookie(url, null, "utf-8", cookie, stringEntity);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
