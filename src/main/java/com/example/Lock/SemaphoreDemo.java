package com.example.Lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/**
 * 信号量，用作限流
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
//        Semaphore semaphore = new Semaphore(2);
        //公平信号量 fair:true
        Semaphore semaphore = new Semaphore(2,true);

        new Thread(()->{
            try {
                //底层是unsafe.compareAndSwapObject方法
                semaphore.acquire();
                System.out.println("T1 Runing ..........");
                Thread.sleep(200);
                System.out.println("T1 Runing ..........");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        });

    }
}
