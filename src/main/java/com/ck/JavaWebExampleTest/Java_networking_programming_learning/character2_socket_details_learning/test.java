package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character2_socket_details_learning;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

public class test {

    //private static final String v_url = "https://www.baidu.com/";
    private static final String v_url = "https://www.cnblogs.com/gne-hwz/p/6952312.html";

    public static void main(String[] args) throws IOException {

        String result = getPostInfo(v_url);
        long begin = System.currentTimeMillis() ;
        System.out.println(result);
        //保存
        File outfile = new File("G:/image"+File.separator+begin+".html");
        FileOutputStream fileOutputStream =  new FileOutputStream(outfile);
        fileOutputStream.write(result.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end - begin)+"ms");

    }
    public static String getPostInfo( String webUrl)    throws IOException
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL url = new URL( webUrl);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("contentType", "UTF-8");
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        conn.setRequestProperty("Accept-Language", Locale.getDefault().toString());
        //conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");

        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setDoOutput(true);
        //String encoding = System.getProperty("file.encoding");
       /* //传送参数
        out = new PrintWriter(conn.getOutputStream());
        out.print(v_urlparam);
        out.flush();
        out.close();*/
        result = readStrByCode(conn.getInputStream(), "UTF-8");

        return result;
    }

    public static String readStrByCode(InputStream is, String code) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        String line="";
        try
        {
            reader = new BufferedReader(new InputStreamReader(is, code));
            while ((line = reader.readLine()) != null)
            {
                builder.append(line+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try
            {
                reader.close();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
