package com.android.test.demo.log;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * des:
 * author: libingyan
 * Date: 18-11-7 09:57
 */
public class FileLogger {

    private static final String LEVEL_V = "V";
    private static final String LEVEL_D = "D";
    private static final String LEVEL_I = "I";
    private static final String LEVEL_W = "W";
    private static final String LEVEL_E = "E";
    private static final long MAX_SIZE = 5242880L;

    private File mFile;
    private String mPackageName;
    private ExecutorService mExecutorService;
    private long mMaxSize = MAX_SIZE;
    private int mMaxCacheCount = 32;
    private long mMaxCacheTime = 60000L;
    private boolean mEnable = true;
    private static LinkedList<LogInfo> sCacheLogInfo = new LinkedList();
    private static Handler sHandler;
    private static final int WHAT = 1;


    public FileLogger(File file, String packageName) {
        mFile = file;
        mPackageName = packageName;
        mExecutorService = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
    }

    public void v(String tag, String message) {
        print(LEVEL_V, tag, message, null);
    }

    public void v(String tag, String message, Throwable t) {
        print(LEVEL_V, tag, message, t);
    }

    public void d(String tag, String message) {
        print(LEVEL_D, tag, message, null);
    }

    public void d(String tag, String message, Throwable t) {
        print(LEVEL_D, tag, message, t);
    }

    public void i(String tag, String message) {
        print(LEVEL_I, tag, message, null);
    }

    public void i(String tag, String message, Throwable t) {
        print(LEVEL_I, tag, message, t);
    }

    public void w(String tag, String message) {
        print(LEVEL_W, tag, message, null);
    }

    public void w(String tag, String message, Throwable t) {
        print(LEVEL_W, tag, message, t);
    }

    public void e(String tag, String message) {
        print(LEVEL_E, tag, message, null);
    }

    public void e(String tag, String message, Throwable t) {
        print(LEVEL_E, tag, message, t);
    }


    public void setMaxSize(long maxSize) {
        this.mMaxSize = maxSize;
    }

    public void setMaxCacheCount(int maxCacheCount) {
        this.mMaxCacheCount = maxCacheCount;
    }

    public void setMaxCacheTime(long maxCacheTime) {
        this.mMaxCacheTime = maxCacheTime;
    }

    public void clear() {
        Runnable runnable = new ClearRunnable();
        mExecutorService.execute(runnable);
    }

    public void flush() {
        Runnable runnable = new FlushRunnable();
        mExecutorService.execute(runnable);
    }

    private void print(String level, String tag, String msg, Throwable t) {
        if (mEnable) {
            WriteRunnable runnable = new WriteRunnable(level, tag, msg, t);
            mExecutorService.execute(runnable);
        }
    }


    private class WriteRunnable implements Runnable {
        private LogInfo mInfo;

        WriteRunnable(String level, String tag, String msg, Throwable throwable) {
            mInfo = new LogInfo(level, tag, msg, throwable);
        }


        @Override
        public void run() {
            if (mFile == null) {
                mEnable = false;
            } else if (mFile.exists() && !mFile.isFile()) {
                mEnable = false;
            } else {
                try {
                    if (!mFile.exists() && !mFile.createNewFile()) {
                        mEnable = false;
                    }
                } catch (Exception e) {
                    mEnable = false;
                }
            }

            /**
             * 条件不满足
             */
            if (!mEnable) {

                return;
            }

            sCacheLogInfo.add(mInfo);
            final int size = sCacheLogInfo.size();
            if (size < mMaxCacheCount) {
                if (sHandler == null) {
                    sHandler = new WriteHandler(FileLogger.this);
                }
                if (!sHandler.hasMessages(WHAT)) {
                    Message message = sHandler.obtainMessage(WHAT);
                    sHandler.sendMessageDelayed(message, mMaxCacheTime);
                }
            } else {
                if (sHandler != null) {
                    sHandler.removeMessages(WHAT);
                }

                /**
                 * 直接运行即可
                 */
                Runnable runnable = new FlushRunnable();
                runnable.run();
            }
        }
    }

    private static class WriteHandler extends Handler {
        private WeakReference<FileLogger> mRefrence;

        WriteHandler(FileLogger fileLogger) {
            super(Looper.getMainLooper());
            mRefrence = new WeakReference<>(fileLogger);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT) {
                FileLogger logger = mRefrence.get();

                if (logger != null) {
                    logger.flush();
                }

            } else {
                super.handleMessage(msg);
            }
        }
    }



    private class ClearRunnable implements Runnable {

        @Override
        public void run() {
            if (mFile != null && mFile.exists() && mFile.isFile()) {
                mFile.delete();
            }
        }
    }

    private class FlushRunnable implements Runnable {

        @Override
        public void run() {
            final int size = sCacheLogInfo.size();
            if (size > 0) {
                PrintWriter printWriter = null;
                try {
                    boolean append = true;
                    if (mFile.length() >= mMaxSize) {
                        append = false;
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(mFile, append);
                    printWriter = new PrintWriter(fileOutputStream);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
                    for (int i = 0; i < size; i++) {
                        LogInfo info = sCacheLogInfo.get(i);
                        Date date = new Date(info.mTime);
                        String text = format.format(date) + " " + Process.myPid() + "/" + FileLogger.this.mPackageName + " " + info.mLevel + "/" + info.mTag + ": " + info.mMsg;
                        printWriter.write(text);
                        printWriter.write("\n");
                        if (info.mThrowable != null) {
                            info.mThrowable.printStackTrace(printWriter);
                        }
                        printWriter.flush();
                    }
                    sCacheLogInfo.clear();
                } catch (Exception e) {
                    ;
                } finally {
                    if (printWriter != null) {
                        printWriter.close();
                    }
                }
            }
        }
    }


    private static class LogInfo {
        private long mTime = System.currentTimeMillis();
        private String mLevel;
        private String mTag;
        private String mMsg;
        private Throwable mThrowable;


        LogInfo(String level, String tag, String msg, Throwable throwable) {
            mLevel = level;
            mTag = tag;
            mMsg = msg;
            mThrowable = throwable;
        }
    }
}
