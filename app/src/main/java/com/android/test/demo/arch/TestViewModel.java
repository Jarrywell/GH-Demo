package com.android.test.demo.arch;

import com.android.test.demo.log.DLog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;


/**
 * des:
 * author: libingyan
 * Date: 18-9-19 20:50
 */
public class TestViewModel extends ViewModel {

    private final String TAG = "TestViewModel";

    private int mIndex = 0;

    private MutableLiveData<String> mLiveData;
    private Handler mHandler;
    private Looper mLooper;

    public TestViewModel() {
        mLiveData = new MutableLiveData<String>();
    }

    public LiveData<String> getLiveData() {
        return mLiveData;
    }


    /**
     * 模拟异步加载数据
     */
    public void load() {
        if (mHandler == null) {
            HandlerThread thread = new HandlerThread("view-model-thread");
            thread.start();
            mLooper = thread.getLooper();
            mHandler = new Handler(mLooper);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                }

                final String value = makeString();

                observe(value);
            }
        });
    }


    /**
     * 通过livedata发出更新通知
     * @param value
     */
    private void observe(String value) {
        DLog.i(TAG, "observe value: " + value);
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mLiveData.setValue(value);
        } else {
            mLiveData.postValue(value);
        }
    }

    private String makeString() {
        return "this is live data string: " + (mIndex++);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mLooper != null) {
            mLooper.quit();
            mLooper = null;
        }
        mHandler = null;
    }
}
