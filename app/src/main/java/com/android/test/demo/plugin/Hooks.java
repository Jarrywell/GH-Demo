package com.android.test.demo.plugin;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * des:
 * author: libingyan
 * Date: 18-8-29 16:02
 */
public class Hooks {

    private static final String TAG = "Hooks";

    public static final boolean ENABLE = true;


    /**
     * hook掉apk的classLoader
     * @param application
     */
    public static void attachBaseContext(Application application) {

        if (!ENABLE) {
            Log.i(TAG, "attachBaseContext() of hook don't enable!!!");
            return;
        }

        try {
            Context base = application.getBaseContext();
            ClassLoader originClassLoader = base.getClassLoader();

            Object oPackageInfo = getField(base, base.getClass(), "mPackageInfo");
            // 获取mPackageInfo.mClassLoader
            ClassLoader oClassLoader = (ClassLoader) getField(oPackageInfo, oPackageInfo.getClass(), "mClassLoader");

            Log.i(TAG, "context.getClassLoader() classLoader: " + originClassLoader);

            Log.i(TAG, "mPackageInfo.mClassLoader: " + oClassLoader); //相等

            // 将新的ClassLoader写入mPackageInfo.mClassLoader
            ClassLoader destClassLoader = new TestClassLoader(originClassLoader);
            setField(oPackageInfo, oPackageInfo.getClass(), "mClassLoader", destClassLoader);

            Thread.currentThread().setContextClassLoader(destClassLoader);

        } catch (Exception e) {
            Log.i(TAG, "hookClassLoader exception!", e);
        }
    }


    /**
     * 反射给对象中的属性重新赋值
     */
    private static void setField(Object obj, Class<?> cl, String field, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cl.getDeclaredField(field);
        declaredField.setAccessible(true);
        declaredField.set(obj, value);
    }

    /**
     * 反射得到对象中的属性值
     */
    private static Object getField(Object obj, Class<?> cl, String field) throws NoSuchFieldException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }
}
