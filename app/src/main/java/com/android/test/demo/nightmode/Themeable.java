package com.android.test.demo.nightmode;

/**
 * Created by tech on 18-4-11.
 */

public interface Themeable {

    /**
     * 标识白天模式
     */
    int THEME_TYPE_DAY = 0x1;

    /**
     * 标识夜间模式
     */
    int THEME_TYPE_NIGHT = 0x02;

    /**
     * 应用主题
     * @param themeType
     */
    void applyTheme(int themeType);
}
