package com.android.test.demo.http;

import android.graphics.Bitmap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BitmapInterface {

    @GET
    Call<Bitmap> getBitmap(@Url String url);
}
