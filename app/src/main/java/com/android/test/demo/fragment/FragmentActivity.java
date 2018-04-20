package com.android.test.demo.fragment;

import com.android.test.demo.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * des:
 * author: libingyan
 * Date: 18-4-20 10:36
 */
public class FragmentActivity extends AppCompatActivity {
    private final String TAG = "Fragment";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        /**
         * Fragment有一个常见的问题，即Fragment重叠问题，这是由于Fragment被系统杀掉，
         * 并重新初始化时再次将fragment加入activity，因此通过在外围加if语句能判断此时
         * 是否是被系统杀掉并重新初始化的情况。
         */
        if (savedInstanceState == null) {
            /**
             * 获取系统内部的android.app.FragmentManager
             */
            //FragmentManager fragmentManager = getFragmentManager();

            /**
             * 获取支持包中的android.support.v4.app.FragmentManager
             */
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                .add(R.id.id_fragment_container, Fragment1.newInstance("this is fragment1"), "f1")
                .addToBackStack("f1-stack") //可选,会加入回退栈，back键触发remove()操作
                .commit(); //commit()方法是异步的，对应的同步方法：commitNow()




            /*java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
             *    at android.support.v4.app.FragmentManagerImpl.checkStateLoss(FragmentManager.java:1341)
             *    at android.support.v4.app.FragmentManagerImpl.enqueueAction(FragmentManager.java:1352)
             *    at android.support.v4.app.BackStackRecord.commitInternal(BackStackRecord.java:595)
             *    at android.support.v4.app.BackStackRecord.commit(BackStackRecord.java:574)
             *
             *    该异常出现的原因是：commit()在onSaveInstanceState()后调用。
             *    解决方案：不要把Fragment事务放在异步线程的回调中
             */
        }

        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = DialogFragment1.newInstance(R.layout.layout_test_fragment_dialog);
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
    }



}
