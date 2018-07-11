package com.android.test.demo.generics;

import android.util.Log;

/**
 * des:自限定
 * author: libingyan
 * Date: 18-7-11 14:27
 */
public class TestBounds5 {

    private final static String TAG = "TestBounds5";


    private static abstract class Holder<T> {
        private T element;

        public void set(T arg) {
            element = arg;
        }

        public T get() {
            return element;
        }

        public void f() {
            Log.i(TAG, "class: " + element.getClass().getSimpleName());
        }
    }

    /**
     * 自限定(指定参数类型为自己本身)
     * 自限定类型的本质就是：基类用子类代替其参数。这意味着泛型基类变成了一种其所有子类
     * 的公共功能模版，但是在所产生的类中将使用确切类型而不是基类型。
     */
    private static class SubHolder1 extends Holder<SubHolder1> {

    }

    /**
     * 不指定参数类型，类型会被默认为Object类型
     */
    private static class SubHolder2 extends Holder {

    }

    /**
     * 使用处指定参数类型
     * @param <T>
     */
    private static class SubHolder3<T> extends Holder<T> {

    }

    public static void test() {
        test1();

        test2();

        test3();
    }


    /**
     *
     */
    private static void test1() {
        /**
         * 声明自限定对象，参数类型是SubHolder1
         */
        SubHolder1 s1 = new SubHolder1();
        SubHolder1 s2 = new SubHolder1();

        /**
         * set()的T类型被替换成SubHolder1
         */
        s1.set(s2);

        /**
         * 返回类型是SubHolder1
         */
        SubHolder1 s3 = s1.get();

        s1.f(); //输出SubHolder1
    }


    private static void test2() {
        /**
         * 不指定参数类型，类型会被默认为Object类型
         */
        SubHolder2 s1 = new SubHolder2();
        SubHolder2 s2 = new SubHolder2();

        /**
         * set()的T类型被替换成Object
         */
        s1.set(s2);

        /**
         * 编译不过，返回类型是Object
         */
        //SubHolder2 s3 = s1.get();

        Object s3 = s1.get();

        s1.f(); //输出SubHolder2
    }


    private static void test3() {
        SubHolder3<SubHolder2> s1 = new SubHolder3();
        SubHolder2 s2 = new SubHolder2();

        s1.set(s2);

        SubHolder2 s3 = s1.get();

        s1.f();
    }


    /**
     * 自限定协变返回类型
     * @param <T>
     */
    interface GetInterface<T extends GetInterface<T>> {

        T get();
    }


    /**
     * 具体的子类型
     * 避免重写基类的方法
     */
    interface SubGetInterface extends GetInterface<SubGetInterface> {}



    public void test4(SubGetInterface g) {
        GetInterface s1 = g.get();
        SubGetInterface s2 = g.get();
    }


    /**
     * 自限定协变参数类型
     * 方法接受只能接受子类型而不是基类型为参数
     * @param <T>
     */
    interface SetInterface<T extends SetInterface<T>> {

        void set(T arg);
    }

    /**
     * 具体的子类型
     * 避免重写基类的方法
     */
    interface SubSetInterface extends SetInterface<SubSetInterface> {}


    public void test5(SubSetInterface s1, SubSetInterface s2, SetInterface sb) {
        /**
         * 编译通过
         */
        s1.set(s2);

        /**
         * 只能接受具体的子类型，不能接受SetInterface基类型
         */
        //s1.set(sb); //error
    }
}
