package com.android.test.demo;

import android.net.Uri;
import android.util.Log;

import java.util.List;
import java.util.Set;

/**
 *
 * URI语法：[scheme:] scheme-specific-part [#fragment]
 *
 * URI分不透明URI和分层URI。
 * 1、不透明URI：不透明的URI指scheme-specific-part不是以正斜杠（/）开头的绝对的URI。
 *     不透明的URI并不是用于分解的。不透明的URI与其它的URI不同，它不服从标准化、分解和相对化。
 *（当且仅当 URI 是绝对的且其特定于方案的部分不是以斜线字符 ('/') 开始时，此 URI 才是不透明的。）
 *
 *
 *
 * 2、分层URI：分层的URI可以是以正斜杠开头的绝对的URI或相对的URL。
 *     scheme-specific-part的语法：[//authority] [path] [?query]。
 *
 * 分层URI分成基于服务器或基于注册的URI，基于服务器的URI[//authority] 部分语法为：[userinfo@] host [:port]。

 * des:测试uri的接口
 * author: libingyan
 * Date: 18-11-28 20:49
 */
public class TestUri {

    private static String TAG = "TestUri";

    /**
     * 测试url
     */
    private static final String url = "https://open.toutiao.com/a6626691667539788292/?utm_campaign=open&utm_medium=webview"
        + "&utm_source=meizu_zx_api&item_id=6626691667539788292&req_id=2018112820022801000805902325900&dt=pro7"
        + "&a_t=11155118104659902191536&city=local&imei=867884030058100&openudid=867884030058100&os=Android"
        + "&label=click_headline&gy=51d497add261dd9874f26ea6ef4b469728f0b73aeb9d65da9ff25c9a052fe4a4b79220ffc4a6388df68cfb4eb06c5dc3dab129ea9cb0b7fcc91cb3d3e667faf162a27572fe8d8965f2b906db516e2324"
        + "&crypt=9188&art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87#test-fragment";


    public static void test() {

        Uri uri = Uri.parse(url);

        Log.i(TAG, "url: " + url);

        /**
         * Gets the decoded fragment part of this URI, everything after the '#'
         * 翻译：获取uri中#号后面的内容(decode后的结果)
         *
         * result:
         * fragment: test-fragment
         */
        final String fragment = uri.getFragment();
        Log.i(TAG, "fragment: " + fragment);


        /**
         * Gets the encoded host from the authority for this URI. For example,
         * if the authority is "bob@google.com", this method will return
         * "google.com".
         * 翻译：获取uri中的host(encode编码)，例如：bob@google.com，则返回google.com
         *
         * result:
         * host: open.toutiao.com
         */
        final String host = uri.getHost();
        Log.i(TAG, "host: " + host);

        /**
         * Gets the scheme of this URI. Example: "http"
         * 翻译：获取uri中的scheme,例如：http
         *
         * result:
         * scheme: https
         */
        final String scheme = uri.getScheme();
        Log.i(TAG, "scheme: " + scheme);

        /**
         * Gets the encoded query component from this URI. The query comes after
         * the query separator ('?') and before the fragment separator ('#'). This
         * method would return "q=android" for
         * "http://www.google.com/search?q=android".
         * 翻译：获取uri中的查询部分(encode编码)，该部分在?之后，fragment(#)之前。
         * 例如：http://www.google.com/search?q=android， encodedQuery: q=android
         *
         *
         * result:
         * encodedQuery: utm_campaign=open&utm_medium=webview&utm_source=meizu_zx_api
         * &item_id=6626691667539788292&req_id=2018112820022801000805902325900&dt=pro7
         * &a_t=11155118104659902191536&city=local&imei=867884030058100&openudid=867884030058100
         * &os=Android&label=click_headline&gy=51d497add261dd9874f26ea6ef4b469728f0b73aeb9d65da9ff25c9a052fe4a4b79220ffc4a6388df68cfb4eb06c5dc3dab129ea9cb0b7fcc91cb3d3e667faf162a27572fe8d8965f2b906db516e2324
         * &crypt=9188&art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87
         *
         * 注意：chTitle=%e4%b8%ad%e6%96%87
         */
        final String encodedQuery = uri.getEncodedQuery();
        Log.i(TAG, "encodedQuery: " + encodedQuery);

        /**
         * 同上getEncodedQuery()，只是结果为decode结果
         *
         * result:
         * query: utm_campaign=open&utm_medium=webview&utm_source=meizu_zx_api
         * &item_id=6626691667539788292&req_id=2018112820022801000805902325900&dt=pro7
         * &a_t=11155118104659902191536&city=local&imei=867884030058100&openudid=867884030058100
         * &os=Android&label=click_headline&gy=51d497add261dd9874f26ea6ef4b469728f0b73aeb9d65da9ff25c9a052fe4a4b79220ffc4a6388df68cfb4eb06c5dc3dab129ea9cb0b7fcc91cb3d3e667faf162a27572fe8d8965f2b906db516e2324
         * &crypt=9188&art-bro-from=mzNewsSdk&chTitle=中文
         *
         * 注意：chTitle=中文
         */
        final String query = uri.getQuery();
        Log.i(TAG, "query: " + query);

        /**
         * Gets the decoded path.
         * 获取uri中的path(decode)
         *
         * result:
         * path: /a6626691667539788292/
         *
         * 注意首尾都带/
         */
        final String path  = uri.getPath();
        Log.i(TAG, "path: " + path);

        /**
         * Gets the scheme-specific part of this URI, i.e.&nbsp;everything between
         * the scheme separator ':' and the fragment separator '#'. If this is a
         * relative URI, this method returns the entire URI. Decodes escaped octets.
         * 翻译：从uri中获取scheme-specific部分（decode编码），scheme-specific位于:与#之间的内容
         *
         * result:
         * schemeSpecificPart: //open.toutiao.com/a6626691667539788292/?utm_campaign=open&utm_medium=webview&utm_source=meizu_zx_api
         * &item_id=6626691667539788292&req_id=2018112820022801000805902325900&dt=pro7
         * &a_t=11155118104659902191536&city=local&imei=867884030058100&openudid=867884030058100
         * &os=Android&label=click_headline&gy=51d497add261dd9874f26ea6ef4b469728f0b73aeb9d65da9ff25c9a052fe4a4b79220ffc4a6388df68cfb4eb06c5dc3dab129ea9cb0b7fcc91cb3d3e667faf162a27572fe8d8965f2b906db516e2324
         * &crypt=9188&art-bro-from=mzNewsSdk&chTitle=中文
         *
         */
        final String schemeSpecificPart = uri.getSchemeSpecificPart();
        Log.i(TAG, "schemeSpecificPart: " + schemeSpecificPart);

        /**
         * 同上
         *
         * 结果为encode
         *
         * result:
         *  encodeSchemeSpecificPart: //open.toutiao.com/a6626691667539788292/?utm_campaign=open&utm_medium=webview&utm_source=meizu_zx_api
         *  &item_id=6626691667539788292&req_id=2018112820022801000805902325900&dt=pro7
         *  &a_t=11155118104659902191536&city=local&imei=867884030058100&openudid=867884030058100
         *  &os=Android&label=click_headline&gy=51d497add261dd9874f26ea6ef4b469728f0b73aeb9d65da9ff25c9a052fe4a4b79220ffc4a6388df68cfb4eb06c5dc3dab129ea9cb0b7fcc91cb3d3e667faf162a27572fe8d8965f2b906db516e2324
         *  &crypt=9188&art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87
         *
         *  注意结果是encde编码
         */
        final String encodeSchemeSpecificPart = uri.getEncodedSchemeSpecificPart();
        Log.i(TAG, "encodeSchemeSpecificPart: " + encodeSchemeSpecificPart);

        /**
         * Gets the decoded path segments.
         * 翻译：获取uri中的segment列表（decode编码）
         *
         * result:
         * segment: a6626691667539788292
         */
        List<String> segments = uri.getPathSegments();
        for (String segment : segments) {
            Log.i(TAG, "segment: " + segment);
        }

        /**
         * Gets the encoded authority part of this URI. For
         * server addresses, the authority is structured as follows:
         * {@code [ userinfo '@' ] host [ ':' port ]}
         *翻译：获取uri中的authority(encode编码) 包括：@host:port
         *
         * result:
         * encodeAuthority: open.toutiao.com
         */
        final String encodeAuthority = uri.getEncodedAuthority();
        Log.i(TAG, "encodeAuthority: " + encodeAuthority);

        /**
         * 同上，只是结果为decode编码
         *
         * result:
         * authority: open.toutiao.com
         */
        final String authority = uri.getAuthority();
        Log.i(TAG, "authority: " + authority);


        /**
         * Gets the decoded user information from the authority.
         * For example, if the authority is "nobody@google.com", this method will
         * return "nobody".
         * 翻译：从authority中获取user信息（decode编码），例如：nobody@google.com，则返回nobody
         *
         * result:
         * userInfo: null
         */
        final String userInfo = uri.getUserInfo();
        Log.i(TAG, "userInfo: " + userInfo);

        /**
         *result:
         * mail userInfo: null
         * 跟注释不一样？！！
         */
        final Uri mail = Uri.parse("bob@google.com:80");
        Log.i(TAG, "mail userInfo: " + mail.getUserInfo());

        /**
         *
         * result:
         * https://open.toutiao.com/a6626691667539788292/?utm_campaign=open&utm_medium=webview&utm_source=meizu_zx_api
         * &item_id=6626691667539788292&req_id=2018112820022801000805902325900&dt=pro7&a_t=11155118104659902191536
         * &city=local&imei=867884030058100&openudid=867884030058100&os=Android&label=click_headline
         * &gy=51d497add261dd9874f26ea6ef4b469728f0b73aeb9d65da9ff25c9a052fe4a4b79220ffc4a6388df68cfb4eb06c5dc3dab129ea9cb0b7fcc91cb3d3e667faf162a27572fe8d8965f2b906db516e2324
         * &crypt=9188&art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87#test-fragment
         */
        Uri.Builder builder = uri.buildUpon();
        Log.i(TAG, "builder: " + builder);


        final String url = "https://open.testurl.com/a6626691667539788292?utm_campaign=open&art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87&testBoolean=true&testInteger=1&testString=测试+value#test-fragment";
        uri = Uri.parse(url);

        Log.i(TAG, "builder: " + uri.buildUpon());

        /**
         *
         * Searches the query string for the first value with the given key and interprets it
         * as a boolean value. "false" and "0" are interpreted as <code>false</code>, everything
         * else is interpreted as <code>true</code>.
         * 翻译：搜索第一个匹配key的字符串，如果value是false或0，则返回false，其余情况返回true
         *
         * result:
         * booleanParameter: true
         */
        final boolean booleanParameter = uri.getBooleanQueryParameter("testBoolean", false);
        Log.i(TAG, "booleanParameter: " + booleanParameter);

        /**
         * 同上
         * 这里测试非boolean的value
         *
         * result:
         * intParameter: true
         */
        final boolean intParameter = uri.getBooleanQueryParameter("testInteger", false);
        Log.i(TAG, "intParameter: " + intParameter);

        /**
         * 同上
         *
         * 这里测试string的value
         *
         * result:
         * stringParameter: true
         *
         * 注意：这里的testString=没有赋值，仍会返回true!!! 参考定义，只有两种情况会返回false
         *
         * */
        final boolean stringParameter = uri.getBooleanQueryParameter("testString", false);
        Log.i(TAG, "stringParameter: " + stringParameter);

        /**
         *
         * 获取最后一段path,(uri中会有多段path)
         * result:
         * lastPath: a6626691667539788292
         */
        final String lastPath = uri.getLastPathSegment();
        Log.i(TAG, "lastPath: " + lastPath);

        /**
         * Searches the query string for the first value with the given key.
         * 翻译：通过key查找第一个对应的vale
         *
         * p><strong>Warning:</strong> Prior to Jelly Bean, this decoded
         * the '+' character as '+' rather than ' '.
         * 警告：从4.4开始，字符串中的'+'会被替换成' ',因此url中的字符最好是被编码后的字符串！！！
         *
         *
         * result:
         * queryValue: 测试 value
         *
         * 注意：原始的url是&testString=测试+value，而最终+被‘ ’替换了，因此url中应都是被encode的结果
         */
        final String queryValue = uri.getQueryParameter("testString");
        Log.i(TAG, "queryValue: " + queryValue);

        /**
         * Returns a set of the unique names of all query parameters. Iterating
         * over the set will return the names in order of their first occurrence.
         * 翻译：按第一次出现的顺序返回query params集合
         *
         * result:
         * key: utm_campaign, queryValue: open
         * key: art-bro-from, queryValue: mzNewsSdk
         * key: chTitle, queryValue: 中文
         * key: testBoolean, queryValue: true
         * key: testInteger, queryValue: 1
         * key: testString, queryValue: 测试 value
         *
         */
        Set<String> params = uri.getQueryParameterNames();
        for (String key: params) {
            Log.i(TAG, "key: " + key + ", queryValue: " + uri.getQueryParameter(key));
        }

        /**
         * Returns true if this URI is absolute, i.e.&nbsp;if it contains an
         * explicit scheme.
         * uri是否是觉得路径
         *
         * result:
         * absolute: true, relative: false
         */
        final boolean absolute = uri.isAbsolute();

        /**
         * Returns true if this URI is relative, i.e.&nbsp;if it doesn't contain an
         * explicit scheme.
         * uri是否是相对路径
         *
         * result:
         * absolute: true, relative: false
         */
        final boolean relative = uri.isRelative();

        Log.i(TAG, "absolute: " + absolute + ", relative: " + relative);

        /**
         * Returns true if this URI is opaque like "mailto:nobody@google.com". The
         * scheme-specific part of an opaque URI cannot start with a '/'.
         *
         * 翻译：像"mailto:nobody@google.com"形式的uri是opaque的, 特点是cheme-specific不是以'/'开头
         *
         * 判断此 URI 是否为不透明的
         *
         *
         * 网上介绍：
         * 不透明URI：不透明的URI指scheme-specific-part不是以正斜杠（/）开头的绝对的URI。
         *     不透明的URI并不是用于分解的。不透明的URI与其它的URI不同，它不服从标准化、分解和相对化。
         *（当且仅当 URI 是绝对的且其特定于方案的部分不是以斜线字符 ('/') 开始时，此 URI 才是不透明的。）
         *
         * result:
         *
         * opaque: false
         *
         */
        final boolean opaque = uri.isOpaque();
        Log.i(TAG, "opaque: " + opaque);


        /**
         * Returns true if this URI is hierarchical like "http://google.com".
         * Absolute URIs are hierarchical if the scheme-specific part starts with
         * a '/'. Relative URIs are always hierarchical.
         * 翻译：类似"http://google.com"就是分层uri.绝对路径的scheme-specific是以'/'开头则是一个分层uri.相对路径总是分层的uri.
         *
         *
         * 网上介绍：
         * 分层URI：分层的URI可以是以正斜杠开头的绝对的URI或相对的URL。
         *     scheme-specific-part的语法：[//authority] [path] [?query]。
         *
         *
         * result:
         * hierarchical: true
         */
        boolean hierarchical = uri.isHierarchical();
        Log.i(TAG, "hierarchical: " + hierarchical);


        /**
         * Return an equivalent URI with a lowercase scheme component.
         * This aligns the Uri with Android best practices for
         * intent filtering.
         *
         * 返回一个相等uri,只是将scheme部分变成小写.
         * 这里主要是配合android的intent的filter
         *
         *
         * <p>For example, "HTTP://www.android.com" becomes
         * "http://www.android.com"
         *
         * HTTP://www.android.com -> http://www.android.com
         *
         * <p>All URIs received from outside Android (such as user input,
         * or external sources like Bluetooth, NFC, or the Internet) should
         * be normalized before they are used to create an Intent.
         * 在通过uri创建intent之前，应该让uri变成normalized。
         *
         *
         * <p class="note">This method does <em>not</em> validate bad URI's,
         * or 'fix' poorly formatted URI's - so do not use it for input validation.
         * A Uri will always be returned, even if the Uri is badly formatted to
         * begin with and a scheme component cannot be found.
         *
         * 这个方法不能验证错误的URI，或者格式不对的URI，所以不要用它进行输入验证。
         * 一个Uri将始终被返回，即使Uri是错误的格式和缺少scheme部分。
         *
         * result:
         * normalizeScheme: https://open.testurl.com/a6626691667539788292?utm_campaign=open
         * &art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87&testBoolean=true&testInteger=1&testString=测试+value#test-fragment
         *
         */
        final Uri normalizeScheme = uri.normalizeScheme();
        Log.i(TAG, "normalizeScheme: " + normalizeScheme);

        /**
         * Returns the encoded string representation of this URI.
         * 返回uri的string(encod编码)
         *
         * result:
         * toString(): https://open.testurl.com/a6626691667539788292?utm_campaign=open
         * &art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87&testBoolean=true&testInteger=1&testString=测试+value#test-fragment
         */
        final String result = uri.toString();
        Log.i(TAG, "toString(): " + result);
    }

    public static void testBuilder() {
        final String url = "https://open.testurl.com/a6626691667539788292?utm_campaign=open&art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87#test-fragment";

        Uri uri = Uri.parse(url);

        /**
         * Constructs a new builder, copying the attributes from this Uri.
         * 翻译：通过uri创建一个builder
         *
         * result:
         * builder: https://open.testurl.com/a6626691667539788292/?utm_campaign=open&art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87#test-fragment
         */
        Uri.Builder builder = uri.buildUpon();
        Log.i(TAG, "builder: " + builder);

        /**
         * Appends the given segment to the path.
         * 在uri中添加一节path
         *
         * result:
         *  appendEncodedPath() - 路径 : https://open.testurl.com/a6626691667539788292/路径?utm_campaign=open&art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87#test-fragment
         *
         *  通过结果可以看出appendEncodedPath()参数默认是encode编码的，它直接拼接在uri中
         */
        builder = builder.appendEncodedPath("路径");
        Log.i(TAG, "appendEncodedPath() - 路径 : " + builder.build());

        /**
         * 同上
         *
         * result:
         * appendPath() - 路径 : https://open.testurl.com/a6626691667539788292/路径/%E8%B7%AF%E5%BE%84?utm_campaign=open&art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87#test-fragment
         *
         * 通过结果可以看出appendPath()参数默认是decode编码的，最终会encode一下在拼接在uri中
         */
        builder = builder.appendPath("路径");
        Log.i(TAG, "appendPath() - 路径 : " + builder.build());


        /**
         *
         * Encodes the key and value and then appends the parameter to the
         * query string.
         * 翻译：将key-value encode后添加到查询串中
         *
         *
         * result:
         * appendQueryParameter(): https://open.testurl.com/a6626691667539788292/路径/%E8%B7%AF%E5%BE%84?utm_campaign=open
         * &art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87&testKey=%E6%B7%BB%E5%8A%A0%E5%8F%82%E6%95%B0%2Bvalue#test-fragment
         *
         * 注意结果：testKey=%E6%B7%BB%E5%8A%A0%E5%8F%82%E6%95%B0%2Bvalue, +好也会被编码!!! 会自动encode key&value
         */
        builder = builder.appendQueryParameter("testKey", "添加参数+value");
        Log.i(TAG, "appendQueryParameter(): " + builder.build());

        /**
         * 测试添加空格后，获取的结果
         *
         * result:
         * testKey: 添加参数+value
         */
        final String testKey = builder.build().getQueryParameter("testKey");
        Log.i(TAG, "testKey: " + testKey);


        /**
         * 测试添加重复的key
         *
         * result:
         * appendQueryParameter(): https://open.testurl.com/a6626691667539788292/路径/%E8%B7%AF%E5%BE%84?utm_campaign=open
         * &art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87&testKey=%E6%B7%BB%E5%8A%A0%E5%8F%82%E6%95%B0&testKey=again#test-fragment
         *
         * 注意:最终会有两个testKey的键值对!!!
         */
        builder = builder.appendQueryParameter("testKey", "again");
        Log.i(TAG, "appendQueryParameter(): " + builder.build());

        /**
         * Encodes and sets the authority.
         * 设置authority 覆盖原有的authority
         *
         * result:
         * authority(): https://open.%E6%B5%8B%E8%AF%95authority.cn/a6626691667539788292/路径/%E8%B7%AF%E5%BE%84?utm_campaign=open
         * &art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87&testKey=%E6%B7%BB%E5%8A%A0%E5%8F%82%E6%95%B0&testKey=again#test-fragment
         *
         * 注意中文被编码
         */
        builder = builder.authority("open.测试authority.cn");
        Log.i(TAG, "authority(): " + builder.build());

        /**
         * 同上
         *
         * result:
         * encodedAuthority(): https://open.测试authority.org/a6626691667539788292/路径/%E8%B7%AF%E5%BE%84?utm_campaign=open
         * &art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87&testKey=%E6%B7%BB%E5%8A%A0%E5%8F%82%E6%95%B0&testKey=again#test-fragment
         *
         * 注意中文不会被编码
         */
        builder = builder.encodedAuthority("open.测试authority.org");
        Log.i(TAG, "encodedAuthority(): " + builder.build());


        /**
         * Clears the the previously set query.
         * 清除所有的key-value查询字段，其余的都保留
         *
         * result:
         * clearQuery(): https://open.testurl.cn/a6626691667539788292/路径/%E8%B7%AF%E5%BE%84#test-fragment
         */
        builder = builder.clearQuery();
        Log.i(TAG, "clearQuery(): " + builder.build());

        /**
         * Sets the previously encoded fragment.
         *
         * result:
         * encodedFragment(): https://open.测试authority.org/a6626691667539788292/路径/%E8%B7%AF%E5%BE%84#测试-fragment
         * 注意中文不会被编码
         *
         */
        builder = builder.encodedFragment("测试-fragment");
        Log.i(TAG, "encodedFragment(): " + builder.build());

        /**
         * 同上
         *
         * result:
         * fragment(): https://open.测试authority.org/a6626691667539788292/路径/%E8%B7%AF%E5%BE%84#%E6%B5%8B%E8%AF%95-fragment
         *
         * 注意中文被编码
         */
        builder.fragment("测试-fragment");
        Log.i(TAG, "fragment(): " + builder.build());

        /**
         * 翻译：替换scheme-specific部分
         *
         * result:
         * encodedOpaquePart(): https:测试-encodedOpaquePart#%E6%B5%8B%E8%AF%95-fragment
         *
         * 注意中文不会被编码
         */
        builder = builder.encodedOpaquePart("测试-encodedOpaquePart");
        Log.i(TAG, "encodedOpaquePart(): " + builder.build());

        /**
         * 同上
         *
         * result:
         * opaquePart(): https:%E6%B5%8B%E8%AF%95-encodedOpaquePart#%E6%B5%8B%E8%AF%95-fragment
         *
         * 注意中文会被编码
         */
        builder = builder.opaquePart("测试-encodedOpaquePart");
        Log.i(TAG, "opaquePart(): " + builder.build());


        /**
         * 先复原
         */
        uri = Uri.parse(url);
        builder = uri.buildUpon();

        /**
         *
         * 替换uri中的路径
         *
         * result:
         *  encodedPath() : https://open.testurl.com/encodedPath-测试?utm_campaign=open
         *  &art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87#test-fragment
         */
        builder = builder.encodedPath("encodedPath-测试");
        Log.i(TAG, "encodedPath() : " + builder.build());

        /**
         * 同上
         *
         * result:
         * path(): https://open.testurl.com/encodedPath-%E6%B5%8B%E8%AF%95?utm_campaign=open
         * &art-bro-from=mzNewsSdk&chTitle=%e4%b8%ad%e6%96%87#test-fragment
         */
        builder = builder.path("encodedPath-测试");
        Log.i(TAG, "path(): " + builder.build());

        /**
         * 替换uri中所有的query字段
         *
         * result:
         *
         * encodedQuery(): https://open.testurl.com/encodedPath-%E6%B5%8B%E8%AF%95?测试-key=测试-value#test-fragment
         *
         * 注意中文不会被编码
         */
        builder = builder.encodedQuery("测试-key=测试-value");
        Log.i(TAG, "encodedQuery(): " + builder.build());

        /**
         * 同上
         *
         * result:
         * query(): https://open.testurl.com/encodedPath-%E6%B5%8B%E8%AF%95?%E6%B5%8B%E8%AF%95-key%3D%E6%B5%8B%E8%AF%95-value#test-fragment
         *
         * 注意中文会被编码
         */
        builder = builder.query("测试-key=测试-value");
        Log.i(TAG, "query(): " + builder.build());

        /**
         *
         * result:
         *
         * scheme(): mail://open.testurl.com/encodedPath-%E6%B5%8B%E8%AF%95?%E6%B5%8B%E8%AF%95-key%3D%E6%B5%8B%E8%AF%95-value#test-fragment
         */
        builder = builder.scheme("mail");
        Log.i(TAG, "scheme(): " + builder.build());
    }
}
