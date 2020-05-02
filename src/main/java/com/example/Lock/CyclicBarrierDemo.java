package com.example.Lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(20,()-> System.out.println("满人"));

        for(int i =0;i<100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //底层实现 ReentrantLock
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


}
