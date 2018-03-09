package com.android.test.demo.lambda;

import android.util.Log;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Comparator.naturalOrder;

/**
 * des:
 * author: libingyan
 * Date: 18-3-6 16:28
 */
public class TaggedArray1<T> {
    private final static String TAG = "TaggedArray";

    private final Object[] elements;

    TaggedArray1(T[] data) {
        elements = Arrays.copyOfRange(data, 0, data.length);
    }

    public static void test() {
        String[] data = {"data1", "data2", "data3", "data4", "data5",
            "data1", "data2", "data3", "data4", "data5",
            "data1", "data2", "data3", "data4", "data5"};

        TaggedArray1<String> taggedArray = new TaggedArray1(data);

        /**
         * 排序
         */
        taggedArray.stream()
            .parallel()
            .sorted(naturalOrder())
            .forEachOrdered(e -> Log.i(TAG, "data: " + e));
    }

    Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false); //使用平行会出错，原因还不能定位出来
    }

    Spliterator<T> spliterator() {
        return new ArraySpliterator(elements, 0);
    }

    static class ArraySpliterator<T> implements Spliterator<T> {
        private final Object[] array;
        private int index;
        private final int fence;
        private final int characteristics;

        ArraySpliterator(T[] array, int characteristics) {
            this(array, 0, array.length, characteristics);
        }


        ArraySpliterator(T[] array, int origin, int fence, int characteristics) {
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.characteristics = characteristics | SIZED | SUBSIZED;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            if (index >= 0 && index < fence) {
                T e = (T) array[index++];
                action.accept(e);
                return true;
            }
            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            final int lo = index;
            final int mid = (index + fence) >>> 1;
            return lo >= mid ? null : new ArraySpliterator(array, lo, index = mid, characteristics);
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Object[] a; int i, hi; // hoist accesses and checks from loop
            if (action == null) {
                throw new NullPointerException();
            }
            if ((a = array).length >= (hi = fence) &&
                (i = index) >= 0 && i < (index = hi)) {
                do { action.accept((T)a[i]); } while (++i < hi);
            }
        }

        @Override
        public long estimateSize() {
            return (long) (fence - index);
        }

        @Override
        public int characteristics() {
            return characteristics;
        }
    }

}
