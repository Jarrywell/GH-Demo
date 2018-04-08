package com.android.test.demo.http;


/**
 * des:
 * author: libingyan
 * Date: 18-4-8 14:39
 */
public interface HttpResult<T> {

    /**
     * 解析返回的json
     * @param content
     * @throws Exception
     */
    T parse(String content) throws Exception;
}