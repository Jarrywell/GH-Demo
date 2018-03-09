package com.android.test.demo.lambda;

import android.util.Log;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * des: https://docs.oracle.com/javase/8/docs/api/java/util/Spliterator.html
 * Date: 18-3-6 11:21
 */
public class TaggedArray<T> {

    private static final String TAG = "TaggedArray";

    private final Object[] elements;

    public static void test() {
        String[] data = {"data1", "data2", "data3", "data4", "data5",
            "data1", "data2", "data3", "data4", "data5",
            "data1", "data2", "data3", "data4", "data5"};

        String[] tags = {"tags1", "tags2", "tags3", "tags4", "tags5",
            "tags1", "tags2", "tags3", "tags4", "tags5",
            "tags1", "tags2", "tags3", "tags4", "tags5"};

        TaggedArray<String> taggedArray = new TaggedArray(data, tags);

        /**
         * 排序
         */
        taggedArray.stream()
            .parallel()
            .sorted(Comparator.naturalOrder())
            .forEach(e -> Log.i(TAG, "data: " + e));

        /**
         * 过滤、转换
         */
        taggedArray.stream()
            .filter(e -> e.endsWith("4"))
            .map(e -> e.toUpperCase())
            .mapToInt(e -> e.length())
            .forEach(e -> Log.i(TAG, "data: " + e));

    }


    /**
     * 构造函数
     * @param data
     * @param tags
     */
    TaggedArray(T[] data, T[] tags) {
        final int size = data.length;
        if (size != tags.length) {
            throw new IllegalArgumentException();
        }
        elements = new Object[size * 2];
        for (int i = 0, j = 0; i < size; i++) {
            elements[j++] = data[i];
            elements[j++] = tags[i];
        }
    }

    Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    Spliterator<T> spliterator() {
        return new TaggedArraySpliterator(elements, 0, elements.length);
    }



    static class TaggedArraySpliterator<T> implements Spliterator<T> {
        private final Object[] array;
        private int origin; //current index
        private final int fence; //length

        TaggedArraySpliterator(Object[] array, int origin, int fence) {
            this.array = array;
            this.origin = origin;
            this.fence = fence;
        }


        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (origin < fence) {
                action.accept((T) array[origin]);
                origin += 2;
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            for (; origin < fence; origin += 2) {
                action.accept((T) array[origin]);
            }
        }

        @Override
        public Spliterator<T> trySplit() {
            final int l = origin;
            int mid = ((l + fence) >>> 1) & ~1; // force midpoint to be even
            //Log.i(TAG, "thread: " + Thread.currentThread().getName() + ":(" + l + ", " + mid + ", " + fence + ")");
            if (l < mid) {
                origin = mid;
                return new TaggedArraySpliterator<T>(array, l, mid);
            } else { //to small
                return null;
            }
        }

        @Override
        public long estimateSize() {
            return (long) (fence - origin) / 2;
        }

        @Override
        public int characteristics() {
            /**
             * 指定数据集默认具有什么属性
             * 比如这里加上DISTINCT属性后，stream().distinct()这里将不起作用了（因为已经指定数据集元素唯一了）
             */
            return ORDERED | SIZED | IMMUTABLE | SUBSIZED/* | DISTINCT*/;
        }
    }
}
