package com.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 不用JUC手写阻塞队列，实现生产者、消费者问题。
 */
public class BlockQueue {

    private List<String> list = new ArrayList<>();
    private int listSize;
    private Object lock = new Object();
    private Object lock2 = new Object();

    //通过构造初始化list 大小
    public BlockQueue(int size) {
        this.listSize = size;
        System.out.println(Thread.currentThread().getName() + "初始化 完成 list 大小为：" + listSize);
    }

    public void put(String name) {
        synchronized (lock) {
            while (list.size() == listSize) {
                //队列已经满了
                System.out.println(Thread.currentThread().getName() + ":当前队列已经满了 需要等待...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(name);
            System.out.println(Thread.currentThread().getName() + ": put 放入队列中的元素 " + name);
            //唤醒其他所有 队列存或者取数据
            lock.notifyAll();

        }
    }

    public String get() {
        synchronized (lock) {
            while (list.size() == 0) {
                //说明当前队列已经空了 需要等待
                System.out.println(Thread.currentThread().getName() + ":当前队列已空，需要等待放入元素...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            String name = list.get(0);
            list.remove(0);
            //唤醒其他所有 队列存或者取数据
            lock.notify();
            System.out.println(Thread.currentThread().getName() + ": 获取到队列数据 " + name);
            return name;
        }
    }

    public static void main(String[] args) {
        BlockQueue queue = new BlockQueue(3);

        //两个生产者
        for(int i=0;i<2;i++){
            new Thread(() -> {
                AtomicInteger num = new AtomicInteger();
                for(int j=0;j<25;j++){
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    queue.put(num + "");
                    num.incrementAndGet();
                }
            }, "producter"+i).start();
        }

        //10个消费者
        for(int i=0;i<10;i++){
            new Thread(() -> {
                for(int j=0;j<5;j++){
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    queue.get();
                }

            }, "consumer"+i).start();
        }

    }


}