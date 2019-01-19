package com.android.test.demo.algorithm.array;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.Arrays;

public class TestArray2 {

    private static final String TAG = Algorithms.TAG;

    /**
     * 找数组中重复的数字
     *
     * 描述
     * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道有几个数字是重复的。
     * 也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
     *
     * 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2
     *
     * 思路：
     * 0~n-1正常的排序应该是A[i]=i；因此可以通过交换的方式，将它们都各自放回属于自己的位置；
     *
     * 时间复杂度：O(n)，空间复杂度：O(1)
     *
     * @param values
     * @param result 返回值
     * @return 找到返回true,其他情况返回false
     */

    private static boolean duplicate(int[] values, int[] result) {
        if (values == null || values.length <= 0) {
            return false;
        }
        /**
         * 边界条件，一定要是在0～n-1范围
         */
        final int length = values.length;
        for (int i = 0; i < length; i++) {
            if (values[i] < 0 || values[i] > length - 1) {
                return false;
            }
        }

        for (int i = 0; i < length; i++) {
            /**
             * 首先比较这个数字是不是等于i
             */
            while (i != values[i]) {
                /**
                 * 再判断它和A[m]是否相等，如果是，则找到了第一个重复的数字（在下标为i和m的位置都出现了m）；
                 * 如果不是，则把A[i]和A[m]交换，即把m放回属于它的位置；
                 */
                if (values[i] == values[values[i]]) {
                    result[0] = values[i];
                    return true;
                } else {
                    int temp = values[i];
                    values[i] = values[temp];
                    values[temp] = temp;
                }
            }
        }
        return false;
    }


    /**
     * 构建乘积数组
     *
     * https://blog.csdn.net/wszy1301/article/details/80910626#2.%E6%9E%84%E5%BB%BA%E4%B9%98%E7%A7%AF%E6%95%B0%E7%BB%84
     *
     * 给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],
     * 其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。不能使用除法。
     *
     * 思路：用矩阵的方式，先计算左下三角，再计算右上三角。根据图来分析即可。
     *
     *新建一个新数组B， 对A数组i项左侧自上往下累乘，对A数组i项右侧自下往上累乘 时间复杂度O(n)
     *
     * @param A
     * @return
     */
    private static int[] multiply(int[] A) {
        if (A == null || A.length <= 0) {
            return A;
        }

        final int length = A.length;
        int[] B = new int[length];
        B[0] = 1;

        /**
         * 先计算左下三角形，此时B[0]只有一个元素，舍为1
         */
        for (int i = 1; i < length; i++) {
            B[i] = B[i - 1] * A[i - 1];
        }

        /**
         * 计算右上三角形
         */
        int temp = 1;
        for (int i = length - 1; i >= 0; i--) {
            /**
             * 最终的B[i]是之前乘好的再乘以右边的
             */
            B[i] = B[i] * temp;
            temp = temp * A[i];
        }
        return B;
    }

    /**
     *
     * 二维数组的查找
     *
     * 描述：
     * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，
     * 输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     *
     * 思路：
     *
     * 那么选取右上角的元素a[row][col]与target进行比较，
     * 当target小于元素a[row][col]时，那么target必定在元素a所在行的左边,
     * 即col--；
     * 当target大于元素a[row][col]时，那么target必定在元素a所在列的下边,
     * 即row++；

     * @param array
     * @param target
     * @return
     */
    private static boolean findOf2Array(int[][] array, int target) {
        int row = 0; //行
        int col = array[0].length - 1; //初始为右上角坐标

        while (row < array.length && col >= 0) {
            if (array[row][col] == target) {
                return true;
            } else if (target < array[row][col]) {
                col--;
            } else {
                row++;
            }
        }

        return false;
    }

    /**
     *
     * 数组中只出现一次的数字
     *
     * 描述：
     * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
     *
     * 参考思路
     * 当只有一个数出现一次时，我们把数组中所有的数，依次异或运算，最后剩下的就是落单的数，因为成对儿出现的都抵消了。
     * @param array
     * @param num1
     * @param num2
     */
    private static void findOfOnce(int[] array, int num1[], int[] num2) {
        int exp = 0;
        for (int num : array) {
            exp = exp ^ num;
        }

        /**
         * -exp和~exp + 1是等价的，补码表示负数，补码等于：取反加1
         */
        //int split = exp & (~exp + 1); //求得二进制中第一位1
        int split = exp & (-exp); //求得二进制中第一位1

        for (int num : array) {
            if ((num & split) != 0) {
                num1[0] = num1[0] ^ num;
            } else {
                num2[0] = num2[0] ^ num;
            }
        }
    }

    /**
     * 找数组中重复的数字
     */
    public static void testDuplicate() {
        int[] values = new int[] {2, 3, 1, 0, 2, 5, 3};
        int[] result = new int[] {-1};

        final boolean duplicate = duplicate(values, result);
        Log.i(TAG, "duplicate: " + duplicate + ", value: " + result[0]);

    }

    /**
     * 构建乘积数组
     */
    public static void testMultiply() {
        int[] A = new int[] {2, 3, 1, 3, 2, 5, 3};
        int[] B = multiply(A);
        Log.i(TAG, "multiply: " + Arrays.toString(B));
    }

    /**
     * 二维数组的查找
     */
    public static void testFindOf2Array() {
        int[][] array = {
                {1, 2, 8, 9},
                {2, 4, 9, 12},
                {4, 7, 10, 13},
                {6, 8, 11, 15}};

        final boolean result = findOf2Array(array, 3);
        Log.i(TAG, "find result: " + result);
    }

    /**
     * 数组中只出现一次的数字
     */
    public static void testFindOnce() {
        int[] value = new int[] {2, 5, 6, 7, 6, 7, 8, 9, 8, 9};
        int[] num1 = new int[] {0};
        int[] num2 = new int[] {0};
        findOfOnce(value, num1, num2);
        Log.i(TAG, "num1: " + num1[0] + ", num2: " + num2[0]);
    }
}
