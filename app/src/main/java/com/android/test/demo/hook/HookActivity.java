package com.android.test.demo.hook;

import com.android.test.demo.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * des:
 * author: libingyan
 * Date: 18-4-17 15:56
 */
public class HookActivity extends AppCompatActivity {
    private final String TAG = "Hook";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_test_hook);

        /**
         * hook掉剪切板
         */
        ClipboardHook.hook();

        /**
         * hook ams 服务
         */
        AmsHook.hook();

        /**
         * hook pms 服务
         */
        PmsHook.hook(this);

        findViewById(R.id.btn_hook_ams).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //测试AMS HOOK (调用其相关方法)
                Uri uri = Uri.parse("http://wwww.baidu.com");
                Intent t = new Intent(Intent.ACTION_VIEW);
                t.setData(uri);
                startActivity(t);
            }
        });

        findViewById(R.id.btn_hook_pms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 测试PMS HOOK (调用其相关方法)
                getPackageManager().getInstalledApplications(0);
            }
        });

    }

}
