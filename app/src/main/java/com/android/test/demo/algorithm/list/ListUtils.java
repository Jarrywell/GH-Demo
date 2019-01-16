package com.android.test.demo.algorithm.list;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

public class ListUtils {

    private static final String TAG = Algorithms.TAG;

    /**
     * 通过数组构建列表
     * @param values
     * @param <T>
     * @return
     */
    public static <T> ListNode<T> createListNodeOfArray(T... values) {
        if (values == null || values.length <= 0) {
            throw new NullPointerException("values is null!!!");
        }

        ListNode<T> pre = null;
        ListNode<T> head = null;
        for (T value : values) {
            ListNode<T> node = new ListNode<>(value);
            if (pre != null) {
                pre.next = node;
            } else {
                head = node; //pre==null时，说明是头节点，用于返回
            }
            pre = node;
        }
        return head;
    }

    /**
     * 反转链表
     * @param head
     * @param <T>
     * @return
     */
    public static <T> ListNode<T> reverse(ListNode<T> head) {
        if (head == null) {
            return null;
        }
        ListNode<T> pre = null; //前一个节点
        ListNode<T> next = null; //下一个节点
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 打印链表
     * @param head
     * @param <T>
     */
    public static <T> void print(ListNode<T> head) {
        StringBuilder builder = new StringBuilder();
        while (head != null) {
            builder.append(head.data);
            if (head.next != null) {
                builder.append("->");
            }
            head = head.next;
        }
        Log.i(TAG, "result: " + builder.toString());
    }


    /**
     * 链表中倒数第k个结点
     * 思路：快指针和慢指针。快指针先走k步
     * @param head
     * @param k
     * @return
     */
    public static <T> ListNode<T> findKthToTail(ListNode<T> head, int k) {
        ListNode<T> slow = head;
        ListNode<T> fast = head;
        if (head == null || k <= 0) {
            return null;
        }
        /**
         * 快指针先走k步
         */
        for (int i = 1; i < k; i++) {
            if (fast.next != null) {
                fast = fast.next;
            } else {
                return null;
            }
        }
        /**
         * 然后快慢指针一起走，快指针到底后，慢指针就是倒数k个节点
         */
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
