package com.android.test.demo.memory;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Random;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * des:
 * author: libingyan
 * Date: 18-3-29 10:15
 */
public class MemoryTest {
    private final String TAG = "MemoryTest";
    private Context mContext;
    private Looper mLooper;
    private Handler mHandler;


    public MemoryTest(Context context) {
        mContext = context;
        HandlerThread thread = new HandlerThread("test memeory");
        thread.start();
        mLooper = thread.getLooper();
        mHandler = new Handler(mLooper);
    }

    public void release() {
        if (mLooper != null) {
            mLooper.quit();
            Log.i(TAG, "memory release.");
        }
    }

    private void printMemory() {
        Runtime runtime = Runtime.getRuntime();

        final long unit = 1024 * 1024; //M

        /**
         * app可申请的最大内存大小
         * /system/build.prop文件设置
         * dalvik.vm.heapgrowthlimit=256m //普通
         * dalvik.vm.heapsize=512m //xml中设置android:largeHeap="true"
         */
        Log.i(TAG, "max memory: " + runtime.maxMemory() / unit + "M");

        /**
         * 当前app已使用的内存大小
         */
        Log.i(TAG, "total memory: " + runtime.totalMemory() / unit + "M");

        /**
         * 已申请内存的剩余内存大小(已经申请，但是还没用到的部分)，而非可使用的剩余内存（maxMemory - totalMemory）
         */
        Log.i(TAG, "free memory: " + runtime.freeMemory() / unit + "M");



        final ActivityManager activityManager = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memeInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memeInfo);

        /**
         * 系统剩余内存
         */
        Log.i(TAG, "availbel memory: " + memeInfo.availMem / unit + "M");

        /**
         * 内核可访问的总内存。这基本上是设备的RAM大小，不包括像DMA缓冲器、基带CPU的RAM等低于内核的固定分配。
         */
        Log.i(TAG, "total memory: " + memeInfo.totalMem / unit + "M");

        /**
         * 当系统剩余内存低于threshold时就看成低内存运行
         */
        Log.i(TAG, "threshold memory: " + memeInfo.threshold / unit + "M" + ", lowMemory: " + memeInfo.lowMemory);
    }


    private void test1() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                printMemory(); //打印初始内存信息

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {}

                final int count = 2000000;

                String[] content = new String[count];

                printMemory(); //打印申请数组大小后的内存信息

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {}

                Random random = new Random();
                for (int i = 0; i < count; i++) {
                    content[i] = new String("value: " + random.nextInt(count));
                }

                printMemory(); //打印实例化很多String后的内存信息

            }
        }, 200);
    }

    public void testWeakRefence() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                KeyValue keyValue = new KeyValue("size", 20);
                WeakReference<KeyValue> weak = new WeakReference<KeyValue>(keyValue);
                int count = 0;
                keyValue = null;
                while (true) {
                    if (weak.get() != null) {
                        count++;
                        Log.i(TAG, "obj is available!! count: " + count + ", obj: " + weak.get());

                        Runtime.getRuntime().gc();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new AssertionError();
                        }
                        System.runFinalization();

                    } else {
                        Log.i(TAG, "obj is not available!! count: " + count + ", obj: " + weak.get());
                        break;
                    }
                }
            }
        });
    }


    private class KeyValue {
        private String key;
        private int value;

        public KeyValue(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
