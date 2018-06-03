package com.android.test.demo.http;

import android.graphics.Bitmap;

import retrofit2.Call;
import retrofit2.http.GET;

@BaseUrl("http://d.hiphotos.baidu.com")
public interface BitmapInterfaces {

    @GET("/image/pic/item/63d0f703918fa0ec53d199aa2a9759ee3d6ddb07.jpg")
    Call<Bitmap> getBitmap1();
}
