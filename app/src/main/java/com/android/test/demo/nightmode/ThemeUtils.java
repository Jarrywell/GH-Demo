package com.android.test.demo.nightmode;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.test.demo.R;
import com.android.test.demo.joor.Reflect;

/**
 * Created by tech on 18-4-11.
 */

public class ThemeUtils {

    private static final String CLASS_NAME_STYLEABLE = "com.android.internal.R$styleable";


    /**
     * 切换Activity的主题（白天/夜间）
     * @param activity
     */
    public static void toggleNightMode(Activity activity) {
        if (activity != null) {
            /**
             * 切换主题设置
             */
            final int theme = switchTheme(activity);


            /**
             * 更新UI
             */
            updateTheme(activity.getWindow().getDecorView(), theme);
        }
    }

    /**
     * 递归的更新主题
     * @param view
     * @param themeType
     */
    private static void updateTheme(View view, int themeType) {
        if (view == null) {
            return;
        }
        if (view instanceof Themeable) {
            ((Themeable) view).applyTheme(themeType);
        }
        if (view instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) view;
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                updateTheme(group.getChildAt(i), themeType);
            }
        }
    }

    /**
     * 获取白天和夜间styleId
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @return
     */
    public static SparseIntArray getThemeStyleIds(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        int dayStyleId = 0;
        int nightStyleId = 0;
        SparseIntArray array = new SparseIntArray(2);
        if (context != null && attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DefaultThemeStyle, 0, defStyleAttr);
            if (typedArray != null) {
                final int resourceId = typedArray.getResourceId(R.styleable.DefaultThemeStyle_DayNightTheme, 0);
                if (resourceId != 0) {
                    TypedArray dayNightTypedArray = context.getTheme().obtainStyledAttributes(resourceId, R.styleable.DayNightStyle);
                    if (dayNightTypedArray != null) {
                        dayStyleId = dayNightTypedArray.getResourceId(R.styleable.DayNightStyle_dayTheme, 0);
                        if (dayStyleId != 0) {
                            array.put(Themeable.THEME_TYPE_DAY, dayStyleId);
                        }
                        nightStyleId = dayNightTypedArray.getResourceId(R.styleable.DayNightStyle_nightTheme, 0);
                        if (nightStyleId != 0) {
                            array.put(Themeable.THEME_TYPE_NIGHT, nightStyleId);
                        }
                        dayNightTypedArray.recycle();
                    }
                }
                typedArray.recycle();
            }
        }
        return array;
    }

    public static void applyView(View view, final int styleId) {
        Reflect reflect = Reflect.on(CLASS_NAME_STYLEABLE);
        TypedArray a = view.getContext().getTheme()
                .obtainStyledAttributes(styleId, reflect.field("View").get());
        if (a != null) {
            final int drawableId = reflect.field("View_background").get();
            final int alphaId = reflect.field("View_alpha").get();

            final int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                final int attr = a.getIndex(i);
                if (attr == drawableId) {
                    final Drawable drawable = a.getDrawable(attr);
                    view.setBackground(drawable);
                } else if (attr == alphaId) {
                    final float alpha = a.getFloat(attr, 1);
                    view.setAlpha(alpha);
                }
            }
            a.recycle();
        }
    }
    /**
     * 应用选择后的styleId到View中
     * @param view
     * @param styleId
     */
    public static void applyTextView(TextView view, final int styleId) {
        Reflect reflect = Reflect.on(CLASS_NAME_STYLEABLE);
        TypedArray a = view.getContext().getTheme()
                .obtainStyledAttributes(styleId, reflect.field("TextView").get());
        if (a != null) {
            final int sizeId = reflect.field("TextView_textSize").get();
            final int colorId = reflect.field("TextView_textColor").get();
            final int textId = reflect.field("TextView_text").get();

            final int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                final int attr = a.getIndex(i);
                if (attr == sizeId) {
                    final int size = a.getDimensionPixelSize(attr, 0);
                    if (size > 0) {
                        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                    }
                } else if (attr == colorId) {
                    final ColorStateList textColor = a.getColorStateList(attr);
                    view.setTextColor(textColor);
                } else if (attr == textId) {
                    final String text = a.getString(attr);
                    view.setText(text);
                }
            }
            a.recycle();
        }
    }

/*    public static void applyTextView(TextView view, final int styleId) {
        TypedArray typedArray = view.getContext().getTheme()
                .obtainStyledAttributes(styleId, com.android.internal.R.styleable.TextView);
        if (typedArray != null) {
            final int count = typedArray.getIndexCount();
            for (int i = 0; i < count; i++) {
                final int attr = typedArray.getIndex(i);
                switch (attr) {
                    case com.android.internal.R.styleable.TextView_textSize: {
                        final int size = typedArray.getDimensionPixelSize(attr, 0);
                        if (size > 0) {
                            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                        }
                        break;
                    }
                    case com.android.internal.R.styleable.TextView_textColor: {
                        ColorStateList textColor = typedArray.getColorStateList(attr);
                        view.setTextColor(textColor);
                        break;
                    }
                    case com.android.internal.R.styleable.TextView_text: {
                        final String text = typedArray.getString(attr);
                        view.setText(text);
                        break;
                    }
                }
            }
            typedArray.recycle();
        }

    }*/

    public static void applyImageView(ImageView view, final int styleId) {
        Reflect reflect = Reflect.on(CLASS_NAME_STYLEABLE);
        TypedArray a = view.getContext().getTheme()
                .obtainStyledAttributes(styleId, reflect.field("ImageView").get());
        if (a != null) {
            final int srcId = reflect.field("ImageView_src").get();

            final int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                final int attr = a.getIndex(i);
                if (attr == srcId) {
                    final Drawable drawable = a.getDrawable(attr);
                    view.setImageDrawable(drawable);
                }
            }
            a.recycle();
        }
    }


    public static void applyNightImageView(NightImageView view, AttributeSet set, final int styleId) {
        TypedArray a = view.getContext().getTheme().obtainStyledAttributes(set,
                R.styleable.NightImageView, 0, styleId);

        final int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            final int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.NightImageView_src_sets: {
                    final int resId = a.getResourceId(attr, 0);
                    if (resId != 0) {
                        view.setSrcSets(resId);
                    }
                    break;
                }
                case R.styleable.NightImageView_current_src: {
                    final String current = a.getString(attr);
                    view.setCurrentSrc(current);
                    break;
                }
                case R.styleable.NightImageView_mask: {
                    final boolean mask = a.getBoolean(attr, false);
                    view.setMaskEnable(mask);
                    break;
                }
            }
        }
        a.recycle();
    }

    private static final String THEME_PREFERENCE_NAME = "day-night-theme";
    private static final String THEME_NIGHT_DAY_KEY = "day-night-key";

    /**
     * 获取当前的主题
     * @param context
     * @return
     */
    public static int getTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(THEME_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(THEME_NIGHT_DAY_KEY, Themeable.THEME_TYPE_DAY);
    }

    /**
     * 切换至下一个主题
     * @param context
     * @return
     */
    public static int switchTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(THEME_PREFERENCE_NAME, Context.MODE_PRIVATE);
        final int theme = sp.getInt(THEME_NIGHT_DAY_KEY, Themeable.THEME_TYPE_DAY);
        final int destTheme = (theme == Themeable.THEME_TYPE_DAY) ?
                Themeable.THEME_TYPE_NIGHT : Themeable.THEME_TYPE_DAY;
        sp.edit().putInt(THEME_NIGHT_DAY_KEY, destTheme).apply();

        return destTheme;
    }

}
