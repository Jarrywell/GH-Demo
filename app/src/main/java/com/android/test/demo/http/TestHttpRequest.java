package com.android.test.demo.http;

import okhttp3.Request;

/**
 * des:
 * author: libingyan
 * Date: 18-4-8 15:09
 */
public class TestHttpRequest {

    void test() {
        HttpRequestBuilder<TestBean> builder = HttpRequestBuilder.get();

        HttpRequest<TestBean> request = builder.url("www.baidu.com")
            .addHeader("a", "value")
            .build();

        /*HttpRequest<TestBean> ss = HttpRequestBuilder.get().url("www.baidu.com")
            .addHeader("a", "value")
            .build();*/


        request.enqueue(new HttpRequest.HttpRequestListener<TestBean>() {
            @Override
            public void onCompleted(HttpResponse<TestBean> response) {

            }

            @Override
            public void onException(Throwable throwable, int httpCode) {

            }
        });


        HttpResponse<TestBean> result = request.execute();
    }
}
