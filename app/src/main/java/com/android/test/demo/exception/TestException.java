package com.android.test.demo.exception;


import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TestException {
    private final String TAG = "TestException";

    public int readValueFromFile(String filename) {
        int size = 0;
        InputStream in = null;
        try {
            Log.d(TAG, "start read file...");
            if (filename == null){
                throw new IllegalArgumentException("filename is null");
            }
            in = new FileInputStream(filename);
            int temp;
            while ((temp = in.read()) != -1) {
                size++;
                //Log.d(TAG, "read:" + temp);
            }
            Log.d(TAG, "read file size:" + size);
            return returnSize(size);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "FileNotFoundException!!!", e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "IOException!!!", e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(TAG, "Exception!!!", e);
            e.printStackTrace();
        } finally { //这里会在return之前执行!!!
            size += 10; //so, 这里会改变正常的返回值吗?
            Log.d(TAG, "read file finally{} size: " + size);
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {

                }
            }
            //return size; //不要在finally中使用return语句!! 这里的return语句会覆盖try中的return.
        }
        return 0;
    }

    private int returnSize(int size) {
        Log.d(TAG, "enter returnSize()");
        return size;
    }


    /**
     * 返回值是2!!! [finally块在retrun语句执行执行之后，返回之前执行]
     * @return
     */
    public int testFinally1() {
        int x = 1;
        try {
            ++x;
            Log.d(TAG, "try{} x: " + x);
            return returnSize(x);
        } finally { //finally块在retrun语句执行执行之后，返回之前执行
            ++x;
            Log.d(TAG, "finally{} x: " + x);
        }
    }

    /**
     * 此处涉及到java的传值和传地址区别 ,返回后map不为null, key=finally
     * @return
     */
    public Map<String, String> testFinally2() {
        Map<String, String> result = new HashMap<>();
        result.put("key", "start");
        try {
            result.put("key", "try");
            return result;
        } catch (Exception e) {
            result.put("key", "catch");
        } finally {
            result.put("key", "finally"); //此处生效
            result = null; //此处不生效
        }
        return result;
    }
}
