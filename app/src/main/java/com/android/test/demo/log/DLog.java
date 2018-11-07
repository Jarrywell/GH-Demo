package com.android.test.demo.log;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.android.test.demo.joor.Reflect;

import java.io.File;

public class DLog {

    private final static String DEFAULT_TAG = "GHDemo";

    /**
     * 日志开关设置
     *
     * adb shell setprop ro.tech.log true
     */
    private static final String LOG_ENABLE_PROP = "ro.tech.log";

    /**
     * 文件日志开关设置
     *
     * adb shell setprop ro.tech.file.log true
     */
    private static final String FILE_LOG_ENABLE_PROP = "ro.tech.file.log";


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


    /**
     * 用于终端日志开关
     */
    private final static boolean LOCAL_LOGED = getBoolean(LOG_ENABLE_PROP, false);


    /**
     * 用于文件日志开关
     */
    private static boolean LOCAL_FILE_LOGED = getBoolean(FILE_LOG_ENABLE_PROP, false);



    public final static boolean LOGED = com.android.test.demo.BuildConfig.DEBUG || LOCAL_LOGED || LOCAL_FILE_LOGED;


    private static FileLogger sFileLogger = null;

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

    public static void flush() {
        if (sFileLogger != null) {
            sFileLogger.flush();
        }
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
                if (sFileLogger != null) {
                    sFileLogger.v(tag, message, t);
                }
                break;
            }
            case Log.DEBUG: {
                if (t == null) {
                    Log.d(tag, message);
                } else {
                    Log.d(tag, message, t);
                }
                if (sFileLogger != null) {
                    sFileLogger.d(tag, message, t);
                }
                break;
            }
            case Log.INFO: {
                if (t == null) {
                    Log.i(tag, message);
                } else {
                    Log.i(tag, message, t);
                }
                if (sFileLogger != null) {
                    sFileLogger.i(tag, message, t);
                }
                break;
            }
            case Log.WARN: {
                if (t == null) {
                    Log.w(tag, message);
                } else {
                    Log.w(tag, message, t);
                }
                if (sFileLogger != null) {
                    sFileLogger.w(tag, message, t);
                }
                break;
            }
            case Log.ERROR: {
                if (t == null) {
                    Log.e(tag, message);
                } else {
                    Log.e(tag, message, t);
                }
                if (sFileLogger != null) {
                    sFileLogger.e(tag, message, t);
                }
                break;
            }
        }
    }

    public static void init(Context context, boolean fileEnable) {
        if ((LOCAL_FILE_LOGED || fileEnable) && context != null && sFileLogger == null) {
            final String packageName = context.getPackageName();
            final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + packageName + "/GHLog/";
            File file = new File(path);
            if (!file.exists() && !file.mkdirs()) {
                LOCAL_FILE_LOGED = false;
                return;
            }
            file = new File(path, "main.log");
            sFileLogger = new FileLogger(file, packageName);
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
