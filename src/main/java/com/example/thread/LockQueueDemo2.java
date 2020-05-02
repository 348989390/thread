package com.example.thread;

import java.util.LinkedList;

/**
 *
 */
public class LockQueueDemo2<T> {

    final private LinkedList list = new LinkedList();
    final private int MAX = 10;
    private int count =0;

    public synchronized void put(T t){
        while(list.size()==MAX){//队列已满

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.add(t);
        count++;
        this.notifyAll();
    }

    public synchronized  T get(){
        T t =null;
        while(list.size()==0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        t = (T) list.removeFirst();
        count--;
        this.notifyAll();
        return t;
    }

    public static void main(String[] args) {
        LockQueueDemo2<String> c = new LockQueueDemo2<>();

        for(int i=0;i<2;i++){
            new Thread(()->{
                for(int j=0;j<25;j++){
                    c.put(Thread.currentThread().getName()+""+j);
                }
            },"productor"+i+":").start();
        }

        for(int i=0;i<10;i++){
            new Thread(()->{
                for(int j=0;j<5;j++){
                    System.out.println(c.get());
                }
            },"consumer"+i+":").start();
        }
    }
}
