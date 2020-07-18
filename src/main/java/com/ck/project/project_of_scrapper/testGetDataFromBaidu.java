package cn.com.sinosoft.sendData.sendToBrmp.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/6/2
 * @Content:测试爬虫
 */
public class testGetDataFromBaidu {
//    private static String url = "http://www.baidu.com";
    private static String url = "http://quote.eastmoney.com/sh601169.html?from=BaiduAladdin";
    public static void main(String[] args) {
        List<String> urls = getSubURLs(url);
        for (String str : urls){
            System.out.println("<INF>"+str);
        }
    }
    /*获取页面包含的全部超链接*/
    public static ArrayList<String> getSubURLs(String urlString) {
        //该方法为每个给定的URL返回一个URL列表
        ArrayList<String> list=new ArrayList<>();
        try {
            java.net.URL url=new java.net.URL(urlString);
            Scanner input=new Scanner(url.openStream());
            int current=0;
            while(input.hasNext()) {
                String line=input.nextLine();//从Web读取每一行
                System.out.println("<INF>"+line);
               /* current=line.indexOf("http:",current);//寻找该行中的URL
                //@todo 增加校验url的功能
                //@todo 增加校验每一个按钮的功能
                while(current>0) {
                    int endIndex=line.indexOf("\"",current);//假设URL以引号"结束
                    if(endIndex>0) {
                        list.add(line.substring(current,endIndex));//一行中可能包含多个URL，
                        current=line.indexOf("http:",endIndex);//方法继续寻找下一个URL
                    }
                    else {
                        current=-1;//如果该行中没有发现URL，curr设为-1
                    }
                }*/
            }
        }
        catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        return list;//页面中包含的URL以一个列表的形式返回
    }
}
