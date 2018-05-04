package com.android.test.demo.swipeback;

import com.android.test.demo.R;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * des:
 * author: libingyan
 * Date: 18-5-4 11:02
 */
public class TestSwipeBackActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_swipe);

        setSwipeBackEnable(true);
    }
}
