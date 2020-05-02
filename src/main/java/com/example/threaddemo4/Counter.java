package com.example.threaddemo4;


import java.util.Vector;

public final class Counter {

    private long value = 0;


    /**
     * java监视器模式确保线程安全
     *
     * @return
     */
    public synchronized long getValues() {
        return value;
    }

    public synchronized long increament() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalStateException("count overflow");
        }
        return ++value;
    }


    //==========================
    public static Object getLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;
            return list.get(lastIndex);
        }
    }

    public static void deleteLast(Vector list) {

        synchronized (list) {
            int lastIndex = list.size() - 1;
            list.remove(lastIndex);
        }
    }


}
