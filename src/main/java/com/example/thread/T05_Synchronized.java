package com.example.thread;

public class T05_Synchronized {

    private static int num = 100;

    private static Object o = new Object();

    static class t1 extends Thread {
        @Override
        public void run() {
            /*for (int i=0;i<50;i++){
                num--;
                System.out.println("t1:"+num);
            }*/
            decreament("t1");
        }
    }

    static class t2 extends Thread {
        @Override
        public void run() {
            /*for (int i=0;i<50;i++){
                num--;
                System.out.println("t2:"+num);
            }*/
            decreament("t2");
        }
    }

    public static void decreament(String name) {
        for (int i = 0; i < 50; i++) {
            synchronized ("1") {
                num--;
                System.out.println(name + ":" + num);
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new t1();
        Thread t2 = new t2();

        t1.start();
        t2.start();
    }
}
