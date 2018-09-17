package com.android.test.demo.arch;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.test.demo.R;

public class ArchBootActivity extends AppCompatActivity {
    private final String TAG = "ArchBootActivity";

    private LifecycleObserver mLifecycleObserver;


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
    }
}
