package com.android.test.demo.http;

import android.util.ArrayMap;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * des:
 * author: libingyan
 * Date: 18-4-8 15:18
 */
public final class HttpRequestBuilder<T extends HttpResult> {
    private final String TAG = "HttpRequestBuilder";

    private Request.Builder requestBuilder;
    private boolean mainThread;
    private long expireTime; //ms
    private String cacheKey;
    private Map<String, Object> queryParams;
    private Map<String, Object> postParams;

    private HttpRequestBuilder(String method, RequestBody body) {
        requestBuilder = new Request.Builder();
        requestBuilder.method(method, body);
    }

    public static <T extends HttpResult> HttpRequestBuilder<T> get() {
        return new HttpRequestBuilder<>("GET", null);
    }

    public static <T extends HttpResult> HttpRequestBuilder<T> post(RequestBody body) {
        return new HttpRequestBuilder<>("POST", body);
    }


    public <T1 extends T> HttpRequestBuilder<T1> url(String url) {
        requestBuilder.url(url);

        return cast();
    }

    public <T1 extends T> HttpRequestBuilder<T1> url(HttpUrl url) {
        requestBuilder.url(url);

        return cast();
    }

    public <T1 extends T> HttpRequestBuilder<T1> url(URL url) {
        requestBuilder.url(url);

        return cast();
    }

    public <T1 extends T> HttpRequestBuilder<T1> addParam(String key, Object value) {
        if (queryParams == null) {
            queryParams = new ArrayMap<>(10);
        }
        queryParams.put(key, value);

        return cast();
    }

    public <T1 extends T> HttpRequestBuilder<T1> addPostParam(String key, Object value) {
        if (postParams == null) {
            postParams = new ArrayMap<>(10);
        }
        postParams.put(key, value);

        return cast();
    }


    public <T1 extends T> HttpRequestBuilder<T1> cacheControl(CacheControl cacheControl) {
        requestBuilder.cacheControl(cacheControl);

        return cast();
    }

    public <T1 extends T> HttpRequestBuilder<T1> tag(Object tag) {
        requestBuilder.tag(tag);

        return cast();
    }


    public <T1 extends T> HttpRequestBuilder<T1> mainThread(boolean mainThread) {
        this.mainThread  = mainThread;

        return cast();
    }

    public <T1 extends T> HttpRequestBuilder<T1> addHeader(String name, String value) {
        requestBuilder.addHeader(name, value);

        return cast();
    }

    public <T1 extends T> HttpRequestBuilder<T1> expireTime(long expireTime, TimeUnit unit) {
        if (expireTime > 0) {
            this.expireTime = unit.toMillis(expireTime);
        }
        return cast();
    }

    public <T1 extends T> HttpRequestBuilder<T1> cacheKey(String cacheKey) {
        this.cacheKey = cacheKey;

        return cast();
    }


    public <T1 extends T> HttpRequest<T1> build(Class<T1> clazz) {
        return new HttpRequestImp<T1>(new HttpRequestBuilderWrapper<T1>(this), clazz);
    }

    @SuppressWarnings("unchecked")
    private <T1 extends T> HttpRequestBuilder<T1> cast() {
        return (HttpRequestBuilder<T1>) this;
    }

    static class HttpRequestBuilderWrapper<T extends HttpResult> {
        HttpRequestBuilder mBuilder;

        HttpRequestBuilderWrapper(HttpRequestBuilder builder) {
            mBuilder = builder;
        }
        public boolean isMainThread() {
            return mBuilder.mainThread;
        }

        public Request.Builder getBuilder() {
            return mBuilder.requestBuilder;
        }

        public long getExpireTime() {
            return mBuilder.expireTime;
        }

        public String cacheKey() {
            return mBuilder.cacheKey;
        }
    }
}
