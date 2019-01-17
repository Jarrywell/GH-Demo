package com.android.test.demo.algorithm.list;


import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

/**
 * 复杂链表的复制
 * http://wiki.jikexueyuan.com/project/for-offer/question-twenty-six.html
 *
 * 题目：请实现函数复制一个复杂链表。在复杂链表中，每个结点除了有一个next域指向下一个结点外，
 * 还有一个sibling指向链表中的任意结点或者null。
 *
 * 难点是sibling指针的拷贝，因为是任意的！！！
 *
 * 不用辅助空间的情况下实现O(n)的时间效率
 *
 * 思路：
 * 第一步：仍然是根据原始链表的每个结点N 创建对应的 N’。把 N’链接在N的后面。(间隔)
 * 第二步：设置复制出来的结点的sibling。假设原始链表上的 N 的 sibling 指向结点 S，那么其对应复制出来的 N’是 N的
 * next 指向的结点，同样 S’也是 S 的 next 指向的结点。
 * 第三步：把这个长链表拆分成两个链表。把奇数位置的结点用 next 链接起来就是原始链表，把偶数位置的结点用 next链
 * 接起来就是复制 出来的链表。
 */
public class TestList2 {
    private static final String TAG = Algorithms.TAG;

    /**
     * 复杂链表数据结构
     * 比平常链表多了一个指针sibling，该指针可以指向任意节点
     * @param <T>
     */
    private static class ComplexListNode<T> extends ListNode<T> {

        /**
         * 指向任意节点
         */
        public ListNode<T> sibling;

        public ComplexListNode(T data) {
            super(data);
        }
    }

    /**
     *
     * @param head
     * @return
     */
    private static ComplexListNode<Integer> clone(ComplexListNode<Integer> head) {
        if (head == null) {
            return null;
        }
        /**
         * 拷贝原节点，并用next指针使之与原节点间隔排列
         */
        ComplexListNode<Integer> result = cloneNodes(head);

        /**
         * 拷贝sibling指针
         */
        result = connectSibling(result);

        /**
         * 拆开两个链表
         */
        result = disConnectSibling(result);

        return result;
    }

    /**
     * 复制链表
     * result: 1->101->2->102->3->103->4->104->5->105
     * @param head
     * @return
     */
    private static ComplexListNode<Integer> cloneNodes(ListNode<Integer> head) {
        ListNode<Integer> result = head;
        while (head != null) {
            //创建一个新的结点，+100为了区别旧节点
            ListNode<Integer> temp = new ComplexListNode<>(head.data + 100);

            /**
             * 将新节点插入到被复制节点的后面
             */
            temp.next = head.next;
            head.next = temp;
            head = temp.next;
        }
        return (ComplexListNode<Integer>) result;
    }

    /**
     * 建立sibling指针
     * @param head
     * @return
     */
    private static ComplexListNode<Integer> connectSibling(ComplexListNode<Integer> head) {
        ComplexListNode<Integer> result = head;

        while (head != null) {
            if (head.sibling != null) {
                /**
                 * 原链表跟复制链表是间隔的，因此原链表的sibling.next就是复制链表的sibling
                 */
                ((ComplexListNode<Integer>) head.next).sibling = head.sibling.next;
            }
            head = (ComplexListNode<Integer>) head.next.next;
        }
        return result;
    }

    /**
     * 将原链表与复制链表拆开，返回复制链表的头节点
     * @param head
     * @return
     */
    private static ComplexListNode<Integer> disConnectSibling(ListNode<Integer> head) {
        if (head == null) {
            return null;
        }

        /**
         * 比如，拆分：1->101->2->102->3->103->4->104->5->105
         */
        ListNode<Integer> newHead = head.next; //用于记录复制链表的头结点
        ListNode<Integer> pointer = newHead; //用于记录当前处理的复制结点
        head.next = newHead.next; //原结点的next指向下一个被复制结点
        head = head.next; //指向新的原结点
        while (head != null) {
            //pointer指向原结点
            pointer.next = head.next;
            pointer = pointer.next;

            //head的下一个指向复制结点的下一个结点，即原来链表的结点
            head.next = pointer.next;
            //head指向下一个原来链表上的结点
            head = pointer.next;
        }
        return (ComplexListNode<Integer>) newHead;
    }

    public static void test() {

        //节点示例图
        /********************************************
         *        ------------------
         *        \|/              |
         * 1-------2-------3-------4-------5
         * |       |      /|\             /|\
         * --------+--------               |
         *         -------------------------
         *
         **********************************************/
        ComplexListNode<Integer> node1 = new ComplexListNode<>(1);
        ComplexListNode<Integer> node2 = new ComplexListNode<>(2);
        ComplexListNode<Integer> node3 = new ComplexListNode<>(3);
        ComplexListNode<Integer> node4 = new ComplexListNode<>(4);
        ComplexListNode<Integer> node5 = new ComplexListNode<>(5);

        //next指针赋值
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        //sibling指针赋值
        node1.sibling = node3;
        node2.sibling = node5;
        node4.sibling = node2;

        ListUtils.print(node1);

        //复制函数
        ComplexListNode<Integer> newHead = clone(node1);
        ListUtils.print(newHead);

        /**
         * 01-17 14:35:27.042 I/Algorithms(  584): data: 101, sibling: 103
         * 01-17 14:35:27.042 I/Algorithms(  584): data: 102, sibling: 105
         * 01-17 14:35:27.042 I/Algorithms(  584): data: 103, sibling: @null
         * 01-17 14:35:27.042 I/Algorithms(  584): data: 104, sibling: 102
         * 01-17 14:35:27.042 I/Algorithms(  584): data: 105, sibling: @null
         */
        printSibling(newHead);
    }

    /**
     * 输出sibling节点
     * @param newHead
     */
    private static void printSibling(ComplexListNode<Integer> newHead) {
        while (newHead != null) {
            Log.i(TAG, "data: " + newHead.data + ", sibling: " +
                    (newHead.sibling != null ? newHead.sibling.data : "@null"));
            newHead = (ComplexListNode<Integer>) newHead.next;
        }
    }
}
