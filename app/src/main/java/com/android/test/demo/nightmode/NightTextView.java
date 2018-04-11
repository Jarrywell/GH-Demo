package com.android.test.demo.nightmode;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.SparseIntArray;

/**
 * Created by tech on 18-4-11.
 */

public class NightTextView extends AppCompatTextView implements Themeable {
    private final String TAG = "NightTextView";

    private SparseIntArray mThemes;
    private int mTheme; //当前主题
    public NightTextView(Context context) {
        this(context, null);
    }

    public NightTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NightTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttributeSet(context, attrs, defStyleAttr);
    }

    private void initAttributeSet(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        mThemes = ThemeUtils.getThemeStyleIds(context, attrs, defStyleAttr);
        applyTheme(ThemeUtils.getTheme(context));
    }


    @Override
    public void applyTheme(int themeType) {
        if (mTheme != themeType) {
            mTheme = themeType;
            ThemeUtils.applyTextView(this, mThemes.get(mTheme));
        }
    }
}
