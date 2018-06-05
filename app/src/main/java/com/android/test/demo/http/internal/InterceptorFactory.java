package com.android.test.demo.http.internal;

import com.android.test.demo.App;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * des:
 * author: libingyan
 * Date: 18-6-5 21:08
 */
public class InterceptorFactory {

    public static NetWorkInterceptor createNetWorkInterceptor() {
        return new NetWorkInterceptor();
    }

    public static LocalInterceptor createLocalInterceptor() {
        return new LocalInterceptor();
    }


    static final class NetWorkInterceptor implements Interceptor {
        final String TAG = "NetWorkInterceptor";
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (isNetworkWorking()) {

                /**
                 * 处理后续请求
                 */
                Response response = chain.proceed(request);

                CacheControl requestCacheControl = request.cacheControl();
                CacheControl responseCacheControl = response.cacheControl();
                Log.d(TAG, "cache contorl -- request: " + requestCacheControl + " & response: " + responseCacheControl);

                /**
                 * 满足缓存时间控制条件，则替换响应头
                 */
                if (responseCacheControl.maxAgeSeconds() == -1 && requestCacheControl.maxAgeSeconds() != -1) {
                    return  response.newBuilder()
                        .header("Cache-Control", requestCacheControl.toString())
                        .removeHeader("Pragma")
                        .build();
                } else {
                    /**
                     * 不需要再次执行一遍
                     */
                    //return chain.proceed(request);

                    return response;
                }
            }

            /**
             * 如果没有网络，不做处理，直接返回，后续interceptor会处理
             */
            return chain.proceed(request);
        }
    }

    static final class LocalInterceptor implements Interceptor {
        final String TAG = "LocalInterceptor";
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            if (!isNetworkWorking()) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();

                Log.i(TAG, "not network! then force request cache!!!");
                Response response = chain.proceed(request);

                return response.newBuilder()
                    .header("Cache-Control", CacheControl.FORCE_CACHE.toString())
                    .removeHeader("Pragma")
                    .build();
            }

            /**
             * 有网络的时候，这个拦截器不做处理，直接返回
             */
            return chain.proceed(request);
        }
    }


    private static boolean isNetworkWorking() {
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
