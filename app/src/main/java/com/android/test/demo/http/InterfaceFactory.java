package com.android.test.demo.http;

import com.android.test.demo.BuildConfig;
import com.android.test.demo.http.internal.HttpFactory;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.android.test.demo.http.internal.HttpFactory.TYPE_BITMAP;
import static com.android.test.demo.http.internal.HttpFactory.TYPE_JSON;

/**
 * des:
 * author: libingyan
 * Date: 18-6-5 21:43
 */
public class InterfaceFactory {

    public static GithubInterface getGitHubHttpInterface() {
        return new HttpFactory.Builder<GithubInterface>()
            .service(GithubInterface.class)
            .log(BuildConfig.DEBUG)
            .type(TYPE_JSON)
            .build();
    }


    public static SinaInterface getSinaHttpInterfaces() {
        return new HttpFactory.Builder<SinaInterface>()
            .service(SinaInterface.class)
            .log(BuildConfig.DEBUG)
            .type(TYPE_JSON)
            .build();
    }


    public static BitmapInterface getBitmapInterfaces() {
        return new HttpFactory.Builder<BitmapInterface>()
            .service(BitmapInterface.class)
            .log(BuildConfig.DEBUG)
            .type(TYPE_BITMAP)
            .build();
    }

    /**
     * 
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
            .subscribeOn(Schedulers.newThread())//子线程访问网络
            .observeOn(AndroidSchedulers.mainThread())//回调到主线程
            .subscribe(observer);
    }
}
