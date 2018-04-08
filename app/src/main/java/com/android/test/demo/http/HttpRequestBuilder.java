package com.android.test.demo.http;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * des:
 * author: libingyan
 * Date: 18-4-8 15:18
 */
public final class HttpRequestBuilder<T extends HttpResult> {

    private Request.Builder mRequestBuilder;

    private HttpRequestBuilder(String method, RequestBody body) {
        mRequestBuilder = new Request.Builder();
        mRequestBuilder.method(method, body);
    }

    public static <T extends HttpResult> HttpRequestBuilder<T> get() {
        return new HttpRequestBuilder<T>("GET", null);
    }

    public static <T extends HttpResult> HttpRequestBuilder<T> post(RequestBody body) {
        return new HttpRequestBuilder<T>("POST", body);
    }

    public HttpRequestBuilder<T> url(String url) {
        mRequestBuilder.url(url);
        return this;
    }

    public HttpRequestBuilder<T> addHeader(String name, String value) {
        mRequestBuilder.addHeader(name, value);
        return this;
    }

    public HttpRequest<T> build() {
        return new HttpRequestImp<T>(mRequestBuilder);
    }
}
