package com.ruoyi.zayy.config;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
public class Config2RestTemplate {
    @Bean
    public RestTemplate restTemplate()throws Exception{
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null,acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(createlgnoreVerifySSL(),
                //指定TLS版本
                null,
                //指定算法
                null,
                //取消域名验证
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                }
        );
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory=
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setReadTimeout(60*1000);//ms
        requestFactory.setConnectTimeout(60*1000);//ms
// 该代码的意思是请求工厂类是否应用缓冲请求正文内部，默认值为true，当post或者put
        requestFactory.setBufferRequestBody(false);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
    @Bean
    public RestTemplate lowDelayRestTemplate()throws Exception{
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain,String authType) -> true;
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null,acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(createlgnoreVerifySSL(),
                //指定TLS版本
                null,
                //指定算法
                null,
                //取消域名验证
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                }
        );
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory=
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setReadTimeout(10*1000);//ms
        requestFactory.setConnectTimeout(10*1000);//ms
// 该代码的意思是请求工厂类是否应用缓冲请求正文内部，默认值为true，当post或者put
        requestFactory.setBufferRequestBody(false);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    private static SSLContext createlgnoreVerifySSL()throws Exception{
        SSLContext sc = SSLContext.getInstance("TLS");

        //实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager=new X509TrustManager(){

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
//                return new X509Certificate[0];
                return null;
            }
        };
        sc.init(null,new TrustManager[] {trustManager},null);
        return sc;
    }
}
