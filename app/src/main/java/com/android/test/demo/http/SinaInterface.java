package com.android.test.demo.http;

import com.android.test.demo.http.internal.BaseUrl;
import com.android.test.demo.http.internal.CacheTimes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

@BaseUrl("http://int.dpool.sina.com.cn")
public interface SinaInterface {

    @Headers(CacheTimes.CACHE_TIME_1_DAY)
    @GET("/iplookup/iplookup.php?format=json&amp;ip=218.192.3.42")
    Call<AddressInfo> requestAddressInfo();
}
