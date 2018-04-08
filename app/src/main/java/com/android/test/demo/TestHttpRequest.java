package com.android.test.demo;

import com.android.test.demo.http.HttpRequest;
import com.android.test.demo.http.HttpRequestBuilder;
import com.android.test.demo.http.HttpResponse;

import okhttp3.Request;

/**
 * des:
 * author: libingyan
 * Date: 18-4-8 15:09
 */
public class TestHttpRequest {

    void doGet() {

        HttpRequest<TestBean> request = HttpRequestBuilder.get().url("www.baidu.com")
            .addHeader("a", "value")
            .mainThread(true)
            .build();

        request.enqueue(new HttpRequest.HttpRequestListener<TestBean>() {
            @Override
            public void onCompleted(Request request, HttpResponse<TestBean> response) {

            }

            @Override
            public void onException(Request request, int httpCode, Throwable throwable) {

            }
        });


        HttpResponse<TestBean> result = request.execute();

    }
}
