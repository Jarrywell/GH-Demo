package com.android.test.demo.algorithm.string;

import android.util.Log;

import com.android.test.demo.algorithm.Algorithms;

import java.util.ArrayList;
import java.util.List;

public class TestString1 {
    private static final String TAG = Algorithms.TAG;


    /**
     * 题目：
     * 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置,
     * 如果没有则返回 -1（需要区分大小写）.
     *
     * 思路：全部由字母组成，char类型可以作为数组的下标
     * @param value
     * @return
     */
    private static int findNotReCharOfIndex(String value) {
        if (value == null || value.length() <= 0) {
            return -1;
        }

        char[] ch = value.toCharArray();
        int[] record = new int[256];
        for (int i = 0; i < ch.length; i++) {
            record[ch[i]]++;
        }
        for (int i = 0; i < ch.length; i++) {
            if (record[ch[i]] == 1) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 返回字符
     * @param value
     * @return
     */
    private static char findNotReChar(String value) {
        if (value == null || value.length() <= 0) {
            return 0;
        }
        char[] ch = value.toCharArray();
        int[] record = new int[256];
        for (int i = 0; i < ch.length; i++) {
            record[ch[i]]++;
        }
        for (int i = 0; i < ch.length; i++) {
            if (record[ch[i]] == 1) {
                return ch[i];
            }
        }
        return 0;
    }

    /**
     * 反转单词的顺序
     *  “I am a student.” --> “student. a am I”
     *
     *  思路：先整体反转，再空格为间隔符反转单词
     *
     * @param value
     * @return
     */
    private static String reversal(String value) {
        if (value == null || value.length() <= 0) {
            return value;
        }
        char[] chars = value.toCharArray();
        int length = chars.length;
        reversal(chars, 0, length - 1);

        int start = 0, end = 0;
        while (start < length && end < length) {
            if (chars[start] == ' ') {
                start++;
                end++;
            } else if (chars[end] == ' ' || chars[end] == '\0') {
                reversal(chars, start, --end);
                start = ++end;
            } else {
                end++;
            }
        }
        return new String(chars);
    }


    /**
     * 左旋转字符串
     * 思路：前n位反转，后几位反转，最后总的反转
     * @param value
     * @param n
     * @return
     */
    private static String leftRotate(String value, int n) {
        if (value == null || value.trim().length() <= 0) {
            return value;
        }

        int length = value.length();

        /**
         * 当length=3，n=4，其实相当于左旋转1位，所以需要取余
         */
        n = n % length;

        char[] chars = value.toCharArray();

        //先旋转前面的
        reversal(chars, 0, n - 1);

        //再旋转后面的字符串
        reversal(chars, n, length -1);

        //最后整体反转
        reversal(chars, 0, length - 1);

        return new String(chars);
    }

    private static void reversal(char[] ch, int start, int end) {
        char c;
        int i = start;
        int j = end;
        while (i < j) {
            c = ch[i]; ch[i] = ch[j]; ch[j] = c;
            i++; j--;
        }
    }


    /**
     * 将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0
     *
     * 思路：若为负数，则输出负数，字符0对应48,9对应57，
     * @param value
     * @return
     */
    private static int str2int(String value) {
        if (value == null || value.length() <= 0) {
            return 0;
        }
        int result = 0;
        int mark = 0;
        int start = 0;
        char[] chars = value.toCharArray();

        /**
         * 处理符号位
         */
        if (chars[0] == '-') {
            start = 1;
            mark = 1; //
        } else if (chars[0] == '+') {
            start = 1;
        }
        for (int i = start; i < chars.length; i++) {
            if (chars[i] < 48 || chars[i] > 57) {
                return 0;
            }
            result = result * 10 + (chars[i] - 48);
        }

        return mark == 0 ? result : -result;
    }

    /**
     * 输入一个字符串，打印出该字符串的所有排列
     * @param value
     * @return
     */
    private static List<String> permutation(String value) {
        List<String> result = new ArrayList<>();
        if (value == null || value.length() <= 0) {
            return result;
        }
        char[] origin = value.toCharArray();

        permutation(origin, 0, origin.length, result);

        return result;
    }

    /**
     * 输入一个字符串，打印出该字符串的所有排列
     *
     * @param chars
     * @param k
     * @param length
     * @return
     */
    private static void permutation(char[] chars, int k, int length, List<String> result) {
        /**
         * 递归结束的条件为n[i..n.len - 1]只有一个字符
         */
        if (k >= length - 1) {
            result.add(new String(chars));
            return;
        }
        char c;
        for (int i = k; i < length; i++) {
            c = chars[k]; chars[k] = chars[i]; chars[i] = c;
            permutation(chars, k + 1, length, result);
            c = chars[k]; chars[k] = chars[i]; chars[i] = c;
        }
    }


    public static void testFindNotReChar() {
        String value = "abaccdeffb";

        final int index = findNotReCharOfIndex(value);
        final char ch = findNotReChar(value);
        Log.i(TAG, "value: " + value + ", first not repeat ch index: " + index + ", ch: " + ch);
    }

    public static void testReversal() {
        String value = "I am a student.";
        final String result = reversal(value);
        Log.i(TAG, "origin: " + value + ", dest: " + result);
    }

    public static void testLeftRotate() {
        String value = "abcXYZdef";
        int n = 3;

        String result = leftRotate(value, n);

        Log.i(TAG, "origin: " + value + ", dest: " + result);
    }

    public static void testStr2Int() {
        String value = "+2345";

        final int result = str2int(value);

        Log.i(TAG, "value: " + value + ", str2int: " + result);
    }

    public static void testPermutation() {
        String value = "abc";

        List<String> result = permutation(value);
        for (String dest: result) {
            Log.i(TAG, dest);
        }

    }
}
