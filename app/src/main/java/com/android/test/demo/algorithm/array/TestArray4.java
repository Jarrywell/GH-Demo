package com.android.test.demo.algorithm.array;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestArray4 {

    private static final String TAG = Algorithms.TAG;


    /**
     * 顺时针打印矩阵
     *
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
     * 例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
     * 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
     *
     *
     * 基本思路：
     * 1.左上角的坐标和右下角的坐标固定了一圈矩阵，先打印这一圈矩阵。其中，对矩阵只有一行或者一列分别打印。
     * 2.打印完了这一圈，左上角坐标都+1 ，右下角的都减一，到更内一圈。
     * 比如：4*4的矩阵，(0,0)--(3,3)然后，(1,1)--(2,2)
     * 3.当左上角跑到右下角下面就结束了。
     *
     * @param matrix
     * @return
     */
    private static List<Integer> printMatrix(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length <= 0 || matrix[0].length < 0) {
            return result;
        }
        int topRow = 0, topCol = 0;
        int bottomRow = matrix.length - 1, bottomCol = matrix[0].length - 1;

        while (topRow <= bottomRow && topCol <= bottomCol) {
            printMatrix(matrix, topRow++, topCol++, bottomRow--, bottomCol--, result);
        }
        return result;
    }

    /**
     *
     * @param matrix
     * @param topRow
     * @param topCol
     * @param bottomRow
     * @param bottomCol
     * @param result
     */
    private static void printMatrix(int[][] matrix, int topRow, int topCol,
                                    int bottomRow, int bottomCol, List<Integer> result) {
        if (topRow == bottomRow) { //只有一行 (顺时针，最后一行一定是从左到右)
            for (int i = topCol; i <= bottomCol; i++) {
                result.add(matrix[topRow][i]);
            }
        } else if (topCol == bottomCol) { //只有一列 （顺时针，最后一列一定是从上到下）
            for (int i = topRow; i <= bottomRow; i++) {
                result.add(matrix[i][topCol]);
            }
        } else { //行列都有
            int currentRow = topRow;
            int currentCol = topCol;

            /**
             * 下面四个循环位置不能变：左->右, 上->下,右->左，下->上 刚好一圈
             */

            while (currentCol < bottomCol) { //左->右
                result.add(matrix[topRow][currentCol]);
                currentCol++;
            }

            while (currentRow < bottomRow) { //上->下
                result.add(matrix[currentRow][bottomCol]);
                currentRow++;
            }

            while (currentCol > topCol) { //右->左
                result.add(matrix[bottomRow][currentCol]);
                currentCol--;
            }

            while (currentRow > topRow) { //下->上
                result.add(matrix[currentRow][topCol]);
                currentRow--;
            }
        }
    }

    /**
     *
     */
    public static void testPrintMatrix() {
        int[][] p = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};

        List<Integer> result = printMatrix(p);

        Log.i(TAG, "print matrix: " + Arrays.toString(result.toArray()));
    }
}
