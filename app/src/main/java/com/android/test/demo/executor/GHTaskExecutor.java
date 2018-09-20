package com.android.test.demo.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * des: 全局线程池
 * author: libingyan
 * Date: 18-9-20 14:15
 */
public class GHTaskExecutor extends TaskExecutor {

    private static volatile GHTaskExecutor sInstance;

    @NonNull
    private TaskExecutor mDelegate;

    @NonNull
    private TaskExecutor mDefaultTaskExecutor;

    @NonNull
    private static final Executor sMainThreadExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            getInstance().postToMainThread(command);
        }
    };

    @NonNull
    private static final Executor sIOThreadExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            getInstance().executeOnDiskIO(command);
        }
    };


    private GHTaskExecutor() {
        mDefaultTaskExecutor = new DefaultTaskExecutor();
        mDelegate = mDefaultTaskExecutor;
    }


    /**
     * Returns an instance of the task executor.
     *
     * @return The singleton GHTaskExecutor.
     */
    @NonNull
    public static GHTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (GHTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = new GHTaskExecutor();
            }
        }
        return sInstance;
    }

    /**
     * Sets a delegate to handle task execution requests.
     * <p>
     * If you have a common executor, you can set it as the delegate and App Toolkit components will
     * use your executors. You may also want to use this for your tests.
     * <p>
     * Calling this method with {@code null} sets it to the default TaskExecutor.
     *
     * @param taskExecutor The task executor to handle task requests.
     */
    public void setDelegate(@Nullable TaskExecutor taskExecutor) {
        mDelegate = taskExecutor == null ? mDefaultTaskExecutor : taskExecutor;
    }

    @Override
    public void executeOnDiskIO(Runnable runnable) {
        mDelegate.executeOnDiskIO(runnable);
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        mDelegate.postToMainThread(runnable);
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    @NonNull
    public static Executor getIOThreadExecutor() {
        return sIOThreadExecutor;
    }

    @Override
    public boolean isMainThread() {
        return mDelegate.isMainThread();
    }


    private static class DefaultTaskExecutor extends TaskExecutor {

        private final Object mLock = new Object();
        private ExecutorService mDiskIO = Executors.newFixedThreadPool(2);

        @Nullable
        private volatile Handler mMainHandler;

        @Override
        public void executeOnDiskIO(Runnable runnable) {
            mDiskIO.execute(runnable);
        }

        @Override
        public void postToMainThread(Runnable runnable) {
            if (mMainHandler == null) {
                synchronized (mLock) {
                    if (mMainHandler == null) {
                        mMainHandler = new Handler(Looper.getMainLooper());
                    }
                }
            }
            //noinspection ConstantConditions
            mMainHandler.post(runnable);
        }

        @Override
        public boolean isMainThread() {
            return Looper.getMainLooper().getThread() == Thread.currentThread();
        }
    }
}
