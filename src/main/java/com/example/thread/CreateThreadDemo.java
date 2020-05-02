package com.example.thread;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateThreadDemo {

    /**
     * 继承Thread类
     */
    static class threadCreateThread extends Thread {
        @Override
        public void run() {
            System.out.println("callableCreateThread============");
            ;
        }
    }

    /**
     * 实现Runnable接口
     */
    static class runableCreateThread implements Runnable {
        @Override
        public void run() {
            System.out.println("runableCreateThread======================");
        }
    }

    /**
     * 实现Callable接口
     */
    static class callableCreateThread implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("callableCreateThread================");
            return "success";
        }
    }


    public static void main(String[] args) {
        //启动线程1
        new Thread().start();

        //启动线程2
        new Thread(new runableCreateThread()).start();

        //启动线程3
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new runableCreateThread());
    }
}
