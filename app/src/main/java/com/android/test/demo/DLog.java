package com.android.test.demo;

import com.android.test.joor.reflect.Reflect;

import android.util.Log;

public class DLog {

    private final static String DEFAULT_TAG = "GHDemo";

    /**
     * 日志开关设置
     *
     * adb shell setprop ro.tech.log true
     */
    private static final String LOG_ENABLE_PROP = "ro.tech.log";


    /**
     * 是否使用默认日志tag:GHDemo
     * adb shell setprop ro.tech.log.gtag true
     */
    private static final String LOG_GTAG_PROP = "ro.tech.log.gtag";

    /**
     * 是否使用全局tag
     */
    private final static boolean GTAG = getBoolean(LOG_GTAG_PROP, true);


    /**
     * 是否打印线程信息
     * adb shell persist.tech.log.thread true
     */
    private static final String LOG_THREAD_PROP = "persist.tech.log.thread";


    /**
     * 打印线程信息
     */
    private final static boolean LOG_THREADED = getBoolean(LOG_THREAD_PROP, true);



    public final static boolean LOGED = com.android.test.demo.BuildConfig.DEBUG || getBoolean(LOG_ENABLE_PROP, false);


    public static void v(String tag, String message) {
        wirte(Log.VERBOSE, tag, message, null);
    }

    public static void v(String tag, String message, Throwable t) {
        wirte(Log.VERBOSE, tag, message, t);
    }

    public static void d(String tag, String message) {
        wirte(Log.DEBUG, tag, message, null);
    }

    public static void d(String tag, String message, Throwable t) {
        wirte(Log.DEBUG, tag, message, t);
    }

    public static void i(String tag, String message) {
        wirte(Log.INFO, tag, message, null);
    }

    public static void i(String tag, String message, Throwable t) {
        wirte(Log.INFO, tag, message, t);
    }

    public static void w(String tag, String message) {
        wirte(Log.WARN, tag, message, null);
    }

    public static void w(String tag, String message, Throwable t) {
        wirte(Log.WARN, tag, message, t);
    }

    public static void e(String tag, String message) {
        wirte(Log.ERROR, tag, message, null);
    }

    public static void e(String tag, String message, Throwable t) {
        wirte(Log.ERROR, tag, message, t);
    }



    /**
     * 实际写日志函数，后续可以扩展为文件存储
     * @param level
     * @param tag
     * @param message
     * @param t
     */
    private static void wirte(int level, String tag, String message, Throwable t) {
        final String value = formatMessage(tag, message);
        final String tags = GTAG ? DEFAULT_TAG : tag;

        int index = 0;
        final int max = 4000;
        String result;
        while(index < value.length()) {
            if (value.length() < index + max) {
                result = value.substring(index);
            } else {
                result = value.substring(index, index + max);
            }
            index += max;
            writeInner(level, tags, result, t);
        }
    }

    private static void writeInner(int level, String tag, String message, Throwable t) {
        switch (level) {
            case Log.VERBOSE: {
                if (t == null) {
                    Log.v(tag, message);
                } else {
                    Log.v(tag, message, t);
                }
                break;
            }
            case Log.DEBUG: {
                if (t == null) {
                    Log.d(tag, message);
                } else {
                    Log.d(tag, message, t);
                }
                break;
            }
            case Log.INFO: {
                if (t == null) {
                    Log.i(tag, message);
                } else {
                    Log.i(tag, message, t);
                }
                break;
            }
            case Log.WARN: {
                if (t == null) {
                    Log.w(tag, message);
                } else {
                    Log.w(tag, message, t);
                }
                break;
            }
            case Log.ERROR: {
                if (t == null) {
                    Log.e(tag, message);
                } else {
                    Log.e(tag, message, t);
                }
                break;
            }
        }
    }


    private static String formatMessage(String tag, String message) {
        String value = message;
        return (GTAG ? "[" + tag + "]:" : "") + (LOG_THREADED ? "(" + Thread.currentThread().getId()
            + " of " + Thread.currentThread().getName() + ") " : "") + value;
    }


    /**
     * 读取系统属性SystemProperties
     * @param propName
     * @param def
     * @return
     */
    private static boolean getBoolean(String propName, boolean def) {
        return Reflect.on("android.os.SystemProperties").call("getBoolean", propName, def).get();
    }
}
