package com.android.test.demo.algorithm.stack;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.Stack;

public class TestStack3 {

    private static final String TAG = Algorithms.TAG;

    /**
     * 栈的压入、弹出序列
     *
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。
     * 假设压入栈的所有数字均不相等。
     *
     * 例如序列1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该压栈序列对应的一个弹出序列，
     * 但4,3,5,1,2就不可能是该压栈序列的弹出序列。
     *
     * 思路：
     * 借用一个辅助的栈，遍历压栈顺序
     *
     * @param pushOrder
     * @param popOrder
     * @return
     */
    public static boolean isPopOrder(int[] pushOrder, int[] popOrder) {
        if (pushOrder == null || pushOrder.length <= 0) {
            return false;
        }
        if (pushOrder == null || pushOrder.length <= 0) {
            return false;
        }
        if (pushOrder.length != popOrder.length) {
            return false;
        }
        int index = 0; //出栈顺序的当前下标
        final int length = pushOrder.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < length; i++) {
            stack.push(pushOrder[i]);
            /**
             * 栈顶元素与弹出序列相等则成立，弹出。
             */
            while (!stack.isEmpty() && popOrder[index] == stack.peek()) {
                stack.pop();
                index++;
            }
        }
        return stack.isEmpty();
    }

    /**
     *
     */
    public static void test() {
        int[] pushOrder = {1, 2, 3, 4, 5};
        int[] popOrder = {4, 5, 3, 2, 1};
        final boolean order = isPopOrder(pushOrder, popOrder);
        Log.i(TAG, "order: " + order);
    }
}
