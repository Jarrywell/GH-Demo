package com.android.test.demo.generics;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TestBounds4 {

    private static final String TAG = "TestBounds4";


    private static void test1() {

        /**
         * 1.定义一个限定类型上界为Fruit的List
         */
        List<? extends Fruit> fruits = new ArrayList<>();

        /**
         * 编译器报错，不能添加任何类型的数据
         * 原因是：
         * List.add(T t)函数通过上面的类型指定后，参数会变成
         * <? extends Fruit>,从这个参数中，编译器无法知道需要哪个具体的Fruit子类型，
         * Orange、Banana甚至Fruit都可以，因此，为了保证类型安全，编译器拒绝任何类型。
         */
        //fruits.add(new Orange());
        //fruits.add(new Fruit());
        //fruits.add(new Object());

        /**
         * 编译器不会报错，因为null没有类型。但也没有任何意义。
         */
        fruits.add(null);

        /**
         * 此处正常！！ 由于我们定义是指定了上界为Fruit，因此此处的返回值肯定至少是Fruit类型的，
         * 而基类型仍可以引用子类型
         */
        Fruit f = fruits.get(0);
    }

    private void test2() {

    /**
     * 1.定义一个Object的List,作为原始数据列表
     */
    List<Object> objects = new ArrayList<>();
    objects.add(new Object()); //添加数据没有问题
    objects.add(new Orange()); //仍然没有问题，

    /**
     * 2.定义一个类型下界限定为Fruit的List,并将objects赋值给它。
     * 此时编译不会报错，因为满足逆变的条件。
     */
    List<? super Fruit> fruits = objects;

    /**
     * 3.add(T t)函数，编译器不会报错，因为fruits接受Fruit的基类类型，
     * 而该类型可以引用其子类型（多态性）
     */
    fruits.add(new Orange());
    //fruits.add(new Fruit());
    fruits.add(new RedApple());

    /**
     * 4.此处编译器报错，因为fruits限定的是下界是Friut类型,因此，
     * 编译器并不知道确切的类型是什么，没法找到一个合适的类型接受返回值
     */
    //Fruit f = fruits.get(0);

    /**
     * 5.此处不会报错，因为Object是Fruit的基类，满足下界的限定
     */
    Object obj = fruits.get(0);

    }

    private interface Fruit {
        void print();
    }

    private static class Apple implements Fruit {

        @Override
        public void print() {
            Log.i(TAG, "Apple");
        }
    }

    private static class Orange implements Fruit {

        @Override
        public void print() {
            Log.i(TAG, "Orange");
        }
    }

    private static class RedApple extends Apple {
        @Override
        public void print() {
            Log.i(TAG, "RedApple");
        }
    }
}
