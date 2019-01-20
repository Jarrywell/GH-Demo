package com.android.test.demo.algorithm.sort;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.Arrays;
import java.util.Stack;

/**
 * 快速排序
 */
public class QuickSort {
    private static final String TAG = Algorithms.TAG;

    /**
     * 快排入口
     * @param array
     */
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        /**
         * 左右指针法
         */
        //sort(array, 0, array.length - 1);

        /**
         * 挖坑法
         */
        //sort2(array, 0, array.length - 1);


        /**
         * 非递归实现
         */
        sort3(array, 0, array.length - 1);
    }

    /**
     * 左右指针法
     * @param array
     * @param low
     * @param high
     */
    private static void sort(int[] array, int low, int high) {
        /**
         * 找到阈值后与第一个元素交换（优化点，可不要）
         */
        final int index = getThresholdIndex(array, low, high);
        swap(array, low, index);

        int base = array[low];

        int start = low;
        int end = high;
        while (start < end) {

            /**
             * 从后往前扫描
             */
            while (start < end && array[end] >= base) {
                end--;
            }
            if (array[end] < base) {
                swap(array, start, end);
            }

            /**
             * 从前往后扫描
             */
            while (start < end && array[start] <= base) {
                start++;
            }
            if (array[start] > base) {
                swap(array, start, end);
            }
        }

        if (start > low) {
            sort(array, low, start - 1);
        }

        if (end < high) {
            sort(array, end + 1, high);
        }
    }

    /**
     * 挖坑法
     * @param array
     * @param low
     * @param high
     */
    private static void sort2(int[] array, int low, int high) {
        /**
         * 找到阈值后与第一个元素交换（优化点，可不要）
         */
        final int index = getThresholdIndex(array, low, high);
        swap(array, low, index);

        int base = array[low];

        int start = low;
        int end = high;
        while (start < end) {

            /**
             * 从后往前扫描
             */
            while (start < end && array[end] >= base) {
                end--;
            }
            array[start] = array[end];

            /**
             * 从前往后扫描
             */
            while (start < end && array[start] <= base) {
                start++;
            }
            array[end] = array[start];

        }

        array[start] = base;

        if (end < high) {
            sort2(array, end + 1, high);
        }

        if (start > low) {
            sort2(array, low, start - 1);
        }

    }

    /**
     * 非递归方式实现快排（用栈模拟）
     * @param array
     * @param low
     * @param high
     */
    private static void sort3(int[] array, int low, int high) {
        Stack<Integer> stack = new Stack<>();
        if (low < high) {
            stack.push(high);
            stack.push(low);
            while (!stack.isEmpty()) {
                low = stack.pop();
                high = stack.pop();

                /**
                 * 找到阈值后与第一个元素交换（优化点，可不要）
                 */
                final int index = getThresholdIndex(array, low, high);
                swap(array, low, index);

                int base = array[low];

                int start = low;
                int end = high;
                while (start < end) {

                    /**
                     * 从后往前扫描
                     */
                    while (start < end && array[end] >= base) {
                        end--;
                    }
                    array[start] = array[end];

                    /**
                     * 从前往后扫描
                     */
                    while (start < end && array[start] <= base) {
                        start++;
                    }
                    array[end] = array[start];

                }
                array[start] = base;

                if (end < high) {
                    stack.push(high);
                    stack.push(end + 1);
                }

                if (start > low) {
                    stack.push(start - 1);
                    stack.push(low);
                }
            }
        }
    }

    /**
     * 交换位置
     * @param array
     * @param start
     * @param end
     */
    private static void swap(int[] array, int start, int end) {
        int c = array[start]; array[start] = array[end]; array[end] = c;
    }


    /**
     * 3数取中间值，保证快排枢轴不在两端
     * @param array
     * @param low
     * @param high
     * @return
     */
    private static int getThresholdIndex(int[] array, int low, int high) {
        final int mid = (low + high) >> 1;

        if (array[low] < array[high]) {
            if (array[mid] < array[low]) {
                return low;
            } else if (array[mid] > array[high]) {
                return high;
            } else {
                return mid;
            }
        } else {
            if (array[mid] < array[high]) {
                return high;
            } else if (array[mid] > array[low]) {
                return low;
            } else {
                return mid;
            }
        }
    }



    /**
     * 快速排序
     */
    public static void test() {
        int[] array = {46, 30, 82, 90, 56, 17, 95, 15, 17};
        sort(array);
        Log.i(TAG, "quick sort: " + Arrays.toString(array));
    }
}
