package com.android.test.demo.plugin.activitys;

import com.android.test.demo.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * des: 模拟插件中的activity,没有在manifest中注册
 * author: libingyan
 * Date: 18-8-29 15:05
 */
public class Plugin1Activity extends AppCompatActivity {

    private final String TAG = "Plugin1Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin1);

        Log.i(TAG, "onCreate()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
