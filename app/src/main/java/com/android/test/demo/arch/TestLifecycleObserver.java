package com.android.test.demo.arch;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.android.test.demo.log.DLog;

public class TestLifecycleObserver implements LifecycleObserver {
    private final String TAG = "TestLifecycleObserver";
    
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        DLog.d(TAG, "onCreate()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        DLog.d(TAG, "onStart()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        DLog.d(TAG, "onResume()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        DLog.d(TAG, "onPause()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        DLog.d(TAG, "onStop()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        DLog.d(TAG, "onDestroy()");
    }
}
