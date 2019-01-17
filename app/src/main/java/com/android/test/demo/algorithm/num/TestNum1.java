package com.android.test.demo.algorithm.num;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

public class TestNum1 {

    private static final String TAG = Algorithms.TAG;

    /**
     * 找第index个丑数
     * 一个数的因子仅仅包括2，3，5的数称为丑数。数字1特别对待也看作是丑数，
     * 所以从1開始的10个丑数分别为1。2。3。4，5，6，8，9。10。12。
     *
     * 思路：下一个丑数一定是前面某个丑数乘以2、3或5的结果，乘数的结果分别为M2,M3,M5,取其中最小的结果为下一个丑数。
     *
     * 取一个数组来放丑数，下一个丑数必定是这个数组里面的数乘以2或者3或者5，这三种中最小的那一个。依次，判断下去。
     *
     * @param index
     * @return
     */
    public static int getUglyNumber(int index) {
        if (index <= 0) {
            return 0;
        }
        int[] result = new int[index];
        result[0] = 1; //初始化时，设置1是丑数，后面通过1递推求出
        int m2 = 0, m3 = 0, m5 = 0; //命中下标下标
        int resM2, resM3, resM5; //存放乘数结果
        for (int i = 1; i < index; i++) {
            resM2 = result[m2] * 2;
            resM3 = result[m3] * 3;
            resM5 = result[m5] * 5;
            result[i] = Math.min(resM2, Math.min(resM3, resM5));
            /**
             * 判断是由谁乘以得到的
             * 有多个相等时也需要多个移动
             */
            if (resM2 == result[i]) {
                m2++;
            }
            if (resM3 == result[i]) {
                m3++;
            }
            if (resM5 == result[i]) {
                m5++;
            }
            //Log.i(TAG, "value: " + result[i]);
        }
        return result[index - 1];
    }

    public static void test() {
        final int index = 100;
        final int result = getUglyNumber(index);
        Log.i(TAG, "" + index + ": " + result);
    }
}
