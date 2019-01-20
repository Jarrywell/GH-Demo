package com.android.test.demo.algorithm.stack;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.Stack;

/**
 * 包含min函数的栈
 * 实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））
 */
public class TestStack2<T extends Comparable> {
    private static final String TAG = Algorithms.TAG;

    Stack<T> mStack = new Stack<>();
    Stack<T> mStackMin =new Stack<>();

    public void push(T e) {
        mStack.push(e);
        if (mStackMin.isEmpty() || e.compareTo(mStackMin.peek()) < 0) {
            mStackMin.push(e);
        } else {
            mStackMin.push(mStackMin.peek());
        }
    }

    public T pop() {
        if (!isEmpty()) {
            mStackMin.pop();
            return mStack.pop();
        }
        return null;
    }

    public T min() {
        return mStackMin.peek();
    }

    public boolean isEmpty() {
        return mStack.isEmpty();
    }

    /**
     * 包含min函数的栈
     */
    public static void test() {
        TestStack2<Integer> testStack2 = new TestStack2<>();
        testStack2.push(5);
        Log.i(TAG, "5 min: " + testStack2.min());
        testStack2.push(4);
        Log.i(TAG, "4 min: " + testStack2.min());
        testStack2.push(3);
        Log.i(TAG, "3 min: " + testStack2.min());
        testStack2.push(8);
        Log.i(TAG, "8 min: " + testStack2.min());
        testStack2.push(10);
        Log.i(TAG, "10 min: " + testStack2.min());
        testStack2.push(11);
        Log.i(TAG, "11 min: " + testStack2.min());
        testStack2.push(12);
        Log.i(TAG, "12 min: " + testStack2.min());
        testStack2.push(1);
        Log.i(TAG, "1 min: " + testStack2.min());

        /*while (!testStack2.isEmpty()) {
            Log.i(TAG, "min: " + testStack2.min());
            testStack2.pop();
        }*/
    }
}
