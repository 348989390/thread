package com.example.thread;

public class ThreadLocalDemo {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public void threadLocalTest() {
        threadLocal.set("主线程设置的值123");
        String str = threadLocal.get();
        System.out.println("主线程获取的值：" + str);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String str = threadLocal.get();
                System.out.println("线程1取到的值" + str);
                threadLocal.set("线程1设置的值456");
                str = threadLocal.get();
                System.out.println("线程1获取的值：" + str);
            }
        });
        thread.start();
        str = threadLocal.get();
        System.out.println("主线程第二次获取的值：" + str);

    }

    public static void main(String[] args) {
        ThreadLocalDemo demo = new ThreadLocalDemo();
        demo.threadLocalTest();
    }
}
