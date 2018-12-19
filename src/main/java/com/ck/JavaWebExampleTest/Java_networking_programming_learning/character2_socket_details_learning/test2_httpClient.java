package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character2_socket_details_learning;

import org.apache.http.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class test2_httpClient {
    //注意：域名和url是两个概念
    String host = "www.cnblogs.com";
    String webUrl ="https://www.cnblogs.com/gne-hwz/p/6952312.html";
    int port = 80;

    public static void main(String[] args) throws IOException {
        test2_httpClient test2_httpClient = new test2_httpClient();
        //test2_httpClient.createSocket();
        test2_httpClient.communicate();
    }

    public void communicate() throws IOException {
        URLConnection urlConnection = null;
        URL url = new URL(webUrl);

        urlConnection = url.openConnection();
        urlConnection.setConnectTimeout(15000);
        //urlConnection.setUseCaches(false);
        urlConnection.setRequestProperty("accept","*/*");
        urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
        urlConnection.setRequestProperty("Content-type", "application/json; charset=utf-8");
        //urlConnection.setRequestProperty("connection","Keep-Alive");
        urlConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
        //urlConnection.setRequestProperty("Accept-Encoding","gzip, deflate, br");
        /*不要设置Accept-Encoding的header，否则则需要设置对应的解压缩模式*/
        urlConnection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

        long begin = System.currentTimeMillis() ;
        String contentType = urlConnection.getContentType();
        System.out.println("连接时间："+ begin +"ms"+" 编码类型："+contentType);


        InputStream connectIn = urlConnection.getInputStream();
        File outfile = new File("G:/image"+File.separator+begin+".html");
        FileOutputStream fileOutputStream =  new FileOutputStream(outfile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connectIn,"utf-8"));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine())!= null){
            builder.append(line+"\n");
        }
        reader.close();
        System.out.println(builder.toString());
        long end = System.currentTimeMillis();
        System.out.println("阅读完毕时间："+end+"ms"+" 耗时："+(end - begin)+"ms");
        fileOutputStream.flush();
        fileOutputStream.close();
        connectIn.close();




        Socket socket = null;
        socket = new Socket(host,port);
        StringBuffer sb = new StringBuffer("GET"+webUrl+"HTTP/1.1\r\n");
        sb.append("Host:"+host+"\r\n");
        sb.append("Accept:*/*\r\n");
        //sb.append("Content-type:application/json; charset=utf-8\r\n");
        //sb.append("Accept-Language:zh-CN\r\n");
        sb.append("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36\r\n");
        sb.append("Connection: Keep-Alive/r/n");
        sb.append("\r\n");
        //发送http请求
        OutputStream socketOut = socket.getOutputStream();
        socketOut.write(sb.toString().getBytes());
        socket.shutdownOutput();
        //接收响应结果
        InputStream socketIn = socket.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        //ByteArrayOutputStream是一个能容量自增的缓冲区，若socketIn.read(buff)返回值是-1，
        // 则表示读到了输入流得末尾，但若网页较大，全部保存在缓冲区中并不合适，建议用bufferedReader
        //逐行读取更加合适，见下方注释部分
        byte[] buff = new byte[1024];
        int len = 1;
        while ((len = socketIn.read(buff))!= -1){
            buffer.write(buff,0,len);
        }
        System.out.println(new String(buffer.toByteArray()));
       /* BufferedReader br = new BufferedReader(new InputStreamReader(socketIn));
        String data;
        while ((data = br.readLine())!= null){
            System.out.println(data);
        }*/
    }
}
