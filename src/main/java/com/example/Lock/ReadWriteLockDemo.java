package com.example.Lock;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    static ReadWriteLock lock = new ReentrantReadWriteLock();

    Hashtable table = new Hashtable();

    HashMap<String, String> map = new HashMap();

    public static void main(String[] args) {
        lock.writeLock().lock();
    }
}
