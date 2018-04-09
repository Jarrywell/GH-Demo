package com.android.test.demo.http;

import okhttp3.Request;

/**
 * des:
 * author: libingyan
 * Date: 18-4-8 14:25
 */
public interface HttpRequest<T extends HttpResult> {


    /**
     *
     * @param <T>
     */
    interface HttpRequestListener<T extends HttpResult> {


        /**
         *
         * @param request
         * @param response
         */
        void onCompleted(Request request, HttpResponse<T> response);


        /**
         *
         * @param request
         * @param httpCode
         * @param throwable
         */
        void onException(Request request, int httpCode, Throwable throwable);
    }


    /**
     *
     * @param listener
     */
    void enqueue(HttpRequestListener<T> listener);


    /**
     *
     * @return
     */
    HttpResponse<T> execute();


    /**
     *
     */
    void cancel();

}
