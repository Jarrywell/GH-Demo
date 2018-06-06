package com.android.test.demo.http.internal;


import android.util.Log;

import com.android.test.demo.App;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class HttpFactory {
    private static final String TAG = "HttpFactory";

    /**
     * 标识返回的数据是Json格式
     */
    public static final String TYPE_JSON = "json";

    /**
     * 标识返回的数据是bitmap
     */
    public static final String TYPE_BITMAP = "image";


    /**
     * 普通Json缓存大小
     */
    private static final int JSON_CACHE_SIZE = 1024 * 1024 * 20;


    /**
     * 图片缓存大小
     */
    private static final int BITMAP_CACHE_SIZE = 1024 * 1024 * 20;


    private final Map<String, OkHttpClient> mClients = new ConcurrentHashMap<>(4);
    private final Map<Class<?>, Object> mInterfaces = new ConcurrentHashMap<>(10);
    private final Map<HttpUrl, List<Cookie>> mCookieStore = new HashMap<>();
    private HttpLoggingInterceptor mLoggingInterceptor;
    private static HttpFactory INSTANCE = new HttpFactory();


    public static class Builder<T> {
        private boolean log;
        private String type;
        private Class<T> service;
        private SSLSocketFactory sSLSocketFactory;

        public Builder<T> type(String type) {
            this.type = type;
            return this;
        }

        public Builder<T> log(boolean log) {
            this.log = log;
            return this;
        }

        public Builder<T> service(final Class<T> service) {
            this.service = service;
            return this;
        }

        public Builder<T> addSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.sSLSocketFactory = sSLSocketFactory;
            return this;
        }

        public T build() {
            Class<T> serviceClz = this.service;
            if (service == null) {
                if (log) Log.i(TAG, "HttpFactory must set http service!");
                return null;
            }
            Object service = INSTANCE.mInterfaces.get(serviceClz);
            if (service != null) {
                return (T) service;
            }
            synchronized (INSTANCE.mInterfaces) {
                service = INSTANCE.mInterfaces.get(serviceClz);
                if (service == null) {
                    String baseUrl = "http://localhost/";
                    if(serviceClz.isAnnotationPresent(BaseUrl.class)) {
                        BaseUrl nameInject = serviceClz.getAnnotation(BaseUrl.class);
                        baseUrl = nameInject.value();
                    }
                    Log.i(TAG, "create base url: " + baseUrl);
                    Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(INSTANCE.getClient(type, sSLSocketFactory, log))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addCallAdapterFactory(Java8CallAdapterFactory.create());
                    if (type.equals(TYPE_JSON)) {
                        builder.addConverterFactory(GsonConverterFactory.create());
                    } else if (type.equals(TYPE_BITMAP)) {
                        builder.addConverterFactory(BitmapConverterFactory.create());
                    }
                    service = builder.build().create(serviceClz);

                    INSTANCE.mInterfaces.put(serviceClz, service);
                }
            }
            return (T) service;
        }
    }

    /*public static <T> T getService(Class<T> interfaces) {
        if (interfaces != null) {
            Object service = INSTANCE.mInterfaces.get(interfaces);
            if (service != null) {
                return (T) service;
            }
            synchronized (INSTANCE.mInterfaces) {
                service = INSTANCE.mInterfaces.get(interfaces);
            }
            return (T) service;
        }
        return null;
    }*/

    private OkHttpClient getClient(String type, SSLSocketFactory sSLSocketFactory, boolean log) {
        OkHttpClient client = mClients.get(type);
        if (client != null) {
            return client;
        }
        synchronized (mClients) {
            client = mClients.get(type);
            if (client == null) {
                client = createHttpClinet(type, sSLSocketFactory, log);
                mClients.put(type, client);
            }
        }
        return client;
    }

    private HttpFactory() {
        mLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i(TAG, message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }

    private OkHttpClient createHttpClinet(String type, SSLSocketFactory sSLSocketFactory, boolean log) {
        final long cacheSize = type.equals(TYPE_JSON) ? JSON_CACHE_SIZE : BITMAP_CACHE_SIZE;
        final File cachePath = new File(App.getContext().getExternalCacheDir(), type);
        final Cache cache = new Cache(cachePath, cacheSize);
        Log.i(TAG, "cache path: " + cachePath.getAbsolutePath() + ", size: " + cacheSize);

        /**
         * 控制最大运行的线程数
         */
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(32); //默认64

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .connectTimeout(8, TimeUnit.SECONDS)
            .writeTimeout(8, TimeUnit.SECONDS)
            .readTimeout(8, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(InterceptorFactory.createLocalInterceptor())
            .addNetworkInterceptor(InterceptorFactory.createNetWorkInterceptor())
            .cache(cache)
            .dispatcher(dispatcher);

        if (sSLSocketFactory != null) {
            builder.sslSocketFactory(sSLSocketFactory);
        }

        if (log) {
            builder.addNetworkInterceptor(mLoggingInterceptor);
        }

        /**
         * 设置cookie
         */
        builder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                mCookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = mCookieStore.get(url);
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });

        return builder.build();
    }
}
