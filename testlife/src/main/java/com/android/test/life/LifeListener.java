package com.android.test.life;

import android.os.Bundle;

public interface LifeListener {

    void onCreate(Bundle bundle);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
