package com.android.test.joor.reflect;


import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class ReflectCache {
    /**
     * The cache of class.
     */
    private static final Map<String, Class<?>> sClasses = new HashMap<>();

    /**
     * The cache of constructor
     */
    private static final Map<String, Constructor<?>> sConstructors = new HashMap<>();

    /**
     * The cache of method
     */
    private static final Map<String, Method> sMethods = new HashMap<>();

    /**
     * The cache of field
     */
    private static final Map<String, Field> sFields = new HashMap<>();

    /**
     * enable of cache reflect
     */
    private boolean cache = false;
    

    public ReflectCache(boolean cache) {
        this.cache = cache;
    }

    public Class<?> getClass(String key) {
        if (cache) {
            return sClasses.get(key);
        }
        return null;
    }

    public boolean putClass(String key, Class<?> classT) {
        if (cache && classT != null) {
            sClasses.put(key, classT);
            return true;
        }
        return false;
    }

    public String formatClassName(String name, ClassLoader classLoader) {
        return String.format("%s.%s", name, (classLoader == null ? "" : classLoader.toString()));
    }

    public void release() {
        sClasses.clear();
        sConstructors.clear();
        sMethods.clear();
        sFields.clear();
    }
}
