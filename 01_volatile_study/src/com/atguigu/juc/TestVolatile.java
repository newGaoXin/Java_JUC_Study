package com.atguigu.juc;

/**
 * 一、volatile 关键字：当多个线程进行操作共享数据时，可以保证内容中的数据可见。
 *                      相较于 synchronized 是一种较为轻量级的同步策略
 *
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性”
 */
public class TestVolatile {

    public static void main(String[] args) {
        TreadDemo treadDemo = new TreadDemo();
        new Thread(treadDemo).start();

        while (true){
            if (treadDemo.getFlag()) {
                System.out.println("-------------");
                break;
            }
        }

    }


}

class TreadDemo implements Runnable {

    private volatile Boolean flag = false;
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;
        System.out.println("flag=" + getFlag());
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
