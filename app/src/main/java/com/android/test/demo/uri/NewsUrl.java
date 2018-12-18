package com.android.test.demo.uri;


import android.text.TextUtils;
import java.util.Map;

/**
 * des: 描述资讯详情页url传递的参数
 *
 * RW_:表示需要先写入，才能读取 -> set()/get()
 * R_:表示是url本身自带的字段 -> get()
 *
 *
 * author: libingyan
 * Date: 18-11-29 15:55
 */
public final class NewsUrl extends UrlBean {

    private final static String TAG = "NewsUrl";

    /**
     * 频道Id
     */
    private static final String URL_RW_CHANNEL_ID_KEY = "channelId";


    /**
     * 频道type,取值参见NewsChannelEntity.cpsource
     */
    private static final String URL_RW_CHANNEL_TYPE_KEY = "mzChannelType";


    /**
     * 频道名称
     */
    private static final String URL_RW_CHANNEL_NAME_KEY = "channelName";


    /**
     * 应该是频道的cp:NewsChannelEntity.cpId
     */
    private static final String URL_RW_CHANNEL_CP_ID_KEY = "cpChannelId";


    /**
     * uniqueId的key固定为mzNewsId，适配相关推荐的
     */
    private static final String URL_RW_UNIQUE_ID_KEY = "mzNewsId";


    /**
     * conteType -对应旧版的newsType
     */
    private static final String URL_RW_CONTENT_TYPE_KEY = "MzContentType";



    /**
     * 文章id(魅族id)
     */
    private static final String URL_RW_ARTICLE_ID_KEY = "mzArticleId";


    /**
     * 文章标题
     */
    private static final String URL_RW_ARTICLE_TITLE_KEY = "mzNewsTitle";



    /**
     * 评论参数
     */
    private static final String URL_RW_BUSINESS_TYPE_KEY = "mpBusinessType";

    /**
     * 评论参数
     */
    private static final String URL_RW_BUSINESS_SUBTYPE_KEY = "mpBusinessSubType";

    /**
     * 评论参数
     */
    private static final String URL_RW_BUSINESS_ID_KEY = "mpBusinessId";


    /**
     * cp id
     */
    private static final String URL_RW_RESOURCE_TYPE_KEY = "resourceType";


    /**
     * 算法版本
     */
    private static final String URL_RW_ALGOVER_KEY = "algoVer";


    /**
     * request id
     */
    private static final String URL_RW_REQUEST_ID_KEY = "requestId";


    /**
     * 标识url是从资讯sdk中打开的key
     */
    private static final String URL_RW_FLAG_FROM_NEWS_SDK_KEY = "art-bro-from";


    /**
     * 只读，推送文章时生成的karl链接自带字段
     */
    private static final String URL_R_PUSH_ID_KEY = "mzPushId";


    /**
     * 只读，推送文章时生成的karl链接自带字段
     */
    private static final String URL_R_PUSH_TYPE_KEY = "pushType";


    /**
     * 原始url
     */
    @Deprecated
    private static final String URL_RW_ORIGIN_URL_KEY = "mzOriginUrl";


    /**
     * 广告id
     */
    private static final String URL_RW_ADVERTISE_ID_KEY = "adId";


    /**
     * 广告的feedSign
     */
    private static final String URL_RW_ADVERTISE_FEED_SIGN_KEY = "feedSign";


    /**
     * 浏览器特有参数，用于详情页的相关推荐接口参数拼接
     */
    @Deprecated
    private static final String URL_R_NEWS_TYPE_KEY = "newsType";



    /**
     *
     * @param url
     * @return
     */
    public static NewsUrl of(String url) {
        return new NewsUrl(url);
    }

    /**
     *
     * @param url
     */
    private NewsUrl(String url) {
        super(url);
    }


    /**
     * 写入频道id
     * @param channelId
     */
    public NewsUrl setChannelId(long channelId) {

        return  addParam(URL_RW_CHANNEL_ID_KEY, String.valueOf(channelId));
    }

    /**
     * 获取频道id
     * @param defalutChannelId
     * @return
     */
    public long getChannelId(long defalutChannelId) {
        return getLongParam(URL_RW_CHANNEL_ID_KEY, defalutChannelId);
    }

    /**
     * 写入频道type
     * @param channelType
     * @return
     */
    public NewsUrl setChannelType(long channelType) {

        return addParam(URL_RW_CHANNEL_TYPE_KEY, String.valueOf(channelType));
    }

    /**
     * 获取频道type
     * @param defaultChannelType
     * @return
     */
    public long getChannelType(long defaultChannelType) {

        return getLongParam(URL_RW_CHANNEL_TYPE_KEY, defaultChannelType);
    }

    /**
     *
     * @param channelName
     * @return
     */
    public NewsUrl setChannelName(String channelName) {

        return addParam(URL_RW_CHANNEL_NAME_KEY, channelName);
    }

    /**
     *
     * @return
     */
    public String getChannelName() {
        return getStringParam(URL_RW_CHANNEL_NAME_KEY);
    }


    /**
     *
     * @param cpChannelId
     * @return
     */
    public NewsUrl setCpChannelId(long cpChannelId) {

        return addParam(URL_RW_CHANNEL_CP_ID_KEY, String.valueOf(cpChannelId));
    }


    /**
     *
     * @return
     */
    public long getCpChannelId() {

        return getLongParam(URL_RW_CHANNEL_CP_ID_KEY, 0);
    }



    /**
     *
     * @param articleId
     * @return
     */
    public NewsUrl setArticleId(long articleId) {

        return addParam(URL_RW_ARTICLE_ID_KEY, String.valueOf(articleId));
    }

    /**
     *
     * @return
     */
    public long getArticleId() {

        return getLongParam(URL_RW_ARTICLE_ID_KEY, 0);
    }

    /**
     *
     * @param uniqueId
     * @return
     */
    public NewsUrl setUniqueId(String uniqueId) {

        return addParam(URL_RW_UNIQUE_ID_KEY, uniqueId);
    }

    /**
     *
     * @return
     */
    public String getUniqueId() {
        return getStringParam(URL_RW_UNIQUE_ID_KEY);
    }


    /**
     *
     * @param contentType
     * @return
     */
    public NewsUrl setContentType(int contentType) {

        return addParam(URL_RW_CONTENT_TYPE_KEY, String.valueOf(contentType));
    }


    /**
     *
     * @return
     */
    public int getContentType() {
        return getIntParam(URL_RW_CONTENT_TYPE_KEY, -1);
    }

    /**
     *
     * @param title
     * @return
     */
    public NewsUrl setArticleTitle(String title) {

        return addParam(URL_RW_ARTICLE_TITLE_KEY, title);
    }

    /**
     *
     * @return
     */
    public String getArticleTitle() {

        return getStringParam(URL_RW_ARTICLE_TITLE_KEY);
    }


    /**
     * 添加mpBusinessType
     * @param mpBusinessType
     */
    public NewsUrl setBusinessType(int mpBusinessType) {

        return addParam(URL_RW_BUSINESS_TYPE_KEY, String.valueOf(mpBusinessType));
    }

    /**
     * 写入mpBusinessType
     * @param defaultBusinessType
     * @return
     */
    public int getBusinessType(int defaultBusinessType) {

        //return getIntParam(URL_RW_BUSINESS_TYPE_KEY, defaultBusinessType);



        /**
         * 不存在mpBussinessXX,则看看originUrl中是否有
         */
        int result =  getIntParam(URL_RW_BUSINESS_TYPE_KEY, -1);
        if (result == -1) {
            final String originUrl = getOriginUrl();
            if (!TextUtils.isEmpty(originUrl)) {
                /**
                 * 从origin url中读取
                 */
                return of(originUrl).getBusinessType(defaultBusinessType);
            } else {
                result = defaultBusinessType;
            }
        }
        return result;
    }

    /**
     *
     * @param mpBusinessSubType
     */
    public NewsUrl setBusinessSubType(int mpBusinessSubType) {

        return addParam(URL_RW_BUSINESS_SUBTYPE_KEY, String.valueOf(mpBusinessSubType));
    }

    /**
     *
     * @param defaultBusinessSubType
     * @return
     */
    public int getBusinessSubType(int defaultBusinessSubType) {

        //return getIntParam(URL_RW_BUSINESS_SUBTYPE_KEY, defaultBusinessSubType);

        /**
         * 不存在mpBussinessXX,则看看originUrl中是否有
         */
        int result = getIntParam(URL_RW_BUSINESS_SUBTYPE_KEY, -1);
        if (result == -1) {
            final String originUrl = getOriginUrl();
            if (!TextUtils.isEmpty(originUrl)) {
                return of(originUrl).getBusinessSubType(defaultBusinessSubType);
            } else {
                result = defaultBusinessSubType;
            }
        }
        return result;
    }

    /**
     *
     * @param mpBusinessId
     */
    public NewsUrl setBusinessId(String mpBusinessId) {

        return addParam(URL_RW_BUSINESS_ID_KEY, mpBusinessId);
    }

    /**
     *
     * @return
     */
    public String getBusinessId() {

        //return getStringParam(URL_RW_BUSINESS_ID_KEY);


        /**
         * 不存在mpBussinessXX,则看看originUrl中是否有
         */
        String result = getStringParam(URL_RW_BUSINESS_ID_KEY);;
        if (TextUtils.isEmpty(result)) {

            final String originUrl = getOriginUrl();

            if (!TextUtils.isEmpty(originUrl)) {
                return of(originUrl).getBusinessId();
            }
        }
        return result;
    }

    /**
     *
     * @param resourceType
     */
    public NewsUrl setResourceType(long resourceType) {

        return addParam(URL_RW_RESOURCE_TYPE_KEY, String.valueOf(resourceType));
    }

    /**
     *
     * @return
     */
    public int getResourceType() {

        return getIntParam(URL_RW_RESOURCE_TYPE_KEY, 6);
    }



    /**
     *
     * @param algoVer
     */
    public NewsUrl setAlgoVer(String algoVer) {
        return addParam(URL_RW_ALGOVER_KEY, algoVer);
    }


    /**
     *
     * @return
     */
    public String getAlgoVer() {

        final String algoVer =  getStringParam(URL_RW_ALGOVER_KEY);
        return !TextUtils.isEmpty(algoVer) ? algoVer : "MEIZU";
    }


    /**
     *
     * @param requestId
     */
    public NewsUrl setReqeustId(String requestId) {
        return addParam(URL_RW_REQUEST_ID_KEY, requestId);
    }


    /**
     *
     * @return
     */
    public String getRequestId() {

        return getStringParam(URL_RW_REQUEST_ID_KEY);
    }


    /**
     *
     * @return
     */
    public NewsUrl setFlagOfNewsSdk() {

        return addParam(URL_RW_FLAG_FROM_NEWS_SDK_KEY, String.valueOf(true));
    }

    /**
     *
     * @return
     */
    public boolean isFromNewsSdk() {

        return getBooleanParam(URL_RW_FLAG_FROM_NEWS_SDK_KEY, false);
    }


    /**
     * 获取url中的pushId
     * @return
     */
    public String getPushId() {

        return getStringParam(URL_R_PUSH_ID_KEY);
    }

    /**
     * 获取url中的pushType
     * @return
     */
    public String getPushType() {
        return getStringParam(URL_R_PUSH_TYPE_KEY);
    }


    /**
     * 设置原始url
     * @param url
     * @return
     */
    @Deprecated
    public NewsUrl setOriginUrl(String url) {
        return addParam(URL_RW_ORIGIN_URL_KEY, url);
    }

    /**
     * 读取原始url
     * @return
     */
    @Deprecated
    public String getOriginUrl() {
        return getStringParam(URL_RW_ORIGIN_URL_KEY);
    }


    /**
     * adId
     * @param advertiseId
     * @return
     */
    public NewsUrl setAdvertiseId(String advertiseId) {

        return addParam(URL_RW_ADVERTISE_ID_KEY, advertiseId);
    }

    /**
     * adId
     * @return
     */
    public String getAdvertiseId() {

        return getStringParam(URL_RW_ADVERTISE_ID_KEY);
    }


    /**
     * feedSign
     * @param feedSign
     * @return
     */
    public NewsUrl setAdFeedSign(String feedSign) {

        return addParam(URL_RW_ADVERTISE_FEED_SIGN_KEY, feedSign);
    }


    /**
     * feedSign
     * @return
     */
    public String getAdFeedSign() {

        return getStringParam(URL_RW_ADVERTISE_FEED_SIGN_KEY);
    }


    /**
     * 获取newsType
     * @return
     */
    @Deprecated
    public int getNewsType() {
        return getIntParam(URL_R_NEWS_TYPE_KEY, -1);
    }


    @Override
    public NewsUrl addParam(String key, String value) {
        return (NewsUrl) super.addParam(key, value);
    }



    @Override
    public NewsUrl addParams(Map<String, String> params) {
        return (NewsUrl) super.addParams(params);
    }
}
