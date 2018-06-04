package com.android.test.demo.generics;


import android.util.Log;

/**
 * des: 范型的类型转换--参考guava-GraphBuilder类
 * 模拟http接口的封装，不具有参考价值，仅表达在范型中类型的转换
 * author: libingyan
 * Date: 18-6-4 09:53
 */
public class TestType1 {

    private static final String TAG = "TestType1";

    public static void test() {

        /**
         * 普通用法--返回指定类型的对象
         */
        //HttpBuilder<TestBean> builder = HttpBuilder.get();


        /**
         * 利用范型通过Builder模式构建一个返回类型为TestBean的Http请求对象
         */
        HttpRequest<TestBean> httpRequest = HttpBuilder.get().build(TestBean.class);

        /**
         * 会在编译期会报错--理由是buildEx()只接受Class<HttpResult>类型，而此时传递的却是Class<TestBean>
         * 使用上述的build()实现即可
         */
        //HttpRequest<TestBean> httpRequestEx = HttpBuilder.get().buildEx(TestBean.class);


        /**
         * 执行该Http请求，并最终返回我们想得到类型的Bean对象.
         *
         * execute()函数中会执行HttpResult中的parse解析函数
         */
        TestBean result = httpRequest.execute();

        Log.i(TAG, "http request result: " + result != null ? result.name : "@null");

    }


    /**
     * 通过范型指定的Http构造器（重点）
     * @param <T>
     */
    static final class HttpBuilder<T extends HttpResult> {

        private String method;

        HttpBuilder(String method) {
            this.method = method;
        }

        /**
         * 静态方法构建HttpBuilder对象
         * @param <T>
         * @return
         */
        public static <T extends HttpResult> HttpBuilder<T> get() {
            return new HttpBuilder<T>("GET");
        }

        /**
         * 构建另一种对象，而类型由HttpBuilder的类型指定(重点)
         * 此处逻辑：为了得到T类型的子类型对象，这里进行再次指定<T1 extends T>来转换
         * @param clz
         * @param <T1>
         * @return
         */
        public <T1 extends T> HttpRequest<T1> build(Class<T1> clz) {
            return new HttpReqestImp<T1>(method, clz);
        }


        /**
         * 该接口并不能构建指定T类型的HttpRequest对象，编译期会报错．如:HttpRequest<TestBean>
         * @param clz
         * @return
         */
        public HttpRequest<T> buildEx(Class<T> clz) {
            return new HttpReqestImp<T>(method, clz);
        }
    }


    /**
     * Http的请求接口
     * @param <T>
     */
    interface HttpRequest<T extends HttpResult> {

        T execute();
    }


    /**
     * HttpRequest的实现类
     * @param <T>
     */
    static final class HttpReqestImp<T extends HttpResult> implements HttpRequest<T> {

        private String method;

        private Class<?> mClz;

        public HttpReqestImp(String method, Class<T> clz) {
            this.method = method;
            mClz = clz;
        }


        @Override
        public T execute() {
            Log.i(TAG, "execute http method is " + method);

            final String content = "this is test Content";
            try {
                Object obj = mClz.newInstance();
                return (T) ((T) obj).parse(content);
            } catch (Exception e) {
                return null;
            }
        }
    }




    /**
     * Http结果解析的抽象范型接口：实现json -> Bean对象时实现该接口即可
     * @param <T>
     */
    interface HttpResult<T> {
        T parse(String content) throws Exception;
    }

    /**
     * 据体的bean对象实现，此时parse()已返回了具体的类型
     */
    static final class TestBean implements HttpResult {
        private String name;

        @Override
        public TestBean parse(String content) throws Exception {
            name = content;
            return this;
        }
    }
}
