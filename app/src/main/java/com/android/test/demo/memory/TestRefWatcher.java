package com.android.test.demo.memory;

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


    public void watch() {
        TestWeakObj obj = new TestWeakObj("test", 100);
        watch(obj);
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
        Log.i(TAG, "weak obj: " + reference.get());

        removeWeaklyReachableReferences();

        gcTrigger.runGc();

        removeWeaklyReachableReferences();

        Log.i(TAG, "weak obj: " + reference.get());
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


    public static class TestWeakObj {
        private String weakKey;
        private int weakValue;

        public TestWeakObj(String key, int value) {
            weakKey = key;
            weakValue = value;
        }
    }

}
