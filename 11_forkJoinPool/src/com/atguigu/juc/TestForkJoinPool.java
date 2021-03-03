package com.atguigu.juc;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoinPool {

    public static void main(String[] args) {
        TestForkJoinPool testForkJoinPool = new TestForkJoinPool();

//        testForkJoinPool.testForkJoin();

//        testForkJoinPool.testFor();

        testForkJoinPool.testJava8();
    }

    public void testForkJoin() {
        Instant start = Instant.now();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinSumCalculate forkJoinSumCalculate = new ForkJoinSumCalculate(0, 1000000000L);
        Long sum = forkJoinPool.invoke(forkJoinSumCalculate);

        Instant end = Instant.now();
        System.out.println(sum);
        System.out.println("耗时:" + Duration.between(start, end).toMillis());
    }

    /**
     * Java 8 新特性
     */
    public void testJava8() {
        Instant start = Instant.now();

        long sum = LongStream.rangeClosed(0L, 1000000000L)
                .parallel()
                .reduce(0L, Long::sum);

        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println("耗时：" + Duration.between(start,end).toMillis());
    }

    public void testFor() {
        Instant start = Instant.now();
        long sum = 0;
        for (long i = 1L; i <= 1000000000L; i++) {
            sum += i;
        }
        Instant end = Instant.now();
        System.out.println(sum);
        System.out.println("耗时：" + Duration.between(start, end).toMillis());

    }
}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private long start;

    private long end;

    private static final long THURSHOLD = 10000L;

    ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = start - end;

        if (length <= THURSHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();    //进行拆分，同时压入线程队列中

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1L, end);
            right.fork();

            return left.join() + right.join();

        }
    }
}
