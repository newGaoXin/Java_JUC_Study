package com.atguigu.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList/CopyOnWriteArraySet : “写入并复制”
 * 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大，并发迭代操作多时可以选择
 */
public class TestCopyOnWriteArrayList {

    public static void main(String[] args) {
        HelloThread helloThread = new HelloThread();
        for (int i = 0; i < 10; i++) {
            new Thread(helloThread).start();
        }
    }
}

class HelloThread implements Runnable{

//    private static List<String> list = Collections.synchronizedList(new ArrayList<String>());

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            list.add("AA");
        }
    }
}
