package com.android.test.demo.http;

import okhttp3.OkHttpClient;

/**
 * des:
 * author: libingyan
 * Date: 18-4-9 14:49
 */
public final class HttpRequestConfigs {
    private OkHttpClient.Builder mBuilder;

    public static void setDefaultConfigs(OkHttpClient.Builder builder) {

    }

    public void setConfigs(OkHttpClient.Builder builder) {
        mBuilder = builder;
    }

    public OkHttpClient.Builder getConfigs() {
        return mBuilder;
    }
}
