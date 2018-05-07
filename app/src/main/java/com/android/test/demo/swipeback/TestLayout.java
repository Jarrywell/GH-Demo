package com.android.test.demo.swipeback;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * des:
 * author: libingyan
 * Date: 18-5-7 09:50
 */
public class TestLayout extends FrameLayout {
    private final String TAG = "TestLayout";

    public TestLayout(@NonNull Context context) {
        this(context, null);
    }

    public TestLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, me.imid.swipebacklayout.lib.R.attr.SwipeBackLayoutStyle);
    }

    public TestLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, me.imid.swipebacklayout.lib.R.styleable.SwipeBackLayout, defStyleAttr,
            me.imid.swipebacklayout.lib.R.style.SwipeBackLayout);

        int edgeSize = a.getDimensionPixelSize(me.imid.swipebacklayout.lib.R.styleable.SwipeBackLayout_edge_size, -1);

        int mode = a.getInt(me.imid.swipebacklayout.lib.R.styleable.SwipeBackLayout_edge_flag, 0);

        Drawable drawable = a.getDrawable(me.imid.swipebacklayout.lib.R.styleable.SwipeBackLayout_shadow_left);

        Log.i(TAG, "edgeSize: " + edgeSize + ", mode: " + mode + "left-drawable: " + drawable);

    }
}
