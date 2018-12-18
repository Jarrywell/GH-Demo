package com.android.test.demo.uri;


import android.net.Uri;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * des: 支持对url增加参数和参数判断
 * [scheme:][//host:port][path][?query][#fragment]
 * author: libingyan
 * Date: 18-11-29 17:51
 */
public class UrlBean {

    private static final String TAG = "UrlBean";


    /**
     * 写入
     */
    private Uri.Builder builder;

    /**
     * 读取
     */
    private Uri uri;


    /**
     * 是否有新写入参数
     */
    private boolean changed;


    /**
     * 缓存
     */
    private static Map<Integer, Uri> cache = new ConcurrentHashMap<>(64);

    /**
     * 构建UrlBean
     * @param url
     * @return
     */
    public static UrlBean of(String url) {
        return new UrlBean(url);
    }


    protected UrlBean(String url) {
        changed = false;
        uri = createUri(url);
    }

    /**
     * 增加缓存策略
     * @param url
     * @return
     */
    private Uri createUri(String url) {
        /**
         * 返回默认的uri
         */
        if (TextUtils.isEmpty(url)) {
            return Uri.EMPTY;
        }

        final int hashcode = url.hashCode();

        Uri uri = cache.get(hashcode);
        if (uri == null) {

            uri = Uri.parse(url);

            cache.put(hashcode, uri);

            Log.i(TAG, "create uri: " + url);
            if (uri.isOpaque()) {
                Log.e(TAG, "", new IllegalArgumentException("url: " + url));
            }

            /**
             * 优化：只有在需要添加参数时，才动态构建builder
             */
            //builder = uri.buildUpon();
        }
        return uri;
    }


    /**
     * 清理缓存
     */
    public static void clear() {
        Log.d(TAG, "release url size: " + cache.size());
        cache.clear();
    }


    /**
     * 获取原始值，注意当url中有多个key值时，则返回第一个value
     * @param key
     * @return
     */
    public final String getStringParam(String key) {
        final Uri uri = getUri();
        if (!uri.isOpaque()) {
            final String result =  uri.getQueryParameter(key);
            return !TextUtils.isEmpty(result) ? result : "";
        }
        return "";
    }


    /**
     * 获取int类型的值
     * @param key
     * @param defaultValue
     * @return
     */
    public final int getIntParam(String key, int defaultValue) {
        final String result = getStringParam(key);
        try {
            return Integer.parseInt(result);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取long类型的值
     * @param key
     * @param defaultValue
     * @return
     */
    public final long getLongParam(String key, long defaultValue) {
        final String result = getStringParam(key);
        try {
            return Long.parseLong(result);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取boolean类型的值
     * 注意：该接口只有value为false或者0的时候才返回false，其他情况返回true.比如：value=''时也是true
     * @param key
     * @param defaultValue
     * @return
     */
    public final boolean getBooleanParam(String key, boolean defaultValue) {
        final Uri uri = getUri();
        if (!uri.isOpaque()) {
            return uri.getBooleanQueryParameter(key, defaultValue);
        } else {
            return defaultValue;
        }
    }


    /**
     * 获取float类型的值
     * @param key
     * @param defaultValue
     * @return
     */
    public final float getFloatParam(String key, float defaultValue) {
        final String result = getStringParam(key);
        try {
            return Float.parseFloat(result);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     * 单个添加参数
     * @param key
     * @param value
     */
    public UrlBean addParam(String key, String value) {

        getBuilder().appendQueryParameter(key, value);

        /**
         * 标识uri的变化
         */
        changed = true;


        return this;
    }


    /**
     * 批量添加参数
     * @param params
     */
    public UrlBean addParams(Map<String, String> params) {
        Set<String> keys = params.keySet();
        for (String key : keys) {
            getBuilder().appendQueryParameter(key, params.get(key));
        }

        /**
         * 标识uri的变化
         */
        changed = true;


        return this;
    }

    /**
     * url中是否存在key
     * @param key
     * @return
     */
    public final boolean contains(String key) {
        final Uri uri = getUri();
        if (!uri.isOpaque()) {
            Set<String> keys = uri.getQueryParameterNames();
            return keys.contains(key);
        }
        return false;
    }



    /**
     * 获取最终的uri（仅在读取的时候才去同步）
     * @return
     */
    private Uri getUri() {
        if (uri == null || changed) {
            uri = getBuilder().build();

            /**
             * 复位: builder数据与uri数据同步完成
             */
            changed = false;
        }
        return uri;
    }

    /**
     *
     * @return
     */
    private Uri.Builder getBuilder() {
        if (builder == null) {
            builder = uri.buildUpon();
        }
        return builder;
    }



    /**
     * 将增加的参数构造成encode编码的url
     * @return
     */
    public final String getUrl() {
        return getUri().toString();
    }

    /**
     * 返回host
     * @return
     */
    public final String getHost() {
       return getUri().getHost();
    }

    /**
     * 返回url
     * @return
     */
    @Override
    public String toString() {
        return getUrl();
    }

    /**
     * 将查询参数格式化成[key=value]返回，用于打印url参数
     * @return
     */
    public final String toParams() {
        Map<String, String> params = getParams();
        StringBuilder builder = new StringBuilder();
        builder.append("url params:");
        Set<String> keys = params.keySet();
        for (String key : keys) {
            builder.append("[").append(key).append("=").append(params.get(key)).append("]");
        }
        return builder.toString();
    }


    /**
     * 获取url中所有的参数（若有重复的key，只返回第一次出现的value）
     * @return
     */
    public final Map<String, String> getParams() {
        Uri uri = getUri();
        if (!uri.isOpaque()) {
            Set<String> keys = uri.getQueryParameterNames();
            Map<String, String> result = new ArrayMap<>(keys.size());
            for (String key : keys) {
                result.put(key, uri.getQueryParameter(key));
            }
            return result;
        }
        return null;
    }

}
