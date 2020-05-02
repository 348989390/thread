package com.example.thread;

import java.util.concurrent.TimeUnit;

/**
 * volatile关键字，使得一个变量在多个线程内可见
 */
public class T201_HelloVolatile {

    private volatile boolean success = true;

    public void m() {
        System.out.println("Thread start");

        while (success) {

        }
        System.out.println("Thread stop");
    }

    public static void main(String[] args) {
        T201_HelloVolatile t = new T201_HelloVolatile();

        new Thread(t::m, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.success = false;

    }
}
