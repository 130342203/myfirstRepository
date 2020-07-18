package com.ck.java_basic.dataStructure.chap4_stackAndQuenue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class brackets {
    public static void main(String[] args) throws IOException {
        String input;
        while (true){
            System.out.print("Enter string contaning delimeters:");
            System.out.flush();
            input = getString();
            if (input.equals("")){
                break;
            }
            BracketChecker checker = new BracketChecker(input);
            checker.check();
        }
    }
    public static String getString() throws IOException {
        InputStreamReader insReader = new InputStreamReader(System.in);
        BufferedReader buffReader = new BufferedReader(insReader);
        String line = buffReader.readLine();
        return line.toString();
    }
}
/*
检测特殊字符是否匹配
*/
class BracketChecker{
    private String input;

    public BracketChecker(String in){
        this.input = in;
    }
    public void check(){
        int stackSize = input.length();
        Stackxx stackxx = new Stackxx(stackSize);

        for (int x=0;x < stackSize;x++){
            char ch = input.charAt(x);
            switch (ch){
                case '[':
                case '{':
                case '(':
                case '【':
                case '（':
                    if (x == stackSize-1){
                        System.out.println("Error: "+ch+" at "+ (x+1));
                    }else {
                        stackxx.push(ch);
                    }
                    break;
                case ']':
                case '}':
                case ')':
                case '】':
                case '）':
                    if (!stackxx.isEmpty()){
                        char chx = stackxx.pop();
                        if ((ch==']' && chx!='[') ||
                            (ch=='}' && chx!='{') ||
                            (ch=='】' && chx!='【') ||
                            (ch=='）' && chx!='（') ||
                            (ch==')' && chx!='(')){
                            System.out.println("Error: "+ch+" at "+ (x+1));
                        }
                    }else {
                        System.out.println("Error: "+ch+" at "+ (x+1));
                    }
                    default:
                        break;
            }

        }
    }
}
