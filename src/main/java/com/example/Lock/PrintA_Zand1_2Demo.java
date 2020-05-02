package com.example.Lock;

import java.util.concurrent.locks.LockSupport;

/**
 * 面试题：使用两个线程按照A1B2C3...Z26顺序打印字符串
 */
public class PrintA_Zand1_2Demo {

    static int num = 0;
    static int count1 = 0;
    static int count2 = 0;
    static Thread t2 =null,t1=null;
    public static void main(String[] args) {
        String str_letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        char chr_str[] = str_letter.toCharArray();

        //打印数字
        t2 = new Thread(()->{
            while(count2<26){
                LockSupport.park();
                num++;
                System.out.print(num);
                LockSupport.unpark(t1);
                count2++;
            }

        },"T2");

        //打印字母
        t1 = new Thread(()->{
            while(count1<26){
                System.out.print(chr_str[num]);
                LockSupport.unpark(t2);
                count1++;
            }
        },"T1");

        t2.start();
        t1.start();
    }
}
