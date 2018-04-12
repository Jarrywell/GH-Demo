package com.android.test.demo.nightmode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

        findViewById(R.id.btn_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeUtils.toggleNightMode(NightModeActivity.this);
            }
        });

        NightImageView imageView = (NightImageView) findViewById(R.id.img_nightmode);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String state = imageView.getCurrentSrc();
                if (state == null) {
                    imageView.setCurrentSrc("on");
                } else if (state.equals("on")) {
                    imageView.setCurrentSrc("off");
                } else {
                    imageView.setCurrentSrc("on");
                }
            }
        });
    }
}
