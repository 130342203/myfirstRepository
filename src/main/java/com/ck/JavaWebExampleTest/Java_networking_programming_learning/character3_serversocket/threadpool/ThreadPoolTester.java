package com.ck.JavaWebExampleTest.Java_networking_programming_learning.character3_serversocket.threadpool;
/*用于测试编写的ThreadPool类的效果*/
public class ThreadPoolTester {
    public static void main(String[] args) {
        int numTasks = 10;
        int poolSize = 5;

        ThreadPool threadPool = new ThreadPool(poolSize);

        //运行任务
        for(int i =0;i<numTasks;i++){
            threadPool.excute(createTask(i));
        }
        threadPool.join();//两种不同的关闭方式
        //threadPool.close();

    }
    private static Runnable createTask(final int taskID){
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Task"+taskID+":start");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Task"+taskID+":end");
                }
            }
        };
    }
}
