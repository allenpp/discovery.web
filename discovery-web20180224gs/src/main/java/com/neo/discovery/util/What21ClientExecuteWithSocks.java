package com.neo.discovery.util;

/**
 * Created by liuyunpeng1 on 2018/2/11.
 */
import java.io.IOException;
import java.net.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class What21ClientExecuteWithSocks {

    public static void socks5(String[] args) throws Exception {
        // ConnectionSocketFactoryע��
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", new MyConnectionSocketFactory()).build();
        // HTTP�ͻ������ӹ����
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(reg);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(connManager)
                .build();
        try {
            // socks�����ַ
            InetSocketAddress socksaddr = new InetSocketAddress("127.0.0.1", 1080);
            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("socks.address", socksaddr);

            // ����Ŀ��
            HttpHost target = new HttpHost("www.baidu.com", 80, "http");
            HttpGet request = new HttpGet("/");
            System.out.println("----------------------------------------");
            System.out.println("ִ������ ��" + request.getRequestLine());
            System.out.println("ͨ������ " + socksaddr);
            System.out.println("ִ��Ŀ¼�� " + target);
            System.out.println("----------------------------------------");

            CloseableHttpResponse response = httpclient.execute(target, request, context);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println("----------------------------------------");
                System.out.println("������Ӧ��" + response.getStatusLine());
                System.out.println("��Ӧ���ݣ�" + EntityUtils.toString(entity));
                System.out.println("----------------------------------------");
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    static class MyConnectionSocketFactory implements ConnectionSocketFactory {

        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            // socket����
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(
                final int connectTimeout,
                final Socket socket,
                final HttpHost host,
                final InetSocketAddress remoteAddress,
                final InetSocketAddress localAddress,
                final HttpContext context) throws IOException, ConnectTimeoutException {
            Socket sock;
            if (socket != null) {
                sock = socket;
            } else {
                sock = createSocket(context);
            }
            if (localAddress != null) {
                sock.bind(localAddress);
            }
            try {
                sock.connect(remoteAddress, connectTimeout);
            } catch (SocketTimeoutException ex) {
                throw new ConnectTimeoutException(ex, host, remoteAddress.getAddress());
            }
            return sock;
        }

    }


    public static void main(String[] args) {
        SocketAddress addr = new InetSocketAddress("127.0.0.1", 1080);
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);
        Socket socket = new Socket(proxy);
        InetSocketAddress dest = new InetSocketAddress("www.baidu.com", 80);
        try {
            socket.connect(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}