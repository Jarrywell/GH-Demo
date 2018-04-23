package com.android.test.demo.nightmode;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;

/**
 * des:
 * author: libingyan
 * Date: 18-4-23 16:27
 */
public class AppCompatNightMode {

    public static void initTheme(Context context) {

        final int currentTheme = getTheme(context);

        /**
         * MODE_NIGHT_NO： 亮色(light)主题，不使用夜间模式
         *
         * MODE_NIGHT_YES：暗色(dark)主题，使用夜间模式
         *
         * MODE_NIGHT_AUTO：根据当前时间自动切换 亮色(light)/暗色(dark)主题
         * （22：00-07：00时间段内自动切换为夜间模式）
         *
         * MODE_NIGHT_FOLLOW_SYSTEM(默认选项)：设置为跟随系统，通常为MODE_NIGHT_NO
         *
         */
        AppCompatDelegate.setDefaultNightMode(currentTheme == Themeable.THEME_TYPE_NIGHT ?
            AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }


    /**
     * 切换系统主题
     * @param activity
     */
    public static void swtichTheme(Activity activity) {

        final int destTheme = switchTheme(activity);

        AppCompatDelegate.setDefaultNightMode(destTheme == Themeable.THEME_TYPE_NIGHT ?
            AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        /**
         * 重启activity
         */
        activity.recreate();

    }


    private static final String THEME_PREFERENCE_NAME = "day-night-theme-sys";
    private static final String THEME_NIGHT_DAY_KEY = "day-night-sys-key";

    /**
     * 获取当前的主题
     * @param context
     * @return
     */
    private static int getTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(THEME_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(THEME_NIGHT_DAY_KEY, Themeable.THEME_TYPE_DAY);
    }

    /**
     * 切换至下一个主题
     * @param context
     * @return
     */
    private static int switchTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(THEME_PREFERENCE_NAME, Context.MODE_PRIVATE);
        final int theme = sp.getInt(THEME_NIGHT_DAY_KEY, Themeable.THEME_TYPE_DAY);
        final int destTheme = (theme == Themeable.THEME_TYPE_DAY) ?
            Themeable.THEME_TYPE_NIGHT : Themeable.THEME_TYPE_DAY;
        sp.edit().putInt(THEME_NIGHT_DAY_KEY, destTheme).apply();

        return destTheme;
    }
}
