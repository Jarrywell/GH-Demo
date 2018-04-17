package com.android.test.demo.hook;

import com.android.test.demo.joor.Reflect;

import android.app.ActivityManager;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * des:
 * author: libingyan
 * Date: 18-4-17 19:40
 */
public class AmsHook {
    private static final String TAG = "AmsHook";


    public static void hook() {
        /**
         * 获取静态变量android.app.ActivityManagerNative.gDefault
         */
        Object gDefault = Reflect.on("android.app.ActivityManagerNative")
            .field("gDefault").get();

        /**
         *ActivityManagerNative的gDefault对象里面原始的IActivityManager对象
         */
        Object rawIActivityManager = Reflect.on(gDefault).get("mInstance");

        Class<?> ActivityManagerInterface = Reflect.on("android.app.IActivityManager").get();

        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
            new Class[] {ActivityManagerInterface},
            new AmsHookHandler(rawIActivityManager));

        Reflect.on(gDefault).set("mInstance", proxy);
    }

    static class AmsHookHandler implements InvocationHandler {

        private Object base;

        AmsHookHandler(Object base) {
            this.base = base;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.i(TAG, "ams method is hooked! name: " + method.getName() + ", args: "
                + Arrays.toString(args));

            return method.invoke(base, args);
        }
    }
}
