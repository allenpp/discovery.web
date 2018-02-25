package com.neo.discovery.util;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuyunpeng1 on 2018/2/9.
 */
public class KeepLiveConn {

        private static void config(HttpRequestBase httpRequestBase) {
            httpRequestBase.setHeader("User-Agent", "Mozilla/5.0");
            httpRequestBase.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpRequestBase.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");//"en-US,en;q=0.5");
            httpRequestBase.setHeader("Accept-Charset", "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");

            // ��������ĳ�ʱ����
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(3000)
                    .setConnectTimeout(3000)
                    .setSocketTimeout(3000)
                    .build();
            httpRequestBase.setConfig(requestConfig);
        }

        public static void PoolingHttpClientConnectionManager(String[] args) {
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
            LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", plainsf)
                    .register("https", sslsf)
                    .build();

            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
            // ��������������ӵ�200
            cm.setMaxTotal(200);
            // ��ÿ��·�ɻ������������ӵ�20
            cm.setDefaultMaxPerRoute(20);

            // ��Ŀ��������������������ӵ�50
            HttpHost localhost = new HttpHost("http://blog.csdn.net/gaolu",80);
            cm.setMaxPerRoute(new HttpRoute(localhost), 50);

            //�������Դ���
            HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
                public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
                    if (executionCount >= 5) {// ����Ѿ�������5�Σ��ͷ���
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {// ������������������ӣ���ô������
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {// ��Ҫ����SSL�����쳣
                        return false;
                    }
                    if (exception instanceof InterruptedIOException) {// ��ʱ
                        return false;
                    }
                    if (exception instanceof UnknownHostException) {// Ŀ����������ɴ�
                        return false;
                    }
                    if (exception instanceof ConnectTimeoutException) {// ���ӱ��ܾ�
                        return false;
                    }
                    if (exception instanceof SSLException) {// ssl�����쳣
                        return false;
                    }

                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    // ����������ݵȵģ����ٴγ���
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }
            };

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setRetryHandler(httpRequestRetryHandler)
                    .build();

            // URL�б�����
            String[] urisToGet = {
                    "http://blog.csdn.net/gaolu/article/details/48466059",
                    "http://blog.csdn.net/gaolu/article/details/48243103",
                    "http://blog.csdn.net/gaolu/article/details/47656987",
                    "http://blog.csdn.net/gaolu/article/details/47055029",

                    "http://blog.csdn.net/gaolu/article/details/46400883",
                    "http://blog.csdn.net/gaolu/article/details/46359127",
                    "http://blog.csdn.net/gaolu/article/details/46224821",
                    "http://blog.csdn.net/gaolu/article/details/45305769",

                    "http://blog.csdn.net/gaolu/article/details/43701763",
                    "http://blog.csdn.net/gaolu/article/details/43195449",
                    "http://blog.csdn.net/gaolu/article/details/42915521",
                    "http://blog.csdn.net/gaolu/article/details/41802319",

                    "http://blog.csdn.net/gaolu/article/details/41045233",
                    "http://blog.csdn.net/gaolu/article/details/40395425",
                    "http://blog.csdn.net/gaolu/article/details/40047065",
                    "http://blog.csdn.net/gaolu/article/details/39891877",

                    "http://blog.csdn.net/gaolu/article/details/39499073",
                    "http://blog.csdn.net/gaolu/article/details/39314327",
                    "http://blog.csdn.net/gaolu/article/details/38820809",
                    "http://blog.csdn.net/gaolu/article/details/38439375",
            };

            long start = System.currentTimeMillis();
            try {
                int pagecount = urisToGet.length;
                ExecutorService executors = Executors.newFixedThreadPool(pagecount);
                CountDownLatch countDownLatch = new CountDownLatch(pagecount);
                for(int i = 0; i< pagecount;i++){
                    HttpGet httpget = new HttpGet(urisToGet[i]);
                    config(httpget);

                    //�����߳�ץȡ
                    executors.execute(new GetRunnable(httpClient,httpget,countDownLatch));
                }

                countDownLatch.await();
                executors.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("�߳�" + Thread.currentThread().getName() + "," + System.currentTimeMillis() + ", �����߳�����ɣ���ʼ������һ����");
            }

            long end = System.currentTimeMillis();
            System.out.println("consume -> " + (end - start));
        }

        static class GetRunnable implements Runnable {
            private CountDownLatch countDownLatch;
            private final CloseableHttpClient httpClient;
            private final HttpGet httpget;

            public GetRunnable(CloseableHttpClient httpClient, HttpGet httpget, CountDownLatch countDownLatch){
                this.httpClient = httpClient;
                this.httpget = httpget;

                this.countDownLatch = countDownLatch;
            }

            @Override
            public void run() {
                CloseableHttpResponse response = null;
                try {
                    response = httpClient.execute(httpget,HttpClientContext.create());
                    HttpEntity entity = response.getEntity();
                    System.out.println(EntityUtils.toString(entity, "utf-8")) ;
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();

                    try {
                        if(response != null)
                            response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }




    public static void keepAlive(){

        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
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
        CloseableHttpClient httpClient = HttpClients.custom()
                .setKeepAliveStrategy(myStrategy)
                .build();

        HttpPost httpPost = null;
        String result = null;
            httpPost = new HttpPost("https://www.fun172.com/Exchange/customer/api/group/matchOdds/1575634906");
            httpPost.setHeader("Accept","*/*");
            httpPost.setHeader("Accept-Encoding","gzip, deflate, br");
            httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
            httpPost.setHeader("Connection","keep-alive");
            httpPost.setHeader("Host","www.fun172.com");
//            httpPost.setHeader("cookie",cookies);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat QBCore/3.43.691.400 QQBrowser/9.0.2524.400");

            try{
                CloseableHttpResponse    response = httpClient.execute(httpPost,HttpClientContext.create());
                HttpEntity entity = response.getEntity();
                System.out.println(EntityUtils.toString(entity, "utf-8")) ;
                EntityUtils.consume(entity);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    public static void main(String[] args) {


        keepAlive();
    }
}
