package com.android.test.demo;

import com.android.test.demo.gradle.TestGradle;
import com.android.test.demo.memory.ActivityRefWatcher;
import com.android.test.demo.memory.TestRefWatcher;
import com.android.test.demo.nightmode.AppCompatNightMode;
import com.android.test.demo.plugin.Hooks;

import android.app.Application;
import android.content.Context;

/**
 * des:
 * author: libingyan
 * Date: 18-4-2 16:09
 */
public class App extends Application {

    private static App INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        AppCompatNightMode.initTheme(this);


        ActivityRefWatcher.install(this, new TestRefWatcher());

        TestGradle.test(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        /**
         * hook classLoader
         */
        Hooks.attachBaseContext(this);
    }

    public static Context getContext() {
        return INSTANCE.getApplicationContext();
    }

}
