package com.example.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();


    public static void main(String[] args) {
        Thread td = new Thread(() -> {
            System.out.println("Condition.await()");
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        td.start();
    }
}
