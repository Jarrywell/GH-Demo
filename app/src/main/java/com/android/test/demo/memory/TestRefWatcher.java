package com.android.test.demo.memory;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.ref.ReferenceQueue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * des:
 * author: libingyan
 * Date: 18-4-2 15:38
 */
public class TestRefWatcher {
    private final String TAG = "TestRefWatcher";

    private final WatchExecutor watchExecutor = new AndroidWatchExecutor(5000);
    private final GcTrigger gcTrigger;
    private final ReferenceQueue<Object> queue;
    private final Set<String> retainedKeys;


    public TestRefWatcher() {
        gcTrigger = GcTrigger.DEFAULT;
        queue = new ReferenceQueue<>();
        retainedKeys = new CopyOnWriteArraySet<>();
    }


    public void watch(Object obj) {
        String key = UUID.randomUUID().toString();
        retainedKeys.add(key);
        final KeyedWeakReference reference = new KeyedWeakReference(obj, key, "test", queue);
        ensureGoneAsync(reference);
    }


    private void ensureGoneAsync(final KeyedWeakReference reference) {
        Log.i(TAG,"ensureGone pre start: " + reference.get() + ", key: " + reference.key + ", ref-obj: " + reference);

        watchExecutor.execute(new Retryable() {
            @Override public Retryable.Result run() {
                 watchInner(reference);
                 return null;
            }
        });

    }

    private void watchInner(final KeyedWeakReference reference) {

        /**
         * 特别注意：这里调试了很久,一直都无法回收对象，差点放弃调试！！
         * 原因是:打印这条日志时调用了reference.get(),导致了一个强引用存在，于是gc不能回收该对象
         */
        //Log.i(TAG, "weak 01 obj: " + reference.get());

        removeWeaklyReachableReferences();

        gcTrigger.runGc();

        removeWeaklyReachableReferences();

        Log.i(TAG, "weak 02 obj: " + reference.get());
    }

    private void removeWeaklyReachableReferences() {
        // WeakReferences are enqueued as soon as the object to which they point to becomes weakly
        // reachable. This is before finalization or garbage collection has actually happened.
        KeyedWeakReference ref;
        Log.i(TAG, "removeWeaklyReachableReferences start!");
        while ((ref = (KeyedWeakReference) queue.poll()) != null) {
            Log.i(TAG, "while ref: " + ref.get() + ", key: " + ref.key + ", ref-obj: " + ref);
            retainedKeys.remove(ref.key);
        }
        Log.i(TAG,"removeWeaklyReachableReferences end!");
    }


    private boolean gone(KeyedWeakReference reference) {
        return !retainedKeys.contains(reference.key);
    }

}
