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

    /**
     * 求1+2+3+...+n
     * 要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
     *
     * 思路：
     * 巧用递归，用了短路，使递归结束。
     *
     * @return
     */
    private static int sum(int n) {
        int result = 0;
        /**
         * 利用与运算第一个条件不成立时，那么第二个条件就不会执行的理论，来退出递归
         */
        boolean flag = (n > 0) && (result = sum(n - 1)) > 0; // >0只是为了构建boolean
        result += n;

        return result;
    }

    /**
     * 不用加减乘除做加法
     *
     * 思路：
     * 二进制每位相加就相当于各位做异或操作
     * 进位值，相当于各位做与操作，再向左移一位
     *
     * @param a1
     * @param a2
     * @return
     */
    private static int add(int a1, int a2) {
        int sum = a1; //当a2为0时可以返回a1
        int carry = 0; //进位
        while (a2 != 0) {
            /**
             * 二进制每位相加就相当于各位做异或操作
             */
            sum = a1 ^ a2;

            /**
             * 进位值，相当于各位做与操作，再向左移一位
             */
            carry = (a1 & a2) << 1;

            /**
             * 累计和
             */
            a1 = sum;
            a2 = carry;
        }

        return sum;
    }


    /**
     * 斐波那契数列 (非递归)
     * @param n
     * @return
     */
    private static int fibonacci(int n) {
        int a1 = 0, a2 = 1;

        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = (a1 + a2);
            a1 = a2;
            a2 = result;
        }
        return result;
    }

    /**
     * 跳台阶
     *
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法
     * （先后次序不同算不同的结果）。
     *
     * 思路：斐波拉契数序列，初始条件n=1:只能一种方法，n=2:两种
     * 对于第n个台阶来说，只能从n-1或者n-2的台阶跳上来，所以
     * F(n) = F(n-1) + F(n-2)
     *
     * @param n
     * @return
     */
    private static int jumpOfFloor(int n) {
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            return jumpOfFloor(n - 1) + jumpOfFloor(n - 2);
        }
    }

    /**
     * 变态跳台阶
     *
     *  一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     *
     *  总结是 f(n)=2f(n -1), f(1)=1
     * @param n
     * @return
     */
    private static int jumpOfFloor2(int n) {
        int result = 1;
        for (int i = 1; i <= n - 1; i++) {
            result = result * 2;
        }
        return result;
    }

    /**
     * 数值的整数次方
     *
     * 思路：考虑exponent<0的情况
     *
     * @param base
     * @param exponent
     * @return
     */
    private static double power(double base, int exponent) {

        double result = 1;
        if (exponent < 0) {
            base = 1 / base;
            exponent = -exponent;
        }

        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    public static void test() {
        final int index = 100;
        final int result = getUglyNumber(index);
        Log.i(TAG, "" + index + ": " + result);
    }

    /**
     * 求1+2+3+...+n
     */
    public static void testSum() {
        final int result = sum(5);
        Log.i(TAG, "result: " + result);
    }

    /**
     * 不用加减乘除做加法
     */
    public static void testAdd() {
        final int result = add(2, 4);
        Log.i(TAG, "result: " + result);
    }

    /**
     * 斐波那契数列
     */
    public static void testFibonacci() {
        final int result = fibonacci(5);
        Log.i(TAG, "result: " + result);
    }

    /**
     * 跳台阶
     */
    public static void testJumpOfFloor() {
        final int result = jumpOfFloor(5);
        Log.i(TAG, "result: " + result);
    }


    /**
     * 变态跳台阶
     */
    public static void testJumpOfFloor2() {
        final int result = jumpOfFloor2(5);
        Log.i(TAG, "result: " + result);
    }

    /**
     * 次方
     */
    public static void testPower() {
        final double result = power(3.0f, -3);
        Log.i(TAG, "result: " + result);
    }
}
