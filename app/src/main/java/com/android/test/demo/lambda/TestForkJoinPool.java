package com.android.test.demo.lambda;

import android.util.Log;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * des:
 * author: libingyan
 * Date: 18-3-9 11:30
 */
public class TestForkJoinPool {
    private final static String TAG = "TestForkJoinPool";

    private final static int THREAD_COUNT = 5;

    public static void test() {
        TestForkJoinPool forkJoinPool = new TestForkJoinPool();
       // forkJoinPool.testRecursiveAction();
        //forkJoinPool.testRecursiveTask();
        forkJoinPool.testSortTask();
    }

    private void testRecursiveAction() {
        PrintTask task = new PrintTask(1, 40);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4); //指定线程数
        Log.i(TAG, Thread.currentThread().getName() + ": " + "start");
        Future result = forkJoinPool.submit(task);
        try {
            /**
             * 注意如果task是不带返回值时, get()不会阻塞，会立即返回
             */
            Log.i(TAG, "Future.get() before");
            result.get();
            Log.i(TAG, "Future.get() after");
        } catch (Exception e) {
            Log.i(TAG, "exception", e);
        }
    }

    /**
     * 测试带返回值的任务
     */
    private void testRecursiveTask() {
        final int count = 200;
        int[] array = new int[count];
        int total = 0;
        final Random random = new Random();
        for (int i = 0; i < count; i++) {
            total += (array[i] = random.nextInt(20));
        }
        Log.i(TAG, "sequential total: " + total);

        /*ForkJoinPool是ExecutorService的子类*/
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumTask task = new SumTask(array, 0, array.length);
        Future<Integer> result = forkJoinPool.submit(task);
        int parallelTotal = 0;
        try {
            /**
             * 获取子任务的结果，注意如果task是带返回值时, get()会阻塞值到运算出结果
             */
            Log.i(TAG, "Future.get() before");
            parallelTotal = result.get();
            Log.i(TAG, "Future.get() after: " + task.isCompletedAbnormally());
        } catch (Exception e) {
            Log.i(TAG, "exception", e);
        }
        Log.i(TAG, "parallel total: " + parallelTotal);
    }

    private void testSortTask() {
        final int count = 500;
        int[] array = new int[count];
        final Random random = new Random();
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < count; i++) {
            array[i] = random.nextInt(200);
            builder.append(array[i]).append(",");
        }
        Log.i(TAG, "start: " + builder.toString());
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SortTask task = new SortTask(array, 0, array.length);
        Future<int[]> result = forkJoinPool.submit(task);
        try {
            /**
             * 获取子任务的结果，注意如果task是带返回值时, get()会阻塞值到运算出结果
             */
            Log.i(TAG, "Future.get() before");
            int[] sortResult = result.get();
            Log.i(TAG, "Future.get() after: " + task.isCompletedAbnormally());
            StringBuilder sortBuilder = new StringBuilder("");
            for (int i = 0; i < count; i++) {
                sortBuilder.append(sortResult[i]).append(",");
            }
            Log.i(TAG, "end: " + sortBuilder.toString());
        } catch (Exception e) {
            Log.i(TAG, "exception", e);
        }
    }



    /**
     * 没有返回值的task写法
     */
    private class PrintTask extends RecursiveAction {

        private int start, end;

        private PrintTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start < THREAD_COUNT) {
                for (int i = start; i < end; i++) {
                    Log.i(TAG, Thread.currentThread().getName() + ": " + i);
                }
                try {
                    Thread.sleep(100); //模拟耗时操作
                } catch (Exception e) {
                }
            } else {
                final int mid = (start + end) / 2;
                PrintTask left = new PrintTask(start, mid);
                //left.fork();

                PrintTask right = new PrintTask(mid, end);
                //right.fork();

                invokeAll(left, right);
            }
        }
    }


    /**
     * 带返回值的task写法
     */
    private class SumTask extends RecursiveTask<Integer> {
        private int[] array;
        private int start, end;

        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start < THREAD_COUNT) {
                int sum = 0;
                Log.i(TAG, Thread.currentThread().getName() + " do sum( " + start + "," + end + ")");
                for (int i = start; i < end; i++) {
                    try {
                        Thread.sleep(100); //模拟耗时操作
                    } catch (Exception e) {
                    }
                    sum += array[i];
                }
                /**
                 * 模拟异常发生
                 */
                /*if (true) {
                    throw new NullPointerException("test");
                }*/
                return sum;
            } else { //开始拆分子任务
                final int mid = (start + end) / 2;
                SumTask left = new SumTask(array, start, mid);

                SumTask right = new SumTask(array, mid, end);

                invokeAll(left, right);

                return left.join() + right.join(); //返回子任务的计算结果
            }
        }
    }

    /**
     * 模拟排序
     */
    private class SortTask extends RecursiveTask<int[]> {
        private int[] array;
        private int start, end;
        public SortTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected int[] compute() {
            if (end - start < THREAD_COUNT) {
                Arrays.sort(array, start, end);  //模拟小任务排序
                return array;
            } else {
                final int mid = (start + end) / 2;
                SortTask left = new SortTask(array, start, mid);
                left.fork();

                SortTask right = new SortTask(array, mid, end);
                right.fork();

                left.join();
                right.join();

                Arrays.sort(array, start, end); //模拟合并后两个数组排序

                return array;
            }
        }
    }
}
