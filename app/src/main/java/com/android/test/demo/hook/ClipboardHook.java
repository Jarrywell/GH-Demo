package com.android.test.demo.hook;

import com.android.test.demo.joor.Reflect;

import android.content.ClipData;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * des: Binder hook
 * author: libingyan
 * Date: 18-4-17 16:39
 */
public class ClipboardHook {

    private static final String TAG = "Clipboard";

    /**
     * hook剪贴板
     */
    public static void hook() {

        Reflect reflect = Reflect.on("android.os.ServiceManager");

        /**
         * 第一步：通过ServiceManager.getService()获取原生远程服务
         */
        IBinder rawBinder = reflect.call("getService", Context.CLIPBOARD_SERVICE).get();


        /**
         * 第二步：通过反射代理构建一个伪造的binder对象（伪造的服务）
         */
        Class<?> serviceManagerClzz = reflect.get();
        /**
         * newProxyInstance()构建的对象是实现了第二个参数interfaces制定的动态代理类，
         * 该动态代理类中的实现逻辑由第三个参赛InvocationHandler代为转发处理
         *
         * 因此,newProxyInstance()返回对象的类型由第二个参数决定
         */
        IBinder hookBinder = (IBinder) Proxy.newProxyInstance(serviceManagerClzz.getClassLoader(),
            new Class[] {IBinder.class},
            new ClipboardProxyHandler(rawBinder));



        /**
         * 第三步：使用伪造的服务替换原生服务
         */
        Map<String, IBinder> cache = reflect.field("sCache").get();
        cache.put(Context.CLIPBOARD_SERVICE, hookBinder);
    }



    /**
     * des:伪造asInterface方法返回的对象
     * author: libingyan
     * Date: 18-4-17 16:28
     */
    static class ClipboardProxyHandler implements InvocationHandler {

        /**
         * BinderProxy对象
         */
        private IBinder base;

        /**
         * android.content.IClipboard$Stub
         */
        private Class<?> stub;

        /**
         * android.content.IClipboard
         */
        private Class<?> iInterface;

        public ClipboardProxyHandler(IBinder base) {
            this.base = base;

            stub = Reflect.on("android.content.IClipboard$Stub").get();
            iInterface = Reflect.on("android.content.IClipboard").get();
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.i(TAG, "ClipboardProxyHandler invoke method: " + method.getName());

            if ("queryLocalInterface".equals(method.getName())) {
                Log.i(TAG, "hook queryLocalInterface");

                /**
                 * public static android.content.IClipboard asInterface(android.os.IBinder obj) {
                 *    if ((obj == null)) {
                 *        return null;
                 *    }
                 *    android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
                 *    if (((iin != null) && (iin instanceof android.content.IClipboard))) {
                 *        return ((android.content.IClipboard) iin);
                 *    }
                 *    return new android.content.IClipboard.Stub.Proxy(obj);
                 * }
                 */

                /**
                 * 这里直接返回真正被Hook掉的Service接口, 并保证queryLocalInterface()永远不为null
                 *
                 * 第二个参数同时指定了IBinder.class, IInterface.class, iInterface，是为
                 * 了queryLocalInterface()下面的if条件检查(意思是代理类需要实现这几个接口)
                 */
                return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                    new Class[]{IBinder.class, IInterface.class, iInterface},
                    new ClipboardHookHandler(base, stub));
            }

            return method.invoke(base, args);
        }
    }

    /**
     * des:伪造的系统服务对象
     * author: libingyan
     * Date: 18-4-17 16:15
     */
    static class ClipboardHookHandler implements InvocationHandler {

        /**
         * 原始的service对象
         */
        private final Object base;


        public ClipboardHookHandler(IBinder base, Class<?> stubClass) {
            //this.base = IClipboard.Stub.asInterface(base);
            this.base = Reflect.on(stubClass).call("asInterface", base).get();
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Log.i(TAG, "ClipboardHookHandler invoke method: " + method.getName() + ", args: " + formatArgs(args));

            /**
             * 替换剪切版的内容
             */
            if ("getPrimaryClip".equals(method.getName())) {
                Log.i(TAG, "Hook getPrimaryClip");
                return ClipData.newPlainText(null, "you are hooked");
            } else if ("hasPrimaryClip".equals(method.getName())) {
                //欺骗系统,使之认为剪切版上一直有内容
                return true;
            }

            //其他接口转调原生的service
            return method.invoke(base, args);
        }

        private String formatArgs(Object[] args) {
            StringBuilder builder = new StringBuilder("arg count " + (args != null ? args.length : 0) + " ");
            if (args != null) {
                for (Object arg : args) {
                    builder.append(arg).append(";");
                }
            }
            return builder.toString();
        }
    }
}
