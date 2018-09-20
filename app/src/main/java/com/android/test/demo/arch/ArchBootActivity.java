package com.android.test.demo.arch;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.test.demo.R;
import com.android.test.demo.log.DLog;

public class ArchBootActivity extends AppCompatActivity {
    private final String TAG = "ArchBootActivity";

    private LifecycleObserver mLifecycleObserver;

    private TestViewModel mViewModle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_arch_boot);


        findViewById(R.id.btn_bind_life).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLifecycleObserver == null) {
                    mLifecycleObserver = new TestLifecycleObserver();
                    getLifecycle().addObserver(mLifecycleObserver);
                }
            }
        });

        findViewById(R.id.btn_unbind_life).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLifecycleObserver != null) {
                    getLifecycle().removeObserver(mLifecycleObserver);
                    mLifecycleObserver = null;
                }
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.id_fragment_container, new TestLifecycleFragment(), "f1")
                .addToBackStack("f1-stack") //可选,会加入回退栈，back键触发remove()操作
                .commit(); //commit()方法是异步的，对应的同步方法：commitNow()



        mViewModle = ViewModelProviders.of(this).get(TestViewModel.class);

        /**
         * activity在前台才会回调
         */
        mViewModle.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                DLog.i(TAG, "view model onChanged() value: " + s);
                ((TextView)(findViewById(R.id.txt_live_data_result))).setText(s);
            }
        });

        /**
         * 不区分activity是否在前台都会回调
         */
        mViewModle.getLiveData().observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                DLog.i(TAG, "view model forever onChanged() value: " + s);
            }
        });

        /**
         * 模拟异步加载数据
         */
        findViewById(R.id.btn_test_live_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewModle.load();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DLog.i(TAG, "onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        DLog.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DLog.i(TAG, "onDestroy()");
    }
}
