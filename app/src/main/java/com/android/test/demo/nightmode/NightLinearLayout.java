package com.android.test.demo.nightmode;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.widget.LinearLayout;

/**
 * Created by tech on 18-4-12.
 */

public class NightLinearLayout extends LinearLayout implements Themeable {
    private final String TAG = "NightLinearLayout";

    private SparseIntArray mThemes;
    private int mTheme; //当前主题

    public NightLinearLayout(Context context) {
        this(context, null);
    }

    public NightLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NightLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mThemes = ThemeUtils.getThemeStyleIds(context, attrs, defStyleAttr);
        applyTheme(ThemeUtils.getTheme(context));
    }

    @Override
    public void applyTheme(int themeType) {
        if (mTheme != themeType) {
            mTheme = themeType;
            ThemeUtils.applyView(this, mThemes.get(mTheme));
        }
    }
}
