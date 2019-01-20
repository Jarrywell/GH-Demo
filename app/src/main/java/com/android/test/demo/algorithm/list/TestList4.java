package com.android.test.demo.algorithm.list;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

public class TestList4 {

    private static final String TAG = Algorithms.TAG;


    /**
     * 判断两个无环单链表是否相交
     *
     * 思路：如果相交，则尾节点一定相同，否则不相交
     *
     * @param headA
     * @param headB
     * @return
     */
    private static boolean isIntersection(ListNode<Integer> headA, ListNode<Integer> headB) {
        if (headA == null || headB == null) {
            return false;
        }

        if (headA == headB) {
            return true;
        }

        while (headA.next != null) {
            headA = headA.next;
        }

        while (headB.next != null) {
            headB = headB.next;
        }

        /**
         * 尾节点是否相同
         */
        return headA == headB;
    }

    /**
     * 判断两个无环单链表是否相交
     */
    public static void testIsIntersection() {
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

        final boolean result = isIntersection(rootA, rootB);
        Log.i(TAG, "intersection: " + result);
    }
}
