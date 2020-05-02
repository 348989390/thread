package com.example.Lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {

    static Thread t2 =null;

    public static void main(String[] args) {
        {
            List list = new ArrayList();


            Thread t1 = new Thread(()->{
                System.out.println("T1开始");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                if(list.size()!=5){
                //unpark可以发生在park之前，此处不需要加判断
                    LockSupport.park();
                    System.out.println("T1结束");
                    LockSupport.unpark(t2);
//                }


            },"T1");

            t1.start();

            t2 = new Thread(()->{
                System.out.println("T2开始");
                while(list.size()<10){
                    list.add(new Object());
                    System.out.println("add"+list.size());
                    if(list.size()==5){
                        LockSupport.unpark(t1);
                        LockSupport.park();
                    }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                }

                System.out.println("T2结束");
            },"T2");
            t2.start();

        }
    }
}
