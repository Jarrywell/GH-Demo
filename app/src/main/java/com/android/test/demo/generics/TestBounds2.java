package com.android.test.demo.generics;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * https://www.zhihu.com/question/25548135/answer/33605942
 */
public class TestBounds2 {

    /**
     * 上界demo
     * @param <T>
     */
    static class Demo1<T extends AbstractList> {}
    private static void test1() {

        /**
         * ArrayList是AbstractList的子类
         */
        Demo1<ArrayList> demo1 = null; //Ok

        /**
         * AbstractCollection不是子类，通不过
         */
        //Demo1<AbstractCollection> demo2 = null; //Error
    }


    /**
     * extends对泛型上限进行了限制,即T必须是Comparable<? super T>的子类，
     * 然后<? super T>表示Comparable<>中的类型下限为T！
     * @param <T>
     */
    static class Demo2<T extends Comparable<? super T>> {}
    private static void test2() {

        /**
         * 可以理解为<GregorianCalendar extends Comparable<Calendar>>是可以运行成功的！
         * 因为Calendar为GregorianCalendar的父类并且GregorianCalendar
         * 实现了Comparable<Calendar>
         */
        Demo2<GregorianCalendar> demo2 = null; //ok
    }


    /**
     * 反例
     * @param <T>
     */
    static class Demo3<T extends Comparable<T>> {}
    private static void test3() {

        /**
         * 因为<T extends Comparable<T>>相当于<GregorianCalendar extends Comparable<GregorianCalendar>>
         * 但是GregorianCalendar并没有实现Comparable<GregorianCalendar>而是实现的Comparable<Calendar>，
         * 这里不在限制范围之内所以会报错！
         */
        //Demo3<GregorianCalendar> demo3 = null; //error
    }

    /**
     * 反例
     * @param <T>
     */
    static class Demo4<T extends Comparable<? extends T>> {}
    private static void test4() {

        /**
         * 同上Demo3
         */
        //Demo4<GregorianCalendar> demo4 = null; //error
    }
}
