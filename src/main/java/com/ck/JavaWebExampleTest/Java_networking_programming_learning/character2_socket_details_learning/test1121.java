package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character2_socket_details_learning;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2019/11/21
 * @Content:
 */
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class test1121 {

    private static final Logger logger = LoggerFactory.getLogger(test1121.class);

    public static void main(String[] args) {
        test1121 test1121 = new test1121();
        sendPost("http://localhost:8080/api/v1/plugins/qs_ahth_test","你好！");
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    //更改为动态的进行尝试
    public static void sendPost(String url, String param) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        String result = null;

        try {
            Map<String,String> params = new HashMap<>();
            List<BasicNameValuePair> parames = new ArrayList<>();
            parames.add(new BasicNameValuePair("code", "001"));
            parames.add(new BasicNameValuePair("name", "测试"));

            post.setEntity(new UrlEncodedFormEntity(parames, "UTF-8"));

            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (res.getEntity().isStreaming() && "image/jpeg".equals(res.getEntity().getContentType().getValue())){
                    File file = new File("C:/Users/Administrator/Desktop/5.jpg");
                    if (!file.exists()){
                        file.createNewFile();
                    }
                    OutputStream outputStream = new FileOutputStream(file);
                    res.getEntity().writeTo(outputStream);
                    outputStream.flush();
                    logger.info( "ojbk");
                }
                result = EntityUtils.toString(res.getEntity());// 返回json格式：

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.error("【post关闭】 异常IOException = ", e);
            }
        }
//        return result;

//        PrintWriter out = null;
//        BufferedReader in = null;
//        String result = "";
//        HttpURLConnection httpURLConnection = null;
//        try {
//            URL realUrl = new URL(url);
//            // 打开和URL之间的连接
//            URLConnection conn = realUrl.openConnection();
//            httpURLConnection = (HttpURLConnection) conn;
//            // 设置通用的请求属性
//            httpURLConnection.setRequestProperty("accept", "*/*");
//            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
//            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//            httpURLConnection.setRequestProperty("Transfer-Encoding", "chunked");
//            httpURLConnection.setConnectTimeout(5000);
//            httpURLConnection.setReadTimeout(60000);
//            // 发送POST请求必须设置如下两行
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setDoInput(true);
//            // 获取URLConnection对象对应的输出流
//
//            Map<String,String> params = new HashMap<String,String>();
//
//            params.put("data", JSON.toJSONString("人民万岁！"));
//
//            out = new PrintWriter(httpURLConnection.getOutputStream());
//            // 发送请求参数
//            out.print(param);
//            // flush输出流的缓冲
//            out.flush();
//            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//        } catch (Exception e) {
//            logger.error("发送 POST 请求出现异常！"+e.getMessage());
//        }
//        // 使用finally块来关闭输出流、输入流
//        finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                System.out.println("jieshu");
//            }
//        }
    }
}