package com.android.test.demo.http;

import com.alibaba.fastjson.JSON;

import com.android.test.demo.http.HttpRequestBuilder.HttpRequestBuilderWrapper;

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
    private HttpRequestBuilderWrapper mBuilder;
    private OkHttpClient.Builder mOKHttpClientBuilder;
    private Class<T> mResponseClzz;
    private Call mCall;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    HttpRequestImp(HttpRequestBuilderWrapper builder, Class<T> clazz) {
        if (sOkHttpClient == null) {
            if (mOKHttpClientBuilder == null) {
                mOKHttpClientBuilder = new OkHttpClient.Builder();
            }
            sOkHttpClient = mOKHttpClientBuilder.build();
        }
        mBuilder = builder;
        mResponseClzz = clazz;
    }

    @Override
    public void enqueue(HttpRequestListener listener) {
        mCall = sOkHttpClient.newCall(mBuilder.getBuilder().build());
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
        mCall = sOkHttpClient.newCall(mBuilder.getBuilder().build());
        Response response = null;
        try {
            response = mCall.execute();
        } catch (Exception e) {};

        return new HttpResponse<T>(parse(response), response);
    }

    private void onCompleted(Call call, Response response, HttpRequestListener listener) {
        Request request = call.request();
        HttpResponse httpResponse = new HttpResponse(parse(response), response);

        if (!mBuilder.isMainThread()) {
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
        if (!mBuilder.isMainThread()) {
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

    private T parse(Response response) {
        ResponseBody body = response.body();
        String content = "";
        try {
            content = body.string();
        } catch (Exception e) {}

        return JSON.parseObject(content, mResponseClzz);
    }

}
