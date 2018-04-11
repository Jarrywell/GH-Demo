package com.android.test.demo.nightmode;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by tech on 18-4-11.
 */

public class NightImageView extends AppCompatImageView {
    public NightImageView(Context context) {
        super(context);
    }

    public NightImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NightImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
