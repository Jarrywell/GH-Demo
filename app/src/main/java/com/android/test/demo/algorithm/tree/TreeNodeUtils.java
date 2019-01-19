package com.android.test.demo.algorithm.tree;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

            /**
             * 指向父节点
             */
            node.next = pNode;

            return root;
        }
    }

    /**
     * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。
     *
     * https://blog.csdn.net/qq_28081081/article/details/80878763
     * https://www.cnblogs.com/hapjin/p/5827687.html
     *
     * 思路：
     * ①node节点有右孩子: 下一个结点就是以node结点的右孩子为根的子树中的最左下结点。
     * ②node节点没有右孩子时，往上找一个结点是该其父亲的左孩子,找到就返回该父节点
     *
     * @param root
     * @param pNode
     * @param <T>
     * @return
     */
    public static <T> TreeNode<T> nextNode(TreeNode<T> root, TreeNode<T> pNode) {
        if (pNode == null) {
            return null;
        }
        TreeNode<T> p = pNode;

        /**
         * 1.如果有右孩子，后继节点就是最左边的
         *
         * 如果有右子树，则找右子树的最左节点
         */
        if (p.right != null) {
            p = p.right;

            /**
             * 如果此时p没有左子树，那么它就是下一个结点 ，就是最左边的了
             */
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        /**
         * 非跟结点，并且没有右子树
         * 如果没有右孩子，判断是否是父节点的左孩子，是的话，返回，不是继续往上找
         */
        while (p.next != null) {

            /**
             * 找到一个结点是该其父亲的左孩子,找到就是返回父节点作为后继节点
             */
            if (p.next.left == pNode) {
                return p.next;
            }

            p = p.next;
        }

        return null;
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

    /**
     * 层序遍历 (非递归)
     * @param root
     * @param <T>
     * @return
     */
    public static <T> List<T> levelOrderTraversal(TreeNode<T> root) {
        List<T>result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        TreeNode<T> p = root;
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.offer(p);

        while (!queue.isEmpty()) {
            TreeNode<T> node = queue.poll();
            result.add(node.data);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return result;
    }

    /**
     * 二叉树的深度
     * 注意最后加1，因为左右子树的深度大的+根节点的深度1
     * @param root
     * @param <T>
     * @return
     */
    public static <T> int depth(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        int left = depth(root.left);
        int right = depth(root.right);

        return (left > right ? left : right) + 1;
    }


    /**
     * 输入一棵二叉树，判断该二叉树是否是平衡二叉树
     *
     * https://www.cnblogs.com/hapjin/p/5682704.html
     *
     * @param root
     * @param <T>
     * @return
     */
    public static <T> boolean isBalanced(TreeNode<T> root) {
        if (root == null) {
            return true;
        }

        /**
         * 分别求出左右子树的高度
         */
        final int left = depth(root.left);
        final int right = depth(root.right);

        if (Math.abs(left - right) > 1) {
            return false;
        } else {
            return isBalanced(root.left) && isBalanced(root.right);
        }
    }

    /**
     * 请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
     *
     * @param root
     * @param <T>
     * @return
     */
    public static <T extends Comparable> boolean isSymmetrical(TreeNode<T> root) {
        if (root == null) {
            return true;
        }
        return judge(root.left, root.right);
    }

    /**
     * 判断两棵二叉树是否对称
     *
     * 思路：
     * A.左边 == B.右边， B.右边 == A.左边
     *
     * @param left
     * @param right
     * @param <T>
     * @return
     */
    public static <T extends Comparable> boolean judge(TreeNode<T> left, TreeNode<T> right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left.data.compareTo(right.data) != 0) {
            return false;
        }
        return judge(left.left, right.right) && judge(left.right, right.left);
    }

    public static void test() {
        //final Integer p[] = {4, 5, 6, 1, 2, 3};
        //final Integer p[] = {5, 3, 7, 2, 4, 6, 8};
        final Integer p[] = {10, 5, 15, 3, 7, 11, 17, 6};

        TreeNode<Integer> root = createTreeNodeOfArray(p);

        TreeNode<Integer> node = search(root, 1);
        Log.i(TAG, "value: " + (node != null ? node.data : "@null"));

        List<Integer> inOrder = inorderTraversal(root);
        Log.i(TAG, "inOrder: " + Arrays.toString(inOrder.toArray()));

        List<Integer> preOrder = preOrderTraversal(root);
        Log.i(TAG, "preOrder: " + Arrays.toString(preOrder.toArray()));

        List<Integer> postOrder = postOrderTraversal(root);
        Log.i(TAG, "postOrder: " + Arrays.toString(postOrder.toArray()));

        List<Integer> levelOrder = levelOrderTraversal(root);
        Log.i(TAG, "levelOrder: " + Arrays.toString(levelOrder.toArray()));

        final int depth = depth(root);
        Log.i(TAG, "depth: " + depth);

        /**
         * 平衡
         */
        boolean balanced = isBalanced(root);
        Log.i(TAG, "balanced: " + balanced);


        /**
         * 不平衡
         */
        root = createTreeNodeOfArray(20, 8, 30, 5, 10, 7, 15, 14, 16);
        inOrder = inorderTraversal(root);
        Log.i(TAG, "inOrder: " + Arrays.toString(inOrder.toArray()));

        balanced = isBalanced(root);
        Log.i(TAG, "balanced: " + balanced);

        /**
         * 是否对称
         */
        boolean symmetrical = isSymmetrical(root);
        Log.i(TAG, "symmetrical: " + symmetrical);
    }

}