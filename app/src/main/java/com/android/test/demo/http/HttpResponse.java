package com.android.test.demo.http;

import okhttp3.Response;

/**
 * des:
 * author: libingyan
 * Date: 18-4-8 15:04
 */
public final class HttpResponse <T extends HttpResult>{

    private T result;

    private Response response;

    HttpResponse(T result, Response response) {
        this.result = result;
        this.response = response;
    }

    public T getResult() {
        return result;
    }

    public Response getResponse() {
        return response;
    }
}
