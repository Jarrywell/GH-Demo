package com.android.test.demo.generics;


import java.util.ArrayList;
import java.util.List;

/**
 * Java中的逆变与协变
 * https://www.cnblogs.com/en-heng/p/5041124.html
 */
public class TestBounds3 {

    /**
     * Number是Integer父类
     */
    private static void test1() {

        /**
         * 普通用法1
         */
        Number number = new Integer(1);

        /**
         * 普通用法2
         */
        List<Number> list1 = new ArrayList<>();
        list1.add(new Integer(1));
        list1.add(new Float(1.0f));


        /**
         * error
         * list2.add(E e) -> list2.add(? extends Number)
         * 表示list2所持有的类型为Number与Number派生子类中的某一类型，其中包含Integer类型
         * 却又不特指为Integer类型（Integer像个备胎一样！！！），故add Integer时发生编译错误。
         */
        List<? extends Number> list2 = new ArrayList<>();
        //list2.add(new Integer(1)); //error
        //list2.add(new Float(1.0f)); //error


        /**
         * ok
         * list3.add(E e) -> list3.add(? super Number)
         * <? super Number>表示list3所持有的类型为Number与Number的基类中的某一类型，
         * 其中Integer与Float必定为这某一类型的子类；所以add方法能被正确调用。
         */
        List<? super Number> list3 = new ArrayList<>();
        list3.add(new Integer(1)); //ok
        list3.add(new Float(1.0f)); //ok
    }
}
