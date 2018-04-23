package com.android.test.demo;

import com.android.test.demo.memory.ActivityRefWatcher;
import com.android.test.demo.memory.TestRefWatcher;
import com.android.test.demo.nightmode.AppCompatNightMode;

import android.app.Application;

/**
 * des:
 * author: libingyan
 * Date: 18-4-2 16:09
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppCompatNightMode.initTheme(this);


        ActivityRefWatcher.install(this, new TestRefWatcher());
    }


}
