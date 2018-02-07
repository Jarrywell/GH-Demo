package com.android.test.demo.lambda;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * des:
 * author: libingyan
 * Date: 18-2-6 11:24
 */
public class TestLambda {
    private static final String TAG = "TestLambda";
    private Context mContext;

    private TestLambda(Context context) {
        mContext = context;
    }

    public static void test(Context context) {
        TestLambda testLambda = new TestLambda(context);
        //testLambda.test1();
        //testLambda.test2();
        //testLambda.test3();
        //testLambda.test4();
        //testLambda.test5();
        //testLambda.test6();
        //testLambda.test7();
        //testLambda.test8();
        //testLambda.test9();
        //testLambda.test10();
        //testLambda.test11();
        testLambda.test12();
    }

    /**
     * lambda forEach迭代
     */
    private void test1() {
        /**
         * 简单lambda
         */
        Arrays.asList("a", "b", "c").forEach(e -> Log.i(TAG, "value: " + e));

        /**
         * lambda复杂语句使用{}，类似普通函数一样
         */
        Arrays.asList("a", "b", "c").forEach((String e) -> {
            String value = e.toUpperCase();
            Log.i(TAG, "supper value: " + value);
        });

        /**
         * 表达式中使用局部变量或者类成员变量
         */
        /*final */String separator = ",";
        Arrays.asList("a", "b", "c").forEach((String e) -> {
            Log.i(TAG, "value: " + (e + separator));
        });


        /**
         * lambda表达式替代Comparator
         */
        List<String> arrays = Arrays.asList("a", "c", "b");
        Collections.sort(arrays, (value1, value2) -> value1.compareTo(value2));
        arrays.forEach(e -> Log.i(TAG, e));


    }


    /**
     * lambda表达式替换匿名类
     */
    private void test2() {
        /**
         * 使用lambda表达式替换匿名类
         */
        new Thread(()-> {
            final int a = 10;
            final int b = 20;
            Log.i(TAG, "lambda runnable() of " + (a + b));
        }).start();

        /**
         * java8之前
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int a = 10;
                final int b = 20;
                Log.i(TAG, "thread runnable() of " + (a + b));
            }
        }).start();



        /**
         * lambda表达式替换监听器，注意参数的匹配
         */
        TestListener testListener = new TestListener();
        testListener.setOnTestListener((count, name) -> {
            Log.i(TAG, "on listener count: " + count + ", name: " + name);
        });
    }


    /**
     * lamdba表达式只能接受只有个抽象方法的接口
     */
    private /*abstract class*/ interface OnTestListener {
        void onSuccess(int count, String name);

        /**
         * 只能只有一个接口
         */
        /*void onFailed(double code);*/
    }

    private class TestListener {
        public void setOnTestListener(OnTestListener listener) {
            if (listener != null) {
                listener.onSuccess(100, "test listener!!!");
            }
        }
    }

    /**
     * 使用lambda表达式和函数式接口Predicate
     */
    private void test3() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        filter(languages, "J of start", name -> ((String) name).startsWith("J")); //过滤J开头的单词
        filter(languages, "a of end", name -> ((String) name).endsWith("a")); //过滤a结尾的单词
        filter(languages, "all", name -> true); //返回所有单词
        filter(languages, "empty", name -> false); //返回空
        filter(languages, "length > 4", name -> ((String) name).length() > 4);

    }

    private void filter(List<?> names, String tag, Predicate condition) {
        Log.i(TAG, "+++ start:" + tag + " +++");

        /**
         *
         */
        /*names.forEach(e -> {
            if (condition.test(e)) {
                Log.i(TAG, "value: " + e);
            }
        });*/

        /**
         * java 8 stream()的实现
         */
        names.stream().filter(name -> condition.test(name)) //过滤条件
            .forEach(name -> Log.i(TAG, "value: " + name)); //迭代
        Log.i(TAG, "+++ end +++");
    }

    /**
     * 如何在lambda表达式中加入Predicate
     */
    private void test4() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        Predicate<String> startWithJ = n -> n.startsWith("J");
        Predicate<String> length4 = n -> n.length() == 4;

        languages.stream().filter(startWithJ.and(length4)).forEach(name -> Log.i(TAG, "value: " + name));
    }


    /**
     * 使用lambda表达式的Map示例 --map():转换操作
     */
    private void test5() {
        List<Integer> values = Arrays.asList(100, 200, 300, 400, 500);
        values.stream().map(cost -> cost + 0.12 * cost).forEach(e -> Log.i(TAG, "value: " + e));
    }

    /**
     * lambda表达式的Map和Reduce示例 -- Map和Reduce操作是函数式编程的核心操作
     */
    private void test6() {
        List<Integer> values = Arrays.asList(100, 200, 300, 400, 500);
        double total = values.stream().map(cost -> cost + 0.12 * cost).reduce((sum, cost) -> sum + cost).get();
        Log.i(TAG, "total: " + total);
    }

    /**
     *通过过滤创建一个String列表
     */
    private void test7() {
        List<String> values = Arrays.asList("abc", "", "bcd", "", "defg", "jk");
        List<String> filter = values.stream().filter(x -> x.length() > 2).collect(Collectors.toList());
        Log.i(TAG, "values: " + values + ", filter: " + filter);
    }

    /**
     * 对列表的每个元素应用函数
     */
    private void test8() {
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        final String result = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        Log.i(TAG, "result: " + result);
    }

    /**
     * 复制不同的值，创建一个子列表
     */
    private void test9() {
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> result = numbers.stream().map(x -> x * x).distinct().collect(Collectors.toList());
        Log.i(TAG, "numbers: " + numbers + ", result: " + result);
    }

    /**
     * 计算集合元素的最大值、最小值、总和以及平均值
     */
    private void test10() {
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics summary = primes.stream().mapToInt(x -> x).summaryStatistics();
        Log.i(TAG, "max: " + summary.getMax() + ", min: " + summary.getMin() + ",avg: "
            + summary.getAverage() + ", count: " + summary.getCount());
    }


    private void test11() {
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        /**
         * lambda表达式定义定义
         */
        Comparator<String> comparator = (first, second) -> first.length() - second.length();
        Collections.sort(G7, comparator);
        Log.i(TAG, "sort: " + G7);
    }

    private void test12() {
        List<Task> values = new ArrayList<>();
        values.add(new Task(1, "title 1", 10));
        values.add(new Task(2, "title 2", 10));
        values.add(new Task(3, "title 3", 0));
        values.add(new Task(4, "title 4", 10));
        values.add(new Task(5, "title 5", 0));

        /**
         *
         */
        //values.stream().filter(value -> value.mValue == 10).forEach(e -> Log.i(TAG, "title: " + e.getTitle()));

        /**
         * 利用Predicate以及Function过滤满足条件的title
         */
        getTitles(values, e -> e.getValue() == 10, e -> e.getTitle()).forEach(e -> Log.i(TAG, "title: " + e));

    }

    /**
     *
     * @param task
     * @param filterTasks:Predicate用来定义对一些条件的检查
     * @param extractor: Function 需要一个值并返回一个结果
     * @param <R>
     * @return
     */
    private <R> List<R> getTitles(List<Task> task, Predicate<Task> filterTasks, Function<Task, R> extractor) {
        List<R> titles = new ArrayList();
        task.forEach(e -> {
            if (filterTasks.test(e)) {
                titles.add(extractor.apply(e));
            }
        });
        return titles;
    }
}
