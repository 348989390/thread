package com.example.thread;

/**
 * synchronized可重入 方法1中可以调用方法2，不会产生死锁
 * 子类调用父类synchronized方法时，可重入
 */
public class T09_synchronizedCR {
    synchronized void method1() {
        System.out.println("m1 start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        method2();
        System.out.println("m1 end");
    }

    synchronized void method2() {
        System.out.println("m2 start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 end");
    }

    public static void main(String[] args) {
//        new T09_synchronizedCR().method1();
        new tt().method3();
    }
}

class tt extends T09_synchronizedCR {
    synchronized void method3() {
        System.out.println("child start");
        super.method1();
        System.out.println("child end");
    }
}
