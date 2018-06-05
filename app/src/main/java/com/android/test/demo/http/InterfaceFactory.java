package com.android.test.demo.http;

import com.android.test.demo.http.internal.HttpFactory;

import static com.android.test.demo.http.internal.HttpFactory.TYPE_BITMAP;
import static com.android.test.demo.http.internal.HttpFactory.TYPE_JSON;

/**
 * des:
 * author: libingyan
 * Date: 18-6-5 21:43
 */
public class InterfaceFactory {


    public static GithubInterface getGitHubHttpInterface() {
        return HttpFactory.create(GithubInterface.class, TYPE_JSON);
    }


    public static SinaInterface getSinaHttpInterfaces() {
        return HttpFactory.create(SinaInterface.class, TYPE_JSON);
    }


    public static BitmapInterface getBitmapInterfaces() {
        return HttpFactory.create(BitmapInterface.class, TYPE_BITMAP);
    }
}
