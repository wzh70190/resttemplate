package com.example.resttem.rest.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HttpexecutorApplication {

    private static final HostnameVerifier PROMISCUOUS_VERIFIER = (s, sslSession ) -> true;

    public static void main(String[] args) {
        SpringApplication.run(HttpexecutorApplication.class, args);
    }

    @Bean
    public RestTemplate commonRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        //验证主机名和服务器验证方案的匹配是可接受的

        restTemplate.setRequestFactory(getRequestFactory(20000,20000));
        List<ClientHttpRequestInterceptor> interceptorsTimeout = new ArrayList<ClientHttpRequestInterceptor>();
        interceptorsTimeout.add(new HeaderRequestInterceptor());
        restTemplate.setInterceptors(interceptorsTimeout);

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        restTemplate.getMessageConverters().set(1,stringConverter);

        return restTemplate;
    }

    @Bean
    public RestTemplate longRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        //验证主机名和服务器验证方案的匹配是可接受的

        restTemplate.setRequestFactory(getRequestFactory(30000,30000));
        List<ClientHttpRequestInterceptor> interceptorsTimeout = new ArrayList<ClientHttpRequestInterceptor>();
        interceptorsTimeout.add(new HeaderRequestInterceptor());
        restTemplate.setInterceptors(interceptorsTimeout);

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        restTemplate.getMessageConverters().set(1,stringConverter);

        return restTemplate;
    }


    @Bean
    public RestTemplate shortRestTemplate(){

        RestTemplate bigDataRestTemplate = new RestTemplate();
        //验证主机名和服务器验证方案的匹配是可接受的

        bigDataRestTemplate.setRequestFactory(getRequestFactory(10000,10000));
        List<ClientHttpRequestInterceptor> interceptorsTimeout = new ArrayList<ClientHttpRequestInterceptor>();
        interceptorsTimeout.add(new HeaderRequestInterceptor());
        bigDataRestTemplate.setInterceptors(interceptorsTimeout);

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        bigDataRestTemplate.getMessageConverters().set(1,stringConverter);

        return bigDataRestTemplate;
    }

    private SimpleClientHttpRequestFactory getRequestFactory(int readTimeout , int connectTimeout) {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof HttpsURLConnection) {
                    //处理主机名验证
                    ((HttpsURLConnection) connection).setHostnameVerifier(PROMISCUOUS_VERIFIER);
                    //https认证
                    ((HttpsURLConnection) connection).setSSLSocketFactory(trustSelfSignedSSL());
                }
                super.prepareConnection(connection, httpMethod);
            }
        };
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setConnectTimeout(connectTimeout);

        return requestFactory;
    }

    private static class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            HttpRequest wrapper = new HttpRequestWrapper(request);
            wrapper.getHeaders().set("Accept-charset", "utf-8");
            return execution.execute(wrapper, body);
        }
    }

    public SSLSocketFactory trustSelfSignedSSL() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            return ctx.getSocketFactory();
        } catch (Exception ex) {
            throw new RuntimeException("Exception occurred ", ex);
        }
    }
}
