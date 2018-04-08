package com.android.test.demo.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * des:
 * author: libingyan
 * Date: 18-4-8 15:37
 */
class HttpRequestImp<T extends HttpResult> implements HttpRequest {
    private final String TAG = "HttpRequest";
    private static OkHttpClient sOkHttpClient;
    private Request.Builder mBuilder;
    private Call mCall;

    HttpRequestImp(Request.Builder builder) {
        if (sOkHttpClient == null) {
            sOkHttpClient = new OkHttpClient.Builder().build();
        }
        mBuilder = builder;
    }

    @Override
    public void enqueue(HttpRequestListener listener) {
        mCall = sOkHttpClient.newCall(mBuilder.build());
        mCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    @Override
    public HttpResponse<T> execute() {
        mCall = sOkHttpClient.newCall(mBuilder.build());
        Response response = null;
        try {
            response = mCall.execute();
        } catch (Exception e) {};

        ResponseBody body = response.body();
        String content = "";
        try {
            content = body.string();
        } catch (Exception e) {}

        T result = null;
        return new HttpResponse<T>(result, response);
    }


    @Override
    public void cancel() {
        if (mCall != null && !mCall.isExecuted() && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }
}
