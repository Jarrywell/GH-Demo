package com.android.test.demo.lambda;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * des: https://github.com/shekhargulati/little-java-functions
 * author: libingyan
 * Date: 18-2-9 10:17
 */
public class TestFunction {
    private final String TAG = "TestFunction";

    public static void test() {
        TestFunction test = new TestFunction();
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
        test.test11();
    }

    /**
     * 将数组分割成特定大小的小数组
     * copyOfRange(): 将一个原始的数组original，从小标from开始复制，复制到小标to，生成一个新的数组。
     */
    private void test1() {
        int[] numbers = new int[20];
        Arrays.fill(numbers, 1);

        final int size = 9; //小数组的大小

        Log.i(TAG, "count: " + Math.ceil((double)numbers.length / size));

        int[][] result = IntStream.iterate(0, i -> i + size) //每个小数组的起始index
            .limit((long) Math.ceil(((double) numbers.length / size))) //总共有多少个小数组
            .mapToObj(cur -> Arrays.copyOfRange(numbers, cur,
                (cur + size > numbers.length ? numbers.length : cur + size))) //拷贝目标数据生成新数组
            .toArray(int[][]::new);

        /**
         * 遍历二维数组
         */
        //Arrays.stream(result).forEach(e -> Arrays.stream(e).forEach(v -> Log.i(TAG, "value: " + v)));

        Arrays.stream(result).forEach(e -> {
            Log.i(TAG, "size: " + e.length + ", start: " + e[0] + ", end: " + e[e.length - 1]);
        });
    }

    private void test2() {
        String[] first = {"a", "b"};
        String[] second = {"c", "d"};
        String[] third = contact(first, second);
        Stream.of(third).forEach(e -> Log.i(TAG, "value: " + e));
    }

    /**
     * 拼接两个数组,生成新数组
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    private <T> T[] contact(T[] first, T[] second) {
        return Stream.concat(Stream.of(first), Stream.of(second)) //生成新的Stream
            .toArray(length -> (T[])Arrays.copyOf(new Object[0], length, first.getClass())); //toArray()-->IntFunction<A[]> generator
    }


    private void test3() {
        Integer[] input = {1, 2};

        int[] result = deepFlatten(input);

        Arrays.stream(result).forEach(e -> Log.i(TAG, "value: " + e));
    }


    /**
     * Deep flattens an array.
     * @param input
     * @return
     */
    private int[] deepFlatten(Object[] input) {
        return Arrays.stream(input)
            .flatMapToInt(o -> {
                if (o instanceof Object[]) {
                    return Arrays.stream(deepFlatten((Object[]) o));
                }
                return IntStream.of((Integer) o);
            }).toArray();
    }


    private void test4() {
        int[] first = {1, 2, 3, 4};
        int[] second = {3, 4, 5, 6};

        final int[] third = difference(first, second);
        Arrays.stream(third).forEach(e -> Log.i(TAG, "thrid: " + e));

        final int[] four = difference(first, second, (a, b) -> (a - b));
        Arrays.stream(four).forEach(e -> Log.i(TAG, "four: " + e));

        Integer[] A = {1, 2, 3, 4};
        Integer[] B = {3, 4, 5, 6};
        Integer[] result = symmetricDifference(A, B);
        Arrays.stream(result).forEach(e -> Log.i(TAG, "result: " + e));
    }

    /**
     * 获取first中不在second中的元素
     * Create a Set from b, then use Arrays.stream().filter() on a to only keep values not contained in b.
     * @param first
     * @param second
     * @return
     */
    private int[] difference(int[] first, int[] second) {
        Set<Integer> set = Arrays.stream(second).boxed().collect(Collectors.toSet());
        return Arrays.stream(first).filter(v -> !set.contains(v)).toArray();
    }

    /**
     * 获取first中不在second中的元素
     * Uses Arrays.stream().filter and Arrays.stream().noneMatch() to find the appropriate values.
     * @param first
     * @param second
     * @param comparator
     * @return
     */
    private int[] difference(int[] first, int[] second, IntBinaryOperator comparator) {
        return Arrays.stream(first).filter(a -> Arrays.stream(second)
            .noneMatch(b -> comparator.applyAsInt(a, b) == 0)).toArray();
    }


    /**
     * 返回既不在first中，也不在second中的元素
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    private <T> T[] symmetricDifference(T[] first, T[] second) {
        Set<T> firstSet = new HashSet<T>(Arrays.asList(first));
        Set<T> secondSet = new HashSet<T>(Arrays.asList(second));

        return Stream.concat(Arrays.stream(first).filter(a -> !secondSet.contains(a)),
            Arrays.stream(second).filter(b -> !firstSet.contains(b)))
            .toArray(length -> (T[]) Arrays.copyOf(new Object[0], length, first.getClass()));
    }


    private void test5() {
        final int[] elements = {1, 2, 3, 2, 1, 4};
        final int[] result = distinctValue(elements);

        Arrays.stream(result).forEach(e -> Log.i(TAG, "value: " + e));
    }

    /**
     * 数组去重
     * @param elements
     * @return
     */
    private int[] distinctValue(int[] elements) {
        return Arrays.stream(elements).distinct().toArray();
    }


    private void test6() {
        final int[] elements = {0, 1, 2, 3, 4, 5, 3, 6};
        final int[] result = dropElements(elements, e -> e == 3); //{3, 4, 5, 3, 6}
        Arrays.stream(result).forEach(e -> Log.i(TAG, "value: " + e));
    }

    /**
     * 删除数组中的元素，直到传递函数返回true。返回数组中的其余元素。
     * @param elements
     * @param condition
     * @return
     */
    private int[] dropElements(int[] elements, IntPredicate condition) {
        while (elements.length > 0 && !condition.test(elements[0])) {
            elements = Arrays.copyOfRange(elements, 1, elements.length);
        }
        return elements;
    }


    private void test7() {
        final int[] elements = {0, 1, 2, 3, 4, 5, 3, 6};
        final int[] drops = dropRight(elements, 2); //{0, 1, 2, 3, 4, 5}
        Arrays.stream(drops).forEach(e -> Log.i(TAG, "value: " + e));
    }

    /**
     * 返回数组后面n个元素
     * @param elements
     * @param n
     * @return
     */
    private int[] dropRight(int[] elements, int n) {
        return n < elements.length
            ? Arrays.copyOfRange(elements, 0, elements.length - n)
            : new int[0];
    }


    private void test8() {
        final int[] elements = {0, 1, 2, 3, 4, 5, 3, 6}; // {0, 3, 3}
        final int[] result = everyNth(elements, 3);
        Arrays.stream(result).forEach(e -> Log.i(TAG, "value: " + e));
    }
    /**
     * 返回以nth为频率的数组元素
     * @param elements
     * @param nth
     * @return
     */
    private int[] everyNth(int[] elements, int nth) {
        return IntStream.range(0, elements.length)
            .filter(i -> i % nth == 0) //过滤被nth整除的元素
            .map(i -> elements[i]) //转换为对应i的elements元素
            .toArray();
    }


    private void test9() {
        final int[] elements = {0, 0, 1, 2, 3, 4, 5, 3, 6};
        final int value = indexOf(elements, 3);
        Log.i(TAG, "value: " + value);

        final int lastValue = lastIndexOf(elements, 3);
        Log.i(TAG, "last value: " + lastValue);

        final int result[] = initlizedOfValue(5, 22);
        Arrays.stream(result).forEach(e -> Log.i(TAG, "value: " + e));

        final Integer[] arrays = {1, 2, 3, 4, 6};
        final int code = isSorted(arrays);
        Log.i(TAG, "compare value: " + code);

        final int random = random(arrays);
        Log.i(TAG, "random value: " + random);

    }


    /**
     * 返回value在elements出现的index，若没有出现则返回-1
     * @param elements
     * @param value
     * @return
     */
    private int indexOf(int[] elements, int value) {
        return IntStream.range(0, elements.length)
            .filter(index -> elements[index] == value)
            .findFirst().orElse(-1);
    }


    /**
     * 返回value在elements最后出现的index，若没有出现则返回-1
     * IntStream.iterate().limit().filter()
     * @param elements
     * @param value
     * @return
     */
    private int lastIndexOf(int[] elements, int value) {
        return IntStream.iterate(elements.length -1, i -> i - 1)
            .limit(elements.length)
            .filter(index -> elements[index] == value)
            .findFirst().orElse(-1);
    }

    /**
     * 返回以特定值填充的数组
     * @param length
     * @param value
     * @return
     */
    private int[] initlizedOfValue(int length, int value) {
        return IntStream.generate(() -> value).limit(length).toArray();
    }


    /**
     * 判断数组是否有序，升序：1, 降序：-1, 无序：0
     * @param arrays
     * @param <T>
     * @return
     */
    private <T extends Comparable<? super T>> int isSorted(T[] arrays) {
        final int direction = arrays[0].compareTo(arrays[1]) < 0 ? 1 : -1;
        for (int i = 0; i < arrays.length; i++) {
            if (i == arrays.length - 1) {
                return direction;
            } else if (arrays[i].compareTo(arrays[i + 1]) * direction > 0) {
                return 0;

            }
        }
        return direction;
    }

    private <T> T random(T[] array) {
        return array[(int) Math.floor(Math.random() * array.length)];
    }


    private void test10() {
        Integer[] first = {1, 2, 3, 4};
        Integer[] second = {3, 4, 5, 6};
        Integer[] result = similarity(first, second);
        Arrays.stream(result).forEach(e -> Log.i(TAG, "value: " + e));

        Integer[] tail = tail(first);
        Arrays.stream(tail).forEach(e -> Log.i(TAG, "tail: " + e));

        Integer[] take = take(first, 2);
        Arrays.stream(take).forEach(e -> Log.i(TAG, "take: " + e));

        Integer[] takeRight = takeRight(first, 2);
        Arrays.stream(takeRight).forEach(e -> Log.i(TAG, "takeRight: " + e));

        Integer[] without = without(second, 4, 6);
        Arrays.stream(without).forEach(e -> Log.i(TAG, "without: " + e));
    }

    private void test11() {
        final String content = "12312312312a";
        final boolean number = isNumeric(content);
        Log.i(TAG, content + " is number: " + number);

        final String input = "this is Jarry";
        final String reverse = reverseString(input);
        Log.i(TAG, input + " reverse: " + reverse);
    }


    /**
     * 返回在两个数组中都出现的元素
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    private <T> T[] similarity(T[] first, T[] second) {
        return Arrays.stream(first)
            .filter(a -> Arrays.stream(second).anyMatch(b -> Objects.equals(a, b))) //a.anyMatch(b)
            // Make a new array of first's runtime type, but empty content:
            .toArray(length -> (T[]) Arrays.copyOf(new Object[0], length, first.getClass()));
    }

    /**
     * 去掉第一个元素
     * @param array
     * @param <T>
     * @return
     */
    private <T> T[] tail(T[] array) {
        return array.length > 1 ? Arrays.copyOfRange(array, 1, array.length) : array;
    }

    /**
     * 返回数组的前count个元素
     * @param array
     * @param count
     * @param <T>
     * @return
     */
    private <T> T[] take(T[] array, int count) {
        return count < array.length ? Arrays.copyOfRange(array, 0, count) : array;
    }

    /**
     * 返回数组的最后count个元素
     * @param array
     * @param count
     * @param <T>
     * @return
     */
    private <T> T[] takeRight(T[] array, int count) {
        return count < array.length ? Arrays.copyOfRange(array, array.length - count, array.length) : array;
    }

    /**
     * 数组过滤掉elements指定的元素集合
     * @param array
     * @param elements
     * @param <T>
     * @return
     */
    private <T> T[] without(T[] array, T... elements) {
        List<T> elementsList = Arrays.asList(elements);
        return Arrays.stream(array).filter(e -> !elementsList.contains(e))
            .toArray(length -> (T[]) Arrays.copyOf(new Object[0], length, array.getClass()));
    }

    private boolean isNumeric(String input) {
        return IntStream.range(0, input.length())
            .allMatch(index -> Character.isDigit(input.charAt(index)));
    }

    private String reverseString(String content) {
        return new StringBuilder(content).reverse().toString();
    }
}
