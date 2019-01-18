package com.android.test.demo.algorithm.tree;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class TreeNodeUtils {

    private static final String TAG = Algorithms.TAG;

    /**
     * 通过数组创建一棵二叉树
     * @param values
     * @param <T>
     * @return 返回二叉树的根节点
     */
    public static <T extends Comparable> TreeNode<T> createTreeNodeOfArray(T... values) {
        TreeNode<T> root = null;
        for (T value : values) {
            root = insertNode(root, value);
        }
        return root;
    }

    /**
     * 搜索节点
     * @param root
     * @param value
     * @param <T>
     * @return
     */
    public static <T extends Comparable> TreeNode<T> search(TreeNode<T> root, T value) {
        TreeNode<T> result = null;
        TreeNode<T> p = root;
        while (p != null) {
            int compare = value.compareTo(p.data);
            if (compare < 0) {
                p = p.left;
            } else if (compare > 0) {
                p = p.right;
            } else {
                result = p;
                break;
            }
        }
        return result;
    }


    /**
     * 在二叉树中插入一个节点
     * @param root
     * @param value
     * @param <T>
     * @return 返回二叉树的根节点
     */
    public static <T extends Comparable> TreeNode<T> insertNode(TreeNode<T> root, T value) {
        if (root == null) {
            return new TreeNode<>(value);
        } else {
            TreeNode<T> pNode = null; //待插入结点的父结点
            TreeNode<T> tNode = root; //当前节点
            TreeNode<T> node = new TreeNode<>(value);
            /**
             * 找待插入节点的父节点
             */
            while (tNode != null) {
                pNode = tNode;
                if (value.compareTo(tNode.data) < 0) {
                    tNode = tNode.left;
                } else {
                    tNode = tNode.right;
                }
            }

            /**
             * 在父节点上插入新节点
             */
            if (value.compareTo(pNode.data) < 0) {
                pNode.left = node;
            } else {
                pNode.right = node;
            }
            return root;
        }
    }


    /**
     * 中序遍历（非递归）
     * @param root
     * @param <T>
     * @return
     */
    public static <T> List<T> inorderTraversal(TreeNode<T> root) {
        List<T> result = new ArrayList<>();
        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> p = root;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                TreeNode<T> node = stack.pop();
                result.add(node.data);
                p = node.right;
            }
        }
        return result;
    }

    /**
     * 前序遍历
     * @param root
     * @param <T>
     * @return
     */
    public static <T> List<T> preOrderTraversal(TreeNode<T> root) {
        List<T> result = new ArrayList<>();
        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> p = root;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                result.add(p.data);
                p = p.left;
            } else {
                TreeNode<T> node = stack.pop();
                p = node.right;
            }
        }
        return result;
    }

    /**
     * 后序遍历
     * @param root
     * @param <T>
     * @return
     */
    public static <T> List<T> postOrderTraversal(TreeNode<T> root) {
        List<T> result = new ArrayList<>();
        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> p = root;
        TreeNode<T> prev = root;
        while (p != null || !stack.isEmpty()) {

            /**
             * 先从左子树走到底
             */
            while (p != null) {
                stack.push(p);
                p = p.left;
            }

            /**
             * 拿到最后一个节点的右孩子
             */
            p = stack.peek().right;
            //右孩子为空，或者
            if (p == null || p == prev) {
                p = stack.pop();
                result.add(p.data);
                prev = p;
                p = null;
            }
        }

        return result;
    }


    public static void test() {
        //final Integer p[] = {4, 5, 6, 1, 2, 3};
        //final Integer p[] = {5, 3, 7, 2, 4, 6, 8};
        final Integer p[] = {10, 5, 15, 3, 7, 11, 17, 6};

        TreeNode<Integer> root = createTreeNodeOfArray(p);

        List<Integer> result = inorderTraversal(root);

        Log.i(TAG, "result: " + Arrays.toString(result.toArray()));

        TreeNode<Integer> node = search(root, 1);
        Log.i(TAG, "value: " + (node != null ? node.data : "@null"));

        List<Integer> preOrder = preOrderTraversal(root);
        Log.i(TAG, "preOrder: " + Arrays.toString(preOrder.toArray()));

        List<Integer> postOrder = postOrderTraversal(root);
        Log.i(TAG, "postOrder: " + Arrays.toString(postOrder.toArray()));
    }

}
