package com.ck.project.project_of_scrapper;


import java.io.IOException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class testHtmlunit2 {
    private static String url = "";

    public static void main(String[] args) throws Exception {
        WebClient webClient=new WebClient();

    }
    public static void testBaidu() throws IOException {
        //使用webClient的构造方法指定以何种浏览器打开网页，此处为以firefox打开,也可以不指定
        WebClient webClient=new WebClient();

        //使用代理IP，这是为了应对网站有反爬系统的情况
        //WebClient webClient2=new WebClient(BrowserVersion.FIREFOX_52,"202.106.16.36",3128);

        //HTMLClient对css和js的支持不好，所以将其关闭
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);

        HtmlPage page=webClient.getPage("http://www.baidu.com");

        //获取当前页面的html
        String string=page.asXml();
        System.out.println("html内容为："+string);

        //获取当前页面的文本
        String string2=page.asText();
        System.out.println("纯文本内容为："+string2);

        //获取百度首页“百度一下”按钮
        HtmlInput button=(HtmlInput) page.getElementById("su");
        System.out.println("百度一下按钮的默认值为："+button.getDefaultValue());

        //获取百度首页搜索输入框
        HtmlInput input=(HtmlInput) page.getElementById("kw");
        System.out.println("百度首页输入框的最大长度为："+input.getAttribute("maxlength"));

        //找到百度首页所有div并打印第一个的html
        List<?> divs=page.getByXPath("//div");
        HtmlDivision division=(HtmlDivision) divs.get(0);
        System.out.println(division.asXml());

        //查找并获取特定条件的input
        List<?> inputs=page.getByXPath("//input[@id='su']");
        HtmlInput input2=(HtmlInput) inputs.get(0);
        System.out.println(input2.asXml());

        //为百度首页的搜索输入框设置值并提交搜索
        HtmlInput input3=(HtmlInput) page.getElementById("kw");
        //调用setValueAttribute方法设置值
        input3.setValueAttribute("日向優梨");
        HtmlInput button2=(HtmlInput) page.getElementById("su");
        //模拟点击“百度一下”按钮
        HtmlPage page2=button2.click();
        System.out.println(page2.asXml());

        //关闭webclient
        webClient.close();
    }
}