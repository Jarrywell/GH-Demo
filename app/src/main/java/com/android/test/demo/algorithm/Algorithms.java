package com.android.test.demo.algorithm;

import com.android.test.demo.algorithm.array.TestArray1;
import com.android.test.demo.algorithm.array.TestArray2;
import com.android.test.demo.algorithm.array.TestArray3;
import com.android.test.demo.algorithm.array.TestArray4;
import com.android.test.demo.algorithm.bit.TestBit1;
import com.android.test.demo.algorithm.list.TestList1;
import com.android.test.demo.algorithm.list.TestList2;
import com.android.test.demo.algorithm.list.TestList3;
import com.android.test.demo.algorithm.list.TestList4;
import com.android.test.demo.algorithm.num.TestNum1;
import com.android.test.demo.algorithm.num.TestNum2;
import com.android.test.demo.algorithm.queue.TestQueue1;
import com.android.test.demo.algorithm.search.BinarySearchHelper;
import com.android.test.demo.algorithm.sort.HeapSort;
import com.android.test.demo.algorithm.sort.QuickSort;
import com.android.test.demo.algorithm.stack.TestStack1;
import com.android.test.demo.algorithm.stack.TestStack2;
import com.android.test.demo.algorithm.string.TestString1;
import com.android.test.demo.algorithm.tree.TestTreeNode1;
import com.android.test.demo.algorithm.tree.TestTreeNode2;
import com.android.test.demo.algorithm.tree.TestTreeNode3;
import com.android.test.demo.algorithm.tree.TestTreeNode4;
import com.android.test.demo.algorithm.tree.TreeNodeUtils;

public class Algorithms {
    public static final String TAG = "Algorithms";

    public static void test() {
        /**
         * 链表测试
         */
        //testList();

        /**
         * 排序测试
         */
        testSort();

        /**
         * 数字算法测试
         */
        //testNum();

        /**
         * 数组测试
         */
        //testArray();

        /**
         * 位运算
         */
        //testBit();

        /**
         * 二叉树
         */
        //testTreeNode();

        /**
         * 队列
         */
        //testQueue();

        /**
         * 字符串
         */
        //testString();

        /**
         * 二分搜索
         */
        //testBinarySearch();

        /**
         * 堆栈测试
         */
        //testStack();
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
         * 删除链表中倒数第k个结点
         */
        TestList1.testRemoveKthToTail();

        /**
         * 求单链表的中间节点
         */
        TestList1.testFindMidNode();

        /**
         * 判断单链表是否存在环
         */
        TestList1.testCycle();

        /**
         * 复杂链表的复制
         */
        TestList2.test();

        /**
         * 从尾到头打印链表
         */
        TestList3.testTail2Head();

        /**
         * 合并两个排序的链表
         */
        TestList3.testMerge();

        /**
         * 求两个无环单链表的第一个相交点
         */
        TestList3.testFindFirstSharedNode();

        /**
         * 判断两个无环单链表是否相交
         */
        TestList4.testIsIntersection();
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

        /**
         * 快速排序
         */
        QuickSort.test();
    }

    private static void testNum() {
        /**
         * 找第index个丑数
         */
        TestNum1.test();

        /**
         * 求1+2+3+...+n
         */
        TestNum1.testSum();

        /**
         * 不用加减乘除做加法
         */
        TestNum1.testAdd();

        /**
         * 斐波那契数列
         */
        TestNum1.testFibonacci();


        /**
         * 跳台阶
         */
        TestNum1.testJumpOfFloor();

        /**
         * 变态跳台阶
         */
        TestNum1.testJumpOfFloor2();

        /**
         *
         */
        TestNum1.testPower();

        /**
         * 机器人运动范围
         */
        TestNum2.testMovingCount();
    }


    private static void testArray() {
        /**
         * 滑动窗口的最大值
         */
        TestArray1.test();

        /**
         * 找数组中重复的数字
         */
        TestArray2.testDuplicate();

        /**
         * 构建乘积数组
         */
        TestArray2.testMultiply();

        /**
         * 二维数组的查找
         */
        TestArray2.testFindOf2Array();

        /**
         * 数组中只出现一次的数字
         */
        TestArray2.testFindOnce();

        /**
         * 和为S的两个数
         */
        TestArray2.testFindOfSum();

        /**
         *
         */
        TestArray2.testFindContinueOfSum();

        /**
         * 调整数组顺序使奇数位于偶数前面
         */
        TestArray2.testAdjustArrayOfParity();

        /**
         * 数组中出现次数超过一半的数字
         */
        TestArray3.testFindHalfMoreOfArray();

        /**
         * 连续子数组的最大和
         */
        TestArray3.testFindMaxOfSubArray();

        /**
         * 把数组排成最小的数
         */
        TestArray3.testJoinMinNum();

        /**
         * 数字在排序数组中出现的次数
         */
        TestArray3.testCountOfK();

        /**
         * 扑克牌顺子
         */
        TestArray3.testIsContinues();


        /**
         * 约瑟夫环问题
         */
        TestArray3.testJosephus();

        /**
         *
         */
        TestArray4.testPrintMatrix();
    }

    /**
     * 位运算
     */
    private static void testBit() {
        /**
         * 测试数中1的个数
         */
        TestBit1.test1Count();

        /**
         * 测试数中0的个数
         */
        TestBit1.test0Count();

        /**
         * 测试数中高位连续0的个数
         */
        TestBit1.testHigh0Count();
    }

    /**
     * 二叉树
     */
    private static void testTreeNode() {
        /**
         * 二叉搜索树的第K个节点
         */
        TestTreeNode1.test();

        /**
         * 二叉搜索树的基础算法
         */
        TreeNodeUtils.test();

        /**
         *
         */
        TestTreeNode2.test();

        /**
         * 路径和
         */
        TestTreeNode3.test();

        /**
         *
         */
        TestTreeNode4.test();

        /**
         *
         */
        TestTreeNode4.testMirror();

        /**
         *
         */
        TestTreeNode4.testBst();

        /**
         *
         */
        TestTreeNode4.testConvert();


        /**
         *
         */
        TestTreeNode4.testSymmetrical();
    }

    /**
     * 队列测试
     */
    private static void testQueue() {
        /**
         * 最大堆 最小堆 找中位数
         */
        TestQueue1.test();
    }

    private static void testString() {
        /**
         * 到第一个只出现一次的字符
         */
        TestString1.testFindNotReChar();

        /**
         * 字符串反转
         */
        TestString1.testReversal();

        /**
         * 左旋转字符串
         */
        TestString1.testLeftRotate();

        /**
         * 将一个字符串转换成一个整数
         */
        TestString1.testStr2Int();

        /**
         * 输入一个字符串，打印出该字符串的所有排列
         */
        TestString1.testPermutation();
    }

    /**
     * 二分搜索
     */
    private static void testBinarySearch() {

        /**
         * 二分查找
         */
        BinarySearchHelper.testBinarySearch();

        /**
         * 找第一次出现的位置
         */
        BinarySearchHelper.testBinarySearchOfFirstK();

        /**
         * 找最后一次出现的位置
         */
        BinarySearchHelper.testBinarySearchOfLastK();

        /**
         * 查找小于关键字的最大数字出现的位置
         */
        BinarySearchHelper.testBinarySearchLessKOfMax();

        /**
         * 查找大于关键字的最小数字出现的位置
         */
        BinarySearchHelper.testBinarySearchMoreKOfMin();

        /**
         * 旋转数组的最小数字
         */
        BinarySearchHelper.testBinarySearchMinOfRotateArray();
    }

    /**
     * 栈
     */
    private static void testStack() {

        /**
         * 用两个栈实现队列
         */
        TestStack1.test();

        /**
         * 包含min函数的栈
         */
        TestStack2.test();
    }
}
