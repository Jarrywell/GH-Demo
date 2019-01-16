package com.android.test.demo.algorithm;

import com.android.test.demo.algorithm.list.TestList1;
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
        testSort();
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
    }

    private static void testSort() {
        /**
         * 堆排序
         */
        HeapSort.test();
    }
}
