package com.android.test.demo.http;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * des:
 * author: libingyan
 * Date: 18-4-8 15:18
 */
public final class HttpRequestBuilder<T extends HttpResult> {

    public boolean mainThread;
    public Request.Builder requestBuilder;

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
        HttpRequestBuilder<T1> builder = cast();
        return builder;
    }

    public <T1 extends T> HttpRequestBuilder<T1> mainThread(boolean mainThread) {
        this.mainThread  = mainThread;

        return cast();
    }

    public <T1 extends T> HttpRequestBuilder<T1> addHeader(String name, String value) {
        requestBuilder.addHeader(name, value);

        return cast();
    }

    public <T1 extends T> HttpRequest<T1> build() {
        return new HttpRequestImp<T1>(this);
    }

    @SuppressWarnings("unchecked")
    private <T1 extends T> HttpRequestBuilder<T1> cast() {
        return (HttpRequestBuilder<T1>) this;
    }
}
