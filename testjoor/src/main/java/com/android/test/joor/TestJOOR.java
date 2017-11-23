package com.android.test.joor;

import com.android.test.joor.reflect.Reflect;
import com.android.test.joor.reflect.ReflectException;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class TestJOOR extends AppCompatActivity {
    private final String TAG = "TestJOOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_joor);

        /**
         * 1、jdk的反射与joor的反射调用对比
         */
        //Log.d(TAG, "jdk value: " + getValue("ro.test.value", "joor"));
        //Log.d(TAG, "jdk value: " + getValueEx("ro.test.value", "joor"));

        /**
         * 2、调用String的非静态方法（通过实例对象构造Reflect）
         */
        final String value = "1234";
        final String subString  = Reflect.on((Object) value).call("substring", 0, 2).get();
        Log.d(TAG, "origin: " + value + ", substring: " + subString);

        /**
         * 3、调用String的静态方法（通过String.class类名构造Reflect）
         */
        final String valueOf = Reflect.on(String.class).call("valueOf", true).get();
        Log.d(TAG, "valueOf: " + valueOf);

        /**
         * 4、修改private 属性
         */
        TestField testField = new TestField();
        final int init_int = Reflect.on(testField).get("INT2");
        Log.d(TAG, "initF_int: " + init_int);

        final Reflect setReflect = Reflect.on(testField).set("INT2", 300);
        Log.d(TAG, "set_int: " + setReflect.field("INT2").get());

        /**
         * 5、修改static属性
         */
        final int sInit_int = Reflect.on(TestField.class).get("S_INT1");
        Log.d(TAG, "sInit_int: " + sInit_int);

        Reflect obj = Reflect.on("com.android.test.joor.TestJOOR$TestField").set("S_INT2", 500);
        Log.d(TAG, "set_sint: " + obj.field("S_INT2").get());

        /**
         * 6、复杂链式修改多个属性值
         */
        Reflect.on(testField).set("I_DATA", Reflect.on(TestField.class).create())
            .field("I_DATA").set("INT1", 700).set("INT2", 800);
        Log.d(TAG, "chain value: " + testField.I_DATA.INT1 + ", value2: " + testField.I_DATA.INT2);


        /*try {
            Field field = TestField.class.getDeclaredField("F_INT3");
            final int modifiers =  field.getModifiers();
            Log.d(TAG, "modifiers: " + modifiers);
            if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            }
        } catch (Exception e) {
            Log.d(TAG, "exception: ", e);
        }*/

        /**
         * interface实现类对象的代理
         */
        final String asResult = Reflect.on((Object) "abc").as(POJOInterface.class).substring(1, 2);
        Log.d(TAG, "asResult: " + asResult);

        Reflect.on(testField).as(POJOInterface.class).tetProxy("this is proxy test!!");



        Map<String, Object> map = new TestBean();

        /**
         * TestBean没有实现setFoo()方法，该字段存入map键值对中
         */
        Reflect.on(map).as(POJOInterface.class).setFoo("abc");
        int size = map.size();
        final String value1 = (String) map.get("foo");
        final String value2 = Reflect.on(map).as(POJOInterface.class).getFoo();
        Log.d(TAG, "size: " + size + ", value1: " + value1 + ", value2: " + value2);

        /**
         *
         */
        Reflect.on(map).as(POJOInterface.class).setBaz("baz");
        size = map.size();
        final String value4 = (String) map.get("baz"); //value4 = null;
        final String value5 = Reflect.on(map).as(POJOInterface.class).getBaz(); //value5 = "MyMap: baz-test";
        Log.d(TAG, "size: " + size + ", value4: " + value4 + ", value5: " + value5);

        Reflect.on(map).as(POJOInterface.class).tetProxy("this is proxy test!!");
    }

    private class TestBean extends HashMap<String, Object> {

        private String baz;

        public void setBaz(String baz) {
            this.baz = "POJO-MyMap: " + baz;
        }

        public String getBaz() {
            return baz;
        }
    }

    public static class TestField {
        /**
         * private
         */
        private int INT1 = new Integer(0);
        private Integer INT2 = new Integer(2);


        /**
         * private & static
         */
        private static int S_INT1 = new Integer(3);
        private static Integer S_INT2 = new Integer(0);

        /**
         * private & final
         */
        private final int F_INT3 = new Integer(4);

        /**
         * object
         */
        private TestField I_DATA;


        public void tetProxy(String message) {
            Log.d("TestJOOR", "tetProxy: " + message);
        }
    }


    /**
     * 反射读取系统SystemProperties的属性值
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getValue(String key, String defaultValue) {
        String value = "";
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            Method getMethod = cls.getDeclaredMethod("get", String.class, String.class);
            getMethod.setAccessible(true);
            return (String) getMethod.invoke(null, key, defaultValue);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 反射读取系统SystemProperties的属性值
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getValueEx(String key, String defaultValue) {
        String value;
        try {
            value = Reflect.on("android.os.SystemProperties").call("get", key, defaultValue).get();
        } catch (ReflectException e) {
            e.printStackTrace();
            value = "";
        }
        return value;
    }

}
