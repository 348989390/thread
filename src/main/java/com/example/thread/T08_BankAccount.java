package com.example.thread;

import java.util.concurrent.TimeUnit;

/**
 * 写方法加上synchronized,读方法不加synchronized，可能会导致出现脏读
 */
public class T08_BankAccount {

    private String name;

    private Double balance = 0.5;

    public synchronized void set(String name, Double balance) {
        this.name = name;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.balance = balance;
    }

    public synchronized double getBalance(String name) {
        return this.balance;
    }

    public static void main(String[] args) {
        T08_BankAccount account = new T08_BankAccount();

        new Thread(() -> account.set("zhangdan", 20.5)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(account.getBalance("zhangsan"));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account.getBalance("zhangsan"));
    }
}
