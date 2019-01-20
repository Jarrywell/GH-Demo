package com.android.test.demo.algorithm.num;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

public class TestNum2 {
    private static final String TAG = Algorithms.TAG;

    /**
     *机器人运动范围
     *
     * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，
     * 但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,37），
     * 因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
     *
     * https://blog.csdn.net/wszy1301/article/details/80910626#2%E3%80%81%E6%9C%BA%E5%99%A8%E4%BA%BA%E8%BF%90%E5%8A%A8%E8%8C%83%E5%9B%B4
     *
     * @param rows
     * @param cols
     * @param threshold
     * @return
     */
    private static int movingCount(int rows, int cols, int threshold) {
        int[][] flag = new int[rows][cols];
        return movingCount(0, 0, rows, cols, threshold, flag);
    }

    private static int movingCount(int i, int j, int rows, int cols, int threshold, int[][] flag) {
        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            return 0;
        }
        if (sum(i) + sum(j) > threshold || flag[i][j] == 1) {
            return 0;
        }

        flag[i][j] = 1;

        return 1 + movingCount(i - 1, j, rows, cols, threshold, flag) //左
                + movingCount(i, j - 1, rows, cols, threshold, flag) //上
                + movingCount(i + 1, j, rows, cols, threshold, flag) //右
                + movingCount(i, j + 1, rows, cols, threshold, flag); //下
    }

    private static int sum(int n) {
        int result = 0;
        while (n != 0) {
            result += n % 10;
            n = n / 10;
        }
        return result;
    }

    /**
     * 机器人运动范围
     */
    public static void testMovingCount() {
        int result = movingCount(100, 100, 8);
        Log.i(TAG, "movingCount: " + result);
    }
}
