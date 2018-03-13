package com.android.test.demo.lambda;

import android.util.Log;

import java.util.EnumMap;
import java.util.Map;

/**
 * des: 枚举类型示例，以及flag标记的位运算
 * author: libingyan
 * Date: 18-3-12 16:39
 */
public enum  StreamOpFlag {

    DISTINCT(0, set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP)),

    SORTED(1, set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP)),

    ORDERED(2, set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP).clear(Type.TERMINAL_OP).clear(Type.UPSTREAM_TERMINAL_OP)),

    SIZED(3, set(Type.SPLITERATOR).set(Type.STREAM).clear(Type.OP)),

    SHORT_CIRCUIT(12, set(Type.OP).set(Type.TERMINAL_OP));

    enum Type {
        SPLITERATOR,

        STREAM,

        OP,

        TERMINAL_OP,

        UPSTREAM_TERMINAL_OP
    }

    private final static int NON_BITS = 0b00;

    private final static int SET_BITS = 0b01;

    private final static int CLEAR_BITS = 0b10;

    private final static int PRESERVE_BITS = 0b11;

    private static MaskBuilder set(Type t) {
        return new MaskBuilder(new EnumMap<>(Type.class)).set(t);
    }


    private static class MaskBuilder {
        private Map<Type, Integer> map;

        private MaskBuilder(Map<Type, Integer> map) {
            this.map = map;
        }

        private MaskBuilder mask(Type t, Integer value) {
            map.put(t, value);
            return this;
        }

        MaskBuilder set(Type t) {
            return mask(t, SET_BITS);
        }

        MaskBuilder clear(Type t) {
            return mask(t, CLEAR_BITS);
        }

        MaskBuilder setAndClear(Type t) {
            return mask(t, PRESERVE_BITS);
        }

        Map<Type, Integer> build() {
            for (Type t : Type.values()) {
                map.putIfAbsent(t, NON_BITS);
            }
            return map;
        }
    }

    private final Map<Type, Integer> maskTable;

    private final int bitPosition;

    private final int set;

    private final int clear;

    private final int  preserve;


    StreamOpFlag(int position, MaskBuilder builder) {
        this.maskTable = builder.build();
        Log.i("StreamOpFlag", "StreamOpFlag construct position: " + position);
        position *= 2; //x2是因为SET_BITS等标志用两位表示的，移位操作以长度为单位是为了避免后续的&,|运算的便利
        this.bitPosition = position;
        this.set = SET_BITS << position;
        this.clear = CLEAR_BITS << position;
        this.preserve = PRESERVE_BITS << position;
    }

    public int set() {
        return set;
    }

    public int clear() {
        return clear;
    }

    public boolean isStreamFlag() {
        return maskTable.get(Type.STREAM) > 0;
    }

    public boolean isKnown(int flags) {
        return (flags & preserve) == set;
    }

    public boolean isCleared(int flags) {
        return (flags & preserve) == clear;
    }

    public boolean isPreserved(int flags) {
        return (flags & preserve) == preserve;
    }

    public boolean canSet(Type t) {
        return (maskTable.get(t) & SET_BITS) > 0;
    }

    public static final int SPLITERATOR_CHARACTERISTICS_MASK = createMask(Type.SPLITERATOR);

    public static final int STREAM_MASK = createMask(Type.STREAM);

    public static final int OP_MASK = createMask(Type.OP);

    public static final int TERMINAL_OP_MASK = createMask(Type.TERMINAL_OP);

    public static final int UPSTREAM_TERMINAL_OP_MASK = createMask(Type.UPSTREAM_TERMINAL_OP);


    /**
     * 把所有枚举类型(DISTINCT、SORTED等)对应Type的操作合并到一个整数中
     * @param t
     * @return
     */
    private static int createMask(Type t) {
        int mask = 0;
        for (StreamOpFlag flag : StreamOpFlag.values()) {
            mask |= flag.maskTable.get(t) << flag.bitPosition;
        }
        return mask;
    }

    private static final int FLAG_MASK = createFlagMask();

    /**
     * 构造所有操作的mask位宽及值
     * @return
     */
    private static int createFlagMask() {
        int mask = 0;
        for (StreamOpFlag flag : StreamOpFlag.values()) {
            mask |= flag.preserve;
        }
        return mask;
    }

    private static final int FLAG_MASK_IS = STREAM_MASK;

    private static final int FLAG_MASK_NOT = STREAM_MASK << 1;

    public static final int IS_DISTINCT = DISTINCT.set;

    public static final int NOT_DISTINCT =  DISTINCT.clear;

    public static final int IS_SORTED = SORTED.set;

    public static final int NOT_SORTED = SORTED.clear;

    private static int getMask(int flags) {
        return (flags == 0)
            ? FLAG_MASK
            : ~(flags | ((FLAG_MASK_IS & flags) << 1) | ((FLAG_MASK_NOT & flags) << 1));
    }

    public static int combineOpFlags(int newStreamOrOpFlags, int prevCombOpFlags) {
        return prevCombOpFlags & StreamOpFlag.getMask(newStreamOrOpFlags) | newStreamOrOpFlags;
    }

}
