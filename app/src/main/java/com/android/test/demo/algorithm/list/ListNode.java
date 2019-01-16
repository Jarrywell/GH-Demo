package com.android.test.demo.algorithm.list;

/**
 * 链表数据结构
 * @param <T>
 */
public final class ListNode<T> {

    public ListNode(T data) {
        this.data = data;
    }

    /**
     * 存放数据
     */
    public T data;

    /**
     * 指向下一个节点
     */
    public ListNode<T> next;
}
