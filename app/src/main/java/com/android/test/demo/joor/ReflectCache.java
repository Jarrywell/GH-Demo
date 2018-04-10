package com.android.test.demo.joor;



import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


class ReflectCache {
    private static final String TAG = "ReflectCache";
    /**
     * The cache of class, constructor, method, field
     */
    private static final Map<String, Object> sCache = new ConcurrentHashMap<>(); //HashMap??

    /**
     * enable of cache reflect
     */
    private boolean cache = false;
    

    public ReflectCache(boolean cache) {
        this.cache = cache;
    }

    public Class<?> getClass(String key) {
        final Object obj = get(key);
        if (obj != null && obj instanceof Class<?>) {
            return (Class<?>) obj;
        }
        return null;
    }

    public Constructor<?> getConstructor(String key) {
        final Object obj = get(key);
        if (obj != null && obj instanceof Constructor<?>) {
            return (Constructor<?>) obj;
        }
        return null;
    }

    public Method getMethod(String key) {
        final Object obj = get(key);
        if (obj != null && obj instanceof Method) {
            return (Method) obj;
        }
        return null;
    }

    public Field getField(String key) {
        final Object obj = get(key);
        if (obj != null && obj instanceof Field) {
            return (Field) obj;
        }
        return null;
    }

    public boolean contains(String key) {
        if (cache) {
            return sCache.containsKey(key);
        }
        return false;
    }

    private Object get(String key) {
        if (cache) {
            return sCache.get(key);
        }
        return null;
    }

    public boolean put(String key, Object reflectObj) {
        if (cache && reflectObj != null) {
            sCache.put(key, reflectObj);
            return true;
        }
        return false;
    }

    public String formatClassKey(String name, ClassLoader classLoader) {
        return String.format("%s%s", (classLoader == null ? "" : (classLoader.toString()) + "."), name);
    }

    public String formatFieldKey(Class<?> clazz, String name) {
        return clazz.getName() + "." + name;
    }

    public String formatMethodKey(Class<?> clazz, String name, Class<?>[] types) {
        return clazz.getName() + "." + name + "(" + formatArgsKey(types) + ")";
    }

    public String formatConstructorKey(Class<?> clazz, Class<?>[] types) {
        return clazz.getName() + "(" + formatArgsKey(types) + ")";
    }


    private static String formatArgsKey(Class<?>[] types) {
        String paramsKey = "";
        if (types != null && types.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (Class<?> classT : types) {
                builder.append(classT.getName());
                builder.append(",");
            }
            if (builder.length() > 0) {
                paramsKey = builder.substring(0, builder.length() - 1);
            }
        }
        return paramsKey;
    }


    public void release(boolean print) {
        if (print) {
            Set<String> keys = sCache.keySet();
            for (String key : keys) {
                Log.d(TAG, "key: " + key + ", value: " + sCache.get(key));
            }
        }
        sCache.clear();
    }
}
