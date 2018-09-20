package com.android.test.demo;

import com.android.test.demo.arch.ArchBootActivity;
import com.android.test.demo.exception.TestException;
import com.android.test.demo.executor.GHTaskExecutor;
import com.android.test.demo.fragment.FragmentActivity;
import com.android.test.demo.generics.TestArray;
import com.android.test.demo.generics.TestBounds5;
import com.android.test.demo.generics.TestType1;
import com.android.test.demo.hook.HookActivity;
import com.android.test.demo.joor.test.TestJOOR;
import com.android.test.demo.life.TestLifeActivity;
import com.android.test.demo.log.DLog;
import com.android.test.demo.memory.MemoryTest;
import com.android.test.demo.memory.TestRefWatcher;
import com.android.test.demo.nightmode.NightModeActivity;
import com.android.test.demo.http.HttpDemoActivity;
import com.android.test.demo.plugin.activitys.OriginActivity;
import com.android.test.demo.state.TankStateManchineActivity;
import com.android.test.demo.swipeback.TestSwipeBackActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TestGH";
    private MemoryTest mMemeory;
    private Object mRefWatchObj = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (DLog.LOGED) DLog.i(TAG, "MainActivity is oncrated!!");


        findViewById(R.id.btn_joor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestJOOR.test(MainActivity.this);
                TestJOOR.release();
            }
        });

        findViewById(R.id.btn_view_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TestLifeActivity.class));
            }
        });
        findViewById(R.id.btn_state_machine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TankStateManchineActivity.class));
            }
        });

        findViewById(R.id.btn_test_weakreference).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRefWatcher refWatcher = new TestRefWatcher();
                refWatcher.watch(mRefWatchObj);

                /**
                 * 重要，模拟取消强引用
                 */
                //mRefWatchObj = null;
            }
        });

        findViewById(R.id.btn_test_http).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HttpDemoActivity.class));
            }
        });

        findViewById(R.id.btn_test_night_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NightModeActivity.class));
            }
        });

        findViewById(R.id.btn_test_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HookActivity.class));
            }
        });

        findViewById(R.id.btn_test_fragments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FragmentActivity.class));
            }
        });

        findViewById(R.id.btn_test_swipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TestSwipeBackActivity.class));
            }
        });

        findViewById(R.id.btn_test_plugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 这里启动的是一个在manifest中注册过的原生activity,
                 * 但在系统加载OriginActivity回调loadClass()的时候,我们替换他为一个插件中的Plugin1Activity(模拟)
                 * 并能回调actvity的生命周期
                 */
                startActivity(new Intent(getApplicationContext(), OriginActivity.class));
            }
        });

        findViewById(R.id.btn_test_arch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ArchBootActivity.class));
            }
        });

        startActivity(new Intent(getApplicationContext(), ArchBootActivity.class));
        //测试异常
        //testException();

        //测试DAG
        //TestDAG testDAG = new TestDAG();
        //testDAG.test1();
        //testDAG.test2();
        //testDAG.test3();
        //testDAG.test6();

        //GraphDemo.testGraphs();

        //CriticalPath.test();
        //Dijkstra.test();
        //Floyd.test();
        //Prim.test();
        //Kruskal.test();

        //TestTraverser.test();

        //TestLambda.test(this);

        //TestStream.test();
        //TestCollector.test();
        //TestFunction.test();

        //TestForkJoinPool.test();

        /*final int flag = StreamOpFlag.STREAM_MASK;
        Log.i(TAG, "spliterator: 0b" + Integer.toBinaryString(flag) + ", not 0b" + Integer.toBinaryString(flag << 1));*/

        /*final int distinctSet = StreamOpFlag.SHORT_CIRCUIT.set();
        final int mask = StreamOpFlag.getMask(distinctSet);
        Log.i(TAG, "flag: 0b" + Integer.toBinaryString(distinctSet) + ", mask: 0b" + Integer.toBinaryString(mask));*/

        //Hsm1.test();
        //Hsm2.test();

        /**
         * 内存相关测试
         */
        //mMemeory = new MemoryTest(this);
        //mMemeory.testWeakRefence();

        //TestHelloWorld.test();

        //TestType1.test();

        //TestArray.test();

        TestBounds5.test();

        /**
         * 测试Executor
         */
        testExecutor();

    }


    private void testException() {
        TestException testException = new TestException();

        final String fileName = "/sdcard/img_h5.txt";
        final int size = testException.readValueFromFile(fileName);
        //final int size = testException.readValueFromFile(null);
        Log.d(TAG, "readValueFromFile return size: " + size);

        Map<String, String> map = testException.testFinally2();
        Log.d(TAG, "testFinally2 map: " + (map != null ? map.get("key") : "null"));

        final int value = testException.testFinally1();
        Log.d(TAG, "testFinally1 return size: " + value);

        testException.testGHException();

    }

    private void testExecutor() {
        GHTaskExecutor.getInstance().executeOnDiskIO(new Runnable() {
            @Override
            public void run() {
                DLog.i(TAG, "executeOnDiskIO() this is io trhead!!!, thread name: " + Thread.currentThread().getName());
            }
        });

        GHTaskExecutor.getInstance().postToMainThread(new Runnable() {
            @Override
            public void run() {
                DLog.i(TAG, "postToMainThread() this is main trhead!!!, thread name: " + Thread.currentThread().getName());
            }
        });

        GHTaskExecutor.getInstance().executeOnMainThread(new Runnable() {
            @Override
            public void run() {
                DLog.i(TAG, "executeOnMainThread() this is main trhead!!!, thread name: " + Thread.currentThread().getName());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMemeory != null) {
            mMemeory.release();
        }
    }
}
