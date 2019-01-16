package com.android.test.demo.algorithm.list;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;


public class TestList1 {
    private static final String TAG = Algorithms.TAG;


    /**
     * 删除链表中重复的节点
     */
    public static void testDeleteDuplication() {
        /**
         * 构建链表序列
         */
        ListNode<Integer> head = ListUtils.createListNodeOfArray(1, 2, 3, 3, 4, 4, 5);
        //ListNode<Integer> head = ListUtils.createListNodeOfArray(1, 1);
        ListUtils.print(head);

        /**
         * 输出结果
         */
        ListNode<Integer> result = deleteDuplication(head);
        ListUtils.print(result);
    }

    /**
     * 反转链表
     */
    public static void testReverse() {
        /**
         * 构建链表序列
         */
        ListNode<Integer> head = ListUtils.createListNodeOfArray(1, 2, 3, 3, 4, 4, 5);
        ListUtils.print(head);

        ListNode<Integer> result = ListUtils.reverse(head);
        ListUtils.print(result);
    }

    /**
     * 链表中倒数第k个结点
     */
    public static void testFindKthToTail() {
        /**
         * 构建链表序列
         */
        ListNode<Integer> head = ListUtils.createListNodeOfArray(1, 2, 3, 4, 5, 6);
        ListUtils.print(head);

        final int k = 4;
        ListNode<Integer> result = ListUtils.findKthToTail(head, k);
        Log.i(TAG, "find " + k + " tail: " + (result != null ? result.data : "@null"));
    }


    /**
     * 删除链表中重复的节点
     *
     * 描述：在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点（重复的结点不保留），
     * 返回链表头指针。
     *
     * 关键词：排序、重复节点
     *
     * 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
     *
     * 思路：需要两个指针，一个指向前一个节点preNode，另一个指向当前节点node，如果遇到相等的节点，node向后移动，
     * preNode不动，存下node.data方便后面的比较，直到遇到node.data和node.next.data不相等，
     * preNode就可以指向node.next
     *
     * @param head
     * @return
     */
    private static ListNode<Integer> deleteDuplication(ListNode<Integer> head) {

        if (head == null) {
            return null;
        }

        //新建一个节点，防止头结点被删除
        ListNode<Integer> firstNode = new ListNode<>(-1);
        firstNode.next = head;

        ListNode<Integer> current = head; //当前节点
        ListNode<Integer> pre = firstNode; //前一个节点

        while (current != null && current.next != null) {
            if (current.data == current.next.data) { //相等说明出现重复节点了
                final int data = current.data;
                //向后找到第一个不相同的，因为链表是排序了的
                while (current != null && current.data == data) {
                    current = current.next;
                }
                pre.next = current;
            } else { //不相等则两个指针都向后移动一个
                pre = current;
                current = current.next;
            }
        }

        return firstNode.next;
    }

}
