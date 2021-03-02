package com.atguigu.juc;

/**
 * 消费者与生产者案例
 */
public class TestProducerAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Product product = new Product(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(product, "生产者 A").start();
        new Thread(consumer, "消费者 B").start();

        new Thread(product, "生产者 C").start();
        new Thread(consumer, "消费者 D").start();
    }
}
//
//class Clerk {
//
//    private int product = 0;
//
//    // 进货
//    public synchronized void get() {
//        while (product >= 1) {  // 为了避免虚假唤醒问题，应该总是使用在循环中
//            System.out.println("产品已满！");
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(Thread.currentThread().getName() + " : " + ++product);
//        this.notifyAll();
//
//    }
//
//    // 销货
//    public synchronized void sale() {
//        while (product <= 0) {
//            System.out.println("缺货！");
//
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(Thread.currentThread().getName() + " : " + --product);
//        this.notifyAll();
//
//
//    }
//}
//
//// 生成者
//class Product implements Runnable {
//
//    private Clerk clerk;
//
//    public Product(Clerk clerk) {
//        this.clerk = clerk;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 20; i++) {
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            clerk.get();
//        }
//    }
//}
//
//class Consumer implements Runnable {
//
//    private Clerk clerk;
//
//    public Consumer(Clerk clerk) {
//        this.clerk = clerk;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 20; i++) {
//            clerk.sale();
//        }
//    }
//}
