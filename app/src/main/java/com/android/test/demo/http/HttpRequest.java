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
     * @param listener
     */
    void enqueue(HttpRequestListener<T> listener);


    /**
     *
     * @return
     */
    HttpResponse<T> execute();


    void cancel();


    interface HttpRequestListener<T extends HttpResult> {

        /**
         *
         * @param response
         */
        void onCompleted(HttpResponse<T> response);


        /**
         *
         * @param throwable
         * @param httpCode
         */
        void onException(Throwable throwable, int httpCode);
    }

}
