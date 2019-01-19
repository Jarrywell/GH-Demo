package com.android.test.demo.algorithm.tree;

/**
 * 二叉树结构
 * @param <T>
 */
public class TreeNode<T> {

    TreeNode(T data) {
        this.data = data;
    }

    /**
     * 存放数据
     */
    public T data;

    /**
     * 左子树
     */
    TreeNode<T> left;

    /**
     * 右子树
     */
    TreeNode<T> right;

    /**
     * 指向父节点
     */
    @Deprecated
    TreeNode<T> next;

}
