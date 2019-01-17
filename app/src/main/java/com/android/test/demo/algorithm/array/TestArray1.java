package com.android.test.demo.algorithm.array;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 滑动窗口的最大值
 *
 * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。
 *
 * 例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
 * 那么一共存在6个滑动窗口：{[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}，
 * {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
 * 他们的最大值分别为{4,4,6,6,6,5}。
 *
 * 思路：
 * 借助一个辅助队列，从头遍历数组，根据如下规则进行入队列或出队列操作：
 * 0. 如果队列为空，则当前数字入队列
 * 1. 如果当前数字大于队列尾，则删除队列尾，直到当前数字小于等于队列尾，或者队列空，然后当前数字入队列
 * 2. 如果当前数字小于队列尾，则当前数字入队列
 * 3. 如果队列头超出滑动窗口范围，则删除队列头
 * 这样能始终保证队列头为当前的最大值
 */
public class TestArray1 {

    private static final String TAG = Algorithms.TAG;

    /**
     *
     * @param values
     * @param windowSize
     * @return
     */
    private static List<Integer> maxInWindows(int[] values, int windowSize) {
        List<Integer> result = new ArrayList<>();

        /**
         * 边界条件
         */
        if (values == null || values.length <= 0
                || windowSize <= 0 || windowSize > values.length) {
            return result;
        }


        /**
         * 使用LinkedList模拟队列
         */
        LinkedList<Integer> queue = new LinkedList<>();
        final int length = values.length;
        for (int i = 0; i < length; i++) {
            if (!queue.isEmpty()) {

                /**
                 * 3. 如果队列头超出滑动窗口范围，则删除队列头
                 */
                if (i >= (queue.peek() + windowSize)) {
                    queue.pop();
                }

                /**
                 * 1. 如果当前数字大于队列尾，则删除队列尾，直到当前数字小于等于队列尾，或者队列空
                 */
                while (!queue.isEmpty() && values[i] >= values[queue.getLast()]) {
                    queue.removeLast();
                }
            }

            /**
             * 0. 1. 2步骤 当前数字入队列
             * 然后当前数字入队列
             */
            queue.offer(i);

            /**
             * 满足条件，打印
             */
            if (i + 1 >= windowSize) {
                result.add(values[queue.peek()]);
            }
        }
        return result;
    }

    public static void test() {
        int[] value = new int[] {2,3,4,2,6,2,5,1};
        List<Integer> result = maxInWindows(value, 3);
        Log.i(TAG, "windows result: " + Arrays.toString(result.toArray()));
    }
}
