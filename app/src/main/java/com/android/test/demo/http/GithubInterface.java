package com.android.test.demo.http;

import com.android.test.demo.http.internal.BaseUrl;
import com.android.test.demo.http.internal.CacheTimes;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

@BaseUrl("https://api.github.com")
public interface GithubInterface {

    /**
     * 普通请求返回Call
     * @param owner
     * @param repo
     * @return
     */
    @Headers(CacheTimes.CACHE_TIME_30_SECONDS)
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(@Path("owner") String owner, @Path("repo") String repo);


    /**
     * RxJava形式请求返回Observable
     * @param owner
     * @param repo
     * @return
     */
    @Headers(CacheTimes.CACHE_TIME_30_SECONDS)
    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> contributorsOfRxJava(@Path("owner") String owner, @Path("repo") String repo);


    /**
     * Java8形式请求返回CompletableFuture
     * @param owner
     * @param repo
     * @return
     */
    @Headers(CacheTimes.CACHE_TIME_30_SECONDS)
    @GET("/repos/{owner}/{repo}/contributors")
    CompletableFuture<List<Contributor>> contributorsOfJava8(@Path("owner") String owner, @Path("repo") String repo);

}
