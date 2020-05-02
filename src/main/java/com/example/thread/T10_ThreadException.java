package com.example.thread;

import java.util.concurrent.TimeUnit;

/**
 * 异常锁，线程抛出异常的时候，要及时catch异常，处理异常
 */
public class T10_ThreadException {
    private int count = 0;

    synchronized void m() {
        System.out.println(Thread.currentThread().getName() + " start");
        while (true) {
            System.out.println(count);
            count++;
            System.out.println(count);
            System.out.println(Thread.currentThread().getName() + " start");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (count == 5) {
                int i = 1 / 0;
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        T10_ThreadException t = new T10_ThreadException();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                t.m();
            }
        };

        new Thread(r, "r1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r, "r2").start();

    }
}
