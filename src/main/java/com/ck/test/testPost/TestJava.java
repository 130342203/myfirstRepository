package com.ck.test.testPost;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;

public class TestJava {

    public static String reqJson() throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        String ss = "��Ⱦ������";
        System.out.println("<INF>��ʼ���룺" + getEncoding(ss));
        String ss_new = URLEncoder.encode(new String(ss.getBytes("GB2312"), "GBK"), "utf-8");
        System.out.println("<INF>תutf-8��" + ss_new);
        System.out.println("<INF>תutf-8��" + getEncoding(ss_new));
        sb.append("{\"modelType\":\"" + URLEncoder.encode(ss, "GBK") + "\",\"paramType\":\"model\",");
        sb.append("\"params\":\"djkjdsdlk4f1ls3del==\",");
        sb.append("\"password\":\"a03b6fe1a\",");
        sb.append("\"username\":\"jikongshujuchuanshu\"}");
        System.out.println("<INF>����json���룺" + getEncoding(sb.toString()));
        return sb.toString();
//		String res = new String(sb.toString().getBytes("GB2312"),"UTF-8");
//		System.out.println("<INF>"+getEncoding(res));
//		return res;
    }

    //�жϱ����ʽ
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    public static String reqJson1() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"modelType\":\"��Ⱦ������1\",\"paramType\":\"model\",");
        sb.append("\"params\":\"djkjdsdlk4f1ls3del==\",");
        sb.append("\"password\":\"a03b6fe1a\",");
        sb.append("\"username\":\"jikongshujuchuanshu\"}");
        return sb.toString();
    }


    public static void method1() {
        FileWriter fw = null;
        try {
//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
            File f = new File("D:\\dd.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println("׷������");
        pw.println("׷������1");
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String a() throws MalformedURLException, IOException {
        method1();
        reqJson();
        HttpURLConnection httpURLConnection = (HttpURLConnection) new java.net.URL("http://59.225.200.64:8791/brmp-webservice-in/webservice/BrmpObj/reqBrmp").openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.write(reqJson());
        printWriter.flush();
        printWriter.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        System.out.println(reqJson());
        System.out.println(response.toString());
        return "";
    }


    public static void main(String[] args) {
        try {
            a();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

