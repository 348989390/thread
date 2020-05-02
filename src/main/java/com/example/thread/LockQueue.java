package com.example.thread;

import java.util.Stack;

/**
 * JAVA实现一个阻塞队列
 *
 * @param <T>
 */
public class LockQueue<T> {

    //push锁
    private final static Object pushLock = new Object();
    //pop锁
    private final static Object popLock = new Object();

    //数据存储
    private Stack<T> stack;
    //队列最大长度
    private int maxSize = 0;
    //队列最小长度
    private int minSize = 0;

    public LockQueue(int size) {
        this.maxSize = size;
        stack = new Stack<T>();
    }

    public void push(T t) {
        Object o = new Object();
        synchronized (o) {
            if (stack.size() >= maxSize) {
                pushLock();
            }
            stack.push(t);
            popUnLock();
        }
    }

    public T pop() {
        Object o = new Object();
        synchronized (o) {
            if (stack.size() == minSize) {
                popLock();
            }
            T t = stack.pop();
            pushUnLock();
            return t;
        }
    }

    private void pushLock() {
        synchronized (pushLock) {
            try {
                pushLock.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void pushUnLock() {
        synchronized (pushLock) {
            pushLock.notify();
        }
    }

    private void popLock() {
        synchronized (popLock) {
            try {
                popLock.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void popUnLock() {
        synchronized (popLock) {
            popLock.notify();
        }
    }

    public static void main(String[] args) {
        LockQueue<String> queue = new LockQueue<>(10);

        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String s = queue.pop();
                System.out.println("去除数据" + s);
            }
        });

        Thread t2 = new Thread(() -> {
            Integer i = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                queue.push(i + "");
                System.out.println("添加队列信息：" + i);

            }
        });

        t1.start();
        t2.start();


    }
}
