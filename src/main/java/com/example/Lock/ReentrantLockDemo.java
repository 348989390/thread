package com.example.Lock;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 手写ReentrantLock，加锁，解锁
 */
public class ReentrantLockDemo {

    //锁持有者
    AtomicReference<Thread> owner = new AtomicReference<>();
    //等待队列
    LinkedBlockingDeque<Thread> blockingDeque = new LinkedBlockingDeque();
    //
    AtomicInteger count = new AtomicInteger();

    public void lock() {
        if (!trylock()) {//抢锁失败
            blockingDeque.offer(Thread.currentThread());
            for (; ; ) {
                Thread thread = blockingDeque.peek();
                if (owner.get() == thread) {
                    //当前线程在队列头
                    if (!trylock()) {
                        //抢锁失败，挂起线程，继续等待
                        LockSupport.park();
                    } else {
                        //抢锁成功、出队列
                        blockingDeque.poll();
                        return;
                    }
                } else {
                    //当前线程不在队列头
                    LockSupport.park();
                }
            }
        }
    }

    public boolean trylock() {
        int ct = count.get();
        //判断count是否为0
        if (ct != 0) {//有人持有锁
            //判断持有锁的线程，是不是当前线程
            if (owner.get() == Thread.currentThread()) {
                //当前线程持有锁，做重入操作
                count.set(ct + 1);
                return true;
            } else {//不是当前线程持有锁,抢锁失败
                return false;
            }
        } else {
            //若count=0,进行cas操作抢锁
            if (count.compareAndSet(ct, ct + 1)) {
                //抢锁成功，owner设置为当前线程ID
                owner.set(Thread.currentThread());
                return true;
            } else {
                //cas操作失败，抢锁失败
                return false;
            }
        }
    }

    public boolean tryunlock() {
        //判断，是否是当前线程占有锁
        if (owner.get() == Thread.currentThread()) {
            int ct = count.get();
            int nextct = ct - 1;
            count.set(nextct);
            //判断count值是否是0，
            if (nextct == 0) {
                owner.compareAndSet(Thread.currentThread(), null);
                return true;
            } else {
                return false;
            }
        } else {
            //不是当前线程占有锁
            throw new IllegalMonitorStateException();
        }

    }

    public void unlock() {
        if (tryunlock()) {
            //解锁成功
            Thread th = blockingDeque.peek();
            if (th != null) {
                LockSupport.unpark(th);
            }
        }
    }


    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }

}
