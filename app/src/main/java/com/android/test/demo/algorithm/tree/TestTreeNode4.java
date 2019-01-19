package com.android.test.demo.algorithm.tree;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class TestTreeNode4 {

    private static final String TAG = Algorithms.TAG;

    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     *
     * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     *
     * 思路：利用前序遍历的顺序（排在前面的总是根节点）将中序列表拆分
     *
     * @param preOrder
     * @param inOrder
     * @return
     */
    private static TreeNode<Integer> reBuildTree(int[] preOrder, int[] inOrder) {

        if (preOrder == null || preOrder.length <= 0) {
            return null;
        }
        if (inOrder == null || inOrder.length <= 0) {
            return null;
        }
        if (preOrder.length != inOrder.length) {
            return null;
        }

        TreeNode<Integer> root = new TreeNode<>(preOrder[0]);

        for (int i = 0; i < inOrder.length; i++) {
            if (inOrder[i] == preOrder[0]) {
                root.left = reBuildTree(Arrays.copyOfRange(preOrder, 1, i + 1),
                        Arrays.copyOfRange(inOrder, 0, i));

                root.right = reBuildTree(Arrays.copyOfRange(preOrder, i + 1, preOrder.length),
                        Arrays.copyOfRange(inOrder, i + 1, inOrder.length));
            }
        }
        return root;
    }

    /**
     * 操作给定的二叉树，将其变换为源二叉树的镜像 (递归)
     * @param root
     * @return
     */
    private static void mirror(TreeNode<Integer> root) {
        if (root == null) {
            return;
        }
        TreeNode<Integer> p = root;
        if (p.left != null || p.right != null) {

            TreeNode<Integer> node = p.left;
            p.left = p.right;
            p.right = node;

            mirror(p.left);
            mirror(p.right);
        }
    }

    /**
     * 操作给定的二叉树，将其变换为源二叉树的镜像 (非递归，使用栈模拟递归)
     * @param root
     */
    private static void mirror1(TreeNode<Integer> root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        TreeNode<Integer> p = root;

        stack.push(p);
        while (!stack.isEmpty()) {
            TreeNode<Integer> node = stack.pop();
            if (node.left != null || node.right != null) {
                TreeNode<Integer> temp = node.left;
                node.left = node.right;
                node.right = temp;
            }
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
    }

    /**
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。(有问题)
     *
     * https://www.cnblogs.com/wanglei5205/p/8684408.html
     *
     * 二叉搜索树的性质：所有左子树的节点小于根节点，所有右子树的节点值大于根节点的值。
     *
     * 算法步骤：
     * 1.后序遍历的最后一个值为root，在前面的数组中找到第一个大于root值的位置。
     * 2.这个位置的前面是root的左子树，右边是右子树。然后左右子树分别进行这个递归操作。
     * 3.其中，如果右边子树中有比root值小的直接返回false
     *
     * @param values
     * @param start
     * @param end
     * @return
     */
    private static boolean isBst(int[] values, int start, int end) {

        if (start >= end) { //结束条件
            return true;
        }

        /**
         * 寻找大于root的第一个节点，然后再分左右两部分
         */
        int index = start;
        for (; index < end; index++) {
            if (values[index] > values[end]) {
                break;
            }
        }

        /**
         * 若右子树有小于根节点的值，直接返回false
         */
        for (int i = index; i < end; i++) {
            if (values[i] < values[end]) {
                return false;
            }
        }


        /**
         * 最后减1是去掉根节点
         */
        return isBst(values, start, index - 1) && isBst(values, index, end - 1);
    }

    private static boolean isBst(int[] values) {
        if (values == null || values.length <= 0) {
            return false;
        }
        return isBst(values, 0, values.length -1);
    }

    /**
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，
     * 只能调整树中结点指针的指向。
     *
     * https://blog.csdn.net/qq_28081081/article/details/80795174
     *
     * 其实这是一个TreeNode的遍历，利用一个栈实现二叉树的中序遍历，题中说这是一颗二叉树，
     * 那么二叉树的中序遍历是一个有顺序的，那么这个时候只需要在中序遍历的时候当找到一个遍
     * 历节点的时候将这个节点保存起来，然后遍历下一个节点的时候将保存的节点的right域指向
     * 下一个结点，下一个结点的left域指向上一个结点。这样一来就形成了一个排序的双向链表。
     *
     * @param root
     * @return
     */
    private static TreeNode<Integer> convert(TreeNode<Integer> root) {

        Stack<TreeNode<Integer>> stack = new Stack<>();
        TreeNode<Integer> p = root;
        TreeNode<Integer> reRoot = null;
        TreeNode<Integer> listNode = null;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                TreeNode<Integer> node = stack.pop();
                if (listNode == null) {
                    listNode = node;
                    reRoot = listNode;
                } else {
                    /**
                     * 遍历下一个节点的时候将保存的节点的right域指向
                     * 下一个结点，下一个结点的left域指向上一个结点
                     */
                    listNode.right = node;
                    node.left = listNode;
                    listNode = listNode.right;
                }
                p = node.right;
            }
        }
        return reRoot;
    }



    public static void test() {
        int[] preOrder = new int[] {1, 2, 4, 7, 3, 5, 6, 8};
        int[] inOrder = new int[] {4, 7, 2, 1, 5, 3, 8, 6};

        TreeNode<Integer> root = reBuildTree(preOrder,inOrder);
        List<Integer> order = TreeNodeUtils.levelOrderTraversal(root);
        Log.i(TAG, "result: " + Arrays.toString(order.toArray()));
    }


    public static void testMirror() {
        TreeNode<Integer> root = TreeNodeUtils.createTreeNodeOfArray(8, 6, 10, 5, 7, 9);
        List<Integer> order = TreeNodeUtils.inorderTraversal(root);
        Log.i(TAG, "result: " + Arrays.toString(order.toArray()));

        //mirror(root);
        mirror1(root);

        order = TreeNodeUtils.inorderTraversal(root);
        Log.i(TAG, "result: " + Arrays.toString(order.toArray()));

    }

    public static void testBst() {
        //int[] postOrder = new int[] {4, 7, 5, 12, 10};
        int[] postOrder = new int[] {4, 6, 7, 5};

        final boolean isBst = isBst(postOrder);

        Log.i(TAG, "isBst: " + isBst);

    }

    public static void testConvert() {
        TreeNode<Integer> root = TreeNodeUtils.createTreeNodeOfArray(10, 5, 12, 4, 7);
        List<Integer> inorder = TreeNodeUtils.inorderTraversal(root);
        Log.i(TAG, "inorder: " + Arrays.toString(inorder.toArray()));

        TreeNode<Integer> reRoot = convert(root);
        TreeNode<Integer> p = reRoot;
        while (p != null) {
            Log.i(TAG, "listNode: " + p.data);
            p = p.right;
        }
    }

    /**
     * 判断两课二叉树是否对称
     */
    public static void testSymmetrical() {
        TreeNode<Integer> root1 = TreeNodeUtils.createTreeNodeOfArray(8, 6, 10, 5, 7, 9);
        List<Integer> order = TreeNodeUtils.inorderTraversal(root1);
        Log.i(TAG, "root1: " + Arrays.toString(order.toArray()));

        TreeNode<Integer> root2 = TreeNodeUtils.createTreeNodeOfArray(8, 6, 10, 5, 7, 9);
        mirror1(root2); //对应镜像
        List<Integer> order2 = TreeNodeUtils.inorderTraversal(root2);
        Log.i(TAG, "root2: " + Arrays.toString(order2.toArray()));

        boolean symmetrical = TreeNodeUtils.judge(root1, root2);
        Log.i(TAG, "root1 & roo2 judge: " + symmetrical);
    }
}
