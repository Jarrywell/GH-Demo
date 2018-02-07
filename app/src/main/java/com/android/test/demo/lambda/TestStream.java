package com.android.test.demo.lambda;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * des:
 * Date: 18-2-7 11:12
 */
public class TestStream {
    private final String TAG = "TestStream";

    public static void test() {
        TestStream test = new TestStream();
        //test.test1();
        //test.test2();
        //test.test3();
        //test.test4();
        //test.test5();
        //test.test6();
        //test.test7();
        //test.test8();
        //test.test9();
        test.test10();
    }

    /**
     * stream generate()
     */
    private void test1() {
        Stream<String> stream = Stream.generate(() -> UUID.randomUUID().toString());
        stream.limit(10).forEach(e -> Log.i(TAG, "uuid: " + e));
    }

    /**
     * 功能：过滤value=10的task,并按id排序的title列表的前两个
     */
    private void test2() {
        List<Task> values = new ArrayList<>();
        values.add(new Task(2, "title 2", 10));
        values.add(new Task(1, "title 1", 10));
        values.add(new Task(3, "title 3", 0));
        values.add(new Task(4, "title 4", 10));
        values.add(new Task(5, "title 5", 0));

        List<String> titles = values.stream().filter(task -> task.getValue() == 10)
            //.sorted(Comparator.comparing(e -> e.getId()))
            .sorted(Comparator.comparing(Task::getId).reversed())
            .map(e -> e.getTitle())
            .skip(1) //跳过的条数
            .limit(2) //最大多少个
            .collect(Collectors.toList());

        titles.forEach(e -> Log.i(TAG, "value: " + e));
    }


    private void test3() {
        List<Task> values = new ArrayList<>();
        values.add(new Task(1, "title 1", 10));
        values.add(new Task(1, "title 1", 10));
        values.add(new Task(1, "title 1", 0));
        values.add(new Task(2, "title 2", 10));
        values.add(new Task(2, "title 2", 0));

        /*List<String> titles = values.stream().distinct()
            .map(e -> e.getTitle()).collect(Collectors.toList());
        titles.forEach(e -> Log.i(TAG, "value: " + e));*/

        values.stream().distinct() //打日志看，没有走Task的equals
            .forEach(e -> Log.i(TAG, "value: " + e.getTitle()));

        List<String> strings = Arrays.asList("a", "b", "c", "a", "b", "c", "d");
        strings.stream().distinct().forEach(e -> Log.i(TAG, "value: " + e));
    }

    private void test4() {
        List<Task> values = new ArrayList<>();
        values.add(new Task(2, "title 2", 10));
        values.add(new Task(1, "title 1", 10));
        values.add(new Task(3, "title 3", 0));
        values.add(new Task(4, "title 4", 10));
        values.add(new Task(5, "title 5", 0));

        final long count = values.stream().filter(task -> task.getValue() == 10).count();

        Log.i(TAG, "count: " + count);

    }

    /**
     * flatMap合并操作
     *
     * 非重复的列出所有task中的全部标签
     */
    private void test5() {
        List<Task> values = new ArrayList<>();
        values.add(new Task(2, "title n", 10, Arrays.asList("read", "green")));
        values.add(new Task(1, "title n", 10, Arrays.asList("read", "blue")));
        values.add(new Task(3, "title 3", 0, Arrays.asList("black", "green")));
        values.add(new Task(4, "title 4", 10, Arrays.asList("read", "video")));
        values.add(new Task(5, "title n", 0, Arrays.asList("image", "green")));

        List<String> tags = values.stream()
            .flatMap(task -> task.getTags().stream()) //将所有的tags合并为一个stream
            .distinct().collect(Collectors.toList()); //去重，转换List

        tags.forEach(e -> Log.i(TAG, "value: " + e));
    }

    /**
     * stream的匹配
     */
    private void test6() {
        List<Task> values = new ArrayList<>();
        values.add(new Task(2, "title n", 10, Arrays.asList("read", "green", "all")));
        values.add(new Task(1, "title n", 10, Arrays.asList("read", "blue", "all")));
        values.add(new Task(3, "title 3", 0, Arrays.asList("black", "green", "all")));
        values.add(new Task(4, "title 4", 10, Arrays.asList("read", "video", "all")));
        values.add(new Task(5, "title n", 0, Arrays.asList("image", "green", "all")));

        /**
         * 全匹配（每一个元素都包含，则返回true）
         */
        boolean allMatch = values.stream().filter(task -> task.getValue() == 10)
            .allMatch(task -> task.getTags().contains("all"));
        Log.i(TAG, "allMatch all: " + allMatch);

        /**
         * 匹配任意一个(任意一个元素包含，则返回true)
         */
        boolean anyMatch = values.stream().filter(task -> task.getValue() == 10)
            .anyMatch(task -> task.getTags().contains("video"));

        Log.i(TAG, "anyMatch video: " + anyMatch);

        /**
         * 全不匹配（没有任何一个元素包含，则返回true）
         */
        boolean noneMatch = values.stream().filter(task -> task.getValue() == 10)
            .noneMatch(task -> task.getTags().contains("the"));

        Log.i(TAG, "noneMatch the: " + noneMatch);

        /**
         * 找到任意一个满足条件的task
         */
        Optional<Task> any = values.stream().filter(task -> task.getValue() == 10)
            .findAny();

        Log.i(TAG, "findAny any: " + any.get().getId());

        /**
         * 找到第一个满足条件的task
         */
        Optional<Task> first = values.stream().filter(task -> task.getValue() == 10)
            .findFirst();

        Log.i(TAG, "findAny first: " + first.get().getId());
    }

    /**
     * 创建一个所有title的总览
     * reduce能够把stream变成成一个值。
     * reduce函数接受一个可以用来连接stream中所有元素的lambda表达式。
     */
    private void test7() {
        List<Task> values = new ArrayList<>();
        values.add(new Task(2, "title 2", 10, Arrays.asList("read", "green", "all")));
        values.add(new Task(1, "title 1", 10, Arrays.asList("read", "blue", "all")));
        values.add(new Task(3, "title 3", 0, Arrays.asList("black", "green", "all")));
        values.add(new Task(4, "title 4", 10, Arrays.asList("read", "video", "all")));
        values.add(new Task(5, "title 5", 0, Arrays.asList("image", "green", "all")));

        String titles = values.stream().map(task -> task.getTitle())
            .reduce((first, seconde) -> first + " *** " + seconde).get();

        Log.i(TAG, "titles: " + titles);
    }

    /**
     *
     */
    private void test8() {
        /**
         * range(), 开区间
         */
        IntStream.range(0, 10).forEach(e -> Log.i(TAG, "value:" + e));

        /**
         * rangeClosed(),闭区间
         */
        IntStream.rangeClosed(0, 10).forEach(e -> Log.i(TAG, "value:" + e));

        /**
         * concat(), 合并
         */
        IntStream.concat(IntStream.range(1, 3), IntStream.range(7, 10))
            .forEach(e -> Log.i(TAG, "value: " + e));

        /**
         * iterate(), 创建无限的stream
         */
        LongStream.iterate(1, e -> e + 3).filter(e -> e % 2 == 0).limit(10)
            .forEach(e -> Log.i(TAG, "value: " + e));

    }


    /**
     * stream对数组的处理
     */
    private void test9() {
        String[] tags = {"java", "git", "lambdas", "machine-learning"};
        Arrays.stream(tags).map(e -> e.toUpperCase()).forEach(e -> Log.i(TAG, "value: " + e));
    }


    /**
     * parallel并行处理
     */
    private void test10() {
        List<Integer> values = IntStream.rangeClosed(1, 16).parallel().boxed()
            .collect(Collectors.toList());

        values.forEach(e -> Log.i(TAG, "value: " + e));
    }


}
