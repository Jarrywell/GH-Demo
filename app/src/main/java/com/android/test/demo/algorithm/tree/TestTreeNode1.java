package com.android.test.demo.algorithm.tree;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.Stack;

/**
 * 二叉搜索树的第K个节点
 *
 * 描述：
 * 给定一颗二叉搜索树，请找出其中的第k小的结点。例如， 5 / \ 3 7 /\ /\ 2 4 6 8 中，
 * 按结点数值大小顺序第三个结点的值为4。
 *
 * 思路：
 * 二叉搜索树的中序遍历是一个有顺序的序列，这个时候我就是要中序遍历这颗二叉树，然后设置一个
 * 变量，访问一个变量的时候就加一，判断这个变量和k是否相等，如果相等，则将当前的这个结点返回即可
 *
 */
public class TestTreeNode1 {

    private static final String TAG = Algorithms.TAG;

    public static void test() {

        /**
         * 构建二叉树
         */
        Integer[] values = new Integer[] {5, 3, 7, 2, 4, 6, 8};
        TreeNode<Integer> root = TreeNodeUtils.createTreeNodeOfArray(values);

        /**
         * 递归方式
         */
        TreeNode<Integer> result = kthNode1(root, 3, 1);
        Log.i(TAG, "result1: " + (result != null ? result.data : "@null"));

        /**
         * 非递归
         */
        result = kthNode2(root, 5);
        Log.i(TAG, "result2: " + (result != null ? result.data : "@null"));

    }
    /**
     * 递归形式(有问题)
     * @param root
     * @param k
     * @return
     */
    private static TreeNode<Integer> kthNode1(TreeNode<Integer> root, int k, int index) {
        if (root != null) {

            TreeNode<Integer> left = kthNode1(root.left, k, index);
            if (left != null) {
                return left;
            }

            index++;

            /**
             * 遍历到第k个时返回
             */
            if (index == k) {
                return root;
            }

            TreeNode<Integer> right = kthNode1(root.right, k, index);
            if (right != null) {
                return right;
            }
        }
        return null;
    }

    /**
     * 非递归形式(利用栈模拟递归)
     * @param root
     * @param k
     * @return
     */
    private static TreeNode<Integer> kthNode2(TreeNode<Integer> root, int k) {
        int index = 0;
        TreeNode<Integer> p = root;
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) { //一直走到底
                stack.push(p);
                p = p.left;
            } else {
                TreeNode<Integer> node = stack.pop();
                index++;
                if (index == k) {
                    return node;
                }
                p = node.right;
            }
        }
        return null;
    }
}
