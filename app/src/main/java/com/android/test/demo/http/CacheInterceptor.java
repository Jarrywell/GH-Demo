package com.android.test.demo.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.test.demo.App;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {
    private final String TAG = "CacheInterceptor";

    public CacheInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isNetworkWorking()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (isNetworkWorking()) {
            final CacheControl requestCacheControl = request.cacheControl();
            final CacheControl responseCacheControl = response.cacheControl();

            Log.i(TAG, "request cache control: " + requestCacheControl);
            Log.i(TAG, "response cache control: " + responseCacheControl);
            if (responseCacheControl.maxAgeSeconds() == -1
                    && requestCacheControl.maxAgeSeconds() != -1) {
                return  response.newBuilder()
                        .header("Cache-Control", requestCacheControl.toString())
                        .removeHeader("Pragma")
                        .build();
            }
        } else {
            return response.newBuilder()
                    .header("Cache-Control", CacheControl.FORCE_CACHE.toString())
                    .removeHeader("Pragma")
                    .build();
        }

        return response;
    }


    public static boolean isNetworkWorking() {
        final Context context = App.getContext();
        ConnectivityManager connectManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == connectManager) {
            return false;
        }

        Network[] networks = connectManager.getAllNetworks();
        final NetworkInfo.State state = NetworkInfo.State.CONNECTED;
        for (Network network : networks) {
            if (network != null && connectManager.getNetworkInfo(network).getState() == state) {
                return true;
            }
        }
        return false;
    }
}
