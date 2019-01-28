package com.android.test.demo.threads;

import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程按顺序依次打印ABCD
 *
 * 经典面试题
 *
 * 扩展，以前面试迅雷的面试题：
 * 使用4个线程将多行字符写入一个文件，要求一个线程写入一行并保证最终的顺序
 *
 * 思路也是这个原理，每个线程定义一个order
 */
public class TestPrinter {

    private static final String TAG = "Threads";


    public static void test() {
        AtomicInteger currentCount = new AtomicInteger(0);
        int size = 4;

        Thread a = new Thread(new PrinterTask(currentCount, "A", 0, size));
        Thread b = new Thread(new PrinterTask(currentCount, "B", 1, size));
        Thread c = new Thread(new PrinterTask(currentCount, "C", 2, size));
        Thread d = new Thread(new PrinterTask(currentCount, "D", 3, size));

        d.start();
        c.start();
        b.start();
        a.start();
    }



    static class PrinterTask implements Runnable {
        private String word;
        private int size; //线程总数
        private int order; //打印顺序
        private AtomicInteger currentCount;
        private int maxSize = 5;

        PrinterTask(AtomicInteger currentCount, String word, int order, int size) {
            this.currentCount = currentCount;
            this.word = word;
            this.order = order;
            this.size = size;
        }


        @Override
        public void run() {
            while (maxSize > 0) {
                synchronized (currentCount) {
                    /**
                     * 命中线程则打印，否则wait()，等待其他线程唤醒
                     */
                    if (currentCount.get() % size == order) {
                        logI(TAG, word + " - " + currentCount.get());
                        currentCount.getAndAdd(1); //原子操作

                        currentCount.notifyAll();
                        maxSize--;

                        /**
                         * 测试用模拟耗时，可以去掉
                         */
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                        }
                    } else {
                        try {
                            currentCount.wait();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }

    public static void logI(String tag, String message) {
        logI(tag, message, null);
    }

    /**
     * 日志增加线程信息
     * @param tag
     * @param message
     * @param t
     */
    public static void logI(String tag, String message, Throwable t) {
        Log.i(tag, "[" + Thread.currentThread().getId() + "]" + message, t);
    }
}
