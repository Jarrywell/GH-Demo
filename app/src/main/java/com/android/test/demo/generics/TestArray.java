package com.android.test.demo.generics;

import android.util.Log;

/**
 * des:数组的协变
 * author: libingyan
 * Date: 18-7-11 10:31
 */
public class TestArray {
    private static final String TAG = "TestArray";

    class Fruit {}
    class Apple extends Fruit {}
    class Jonathan extends Apple {}
    class Orange extends Fruit {}

    public static void test() {
        TestArray test = new TestArray();
        test.test1();
    }

    public void test1() {
        /**
         * 创建了一个Apple数组，并将其赋值给一个Fruit数组引用,编译器和运行时都允许
         */
        Fruit[] fruits = new Apple[10];

        /**
         * 将子类对象放置到父类数组中,编译器和运行时都允许
         */
        fruits[0] = new Apple();
        fruits[1] = new Jonathan();

        try {
            /**
             * 将Apple的父类对象放置到子类数组中，编译器允许，但运行时检查抛出异常
             */
            fruits[2] = new Fruit();
        } catch (Exception e) {
            Log.i(TAG, "array exception!", e);
        }

        try {
            /**
             * 将Apple的兄弟对象放置到数组中，编译器允许，但运行时检查抛出异常
             */
            fruits[3] = new Orange();
        } catch (Exception e) {
            Log.i(TAG, "array exception!", e);
        }

    }
}
