package com.ck.project.project_of_scrapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/6/3
 * @Content:
 */
public class testDownloadHtml {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://quote.eastmoney.com/sh601169.html?from=BaiduAladdin");
            url.openConnection();
//            HttpURLConnection httpURLConnection =url;
//            OutputStream outputStream =

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
