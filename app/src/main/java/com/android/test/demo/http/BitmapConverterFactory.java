package com.android.test.demo.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class BitmapConverterFactory extends Converter.Factory {

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == Bitmap.class) {
            return new Converter<ResponseBody, Bitmap>(){

                @Override
                public Bitmap convert(ResponseBody value) throws IOException {
                    return BitmapFactory.decodeStream(value.byteStream());
                }
            };
        } else {
            return null;
        }
    }

}
