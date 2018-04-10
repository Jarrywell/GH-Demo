package com.android.test.demo.nightmode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.test.demo.R;

/**
 * Created by tech on 18-4-10.
 */

public class NightModeActivity extends AppCompatActivity {
    private final String TAG = "NightMode";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_night_mode);
    }
}
