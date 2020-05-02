package com.example.thread;

/**
 * DCL单例模式（双重检查）
 */
public class DCLSingle {

    //volatile共享对象值（可见性），防止指令重排序
    private static volatile DCLSingle single = null;

    private DCLSingle() {

    }

    public static DCLSingle getInstance() {
        if (single == null) {
            synchronized (DCLSingle.class) {
                if (single == null) {
                    single = new DCLSingle();
                }
            }
        }
        return single;
    }
}
