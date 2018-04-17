package com.android.test.demo.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by tech on 18-4-14.
 */

public class TestHelloWorld {
    private static final String TAG ="Proxy";
    public static void test() {
        IHelloWorld hw = new HelloWorld();
        hw.sayHello();

        /**
         * 打印出来看classLoader是同一个：dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.android.test.demo-2/base.apk"],
         * nativeLibraryDirectories=[/data/app/com.android.test.demo-2/lib/arm64, /system/lib64, /vendor/lib64]]]
         */
        Log.i(TAG, "hw classLoader: " + hw.getClass().getClassLoader());
        Log.i(TAG, "thread classLoader: " + Thread.currentThread().getContextClassLoader());


        /**
         * 获取这个对象实现了哪些接口，因为动态代理必须通过接口来实现
         */
        Class<?> interfaces[] = hw.getClass().getInterfaces();
        for (Class<?> clzz : interfaces) {
            Log.i(TAG, "interface: " + clzz);
        }

        InvocationHandler handler = new LoggerHandler<>(hw);
        IHelloWorld proxy = (IHelloWorld) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                hw.getClass().getInterfaces(), handler);

        /**
         * 打印出来看proxy和hw是同一个对象地址！！！
         */
        Log.i(TAG, "newProxyInstance: " + proxy);
        Log.i(TAG, "hw: " + hw);


        proxy.sayHello();
    }
}
