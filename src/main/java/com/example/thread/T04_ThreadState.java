package com.example.thread;

public class T04_ThreadState {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(this.getState());

            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("B" + i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
//        Thread t1 = new MyThread();
//        System.out.println(t1.getState());
//
//        t1.start();
//        try {
//            t1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(t1.getState());


        Thread t1 = new T05_Synchronized.t1();
        Thread t2 = new T05_Synchronized.t2();
        t1.start();
        t2.start();
    }
}
