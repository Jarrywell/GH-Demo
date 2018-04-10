package com.android.test.demo.nightmode.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.android.test.demo.R;

/**
 * Created by tech on 18-4-10.
 * 在主题中指定某个attr的style，并将该attr作为第三个参数传给obtainStyledAttributes
 */

public class TestView3 extends BaseView {

    public TestView3(Context context) {
        super(context);
    }

    public TestView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initAttributeSet(Context context, @Nullable AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.BaseView, R.attr.BaseViewTheme, 0);

        mText = typedArray.getString(R.styleable.BaseView_testText);
        mColor = typedArray.getColor(R.styleable.BaseView_testColor, 0xff000000);
        typedArray.recycle();
    }
}
