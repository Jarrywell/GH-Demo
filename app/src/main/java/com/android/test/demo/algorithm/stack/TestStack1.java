package com.android.test.demo.algorithm.stack;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.Stack;

/**
 * 用两个栈实现队列
 *
 * 思路：入栈给stack1，出栈时，若stack2不为空，则出栈，若为空，把stack1的内容全都放入stack2，然后再出栈
 *
 * @param <T>
 */
public class TestStack1<T> {

    private static final String TAG = Algorithms.TAG;

    private Stack<T> mStack1 = new Stack<>();
    private Stack<T> mStack2 = new Stack<>();

    public void push(T e) {
        mStack1.push(e);
    }

    public T pop() {
        if (mStack2.isEmpty()) {
            while (!mStack1.isEmpty()) {
                mStack2.push(mStack1.pop());
            }
        }
        return mStack2.isEmpty() ? null : mStack2.pop();
    }

    /**
     * 用两个栈实现队列
     */
    public static void test() {
        TestStack1<Integer> testStack = new TestStack1<>();

        testStack.push(1);
        testStack.push(2);

        int result = testStack.pop();
        Log.i(TAG, "result: " + result);

        testStack.push(3);

        Log.i(TAG, "result: " + testStack.pop());
        Log.i(TAG, "result: " + testStack.pop());
        //Log.i(TAG, "result: " + testStack.pop());

    }
}
