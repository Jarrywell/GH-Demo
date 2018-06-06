package com.android.test.demo.http.internal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

final class BitmapConverterFactory extends Converter.Factory {

    private final String TAG = "BitmapConverterFactory";


    public static BitmapConverterFactory create() {
        return new BitmapConverterFactory();
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
        Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

        /**
         * 上传图片需要用到
         */
        //return new BitmapRequestBodyConverter();
        return null;
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        if (getRawType(type) != Bitmap.class) {
            return null;
        }

        return new BitmapResponseBodyConverter();
    }


    final class BitmapResponseBodyConverter implements Converter<ResponseBody, Bitmap> {

        @Override
        public Bitmap convert(ResponseBody value) throws IOException {
            /**
             * 暂时只支持默认设置config
             */
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            return BitmapFactory.decodeStream(value.byteStream(), null, options);
        }
    }

    /**
     * 上传图片需要用到
     */
    final class BitmapRequestBodyConverter implements Converter<Bitmap, RequestBody> {

        @Override
        public RequestBody convert(Bitmap value) throws IOException {
            return null;
        }
    }
}
