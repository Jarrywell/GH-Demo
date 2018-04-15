package com.android.test.demo.proxy;

import android.util.Log;

/**
 * Created by tech on 18-4-14.
 */

public class HelloWorld implements IHelloWorld {

    @Override
    public void sayHello() {
        Log.i(TAG, "hello World!!!");
    }
}
