package com.android.test.demo.algorithm.tree;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
 * 从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
 *
 * https://blog.csdn.net/u013132035/article/details/80621598
 *
 * 注意：是一直到叶子节点
 *
 * 思路：采用前序遍历，并配合栈一起使用
 */
public class TestTreeNode3 {
    private static final String TAG = Algorithms.TAG;

    /**
     *
     * @param root
     * @param expectedSum
     * @return
     */
    private static List<List<Integer>> findPath(TreeNode<Integer> root, int expectedSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        findPath(root, expectedSum, stack, result);

        return result;
    }

    /**
     *
     * @param root
     * @param expectedSum
     * @param stack
     * @param result
     */
    private static void findPath(TreeNode<Integer> root, int expectedSum,
                                 Stack<TreeNode<Integer>> stack, List<List<Integer>> result) {

        if (root == null) {
            return;
        }

        TreeNode<Integer> p = root;

        //不是叶子结点，前序遍历，将当前结点值放入path栈中 （记录当前访问的结点）
        stack.push(p);

        if (p.left == null && p.right == null) {
            /**
             * 对应字节点与预期值相等
             */
            if (p.data == expectedSum) {
                List<Integer> list = new ArrayList<>();
                for (TreeNode<Integer> node : stack) { //入栈顺序，iterator特性
                    list.add(node.data);
                }
                result.add(list);
            }
        } else {
            if (p.left != null) {
                findPath(p.left, expectedSum - p.data, stack, result);
            }
            if (p.right != null) {
                findPath(p.right, expectedSum - p.data, stack, result);
            }
        }

        /**
         * 如果是叶子结点又不满足要求，退回到父结点，删除当前节点
         * 回退到父节点，开始下一次遍历
         */
        stack.pop();
    }

    public static void test() {

        TreeNode<Integer> root = TreeNodeUtils.createTreeNodeOfArray(10, 5, 12, 4, 7);

        List<Integer> inorder = TreeNodeUtils.inorderTraversal(root);
        Log.i(TAG, "inorder: " + Arrays.toString(inorder.toArray()));

        List<List<Integer>> result = findPath(root, 22);
        print(result);
    }

    private static void print(List<List<Integer>> result) {
        int i = 1;
        for (List<Integer> line : result) {
            Log.i(TAG, "level " + i + ": " + Arrays.toString(line.toArray()));
            i++;
        }
    }

}
