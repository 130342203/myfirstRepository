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
 * @Content:��������
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
    /*��ȡҳ�������ȫ��������*/
    public static ArrayList<String> getSubURLs(String urlString) {
        //�÷���Ϊÿ��������URL����һ��URL�б�
        ArrayList<String> list=new ArrayList<>();
        try {
            java.net.URL url=new java.net.URL(urlString);
            Scanner input=new Scanner(url.openStream());
            int current=0;
            while(input.hasNext()) {
                String line=input.nextLine();//��Web��ȡÿһ��
                System.out.println("<INF>"+line);
               /* current=line.indexOf("http:",current);//Ѱ�Ҹ����е�URL
                //@todo ����У��url�Ĺ���
                //@todo ����У��ÿһ����ť�Ĺ���
                while(current>0) {
                    int endIndex=line.indexOf("\"",current);//����URL������"����
                    if(endIndex>0) {
                        list.add(line.substring(current,endIndex));//һ���п��ܰ������URL��
                        current=line.indexOf("http:",endIndex);//��������Ѱ����һ��URL
                    }
                    else {
                        current=-1;//���������û�з���URL��curr��Ϊ-1
                    }
                }*/
            }
        }
        catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        return list;//ҳ���а�����URL��һ���б����ʽ����
    }
}
