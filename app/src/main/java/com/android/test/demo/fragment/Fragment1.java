package com.android.test.demo.fragment;

import com.android.test.demo.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/*
 * fragment 生命周期
 * 05-14 14:10:58.773 I/Fragment1(30174): onAttach()
 * 05-14 14:10:58.773 I/Fragment1(30174): onCreate()
 * 05-14 14:10:58.773 I/Fragment1(30174): onCreateView()
 * 05-14 14:10:58.774 I/Fragment1(30174): onActivityCreated()
 * 05-14 14:10:58.775 I/Fragment1(30174): onStart()
 * 05-14 14:10:58.776 I/Fragment1(30174): onResume()
 *
 * 05-14 14:11:01.826 I/Fragment1(30174): onPause()
 * 05-14 14:11:01.826 I/Fragment1(30174): onStop()
 * 05-14 14:11:01.826 I/Fragment1(30174): onDestroyView()
 * 05-14 14:11:01.833 I/Fragment1(30174): onDestroy()
 * 05-14 14:11:01.833 I/Fragment1(30174): onDetach()
 */

/**
 * des:
 * author: libingyan
 * Date: 18-4-20 10:38
 */
public class Fragment1 extends Fragment {
    private final String TAG = "Fragment1";

    private static final String TITLE_KEY = "fragment_title_key";
    private Activity mActivity;

    private String mTitle;


    public static Fragment newInstance(String title) {
        Fragment1 f = new Fragment1();

        /**
         * 在创建Fragment时要传入参数，必须要通过setArguments(Bundle bundle)方式添加，
         * 而不建议通过为Fragment添加带参数的构造函数，因为通过setArguments()方式添加，
         * 在由于内存紧张导致Fragment被系统杀掉并恢复（re-instantiate）时能保留这些数据。
         */
        Bundle arguments = new Bundle();
        arguments.putString(TITLE_KEY, title);
        f.setArguments(arguments);

        return f;
    }

    /**
     * Fragment与Activity相关联是调用(可以通过该方法获取Activity引用，还可以通过getArguments()获取参数。)
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach()");

        /**
         * 不建议调用getActivity()，而是在onAttach()中将Context对象强转为Activity对象
         */
        mActivity = (Activity) context;

        /**
         * 获取setArguments()时写入的参数
         */
        mTitle = getArguments().getString(TITLE_KEY, "this is default title");
    }


    /**
     * Fragment被创建是调用
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }


    /**
     * 创建Fragment的布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        /**
         * 需要注意的是inflate()的第三个参数是false，因为在Fragment内部实现中，会把该布局添加到container中，
         * 如果设为true，那么就会重复做两次添加，则会抛如下异常:
         * Caused by: java.lang.IllegalStateException: The specified child already has a parent.
         * You must call removeView() on the child's parent first.
         */
        View root = inflater.inflate(R.layout.layout_test_fragment, container, false);
        root.setBackgroundColor(0x55ff0000);


        TextView titleView = (TextView) root.findViewById(R.id.txt_title);
        titleView.setText(mTitle);

        root.findViewById(R.id.btn_add_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *
                 * replace()执行后，fragment1和fragment2的生命周期分两种情况：
                 *
                 * 第一种情况：加addToBackStack()
                 *
                 * 05-14 14:14:05.237 I/Fragment2(30174): onAttach()
                 * 05-14 14:14:05.238 I/Fragment2(30174): onCreate()
                 *
                 * 05-14 14:14:05.238 I/Fragment1(30174): onPause()
                 * 05-14 14:14:05.238 I/Fragment1(30174): onStop()
                 * 05-14 14:14:05.239 I/Fragment1(30174): onDestroyView()
                 *
                 * 05-14 14:14:05.241 I/Fragment2(30174): onCreateView()
                 * 05-14 14:14:05.251 I/Fragment2(30174): onActivityCreated()
                 * 05-14 14:14:05.252 I/Fragment2(30174): onStart()
                 * 05-14 14:14:05.252 I/Fragment2(30174): onResume()
                 *
                 *
                 *
                 * 第二种情况：不加addToBackStack()
                 *
                 * 05-14 14:18:26.051 I/Fragment2(30605): onAttach()
                 * 05-14 14:18:26.051 I/Fragment2(30605): onCreate()
                 *
                 * 05-14 14:18:26.051 I/Fragment1(30605): onPause()
                 * 05-14 14:18:26.052 I/Fragment1(30605): onStop()
                 * 05-14 14:18:26.052 I/Fragment1(30605): onDestroyView()
                 *
                 * //在不加addToBackStack()的情况下，replace()会导致第一个fragment的destory() & detach()
                 * 05-14 14:18:26.052 I/Fragment1(30605): onDestroy()
                 * 05-14 14:18:26.053 I/Fragment1(30605): onDetach()
                 *
                 * 05-14 14:18:26.053 I/Fragment2(30605): onCreateView()
                 * 05-14 14:18:26.058 I/Fragment2(30605): onActivityCreated()
                 * 05-14 14:18:26.059 I/Fragment2(30605): onStart()
                 * 05-14 14:18:26.059 I/Fragment2(30605): onResume()
                 *
                 *
                 * 结论：在Fragment事务中加不加addToBackStack()会影响Fragment的生命周期
                 */
                getFragmentManager().beginTransaction()
                    .replace(R.id.id_fragment_container, Fragment2.newInstance("this is fragment2"), "f2")
                    .addToBackStack("f2-stack")
                    .commit();
            }
        });

        return root;
    }


    /**
     * 当Activity完成onCreate()时调用
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated()");
    }

    /**
     * 当Fragment可见时调用
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    /**
     * 当Fragment可见且可交互时调用
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }


    /**
     * 当Fragment不可交互但可见时调用
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    /**
     * 当Fragment不可见时调用
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    /**
     * 当Fragment的UI从视图结构中移除时调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView()");
    }

    /**
     * 销毁Fragment时调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    /**
     * 当Fragment和Activity解除关联时调用
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
    }
}
