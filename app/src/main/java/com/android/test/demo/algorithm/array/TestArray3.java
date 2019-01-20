package com.android.test.demo.algorithm.array;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;
import com.android.test.demo.algorithm.search.BinarySearchHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TestArray3 {

    private static final String TAG = Algorithms.TAG;


    /**
     *
     * 数组中出现次数超过一半的数字
     *
     * 要找的数字出现的次数超过数组长度的一半，也就是说它出现的次数比其他所有数字出现的次数的和还要多。
     *
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
     * 由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
     * https://www.cnblogs.com/xfxu/p/4583625.html
     *
     * @param array
     * @return
     */
    private static int findHalfMoreOfArray(int[] array) {
        int times = 1;
        int current = array[0];
        for (int i = 1; i < array.length; i++) {
            if (times <= 0) {
                current = array[i];
                times = 1;
            } else if (current == array[i]) {
                times++;
            } else {
                times--;
            }
        }

        times = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == current) {
                times++;
            }
        }
        return (times * 2 > array.length) ? current : 0;
    }

    /**
     * 连续子数组的最大和
     *
     * 给一个数组，返回它的最大连续子序列的和
     *
     * 思路：
     * 基于思想：对于一个数A，若是A的左边累计数非负，那么加上A能使得值不小于A，认为累计值对 整体和是有贡献的。
     * 如果前几项累计值负数，则认为有害于总和，total记录当前值。
     *
     * @param array
     * @return
     */
    private static int findMaxOfSubArray(int array[]) {

        if (array == null || array.length <= 0) {
            return 0;
        }

        int current = array[0];
        int maxTotal = array[0];
        int length = array.length;

        for (int i = 1; i < length; i++) {
            if (current >= 0) {
                current += array[i];
            } else { //负数时，舍弃前面的累计
                current = array[i];
            }
            if (current > maxTotal) {
                maxTotal = current;
            }
        }

        return maxTotal;
    }


    /**
     * 把数组排成最小的数
     *
     * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
     *
     * 思路：
     * 需要重新定义一种比较大小的逻辑（即comparator），用此逻辑来比较两个字符串的大小：如果mn的值>nm的值，
     * 就认为m>n；如果mn的值<nm的值，就认为m<n；如果mn==nm的值，就认为m==n；按照这种逻辑比较m,n两个字符串，
     * 可以将整个数组从小到大排列，于是按照排序后的顺序得到的字符串就是值最小的字符串。
     *
     * https://blog.csdn.net/qq_27703417/article/details/70948904
     *
     * @param array
     * @return
     */
    private static String joinMinNum(int[] array) {
        if (array == null || array.length <= 0) {
            return "";
        }
        final int length = array.length;
        String[] strings = new String[length];
        for (int i = 0; i < length; i++) {
            strings[i] = String.valueOf(array[i]);
        }

        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                final String s1 = o1 + o2;
                final String s2 = o2 + o1;
                return s1.compareTo(s2);
            }
        });

        StringBuilder builder = new StringBuilder();
        for (String value: strings) {
            builder.append(value);
        }

        return builder.toString();
    }

    /**
     * 数字在排序数组中出现的次数
     *
     * 思路：二分查找优化时间复杂度
     *
     * @param array
     * @param target
     * @return
     */
    private static int countOfK(int[] array, int target) {
        if (array == null || array.length <= 0) {
            return 0;
        }

        final int start = BinarySearchHelper.binarySearchOfFirstK(array, target);
        final int end = BinarySearchHelper.binarySearchOfLastK(array, target);

        if (start > -1 && end > -1) {
            return end - start + 1;
        }

        return 0;
    }


    /**
     *
     * 扑克牌顺子
     *
     * 思路：
     * 用数组记录五张扑克牌，将数组调整为有序的，若0出现的次数>=顺子的差值，即为顺子
     *
     * https://blog.csdn.net/wszy1301/article/details/80910626#2.%E6%89%91%E5%85%8B%E7%89%8C%E9%A1%BA%E5%AD%90
     *
     * @param pokers
     * @return
     */
    private static boolean isContinues(int[] pokers) {
        if (pokers == null || pokers.length <= 0) {
            return false;
        }

        int zero = 0; //记录0的个数
        int diff = 0; //空缺的个数

        /**
         * 必须排序
         */
        Arrays.sort(pokers);

        final int length = pokers.length;
        for (int i = 0; i < length - 1; i++) {
            if (pokers[i] == 0) {
                zero++;
                continue;
            }
            if (pokers[i] != pokers[i + 1]) {
                diff += pokers[i + 1] - pokers[i] - 1;
            } else {
                return false; //说明有对子，肯定不是顺子
            }
        }
        return zero >= diff ? true : false;
    }

    /**
     * 约瑟夫环问题
     * @param n
     * @return
     */
    private static int josephus(int n, int k) {
        if (n <= 0) {
            return -1;
        }
        if (k <= 1) {
            return n;
        }

        int last = 0;
        for (int i = 2; i <= n; i++) {
            last = (last + k) % i;
        }
        return last;
    }

    /**
     * 数组中出现次数超过一半的数字
     */
    public static void testFindHalfMoreOfArray() {
        int[] value = new int[] {1, 2, 3, 2, 2, 2, 5, 4, 2}; //正例
        //int[] value = new int[] {1, 2, 3, 2, 2, 3, 3}; //反例
        final int result = findHalfMoreOfArray(value);
        Log.i(TAG, "result: " + result);
    }

    /**
     * 连续子数组的最大和
     */
    public static void testFindMaxOfSubArray() {
        int[] value = new int[] {6, -3, -2, 7, -15, 1, 2, 2};

        final int result = findMaxOfSubArray(value);
        Log.i(TAG, "result: " + result);
    }

    /**
     * 把数组排成最小的数
     */
    public static void testJoinMinNum() {
        final int[] value = new int[] {3, 32, 321};
        final String result = joinMinNum(value);
        Log.i(TAG, "result: " + result);
    }

    /**
     * 数字在排序数组中出现的次数
     */
    public static void testCountOfK() {
        int p[] = {1, 5, 6, 7, 11, 11, 11, 11, 56};
        final int count = countOfK(p, 11);
        Log.i(TAG, "k count: " + count);
    }

    /**
     * 扑克牌顺子
     */
    public static void testIsContinues() {
        int p[] = {5, 3, 1, 4, 0};
        //int p[] = {5, 6, 1, 4, 0};
        boolean continues = isContinues(p);
        Log.i(TAG, "continue: "+ continues);
    }

    /**
     * 约瑟夫环问题
     */
    public static void testJosephus() {
        int result = josephus(5, 3);
        Log.i(TAG, "josephus: " + result);
    }
}
