package com.ck.test.testPost;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;

public class TestJava {

    public static String reqJson() throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        String ss = "传染病数据";
        System.out.println("<INF>初始编码：" + getEncoding(ss));
        String ss_new = URLEncoder.encode(new String(ss.getBytes("GB2312"), "GBK"), "utf-8");
        System.out.println("<INF>转utf-8后：" + ss_new);
        System.out.println("<INF>转utf-8：" + getEncoding(ss_new));
        sb.append("{\"modelType\":\"" + URLEncoder.encode(ss, "GBK") + "\",\"paramType\":\"model\",");
        sb.append("\"params\":\"djkjdsdlk4f1ls3del==\",");
        sb.append("\"password\":\"a03b6fe1a\",");
        sb.append("\"username\":\"jikongshujuchuanshu\"}");
        System.out.println("<INF>最终json编码：" + getEncoding(sb.toString()));
        return sb.toString();
//		String res = new String(sb.toString().getBytes("GB2312"),"UTF-8");
//		System.out.println("<INF>"+getEncoding(res));
//		return res;
    }

    //判断编码格式
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
        sb.append("{\"modelType\":\"传染病数据1\",\"paramType\":\"model\",");
        sb.append("\"params\":\"djkjdsdlk4f1ls3del==\",");
        sb.append("\"password\":\"a03b6fe1a\",");
        sb.append("\"username\":\"jikongshujuchuanshu\"}");
        return sb.toString();
    }


    public static void method1() {
        FileWriter fw = null;
        try {
//如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File("D:\\dd.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println("追加内容");
        pw.println("追加内容1");
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

