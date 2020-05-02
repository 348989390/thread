package com.example.thread;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    public void threadPoolExecutorTest1() throws Exception {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        testconment(poolExecutor);
    }

    public void threadPoolExecutorTest2() throws Exception {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("任务被拒绝");
            }
        });
        testconment(poolExecutor);
    }

    public void testconment(ThreadPoolExecutor poolExecutor) throws Exception {
        for (int i = 0; i < 15; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                System.out.println("线程" + finalI + "开始执行");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + finalI + "执行完毕");
            });
            poolExecutor.submit(thread);
        }
        Thread.sleep(500);
        //查看当前线程池中的线程情况
        System.out.println("当前线程池中线程数量：" + poolExecutor.getPoolSize());
        System.out.println("当前线程池中等待线程数量：" + poolExecutor.getQueue().size());
        Thread.sleep(15000);
        //线程都执行完毕之后，查看线程池中的线程情况
        System.out.println("当前线程池中线程数量：" + poolExecutor.getPoolSize());
        System.out.println("当前线程池中等待线程数量：" + poolExecutor.getQueue().size());
    }

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();
        try {
            pool.threadPoolExecutorTest2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
