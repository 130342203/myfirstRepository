package com.ck.project.project_of_scrapper;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;

import java.io.IOException;
import java.net.URL;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/6/3
 * @Content:
 */
public class testHtmlUnit {

    public static void main(String[] args) throws IOException {
        WebClient webClient = new WebClient();
//don‘t disable it if you want JS ENABLED;
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setTimeout(7000);
        WebRequest request = new WebRequest(new URL("http://quote.eastmoney.com/sh601169.html?from=BaiduAladdin"));
//request.setAdditionalHeader("Authorization", "Basic bG9hbnNkZXY6bG9AbnNkM3Y=");
        Page page = webClient.getPage(request);
        String contentType = page.getWebResponse().getContentType();
        int statusCode = page.getWebResponse().getStatusCode();
        String statusMessage = page.getWebResponse().getStatusMessage();
        long loadTime = page.getWebResponse().getLoadTime();
        System.out.println(statusCode);
        System.out.println(statusMessage);
        System.out.println(contentType);
        System.out.println(loadTime);
        System.out.println(page.getWebResponse().getContentAsString());

    }
}
