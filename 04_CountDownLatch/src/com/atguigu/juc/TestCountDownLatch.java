package com.atguigu.juc;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch：闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 */
public class TestCountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo(countDownLatch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(countDownLatchDemo).start();
        }
        countDownLatch.await();

        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start));
    }
}

class CountDownLatchDemo implements Runnable {

    private CountDownLatch countDownLatch;

    public CountDownLatchDemo(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 1; i <= 5000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            } finally {
                countDownLatch.countDown();
            }
        }


    }
}
