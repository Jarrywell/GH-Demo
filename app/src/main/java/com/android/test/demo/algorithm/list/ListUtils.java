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
        /**
         * 如果head为null的时候，pre就为最后一个节点了，但是链表已经反转完毕，pre就是反转后链表的第一个节点
         */
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
         * 快指针先走k-1步
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

    /**
     * 删除链表中倒数第k个结点
     * @param head
     * @param k
     * @param <T>
     * @return
     */
    public static <T> ListNode<T> removeKthToTail(ListNode<T> head, int k) {
        ListNode<T> slow = head;
        ListNode<T> fast = head;

        if (head == null || k <= 0) {
            return head;
        }

        for (int i = 1; i < k; i++) {
            if (fast.next != null) {
                fast = fast.next;
            } else {
                return head;
            }
        }
        ListNode<T> p = null; //记录目标的前一个节点
        while (fast.next != null) {
            fast = fast.next;
            p = slow;
            slow = slow.next;
        }
        if (p != null) {
            p.next = p.next.next;
        }

        return head;
    }

    /**
     * 求单链表的中间节点
     *
     * 快慢指针
     *
     * @param head
     * @param <T>
     * @return
     */
    public static <T> ListNode<T> findMidNode(ListNode<T> head) {
        if (head == null) {
            return null;
        }

        ListNode<T> fast = head;
        ListNode<T> slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }



    /**
     * 删除链表中的节点
     * 思路：分尾节点(o(n))和非尾节点（o(1)）
     * @param head
     * @param node
     * @param <T>
     * @return
     */
    public static <T> ListNode<T> delete(ListNode<T> head, ListNode<T> node) {
        if (node == null) {
            return head;
        }
        if (node.next != null) {
            node.data = node.next.data;
            node.next = node.next.next;
            return head;
        } else { //尾节点
            ListNode<T> p = head;
            while (p.next != node) {
                p = p.next;
            }
            p.next = null;
        }
        return head;
    }

    /**
     * 判断单链表是否存在环
     *
     * 通过快慢指针来解决，两个指针从头节点开始，慢指针每次向后移动一步，快指针每次向后移动两步，
     * 如果存在环，那么两个指针一定会在环内相遇。
     *
     * @param head
     * @param <T>
     * @return
     */
    public static <T> boolean hasCycle(ListNode<T> head) {
        if (head == null) {
            return false;
        }
        ListNode<T> fast = head;
        ListNode<T> slow = head;

        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 找到环的入口点
     *
     * https://blog.csdn.net/wszy1301/article/details/80910626#7%E3%80%81%E9%93%BE%E8%A1%A8%E4%B8%AD%E7%8E%AF%E7%9A%84%E5%85%A5%E5%8F%A3%E8%8A%82%E7%82%B9
     *
     * 如图所示（https://img-blog.csdn.net/20150907161516402）
     *
     * fast指针走的路径是：a+b+c+b，slow指针走的路径是a+b，又由于快指针比慢指针快一倍：
     * 因此有， 2×(a+b)=a+b+c+b,所以有 a+b+a+b=a+b+c+b, 于是，a==c.
     *
     *
     *
     * @param head
     * @param <T>
     * @return
     */
    public static <T> ListNode<T> findLoopPort(ListNode<T> head) {
        if (head == null) {
            return null;
        }
        ListNode<T> fast = head;
        ListNode<T> slow = head;

        boolean cycle = false;

        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                cycle = true;
                break;
            }
        }

        if ( !cycle) {
            return null;
        }

        /**
         * 这里是由于a==c，所以一起移动时，刚好在入口点重合
         */
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }
}