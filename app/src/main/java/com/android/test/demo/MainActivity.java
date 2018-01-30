package com.android.test.demo;

import com.android.test.demo.exception.TestException;
import com.android.test.demo.graph.CriticalPath;
import com.android.test.demo.graph.Dijkstra;
import com.android.test.demo.graph.Floyd;
import com.android.test.demo.graph.Kruskal;
import com.android.test.demo.graph.Prim;
import com.android.test.demo.graph.TestTraverser;
import com.android.test.joor.TestJOOR;
import com.android.test.life.TestLife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TestGH";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (DLog.LOGED) DLog.i(TAG, "MainActivity is oncrated!!");


        findViewById(R.id.btn_joor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TestJOOR.class));
            }
        });

        findViewById(R.id.btn_view_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TestLife.class));
            }
        });

        //测试异常
        //testException();

        //测试DAG
        //TestDAG testDAG = new TestDAG();
        //testDAG.test1();
        //testDAG.test2();
        //testDAG.test3();
        //testDAG.test6();

        //GraphDemo.testGraphs();

        //CriticalPath.test();
        //Dijkstra.test();
        //Floyd.test();
        //Prim.test();
        //Kruskal.test();

        TestTraverser.test();
    }


    private void testException() {
        TestException testException = new TestException();

        final String fileName = "/sdcard/img_h5.txt";
        final int size = testException.readValueFromFile(fileName);
        //final int size = testException.readValueFromFile(null);
        Log.d(TAG, "readValueFromFile return size: " + size);

        Map<String, String> map = testException.testFinally2();
        Log.d(TAG, "testFinally2 map: " + (map != null ? map.get("key") : "null"));

        final int value = testException.testFinally1();
        Log.d(TAG, "testFinally1 return size: " + value);

        testException.testGHException();
    }
}
