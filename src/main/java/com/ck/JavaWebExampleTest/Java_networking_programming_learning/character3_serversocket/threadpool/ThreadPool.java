package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character3_serversocket.threadpool;

import java.util.LinkedList;

/*创建线程池--threadGroup类的运用
* 1、threadGroup是一个线程组 */
public class ThreadPool extends ThreadGroup{
    private boolean isClosed = false;
    private LinkedList<Runnable> workQueue;
    private static int threadPoolID;
    private int threadID ;

    public ThreadPool(int poolSize){
        super("ThreadPool-"+(threadPoolID++));
        //super()函数在这里是调用了基类threadGroup中的构造函数，其构造函数的作用是创建线程池并且为其命名
        System.out.println("线程池："+this.getName());
        setDaemon(true);
        workQueue = new LinkedList<Runnable>();//创建工作队列
        for (int i=0;i<poolSize;i++){
            new WorkThread().start();//创建并启动该工作线程
        }
    }
    /*向工作队列中加入一个新任务，由工作线程去执行该任务*/
    public synchronized void excute(Runnable task){
        if (isClosed){ //线程池被关闭则抛出异常
            System.out.println("线程池已被关闭，抛出异常-->");
            throw new IllegalStateException();
        }
        if (task != null){
            workQueue.add(task);
            System.out.println("添加新任务--");
            notify(); //唤醒正在getTask()方法中等待任务的工作线程
        }
    }
    /*从工作队列中取出一个任务，工作线程会调用此方法*/
    public synchronized Runnable getTask() throws InterruptedException {
        while (workQueue.size() == 0){
            if (isClosed){return null;}
            wait();//队列中无任务则等待
        }
        return workQueue.removeFirst();
    }
    /*关闭线程池方法一--清空队列并中断所有线程*/
    public synchronized void close(){
        if (!isClosed){
            isClosed = true;
            workQueue.clear();//清空工作队列
            interrupt();//中断所有的线程，该方法继承自threadGroup类
        }
    }
    /*关闭线程池方法二--等待工作线程执行完队列中的所有任务后在关闭线程池*/
    public void join(){
        synchronized (this){//首先同步线程池的关闭标志，不可再继续向队列添加新的任务
            isClosed = true;
            notifyAll();//唤醒所有工作线程
        }
        Thread[] threads = new Thread[activeCount()];
        //enumrate()方法继承自threadGroup类，获得线程组中当前所有的活着的线程
        int count = enumerate(threads);
        for (int i=0;i<count;i++){//等待所有工作线程运行结束
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /*内部类：工作线程*/
    private class WorkThread extends Thread{
        public WorkThread(){
            //加入到当前threadPool线程组中
            super(ThreadPool.this,"WorkThread-"+(threadID++));
            System.out.println("工作线程："+this.getName());
        }
        public void run(){
            while (!isInterrupted()){//isInterrupted()方法继承自threadGroup类，判断线程是否被中断
                Runnable task = null;
                try {
                    task = getTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task == null){
                    return;
                }
                try {
                    task.run();
                }catch (Throwable t){
                    t.printStackTrace();
                }
            }
        }
    }

}
