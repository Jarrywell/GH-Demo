package com.android.test.demo.algorithm;

import com.android.test.demo.algorithm.array.TestArray1;
import com.android.test.demo.algorithm.list.TestList1;
import com.android.test.demo.algorithm.list.TestList2;
import com.android.test.demo.algorithm.num.TestNum1;
import com.android.test.demo.algorithm.sort.HeapSort;

public class Algorithms {
    public static final String TAG = "Algorithms";

    public static void test() {
        /**
         * 链表测试
         */
        testList();

        /**
         * 排序测试
         */
        //testSort();

        /**
         * 数字算法测试
         */
        //testNum();

        /**
         * 数组测试
         */
        //testArray();
    }

    private static void testList() {
        /**
         * 删除链表中重复的节点
         */
        TestList1.testDeleteDuplication();

        /**
         * 反转链表
         */
        TestList1.testReverse();

        /**
         * 链表中倒数第k个结点
         */
        TestList1.testFindKthToTail();

        /**
         * 复杂链表的复制
         */
        TestList2.test();
    }

    private static void testSort() {
        /**
         * 堆排序
         */
        HeapSort.test();

        /**
         * 找最小的k个数
         */
        HeapSort.testKthNum();
    }

    private static void testNum() {
        /**
         * 找第index个丑数
         */
        TestNum1.test();
    }


    private static void testArray() {
        /**
         * 滑动窗口的最大值
         */
        TestArray1.test();
    }
}
