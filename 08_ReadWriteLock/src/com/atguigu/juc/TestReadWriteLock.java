package com.atguigu.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1.ReadWriteLock : 读写锁
 *
 * 写写/读写 需要“互斥”
 * 读读 不需要互斥
 */
public class TestReadWriteLock {

    public static void main(String[] args) {

        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                readWriteLockDemo.set((int) (Math.random() * 101));
            }
        }, "Write").start();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLockDemo.get();

                }
            }, "Read").start();
        }


    }
}

class ReadWriteLockDemo {

    private int number = 0;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void get() {
        readWriteLock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + " : " + number);
        } finally {
            readWriteLock.readLock().unlock();
        }

    }

    public void set(int number) {
        readWriteLock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
