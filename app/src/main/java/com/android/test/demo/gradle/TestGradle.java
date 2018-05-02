package com.android.test.demo.gradle;

import com.android.test.demo.BuildConfig;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * des:
 * author: libingyan
 * Date: 18-5-2 19:42
 */
public class TestGradle {

    private static final String TAG  = "TestGradle";

    private static final String KEY = "com.android.test.holder.key";

    public static void test(Context context) {
        /**
         * 测试manifests中的meta数据gradle替换
         */
        testMetaData(context);

        /**
         * 测试自定义的buildConfig数据
         */
        testBuildConfig();
    }

    private static void testMetaData(Context context) {
        try {
            final String testValue = context.getPackageManager()
                .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                .metaData.getString(KEY);

            Log.i(TAG, "test of gradle place holder value: " + testValue);
        } catch (Exception e) {

        }
    }

    private static void testBuildConfig() {
        Log.i(TAG, "git hash: " + BuildConfig.GIT_HASH + ", build time: " + BuildConfig.BUILD_TIME);
    }
}
