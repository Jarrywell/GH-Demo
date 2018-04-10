package com.android.test.demo.nightmode.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.android.test.demo.R;

/**
 * Created by tech on 18-4-10.
 */

public abstract class BaseView extends View {

    protected final String TAG = "TestView";

    protected String mText = "this is default text";

    protected int mColor = 0xff000000;

    private TextPaint mTextPaint;

    private float mFontSize;

    private StaticLayout mStaticLayout;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mFontSize = context.getResources().getDimension(R.dimen.base_view_font_size);

        initAttributeSet(context, attrs);

        mTextPaint = new TextPaint();

        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mFontSize);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!TextUtils.isEmpty(mText)) {
            mTextPaint.setColor(mColor);

            if(mStaticLayout == null || mStaticLayout.getWidth() != canvas.getWidth()) {
                mStaticLayout = new StaticLayout(mText, mTextPaint, canvas.getWidth(),
                        Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }
            mStaticLayout.draw(canvas);
        }
    }

    protected abstract void initAttributeSet(Context context, @Nullable AttributeSet attrs);
}
