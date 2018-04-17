package com.android.test.demo.hook;

import com.android.test.demo.joor.Reflect;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;

/**
 * des:
 * author: libingyan
 * Date: 18-4-17 19:40
 */
public class PmsHook {
    private static final String TAG = "PmsHook";

    public static void hook(Context context) {

        /**
         * 获取全局的ActivityThread对象
         */
        Object currentActivityThread = Reflect.on("android.app.ActivityThread")
            .call("currentActivityThread").get();

        /**
         * 获取ActivityThread里面原始的sPackageManager
         */
        Object sPackageManager = Reflect.on(currentActivityThread).field("sPackageManager").get();

        /**
         * 准备代理对象
         */
        Class<?> packageManagerInterface = Reflect.on("android.content.pm.IPackageManager").get();
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
            new Class[] {packageManagerInterface},
            new PmsHookHandler(sPackageManager));

        /**
         * 替换掉ActivityThread里面的sPackageManager字段
         */
        Reflect.on(currentActivityThread).set("sPackageManager", proxy);

        /**
         * 替换ApplicationPackageManager里面的mPm对象
         */
        Map<String, Reflect> fields = Reflect.on(context.getPackageManager()).fields();
        Log.i(TAG, "packageManager: " + context.getPackageManager());

        Object mPM = Reflect.on(context.getPackageManager()).field("mPM").get();
        Log.i(TAG, "mPM: " + mPM);

        /**
         * private final IPackageManager mPM; //mPM被final掉了，修改时抛出了异常，后续解决
         */
        //Reflect.on(context.getPackageManager()).set("mPM", proxy);
    }

    static class PmsHookHandler implements InvocationHandler {

        private Object base;

        PmsHookHandler(Object base) {
            this.base = base;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.i(TAG, "pms method is hooked! name: " + method.getName() + ", args: "
                + Arrays.toString(args));

            return method.invoke(base, args);
        }
    }
}
