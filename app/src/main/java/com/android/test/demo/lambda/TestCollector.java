package com.android.test.demo.lambda;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * des:
 * Date: 18-2-8 10:34
 */
public class TestCollector {
    private final String TAG = "TestCollector";

    public static void test() {
        TestCollector test = new TestCollector();
        //test.test1();
        //test.test2();
        //test.test3();
        //test.test4();
        //test.test5();
        //test.test6();
        //test.test7();
        //test.test8();
        //test.test9();
        //test.test10();
        //test.test11();
        test.test12();
    }

    /**
     * toList
     */
    private void test1() {
        List<Task> values = new ArrayList<>();
        values.add(new Task(2, "title 2", 10, Arrays.asList("read", "green", "all")));
        values.add(new Task(1, "title 1", 10, Arrays.asList("read", "blue", "all")));
        values.add(new Task(3, "title 3", 0, Arrays.asList("black", "green", "all")));
        values.add(new Task(4, "title 4", 10, Arrays.asList("read", "video", "all")));
        values.add(new Task(5, "title 5", 0, Arrays.asList("image", "green", "all")));

        List<String> list = values.stream().map(task -> task.getTitle())
            .collect(Collectors.toList());

        list.forEach(e -> Log.i(TAG, "list value: " + e));

        Set<String> set = values.stream().map(task -> task.getTitle()).collect(Collectors.toSet());
        set.forEach(e -> Log.i(TAG, "set value: " + e));

        /**
         * 作为key的title不能重复
         */
        Map<String, Task> map = values.stream().collect(Collectors.toMap(Task::getTitle, task -> task));
        Log.i(TAG, "map: " + map);

        /**
         * identity
         */
        Map<String, Task> map1 = values.stream().collect(Collectors.toMap(Task::getTitle, Function.identity()));
        Log.i(TAG, "map1: " + map1);

        /**
         * key冲突处理
         */
        List<Task> values2 = new ArrayList<>();
        values2.add(new Task(2, "title 2", 10, Arrays.asList("read", "green", "all")));
        values2.add(new Task(1, "title 2", 10, Arrays.asList("read", "blue", "all")));
        Map<String, Task> map2 = values2.stream().collect(Collectors.toMap(Task::getTitle,
            Function.identity(),
            (task1, task2) -> task2)); //key冲突处理
        Log.i(TAG, "map2: " + map2);

        /**
         * LinkedHashMap存放结果
         */
        Map<String, Task>map3 = values2.stream().collect(Collectors.toMap(Task::getTitle, Function.identity(), (task1, task2) -> task1,
            LinkedHashMap::new));
        Log.i(TAG, "map3: " + map3);

        /**
         * toCollection()指定收集器
         */
        Set<Task> set2 = values2.stream().collect(Collectors.toCollection(LinkedHashSet::new));
        Log.i(TAG, "set2: " + set2);
    }


    /**
     * 统计标题最长的task
     */
    private void test2() {
        List<Task> values = getTasks();

        Task task = values.stream().collect(Collectors.collectingAndThen(
            Collectors.maxBy((t1, t2) -> t1.getTitle().length() - t2.getTitle().length()), Optional::get));
        Log.i(TAG, "max title length: " + task.getTitle());
    }


    /**
     * 统计个数
     */
    private void test3() {
        List<Task> values = getTasks();

        final int count = values.stream().collect(Collectors.summingInt(task -> task.getTags().size()));
        Log.i(TAG, "tags count: " + count);
    }

    /**
     * 汇总
     */
    private void test4() {
        List<Task> values = getTasks();
        final String titles = values.stream().map(Task::getTitle).collect(Collectors.joining(";"));
        Log.i(TAG, "titles: " + titles);
    }


    /**
     * parallel()并行 & groupingBy分组
     */
    private void test5() {
        Map<String, List<Integer>> values = IntStream.rangeClosed(1, 16)
            .parallel() //并行
            .boxed()
            .collect(Collectors.groupingBy(e -> Thread.currentThread().getName())); //根据线程名分组

        values.forEach((k, v) -> Log.i(TAG, String.format("%s >> %s", k, v)));
    }

    /**
     * 根据value值分组
     */
    private void test6() {
        List<Task> values = getTasks();
        Map<Integer, List<Task>> result = values.stream().collect(Collectors.groupingBy(Task::getValue));
        result.forEach((k, v) -> Log.i(TAG, String.format("%s: %s", k, v)));
    }

    /**
     * 将task按tag标签分组
     */
    private void test7() {
        List<Task> values = getTasks();

        Map<String, List<Task>> taskTags = values.stream()
            .flatMap(task -> task.getTags().stream().map(tag -> new TaskTag(tag, task))) //将task列表拆分成TaskTag列表（tag - task）
            .collect(Collectors.groupingBy(TaskTag::getTag, Collectors.mapping(TaskTag::getTask, Collectors.toList()))); //将flatMap产生的TaskTag列表按tag进行分组

        taskTags.forEach((k, v) -> Log.i(TAG, String.format("%s: %s", k, v)));
    }

    /**
     * 统计tag对应task的数量
     */
    private void test8() {
        List<Task> values = getTasks();
        Map<String, Long> taskTags = values.stream()
            .flatMap(task -> task.getTags().stream().map(tag -> new TaskTag(tag, task)))
            .collect(Collectors.groupingBy(TaskTag::getTag, Collectors.counting()));

        taskTags.forEach((k, v) -> Log.i(TAG, String.format("%s: %s", k, v)));
    }

    /**
     * 将task列表，按value和id分组
     */
    private void test9() {
        List<Task> values = getTasks();

        Map<Integer, Map<Integer, List<Task>>> result = values.stream().collect(Collectors.groupingBy(Task::getValue, Collectors.groupingBy(Task::getId)));
        result.forEach((k, v) -> Log.i(TAG, String.format("%s: %s", k, v)));
    }

    /**
     * 根据value是否等于10进行二分分割列表
     */
    private void test10() {
        List<Task> values = getTasks();

        Map<Boolean, List<Task>> result = values.stream()
            .collect(Collectors.partitioningBy(task -> task.getValue() == 10));

        result.forEach((k, v) -> Log.i(TAG, String.format("%s: %s", k, v)));
    }


    /**
     * 对目标列表的统计
     */
    private void test11() {
        List<Task> values = getTasks();

        IntSummaryStatistics result = values.stream().map(task -> task.getTitle())
            .collect(Collectors.summarizingInt(e -> e.length()));

        Log.i(TAG, "count: " + result.getCount() + ", avg: " + result.getAverage()
            + ", max: " + result.getMax() + ", sum: " + result.getSum());
    }






    private class TaskTag {
        private String tag;
        private Task task;

        public TaskTag(String tag, Task task) {
            this.tag = tag;
            this.task = task;
        }

        public String getTag() {
            return tag;
        }

        public Task getTask() {
            return task;
        }

    }


    private List<Task> getTasks() {
        List<Task> values = new ArrayList<>();
        values.add(new Task(2, "title 2", 10, Arrays.asList("read", "green", "all")));
        values.add(new Task(1, "title 1", 10, Arrays.asList("read", "blue", "all")));
        values.add(new Task(3, "title 3", 0, Arrays.asList("black", "green", "all")));
        values.add(new Task(4, "title 4 of long", 10, Arrays.asList("read", "video", "all")));
        values.add(new Task(5, "title 5", 1, Arrays.asList("image", "green", "all")));
        return values;
    }


    /**
     * 单词统计
     */
    private void test12() {
        String content = "Suppose you want to create a summary of all the titles. Use the reduce operation, "
            + "which reduces the stream to a value. The reduce function takes a lambda which joins elements of the stream.";

        Map<String, Long> result = Arrays.stream(content.trim().split("\\s")) //以空白符为分隔条件过滤出所有单词
            .map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim()) //去掉非字母字符
            .filter(word -> word.length() > 0) //过滤掉空字符串
            .map(word -> new SimpleEntry(word, 1)) //将单词转换成word:count结构
            .collect(Collectors.groupingBy(SimpleEntry::getKey, Collectors.counting())); //按单词word分组，并统计分组中的数目

        result.forEach((k, v) -> Log.i(TAG, String.format("%s: %s", k, v)));

    }

    private class SimpleEntry {
        private String key;
        private long count;

        private SimpleEntry(String key, long count) {
            this.key = key;
            this.count = count;
        }

        public String getKey() {
            return key;
        }
    }

}
