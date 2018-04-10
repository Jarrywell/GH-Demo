package com.android.test.demo.joor.test;


import android.content.Context;

import com.android.test.demo.joor.Reflect;


public class Test {
    private static final String TAG = "TestJOOR";

    public static void disableResize(Context context) {
        Reflect.on("meizu.splitmode.FlymeSplitModeManager")
            .call("getInstance", context)
            .call("disableResize", context);

        /*try {
            Class<?> clazz = Class.forName("meizu.splitmode.FlymeSplitModeManager");
            Method b = clazz.getMethod("getInstance", Context.class);
            Object instance = b.invoke(null, context);
            Method m = clazz.getMethod("disableResize", Context.class);
            m.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    public static int getStatusBarHeight(Context context) {
        final int statusHeightId = Reflect.on("com.android.internal.R$dimen").create()
            .field("status_bar_height").get();
        return context.getResources().getDimensionPixelSize(statusHeightId);

        /*try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int height = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;*/
    }

    public static boolean isSplitMode(Context context) {
        return Reflect.on("meizu.splitmode.FlymeSplitModeManager")
            .call("getInstance", context)
            .call("isSplitMode").get();

        /*try {
            Class<?> clazz = Class.forName("meizu.splitmode.FlymeSplitModeManager");
            Method b = clazz.getMethod("getInstance", Context.class);
            Object instance = b.invoke(null, context);
            Method m = clazz.getMethod("isSplitMode");
            return (boolean) m.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;*/
    }

    public static int getActivityThreadNumVisibleActivities() {
        return Reflect.on("android.app.ActivityThread")
            .call("currentActivityThread")
            .field("mNumVisibleActivities").get();
        //Log.d(TAG, "currentActivityThread: " + result.get());
        //return 0;
    }
}
