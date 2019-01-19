package com.android.test.demo.algorithm.queue;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位
 * 于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 *
 * 思路：用两个堆实现，左边是做大堆，右边是最小堆，当数据总数是偶数的时候，中位数是两个堆首元素
 * 和的一半；当数据是奇数的时候，可以约定取最大堆或者是最小堆的首元素。
 *
 * 最大最小堆需要满足的条件：
 * （1）最大最小堆任何时候元素个数之差不能大于1
 * （2）左边最大堆的元素最大元素不能大于右边最小堆的最小元素，以保证数据的有序性。
 */
public class TestQueue1 {
    private static final String TAG = Algorithms.TAG;

    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHead = new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    });

    public void insert(int num) {
        final int size = maxHead.size() + minHeap.size();
        if (size % 2 == 0) { //当前有偶数个，则下一次插入时是奇数,进入最大堆
            /**
             * 为了保证大堆（左）元素比小堆（右）都小
             */
            if (!minHeap.isEmpty() && num > minHeap.peek()) {
                minHeap.offer(num);
                num = minHeap.poll();
            }
            maxHead.offer(num);
        } else {
            if (!maxHead.isEmpty() && num < maxHead.peek()) {
                maxHead.offer(num);
                num = maxHead.poll();
            }
            minHeap.offer(num);
        }
    }

    /**
     *
     * @return
     */
    private double getMedian() {
        final int size = maxHead.size() + minHeap.size();
        if (size <= 0) {
            return -1f;
        }

        if (size % 2 == 1) {
            return maxHead.peek();
        } else {
            return (maxHead.peek() + minHeap.peek()) / 2.0f;
        }
    }


    public static void test() {
        TestQueue1 queue1 = new TestQueue1();

        queue1.insert(1);
        queue1.insert(2);

        double result = queue1.getMedian();
        Log.i(TAG, "median: " + result);

        queue1.insert(5);
        result = queue1.getMedian();
        Log.i(TAG, "median: " + result);

        queue1.insert(7);
        result = queue1.getMedian();
        Log.i(TAG, "median: " + result);
    }
}
