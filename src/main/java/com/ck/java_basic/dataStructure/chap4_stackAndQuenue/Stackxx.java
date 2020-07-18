package com.ck.java_basic.dataStructure.chap4_stackAndQuenue;

/*
* 堆栈
* */

public class Stackxx {
    private int maxSize;
    private char[] stackArr;
    private int top;

    public Stackxx(int size){
        maxSize = size;
        this.stackArr = new char[maxSize];
        top = -1;
    }
    public void push(char j){
        stackArr[++top] = j;
    }
    public char pop(){
       return stackArr[top--];
    }
    public boolean isEmpty(){
        return (top == -1);
    }
    public char peek(){
        return stackArr[top];
    }
}
