package com.example.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 面试题1：
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5时，线程2给出提示并结束。
 * 典型wait，notify用法题目
 * notify不会释放锁，wait释放锁
 */
public class NotifyWaitMSDemo {

    public static void main(String[] args) {
        final Object o = new Object();
        List list = new ArrayList();

        new Thread(()->{
            synchronized (o){
                System.out.println("T2启动");
                if(list.size()!=5){
                    try {
                        //等待T1将容器size增加到5，获取锁，打印信息。
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("T2结束");
                    //打印完，让出锁给T1执行，自己执行完后续代码，结束线程。
                    o.notify();
                }
            }
        },"T2").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            synchronized (o){
                System.out.println("T1启动");
                while (list.size()<10){
                    list.add("test");
                    System.out.println("add"+list.size());
                    if(list.size()==5){
                        //让出锁给T2执行
                        o.notify();
                        try {
                            //等待T2执行完，获取锁继续向下执行。
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("T1结束");
            }
        },"T1").start();
    }
}
