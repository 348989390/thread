package com.example.Lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 门栓，初始化设置长度，每次执行 后，countdown()数量减一，await()方法进行阻塞，减完为止
 */
public class CountDownLacthDemo {


    public static void main(String[] args) {

        List list = new ArrayList();
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        new Thread(()->{
            System.out.println("T1开始");

            if(list.size()!=5){
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("T1结束");
            latch2.countDown();
        },"T1").start();

        new Thread(()->{
            System.out.println("T2开始");
            while(list.size()<10){
                list.add(new Object());
                System.out.println("add"+list.size());
                if(list.size()==5){
                    latch.countDown();
                    try {
                        latch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }

            System.out.println("T2结束");
        },"T2").start();

    }


}
