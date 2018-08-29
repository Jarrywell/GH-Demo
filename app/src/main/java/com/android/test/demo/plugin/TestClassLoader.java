package com.android.test.demo.plugin;

import com.android.test.demo.plugin.activitys.OriginActivity;
import com.android.test.demo.plugin.activitys.Plugin1Activity;

import android.util.Log;

import dalvik.system.PathClassLoader;

/**
 * des: 模拟加载插件的classLoader
 * author: libingyan
 * Date: 18-8-29 15:54
 */
public class TestClassLoader extends PathClassLoader {

    private static final String TAG = "TestClassLoader";

    private ClassLoader mOriginClassLoader;

    public TestClassLoader(ClassLoader originClassLoader) {
        super("", originClassLoader.getParent());
        mOriginClassLoader = originClassLoader;
    }


    /**
     * apk首次实例化类时都会走到loadClass()方法来
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Log.i(TAG, "loadClass() name: " + name);

        final String originActivity = OriginActivity.class.getName(); //已注册
        final String pluginActivity = Plugin1Activity.class.getName(); //未注册

        if (name.equals(originActivity)) {
            name = pluginActivity;
        }

        try {
            return mOriginClassLoader.loadClass(name);
        } catch (Exception e) {
            return super.loadClass(name);
        }
    }

}
