package com.android.test.demo.nightmode.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.android.test.demo.R;

/**
 * Created by tech on 18-4-10.
 * 通过obtainStyledAttributes的第四个参数直接传入一个style
 */

public class TestView4 extends BaseView {

    public TestView4(Context context) {
        super(context);
    }

    public TestView4(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestView4(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initAttributeSet(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.BaseView, 0, R.style.BaseViewAttrDefault);

        mText = typedArray.getString(R.styleable.BaseView_testText);
        mColor = typedArray.getColor(R.styleable.BaseView_testColor, 0xff000000);
        typedArray.recycle();
    }
}
