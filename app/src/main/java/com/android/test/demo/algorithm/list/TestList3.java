package com.android.test.demo.algorithm.list;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class TestList3 {
    private static final String TAG = Algorithms.TAG;

    /**
     * 从尾到头打印链表
     *
     * 思路：利用栈
     *
     * @param root
     * @return
     */
    private static List<Integer> tail2Head(ListNode<Integer> root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        ListNode<Integer> p = root;
        Stack<ListNode<Integer>> stack = new Stack<>();

        while (p != null) {
            stack.push(p);
            p = p.next;
        }

        while (!stack.isEmpty()) {
            ListNode<Integer> node = stack.pop();
            result.add(node.data);
        }

        return result;
    }

    /**
     * 合并两个排序的链表
     * @param rootA
     * @param rootB
     * @return
     */
    private static ListNode<Integer> merge(ListNode<Integer> rootA, ListNode<Integer> rootB) {
        if (rootA == null && rootB == null) {
            return null;
        }
        if (rootA == null || rootB == null) {
            return rootA == null ? rootB : rootA;
        }

        ListNode<Integer> p = new ListNode<>(-1); //head
        ListNode<Integer> rootC = p;

        while (rootA != null && rootB != null) {
            if (rootA.data < rootB.data) {
                p.next = rootA; rootA = rootA.next;
            } else {
                p.next = rootB; rootB = rootB.next;
            }
            p = p.next;
        }
        p.next = rootA != null ? rootA : rootB;

        return rootC.next;
    }

    /**
     * 求两个无环单链表的第一个相交点
     *
     * 参考：https://www.jianshu.com/p/a2d53142860c
     *
     * @param headA
     * @param headB
     * @return
     */
    private static ListNode<Integer> findFirstSharedNode(ListNode<Integer> headA, ListNode<Integer> headB) {
        if (headA == null || headB == null) {
            return null;
        }
        if (headA == headB) {
            return headA;
        }

        ListNode<Integer> p1 = headA;
        ListNode<Integer> p2 = headB;

        /**
         * 两个节点相等，或者都等于null
         */
        while (p1 != p2) {

            /**
             * 这里交换位置是为了得出p1-p2的距离,优化需要分别计算两个链表长度的问题
             */
            p1 = (p1 == null) ? headB : p1.next;
            p2 = (p2 == null) ? headA : p2.next;
        }
        return p1;
    }

    /**
     * 从尾到头打印链表
     */
    public static void testTail2Head() {
        ListNode<Integer> root = ListUtils.createListNodeOfArray(1, 2, 3, 4, 5, 6);
        List<Integer> result = tail2Head(root);
        Log.i(TAG, "result: " + Arrays.toString(result.toArray()));
    }

    /**
     * 合并两个排序的链表
     */
    public static void testMerge() {
        ListNode<Integer> rootA = ListUtils.createListNodeOfArray(1, 3, 5, 7);
        ListNode<Integer> rootB = ListUtils.createListNodeOfArray(2, 4, 6, 8, 10, 11, 15);

        ListNode<Integer> rootC = merge(rootB, rootA);

        ListUtils.print(rootC);

    }

    /**
     * 求两个无环单链表的第一个相交点
     */
    public static void testFindFirstSharedNode() {
        ListNode<Integer> rootA = ListUtils.createListNodeOfArray(1, 3, 5, 7);
        ListNode<Integer> rootB = ListUtils.createListNodeOfArray(2, 4, 6, 8, 10, 11, 15);
        ListNode<Integer> rootC = ListUtils.createListNodeOfArray(20, 21, 22);

        ListNode<Integer> pA = rootA;
        while (pA.next != null) {
            pA = pA.next;
        }
        pA.next = rootC;

        ListNode<Integer> pB = rootB;
        while (pB.next != null) {
            pB = pB.next;
        }
        pB.next = rootC;

        ListNode<Integer> node = findFirstSharedNode(rootA, rootB);
        ListUtils.print(node);
    }
}
