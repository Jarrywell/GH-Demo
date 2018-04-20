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

/**
 * des: 同Fragment1
 * author: libingyan
 * Date: 18-4-20 10:38
 */
public class Fragment2 extends Fragment {
    private final String TAG = "Fragment2";

    private static final String TITLE_KEY = "fragment_title_key";
    private Activity mActivity;

    private String mTitle;

    public static Fragment newInstance(String title) {
        Fragment2 f = new Fragment2();

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
        root.setBackgroundColor(0x5500ff00);

        root.findViewById(R.id.btn_add_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                    .replace(R.id.id_fragment_container, Fragment3.newInstance("this is fragment3"), "f3")
                    .addToBackStack("f3-stack")
                    .commit();
            }
        });


        TextView titleView = (TextView) root.findViewById(R.id.txt_title);
        titleView.setText(mTitle);

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
