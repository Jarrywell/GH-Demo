package com.android.test.demo.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * Created by tech on 18-4-14.
 */

public class LoggerHandler<T extends IHelloWorld> implements InvocationHandler {
    private final String TAG = "Proxy";
    private T target;

    public LoggerHandler(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i(TAG, "start log!");

        Object result = method.invoke(target, args);

        Log.i(TAG, "end log!");

        return result;
    }
}
