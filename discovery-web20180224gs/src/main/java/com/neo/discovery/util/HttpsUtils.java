package com.neo.discovery.util;

import org.apache.commons.collections.MapUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Encoder;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpsUtils {

    public static String cookie = "ASP.NET_SessionId=gbike1y3pp0b1vwa3clgtptx; CultureInfo=zh-CN; PlayerPreference=General; intro_BeforeLogin=1; i18next=zh_CN; BIAB_TZ=-480; BIAB_SHOW_TOOLTIPS=false; BIAB_LANGUAGE=zh_CN; _ga=GA1.1.1685646098.1518228399; _gid=GA1.1.276830223.1518228399; _pk_id.4.37b9=6627dafef9e200f8.1518228399.2.1518250545.1518250544.; CSRF-TOKEN=425feb76228981f431778bc0ecf501662f; comm100_guid2_100014005=hwXkjh__fUC1qvQWXn5w6Q; cook88=2338760896.20480.0000";

    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;
    static {
        try {
            builder = new SSLContextBuilder();
            // ??????? ??????????
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory())
                    .register(HTTPS, sslsf)
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200);//max connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * httpClient post????
     * @param url ????url
     * @param header ??????
     * @param param ??????? form??????
     * @param entity ??????? json/xml??????
     * @return ??????? ???????
     * @throws Exception
     *
     */
    public static String post(String  url, Map<String, String> header, Map<String, String> param, HttpEntity entity) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // ????????
            if (MapUtils.isNotEmpty(header)) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            // ???????????
            if (MapUtils.isNotEmpty(param)) {
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    //?????????
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);
            }
            // ??????? ???????
            if (entity != null) {
                httpPost.setEntity(entity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
               readHttpResponse(httpResponse);
            }
        } catch (Exception e) {throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }

    public static String doPost(String url,String jsonstr,String charset){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            StringEntity se = new StringEntity(jsonstr);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
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


    /**
     * ???????
     * @param builder
     * @param hostOrIP
     * @param port
     */
    public static HttpClientBuilder proxy(String hostOrIP, int port){
        // ?????????????????????Э??????
        HttpHost proxy = new HttpHost(hostOrIP, port, "socks5");
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        return HttpClients.custom().setRoutePlanner(routePlanner);
    }


    /**
     * ???????
     *
     * @param url       ??????
     * @param map   ?????б?
     * @param encoding  ????
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String doHttpsPost(String url, Map<String,String> map,String encoding,String cookie,StringEntity stringEntity) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        String body = "";
        //??????????????????https????
        SSLContext sslcontext = createIgnoreVerifySSL();

        // ????Э??http??https????????socket????????????
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);



        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator());
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 111000;
                        } catch(NumberFormatException ignore) {
                        }
                    }
                }
                HttpHost target = (HttpHost) context.getAttribute(
                        HttpClientContext.HTTP_TARGET_HOST);
                if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
                    // Keep alive for 5 seconds only
                    return 5 * 1000;
                } else {
                    // otherwise keep alive for 30 seconds
                    return 30 * 1000;
                }
            }

        };





        //??????????httpclient????
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).setKeepAliveStrategy(myStrategy).build();


        //????post??????????
        HttpPost httpPost = new HttpPost(url);

        //??????
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //???ò??????????????
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("cookie", cookie);

        System.out.println("????????"+url);
        System.out.println("?????????"+nvps.toString());

        //????header???
        //??????????Content-type??????User-Agent??
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");


        //???????????????????????????????
        CloseableHttpResponse response = client.execute(httpPost);
        //?????????
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //???????????????????String????
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //???????
        response.close();
        return body;
    }
    /**
     * ???????
     *
     * @param url       ??????
     * @param map   ?????б?
     * @param encoding  ????
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String doHttpsPostWithTimeOut(String url, Map<String,String> map,String encoding,String cookie,StringEntity stringEntity) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        String body = "";
        //??????????????????https????
        SSLContext sslcontext = createIgnoreVerifySSL();

        // ????Э??http??https????????socket????????????
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);



        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator());
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 111000;
                        } catch(NumberFormatException ignore) {
                        }
                    }
                }
                HttpHost target = (HttpHost) context.getAttribute(
                        HttpClientContext.HTTP_TARGET_HOST);
                if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
                    // Keep alive for 5 seconds only
                    return 5 * 1000;
                } else {
                    // otherwise keep alive for 30 seconds
                    return 30 * 1000;
                }
            }

        };


        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(15000).setConnectionRequestTimeout(11000)
                .setSocketTimeout(15000).build();


        //??????????httpclient????
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(connManager).setKeepAliveStrategy(myStrategy).build();


        //????post??????????
        HttpPost httpPost = new HttpPost(url);

        //??????
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //???ò??????????????
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("cookie", cookie);

        System.out.println("????????"+url);
        System.out.println("?????????"+nvps.toString());

        //????header???
        //??????????Content-type??????User-Agent??
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        httpPost.setHeader("Host","www.fun173.com");
        httpPost.setHeader("Origin","https://www.fun173.com");
        httpPost.setHeader("Referer","https://www.fun173.com/zh-cn/exchange/main.htm/sport/market/1.140333033");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
        httpPost.setHeader("X-CSRF-TOKEN","30f92b53af235dfc0bdb8c020c8615bb2b");
        httpPost.setHeader("X-Requested-With","XMLHttpRequest");

        //???????????????????????????????
        CloseableHttpResponse response = client.execute(httpPost);
        //?????????
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //???????????????????String????
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //???????
        response.close();
        return body;
    }
    /**
     * ???????
     *
     * @param url       ??????
     * @param map   ?????б?
     * @param encoding  ????
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String doHttpsGet(String url, Map<String,String> map,String encoding) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        String body = "";
        //??????????????????https????
        SSLContext sslcontext = createIgnoreVerifySSL();

        // ????Э??http??https????????socket????????????
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https",  new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1.2"},
                        null, new HostnameVerifier(){
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        hostname = "*.betfair.com";
                        return SSLConnectionSocketFactory.getDefaultHostnameVerifier().verify(hostname, session);
                    }

                }))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);









        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator());
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch(NumberFormatException ignore) {
                        }
                    }
                }
                HttpHost target = (HttpHost) context.getAttribute(
                        HttpClientContext.HTTP_TARGET_HOST);
                if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
                    // Keep alive for 5 seconds only
                    return 5 * 1000;
                } else {
                    // otherwise keep alive for 30 seconds
                    return 30 * 1000;
                }
            }

        };









        //??????????httpclient????
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).setKeepAliveStrategy((myStrategy)).build();
//        CloseableHttpClient client = proxy("127.0.0.1", 1080).setConnectionManager(connManager).build();
        //????post??????????
        HttpGet httpGet = new HttpGet(url);

        //??????
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //???ò??????????????
//        httpGet.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("????????"+url);
        System.out.println("?????????"+nvps.toString());

        //????header???
        //??????????Content-type??????User-Agent??
        httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

        try{

            //???????????????????????????????
            CloseableHttpResponse response = client.execute(httpGet);
            //?????????
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //???????????????????String????
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            //???????
            response.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return body;
    }

    /**
     * ??????
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // ??????X509TrustManager???????????????????????????????
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }
    public static CloseableHttpClient getHttpClient() throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(cm)
                .setConnectionManagerShared(true)
                .build();
        return httpClient;
    }
    public static String readHttpResponse(HttpResponse httpResponse)
            throws ParseException, IOException {
        StringBuilder builder = new StringBuilder();
        // ????????????
        HttpEntity entity = httpResponse.getEntity();
        // ?????
        builder.append("status:" + httpResponse.getStatusLine());
        builder.append("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            builder.append("\t" + iterator.next());
        }
        // ?ж?????????????
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            builder.append("response length:" + responseString.length());
            builder.append("response content:" + responseString.replace("\r\n", ""));
        }
        System.out.println(builder.toString());
        return builder.toString();
    }


    public static void main(String[] args) {
//        test();

        buyhttpsPost();
//        genCrsfToken();
    }



    private static   void test(){

//        String url = "https://www.baidu.com";
//        String url = "https://www.fun172.com/Exchange/customer/api/event/details/2022802";
//        String url = "https://www.fun172.com/Exchange/customer/api/group/matchOdds/1575634906";
//        String url = "https://www.fun172.com/zh-cn/exchange/main.htm/ws/multiple-market-prices/558/4t0bmzen/xhr?t=1518250885803";
//        String url = "https://www.fun172.com/zh-cn/exchange/main.htm/ws/multiple-market-prices/988/bbqiteph/xhr?t=1518261012835";
//        String url = "https://www.fun172.com/Exchange/customer/api/multi-market/tabs/28555754";
        String url = "https://www.betfair.com.au/www/sports/exchange/readonly/v1/bymarket?_ak=nzIFcwyWhrlwYMrh&alt=json&marketIds=1.139185491,1.139493976,1.139185657,1.139496339,1.139751383,1.139493514,1.139493794,1.139496889,1.140014852,1.140031881,1.139849950,1.140044837,1.139849952,1.139857395,1.140049338,1.140049337,1.140049330,1.140049336,1.139790477,1.139790085,1.139790141,1.139790253,1.140042377,1.140041905,1.140042220,1.140042685,1.139591744,1.140037838,1.140037803,1.139591919&types=MARKET_STATE";
        try{
//            doPost (url,"{}",null);
//            post (url,null,null,null);
//            String url = "https://sso.tgb.com:8443/cas/login";
            String body = doHttpsGet(url, null, "utf-8");
//            String body = doHttpsPost(url, null, "utf-8",cookie);
            System.out.println("????????????");
            System.out.println(body);
        }catch (Exception e){

        }
    }


    private static void buyhttpsPost(){

        String cookie = "intro_BeforeLogin=1; _ga=GA1.1.1802139949.1519308798; ASP.NET_SessionId=u02y5lhapoveboe3yxgpl0ia; CultureInfo=zh-CN; cook88=2204543168.20480.0000; PlayerPreference=General; _gid=GA1.1.1495271371.1520343780; _pk_ses.4.d4fa=*; comm100_guid2_100014005=HJNje3siikGOgagtNzFm2w; coldSession=99ADC7A3A6761AA3FB93CCBAD7E2A2D794C9CFDE7F18F731E961067ED7AA20F30E527D87017DCFFE9E41A6965004C1D6A806230ABE3F420B63BEE3FD9F9D266DFDCB982D0E94E4623091B7AD213094E057EC492CD42334F5A65AE60549914A1B498FDB770168645DD6F2F07A669F71AF; warmSession=088AD15D9CDE0C2B7016F853BE3F04C8154DC0A9011538DCBCB37F194592B815F4A41CC6625EBFB9A401F59FE2C65599B33A2E9147CD20C43909F6EA8C490623B9D95F9176DC98D7151A4E510D0AA9CCE656E3B7BAB4796DF2862B3FB45BBE3CAD18B42D5710768636638F1A4FF0AAB5; isLogin=1; AFF=100003; MPR=New/Not Qualify 2; PLLC=Continuing; LTDCN=Lifetime Sm.30; MC=liu710927120; intro_AfterLoginliu710927120=1; BIAB_LANGUAGE=zh_CN; BIAB_SHOW_TOOLTIPS=true; i18next=zh_CN; BIAB_TZ=-480; BIAB_LOGIN_POP_UP_SHOWN=true; BIAB_CUSTOMER=8E7FA9A0CD96A6EF0FC6DB9784AA0A9C5CE656099A7EC9B788DE5A4B47E60514E1C3A7CE0767F58B66BBFE372C77951A3FE3F7DFC7F1D848; _pk_id.4.d4fa=0a36474e797f8547.1519308798.4.1520345287.1520343780.; CSRF-TOKEN="+genCrsfToken();
        String url = "https://www.fun173.com/Exchange/customer/api/placeBets";
        Map<String,String> map = new HashMap<String,String>();

        StringEntity stringEntity = new StringEntity("{\"1.140333033\":[{\"selectionId\":56323,\"side\":\"BACK\",\"size\":25,\"price\":2.82,\"persistenceType\":\"LAPSE\",\"handicap\":\"0\",\"eachWayData\":{}}]}","UTF-8");

        try{
            String body = doHttpsPostWithTimeOut(url, null, "utf-8", cookie, stringEntity);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static  String   genCrsfToken( ) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str="zxcvbnmlkjhgfdsaqwertyuiop1234567890";
        //由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<35; ++i){
            //产生0-61的数字
            int number=random.nextInt(35);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        System.out.println(sb.toString());
        //将承载的字符转换成字符串
        return sb.toString();
    }


}