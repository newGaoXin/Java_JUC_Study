package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示
 * 如：ABCABCABC... 依次递归
 */
public class TestABCAlternate {

    public static void main(String[] args) {
        AlternateDemo alternateDemo = new AlternateDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 20; i++) {
                    alternateDemo.loopA(i);
                }
            }
        },"A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 20; i++) {
                    alternateDemo.loopB(i);
                }
            }
        },"B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 20; i++) {
                    alternateDemo.loopC(i);
                    System.out.println("---------------");
                }
            }
        },"C").start();

    }
}

class AlternateDemo {

    private int number =1;

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    /**
     *
     * @param totalLoop 循环第几轮
     */
    public void loopA(int totalLoop){
        lock.lock();

        try{
            if (this.number != 1){
                condition1.await();
            }

            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            this.number = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * @param totalLoop 循环第几轮
     */
    public void loopB(int totalLoop){
        lock.lock();

        try{
            if (this.number != 2){
                condition2.await();
            }

            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            this.number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * @param totalLoop 循环第几轮
     */
    public void loopC(int totalLoop){
        lock.lock();

        try{
            if (number != 3){
                condition3.await();
            }

            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
