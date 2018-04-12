package com.android.test.demo.nightmode;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.SparseIntArray;

import com.android.test.demo.R;

import java.util.List;

/**
 * Created by tech on 18-4-11.
 */

public class NightImageView extends AppCompatImageView implements Themeable {
    private final String TAG ="NightImageView";

    private final SparseIntArray mThemes;
    private int mTheme; //当前主题

    private boolean mMask;
    private final int mMaskColor;

    private List<DrawableItem> mSrcSets;
    private String mCurrentSrc = "on";
    private int mSrcSetResourceId; //srcSetId


    public NightImageView(Context context) {
        this(context, null);
    }

    public NightImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NightImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 夜间模式的蒙层颜色
         */
        mMaskColor = getResources().getColor(R.color.image_mask_default_night);

        ThemeUtils.applyNightImageView(this, attrs, 0);

        mThemes = ThemeUtils.getThemeStyleIds(context, attrs, defStyleAttr);
        applyTheme(ThemeUtils.getTheme(context));
    }

    @Override
    public void applyTheme(int themeType) {
        if (mTheme != themeType) {
            mTheme = themeType;
            final int styleId = mThemes.get(mTheme);

            ThemeUtils.applyView(this, styleId);
            ThemeUtils.applyImageView(this, styleId);
            ThemeUtils.applyNightImageView(this, null, styleId);
        }
    }

    public void setSrcSets(int resId) {
        if (mSrcSetResourceId != resId) {
            mSrcSetResourceId = resId;
            mSrcSets = DrawableItem.loadDrawables(getResources(), mSrcSetResourceId);

            updateSrc();
        }
    }

    public void setCurrentSrc(String currentName) {
        if (mCurrentSrc != null && !mCurrentSrc.equals(currentName)) {
            mCurrentSrc = currentName;
            updateSrc();
        }
    }

    public String getCurrentSrc() {
        return mCurrentSrc;
    }


    public void setMaskEnable(boolean mask) {
        if (mMask == mask) {
            return;
        }
        mMask = mask;
        if (mMask) {
            // 蒙灰处理
            setColorFilter(mMaskColor);
        } else {
            clearColorFilter();
        }
    }



    private void updateSrc() {
        Drawable dr = null;
        if (mSrcSets == null || mSrcSets.size() <= 0) {
            dr = null;
        } else if (mCurrentSrc == null || mCurrentSrc.length() <= 0) {
            dr = mSrcSets.get(0).getDrawable(getContext());
        } else {
            boolean found = false;
            for (DrawableItem item : mSrcSets) {
                if (item != null && mCurrentSrc.equals(item.name)) {
                    found = true;
                    dr = item.getDrawable(getContext());
                    break;
                }
            }
            if (!found) {
                dr = mSrcSets.get(0).getDrawable(getContext());
            }
        }
        setImageDrawable(dr);
    }
}
