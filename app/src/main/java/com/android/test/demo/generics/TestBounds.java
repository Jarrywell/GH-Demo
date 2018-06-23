package com.android.test.demo.generics;

import android.util.Log;

/**
 * 通配符测试
 * https://www.zhihu.com/question/20400700
 *
 * 通配符<?>和类型参数<T>的区别就在于，对编译器来说所有的T都代表同一种类型,但通配符<?>没有这种约束.
 */
public class TestBounds {

    private static final String TAG = "TestBounds";

    /**
     * 验证下界：subscribe(<? super Apple>)
     */
    public static void test1() {
        /**
         * subscribe测试下界符：Plate<? super Apple>
         */
        Observer1<Apple> observer1 = new Observer1<>(new Apple());

        /**
         * p1为Plate<Apple>，Apple满足subscribe()的Plate<? super Apple>
         */
        Plate<Apple> p1 = new Plate<>(new Apple());
        observer1.subscribe(p1);

        /**
         * p2为Plate<Fruit>，Fruit满足subscribe()的Plate<? super Apple>
         */
        Plate<Fruit> p2 = new Plate<>(new Apple());
        observer1.subscribe(p2);

        /**
         * p3为Plate<RedApple>，RedApple不满足subscribe()的Plate<? super Apple>
         */
        //Plate<RedApple> p3 = new Plate<>(new RedApple());
        //observer1.subscribe(p3);
    }

    /**
     * 验证上界：subscribe(<? extends Apple>)
     */
    public static void test2() {
        /**
         * subscribe测试上界符：Plate<? extend Apple>
         */
        Observer2<Apple> observer2 = new Observer2<>(new Apple());

        /**
         * p1为Plate<Apple>，Apple满足subscribe()的Plate<? extends Apple>
         */
        Plate<Apple> p1 = new Plate<>(new Apple());
        observer2.subscribe(p1);

        /**
         * p2为Plate<Fruit>，Fruit不满足subscribe()的Plate<? extends Apple>
         */
        //Plate<Fruit> p2 = new Plate<>(new Apple());
        //observer2.subscribe(p2);

        /**
         * p3为Plate<RedApple>，RedApple满足subscribe()的Plate<? extends Apple>
         */
        Plate<RedApple> p3 = new Plate<>(new RedApple());
        observer2.subscribe(p3);
    }


    /**
     *  上界<? extends T>，副作用
     */
    private static void test3() {

        /**
         * 使用<? extends Fruit>创建容器
         */
        Plate<? extends Fruit> p1 = new Plate<>(new Apple());

        /**
         * 这里不能set：原因是编译器只知道容器内是Fruit或者它的派生类，但具体是什么类型不知道。
         * 可能是Fruit？可能是Apple？ 所以就都不允许了
         */
        //p1.set(new Apple());
        //p1.set(new RedApple());


        /**
         * 读取出来的东西只能存放在Fruit或它的基类里
         */
        Fruit fruit = p1.get();
        Object obj = p1.get();
        //Apple apple = p1.get(); //error
    }

    /**
     * 下界<? super T>的副作用
     */
    private static void test4() {

        /**
         * 使用<? super Apple>创建容器
         */
        Plate<? super Apple> p1 = new Plate<>(new Apple());

        /**
         * 下界规定了元素的最小粒度的下限，实际上是放松了元素的类型控制。既然元素是Apple的基类，那往里存粒度比Apple
         * 小的都可以，即：
         */
        p1.set(new Apple());
        p1.set(new RedApple());

        /**
         * 但往外读取元素比较费劲，只有基类Object才能装得下,元素的基本信息丢失
         */
        //Fruit fruit = p1.get(); //error
        //Apple apple = p1.get(); //error
        Object obj = p1.get(); //ok
    }



    /**
     * 下边界：(? super T)
     * @param <T>
     */
    private static final class Observer1<T> {
        private T item;

        public Observer1(T t) {
            item = t;
        }

        void subscribe(Plate<? super T> plate) {

        }
    }

    /**
     * 上边界：(? extends T)
     * @param <T>
     */
    private static final class Observer2<T> {
        private T item;

        public Observer2(T t) {
            item = t;
        }

        void subscribe(Plate<? extends T> plate) {

        }
    }


    private static final class Plate<T> {
        private T item;

        Plate(T t) {
            item = t;
        }

        public void set(T t) {
            item = t;
        }

        public T get() {
            return item;
        }

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

    private static class RedApple extends Apple {
        @Override
        public void print() {
            Log.i(TAG, "RedApple");
        }
    }
}
