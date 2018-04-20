package com.android.test.demo.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * des: dialog of fragment
 * author: libingyan
 * Date: 18-4-20 15:24
 */
public class DialogFragment1 extends DialogFragment {
    final String TAG = "DialogFragment1";

    private static final String LAYOUT_ID_KEY = "layoutId";

    private Activity mActivity;

    private int mLayoutId;

    public static DialogFragment newInstance(int resId) {
        DialogFragment1 f = new DialogFragment1();

        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_ID_KEY, resId);
        f.setArguments(arguments);

        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = (Activity) context;

        mLayoutId = getArguments().getInt(LAYOUT_ID_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //消除Title区域
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //将背景变为透明

        setCancelable(true);

        View root = inflater.inflate(mLayoutId, container, false);

        return root;
    }
}
