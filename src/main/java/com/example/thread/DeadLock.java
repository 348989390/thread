package com.example.thread;

/**
 * 线程死锁
 */
public class DeadLock {
    private static Object o1 = new Object();
    private static Object o2 = new Object();
    private static Thread t1 = new Thread(() -> {
        synchronized (o1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2) {
                System.out.println("获取o2锁对象");
            }
        }
    });
    private static Thread t2 = new Thread(() -> {
        synchronized (o2) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o1) {
                System.out.println("获取o1锁对象");
            }
        }
    });

    public static void main(String[] args) {
        t1.start();
        t2.start();
    }
}
