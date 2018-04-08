package com.android.test.demo.http;

import android.os.Handler;
import android.os.Looper;

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
    private HttpRequestBuilder mBuilder;
    private Call mCall;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    HttpRequestImp(HttpRequestBuilder builder) {
        if (sOkHttpClient == null) {
            sOkHttpClient = new OkHttpClient.Builder().build();
        }
        mBuilder = builder;
    }

    @Override
    public void enqueue(HttpRequestListener listener) {
        mCall = sOkHttpClient.newCall(mBuilder.requestBuilder.build());
        mCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onCompleted(call, response, listener);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                onException(call, e, listener);
            }
        });
    }

    @Override
    public HttpResponse<T> execute() {
        mCall = sOkHttpClient.newCall(mBuilder.requestBuilder.build());
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

    private void onCompleted(Call call, Response response, HttpRequestListener listener) {
        Request request = call.request();
        HttpResponse httpResponse = new HttpResponse(null, response);

        if (!mBuilder.mainThread) {
            listener.onCompleted(request, httpResponse);
        } else {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onCompleted(request, httpResponse);
                }
            });
        }
    }

    private void onException(Call call, IOException e, HttpRequestListener listener) {
        Request request = call.request();
        if (!mBuilder.mainThread) {
            listener.onException(request, 0, e);
        } else {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onException(request, 0, e);
                }
            });
        }
    }


    @Override
    public void cancel() {
        if (mCall != null && !mCall.isExecuted() && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }
}
