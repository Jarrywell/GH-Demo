package com.android.test.demo.nightmode.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.android.test.demo.R;

/**
 * Created by tech on 18-4-10.
 *
 * 直接在View的xml中定义属性
 */
public class TestView1 extends BaseView {

    public TestView1(Context context) {
        super(context);
    }

    public TestView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initAttributeSet(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            final int count = attrs.getAttributeCount();
            for (int i = 0; i < count; i++) {
                final int resourceId = attrs.getAttributeNameResource(i);
                switch (resourceId) {
                    case R.attr.testAttrText: {
                        mText = attrs.getAttributeValue(i);
                        break;
                    }
                    case R.attr.testAttrColor: {
                        mColor = attrs.getAttributeIntValue(i, 0xff000000);
                        break;
                    }
                }
            }
        }
    }
}
