package com.ck.java_core_knowledge.chapter4.thread;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2019/12/2
 * @Content:
 */
public class testThreadlocal {


    public static void main(String[] args) {
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.get();
    }
}
