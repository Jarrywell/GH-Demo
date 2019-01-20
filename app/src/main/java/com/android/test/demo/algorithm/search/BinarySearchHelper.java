package com.android.test.demo.algorithm.search;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

public class BinarySearchHelper {
    private static final String TAG = Algorithms.TAG;

    /**
     * 二分查找(普通)
     * 循环条件：i <= j
     * @param array
     * @param target
     * @return
     */
    public static int binarySearch(int[] array, int target) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        int length = array.length;
        int i = 0, j = length - 1;
        while (i <= j) {
            int mid = (i + j) / 2;
            if (target < array[mid]) {
                j = mid - 1;
            } else if (target > array[mid]) {
                i = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 二分查找 找第一次出现的位置
     * 循环条件：i < j
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchOfFirstK(int[] array, int target) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        int length = array.length;
        int i = 0, j = length - 1;
        while (i < j) {
            int mid = (i + j) / 2;
            if (target < array[mid]) {
                j = mid - 1;
            } else if (target > array[mid]) {
                i = mid + 1;
            } else {
                j = mid;
            }
        }
        if (array[i] == target) {
            return i;
        }

        return -1;
    }

    /**
     *
     * 二分查找 找最后一次出现的位置
     * 循环条件：i < j - 1
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchOfLastK(int[] array, int target) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        int length = array.length;
        int i = 0, j = length - 1;

        while (i < j - 1) {
            int mid = (i + j) / 2;
            if (target < array[mid]) {
                j = mid - 1;
            } else if (target > array[mid]) {
                i = mid + 1;
            } else {
                i = mid;
            }
        }

        if (array[j] == target) {
            return j;
        }

        if (array[i] == target) {
            return i;
        }

        return -1;
    }

    /**
     * 查找小于关键字的最大数字出现的位置
     * 循环条件：i < j - 1
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchLessKOfMax(int[] array, int target) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        int length = array.length;
        int i = 0, j = length - 1;

        while (i < j - 1) {
            int mid = (i + j) / 2;
            if (target < array[mid]) {
                j = mid - 1;
            } else if (target > array[mid]) {
                i = mid + 1;
            } else {
                j = mid - 1; //在前面找
            }
        }

        if (array[j] < target) {
            return j;
        }

        if (array[i] < target) {
            return i;
        }

        return -1;
    }

    /**
     * 查找大于关键字的最小数字出现的位置
     *
     * 循环条件：i < j
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchMoreKOfMin(int[] array, int target) {
        if (array == null || array.length <= 0) {
            return -1;
        }
        int length = array.length;
        int i = 0, j = length - 1;

        while (i < j) {
            int mid = (i + j) / 2;
            if (target < array[mid]) {
                j = mid - 1;
            } else if (target > array[mid]) {
                i = mid + 1;
            } else {
                i = mid + 1;
            }
        }

        if (array[i] > target) {
            return i;
        }

        /*if (array[j] > target) {
            return j;
        }*/

        return -1;
    }


    /**
     * 旋转数组的最小数字
     *
     * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
     *
     * https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/06.md
     * @param array
     * @return
     */
    private static int binarySearchMinOfRotateArray(int[] array) {
        if (array == null || array.length <= 0) {
            return 0;
        }
        int length = array.length;
        int i = 0, j = length - 1;
        int mid = 0;
        while (i < j) {
            /**
             * 当处理范围只有两个数据时，返回后一个结果
             * 因为array[i] >= array[j]总是成立，后一个结果对应的是最小的值
             */
            if (j - i <= 1) {
                return array[j];
            }

            //取中间的位置
            mid = (i + j) / 2;

            /**
             * 如果三个数都相等，则需要进行顺序处理，从头到尾找最小的值
             */
            /*if (array[i] == array[mid] && array[mid] == array[j]) {
                return array[j];
            }*/


            if (array[mid] >= array[i]) { //比较最左侧
                i = mid;
            } else if (array[mid] <= array[j]) {
                j = mid;
            }
        }

        return array[mid];
    }



    /**
     * 二分查找
     */
    public static void testBinarySearch() {

        int[] array = new int[] {1, 2, 3, 5, 6, 7, 9};
        final int index = binarySearch(array, 3);

        Log.i(TAG, "binary search index: " + index);
    }

    /**
     * 找第一次出现的位置
     */
    public static void testBinarySearchOfFirstK() {
        int[] array = new int[] {1, 2, 3, 3, 3, 4, 5};
        final int index = binarySearchOfFirstK(array, 3);
        Log.i(TAG, "binary search first index: " + index);
    }

    /**
     * 找最后一次出现的位置
     */
    public static void testBinarySearchOfLastK() {
        int p[] = {1, 5, 6, 7, 11, 11, 11, 11, 56};
        final int index = binarySearchOfLastK(p, 11);
        Log.i(TAG, "binary search last index: " + index);
    }

    /**
     * 查找小于关键字的最大数字出现的位置
     */
    public static void testBinarySearchLessKOfMax() {
        int p[] = {1, 5, 6, 7, 11, 11, 11, 11, 56};
        final int index = binarySearchLessKOfMax(p, 11);
        Log.i(TAG, "binary search lees k of max index: " + index);
    }


    /**
     * 查找大于关键字的最小数字出现的位置
     */
    public static void testBinarySearchMoreKOfMin() {
        int p[] = {1, 5, 6, 7, 11, 11, 11, 11, 56};
        final int index = binarySearchMoreKOfMin(p, 11);
        Log.i(TAG, "binary search more k of min index: " + index);
    }

    /**
     * 旋转数组的最小数字
     */
    public static void testBinarySearchMinOfRotateArray() {
        //int[] p = {3, 4, 5, 1, 2};
        int[] p = {6, 7, 7, 8, 8, 2, 3, 4, 5, 6};
        final int num = binarySearchMinOfRotateArray(p);
        Log.i(TAG, "num: " + num);
    }

}
