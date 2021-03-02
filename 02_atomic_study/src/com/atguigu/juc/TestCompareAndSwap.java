package com.atguigu.juc;

public class TestCompareAndSwap {

    public static void main(String[] args) {
        final CompareAndSwap compareAndSwap = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    int expectedValue = compareAndSwap.get();
                    boolean b = compareAndSwap.compareAndSet(expectedValue, (int) (Math.random() * 100));
                    System.out.println(b);

                }
            }).start();
        }
    }
}

class CompareAndSwap {

    private int value;

    // 模拟获取内存中的值
    public synchronized int get() {
        return value;
    }

    // 比较
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            this.value = newValue;
        }

        return oldValue;
    }

    // 设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}
