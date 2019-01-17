package com.android.test.demo.algorithm.sort;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.Arrays;

/**
 * 堆排序算法
 */
public class HeapSort {
    private static final String TAG = Algorithms.TAG;


    /**
     * 排序
     * @param values
     */
    public static void sort(int[] values) {
        sort(values, values != null ? values.length : null);
    }

    /**
     * 排序
     * @param values
     */
    public static void sort(int[] values, int length) {
        if (values == null || length < 2) {
            return;
        }

        //建堆操作
        initHeap(values, length);

        for (int i = length - 1; i >= 1; i--) {
            //把最大元素放到最后一个位置
            final int value = values[0];
            values[0] = values[i];
            values[i] = value;

            //交换位置后，要继续调整使其满足堆的特性
            adjustNode(values, 0, i);
        }

    }

    /**
     * 找最小的k个数
     * @param values
     * @param k
     */
    public static void findKthNum(int[] values, int k) {
        if (values == null) {
            return;
        }
        final int length = values.length;
        if (k >= length || length <2) {
            return;
        }
        //建立大小为k的大堆
        initHeap(values, k);

        /**
         * 核心实现：把接下来剩余元素分别与堆顶元素比较，若比堆顶小（堆顶是堆最大的元素），
         * 则将该元素放在堆顶，并调整堆。
         */
        int value;
        for (int i = k ; i < length; i++) {
            if (values[i] < values[0]) {
                value = values[0];
                values[0] = values[i];
                values[i] = value;
                adjustNode(values, 0, k);
            }
        }
    }

    /**
     * 建堆
     * @param values
     */
    private static void initHeap(int[] values, int length) {
        if (values == null || length < 2) {
            return;
        }
        //建立大顶堆：从最后一个非叶子节点开始：length/2-1
        for (int i = length / 2 - 1; i >= 0; i--) {
            adjustNode(values, i, length);
        }
    }

    /**
     * 加入新元素后调整堆
     * @param values: 排序数组
     * @param i：起始位置
     * @param length：长度
     */
    private static void adjustNode(int[] values, int i, int length) {
        final int top = values[i];
        //2*i+1表示左孩子
        for (int k = 2 * i + 1; k < length; k = 2 * i + 1) {
            //右孩子是不是比左孩子大
            if (k + 1 < length && values[k + 1] > values[k]) {
                k++;
            }
            //左右孩子比父节点大，则交换位置
            if (values[k] > top) {
                values[i] = values[k];
                i = k; //孩子节点变成下次调整的父节点
            } else { //满足大堆特性，退出
                break;
            }
        }
        values[i] = top;
    }


    /**
     * result: [-30, -1, 9, 10, 20, 100]
     */
    public static void test() {
        int[] values = new int[] {9, 10, -1, 20, -30, 100};
        sort(values);

        Log.i(TAG, "result: " + Arrays.toString(values));
    }

    /**
     * 找最小的k个数
     */
    public static void testKthNum() {
        int[] values = new int[] {9, 10, -1, 20, -30, 100, -40, 1, -10};
        findKthNum(values, 3);
        Log.i(TAG, "result: " + Arrays.toString(values));
    }
}
