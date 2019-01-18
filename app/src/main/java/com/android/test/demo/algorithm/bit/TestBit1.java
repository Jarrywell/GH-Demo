package com.android.test.demo.algorithm.bit;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

public class TestBit1 {
    private static final String TAG = Algorithms.TAG;

    /**
     * 测试1的个数
     */
    public static void test1Count() {
        final int value = 16;
        final int result = countOf1(value);
        Log.i(TAG, "value: " + value + ", count of 1: " + result);

    }

    public static void test0Count() {
        final int value = 3;
        final int result = countOf0(value);
        Log.i(TAG, "value: " + value + ", count of 0: " + result);
    }

    public static void testHigh0Count() {
        final int value = 4;
        final int result = countOf0High(value);
        Log.i(TAG, "value: " + value + ", count high of 0: " + result);
    }

    /**
     * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
     *
     * output: value: 16, count of 1: 1
     * @param value
     * @return
     */
    private static int countOf1(int value) {
        int count = 0;
        while (value > 0) {
            /**
             * n&(n-1)操作相当于把二进制表示中最右边的1变成0。所以只需要看看进行了多少次这样的操作即可。
             */
            value = value & (value - 1);
            count++;
        }
        return count;
    }

    /**
     * 输入一个整数，输出该数二进制表示中0的个数。其中负数用补码表示。
     *
     * output: value: 3, count of 0: 30
     * @param value
     * @return
     */
    private static int countOf0(int value) {
        int count = 0;
        while (value > 0) {
            /**
             * n|(n+1)消除所有0，变成1
             */
            value = value | (value + 1);
            count++;
        }
        return count;
    }

    /**
     * 获得变量二进制表示中高位连续0的个数
     * output: value: 4, count high of 0: 29
     * @param value
     * @return
     */
    private static int countOf0High(int value) {
        int count = 0;
        final int mask = 0x80000000;
        int j = value & mask;
        while (j == 0) {
            count++;

            value <<= 1;

            j = value & mask;
        }

        return count;
    }


}
