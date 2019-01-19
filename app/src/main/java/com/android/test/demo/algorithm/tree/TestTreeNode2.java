package com.android.test.demo.algorithm.tree;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class TestTreeNode2 {

    private static final String TAG = Algorithms.TAG;

    /**
     * 层序遍历，并且每层需要分行打印
     * @param root
     * @return
     */
    private static List<List<Integer>> levelOrderTraversalOfNewLine(TreeNode<Integer> root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        TreeNode<Integer> p = root;
        queue.offer(p);

        TreeNode<Integer> newLineLast = null; //下一行最右节点
        List<Integer> line = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode<Integer> node = queue.poll();
            line.add(node.data);

            if (node.left != null) {
                queue.offer(node.left);
                newLineLast = node.left;
            }

            if (node.right != null) {
                queue.offer(node.right);
                newLineLast = node.right;
            }

            /**
             * 当前节点等于下一行最右节点时需要换行了
             */
            if (node == p) {
                result.add(new ArrayList<>(line));
                p = newLineLast;
                line.clear();
            }
        }

        return result;
    }

    /**
     * Z字型打印二叉树
     * 题目：请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，
     * 第三行按照从左到右的顺序打印，其他行以此类推。
     *
     * 思路：每行的节点的访问顺序是相反的，我们可以用两个栈来隔行存储，一个栈中根据“左结点->右结点”的顺序访问
     * 另一个栈的栈顶元素，而另一个栈根据“右子树->左子树”的顺序访问另一个栈的栈顶元素，直到两个栈都为空
     *
     * @param root
     * @return
     */
    private static List<List<Integer>> ZOrderTraversalOfNewLine(TreeNode<Integer> root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode<Integer>> stack1 = new Stack<>();
        Stack<TreeNode<Integer>> stack2 = new Stack<>();

        TreeNode<Integer> p = root;

        /**
         * 处理根节点
         */
        List<Integer> line = new ArrayList<>();
        line.add(p.data);
        result.add(new ArrayList<>(line));
        line.clear();

        stack1.push(p);

        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            if (stack2.isEmpty()) { //表示需要开始遍历stack1
                while (!stack1.isEmpty()) {
                    TreeNode<Integer> node = stack1.pop();
                    /**
                     * stack2的顺序是从右到左，因此先处理right,再left
                     */
                    if (node.right != null) {
                        line.add(node.right.data);
                        stack2.push(node.right);
                    }
                    if (node.left != null) {
                        line.add(node.left.data);
                        stack2.push(node.left);
                    }
                }
            } else {  //表示需要开始遍历stack2
                while (!stack2.isEmpty()) {
                    TreeNode<Integer> node = stack2.pop();
                    /**
                     * stack1的顺序是从左到右，因此先处理left,再right
                     */
                    if (node.left != null) {
                        line.add(node.left.data);
                        stack1.push(node.left);
                    }
                    if (node.right != null) {
                        line.add(node.right.data);
                        stack1.push(node.right);
                    }
                }
            }
            if (line.size() > 0) {
                result.add(new ArrayList<>(line));
                line.clear();
            }
        }

        return result;
    }

    public static void test() {
        //final Integer p[] = {10, 5, 15, 3, 7, 11, 17, 6};
        final Integer p[]  = new Integer[] {5, 3, 7, 2, 4, 6, 8};
        TreeNode<Integer> root = TreeNodeUtils.createTreeNodeOfArray(p);

        List<List<Integer>> result = levelOrderTraversalOfNewLine(root);
        print(result);

        result = ZOrderTraversalOfNewLine(root);
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
