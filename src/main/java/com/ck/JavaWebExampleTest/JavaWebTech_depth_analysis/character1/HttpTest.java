package com.ck.JavaWebExampleTest.JavaWebTech_depth_analysis.character1;


import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;

public class HttpTest {
    CloseableHttpClient httpClient = HttpClients.custom().setRetryHandler(new DefaultHttpRequestRetryHandler(0,false)).build();

}
