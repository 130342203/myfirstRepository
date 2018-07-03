package com.ck.JavaWebExampleTest.javaLeague.daily_acm;

import java.io.*;

public class acm1 implements java.io.Serializable {
public int num = 120;
    public static void main(String[] args) throws FileNotFoundException {
        acm1  acm1 = new acm1();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("G:\\123.dat");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    private static void test1(){
        */
/*例一：统计元音字母数量*//*

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        char chs[] ={'a','e','i','o','u'};
        while(n-->0){
            String str = sc.nextLine();
            int[] num =new int[5];
            for(int i=0;i<str.length();i++){
                char ch = str.charAt(i);
                for(int j=0;j<chs.length;j++){
                    if(ch==chs[j]){
                        num[j]++;
                        break;
                    }
                }
            }
            for(int i=0;i<chs.length;i++){
                System.out.println(chs[i]+":"+num[i]);
            }
            if(n>0){
                System.out.println();
            }
        }
    }
*/
}
