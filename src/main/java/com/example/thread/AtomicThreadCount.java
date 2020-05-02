package com.example.thread;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class AtomicThreadCount {

    static volatile Long count2 = 0L;
    static volatile AtomicLong count1 = new AtomicLong(0L);
    static volatile LongAdder count3 = new LongAdder();

    public static void main(String[] args) {
        Thread[] threads = new Thread[1000];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 100000; k++) {
                    count1.incrementAndGet();
                }
            });
        }
        long start = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Atomic:" + count1.get() + "time:" + (end - start));
        System.out.println("--------------------------------------------");
        //-------------------------------------------------
        Object lock = new Object();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int k = 0; k < 100000; k++) {
                        synchronized (lock) {
                            count2++;
                        }
                    }
                }
            });
        }
        start = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = System.currentTimeMillis();
        System.out.println("sycn" + count2 + "time:" + (end - start));
        System.out.println("---------------------------------");
        //-------------------------------------------------
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 100000; k++) {
                    count3.increment();
                }
            });
        }
        start = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = System.currentTimeMillis();
        System.out.println("LongAdder" + count3.longValue() + "time:" + (end - start));
    }
}
