package com.android.test.demo.http;

public interface CacheTimes {

    /**
     * 设置缓存时间字符串
     */
    String CACHE_TIME_CONTENT_PREFIX = "Cache-Control: max-age=";

    /**
     * 缓存时间为1天
     */
    String CACHE_TIME_1_DAY = CACHE_TIME_CONTENT_PREFIX + (3600 * 24);

    /**
     * 缓存时间为1小时
     */
    String CACHE_TIME_1_HOUR = CACHE_TIME_CONTENT_PREFIX + 3600;



    /**
     * 缓存时间为10分钟
     */
    String CACHE_TIME_10_MINUTE = CACHE_TIME_CONTENT_PREFIX + (60 * 10);


    /**
     * 缓存时间为1分钟
     */
    String CACHE_TIME_1_MINUTE = CACHE_TIME_CONTENT_PREFIX + 60;


    /**
     * 缓存时间为30秒
     */
    String CACHE_TIME_30_SECONDS = CACHE_TIME_CONTENT_PREFIX + 30;

    /**
     * 缓存时间为20秒
     */
    String CACHE_TIME_20_SECONDS = CACHE_TIME_CONTENT_PREFIX + 20;

}
