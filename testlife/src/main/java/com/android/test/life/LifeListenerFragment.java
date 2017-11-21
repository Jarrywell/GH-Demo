package com.android.test.life;

import android.app.Fragment;
import android.os.Bundle;

public class LifeListenerFragment extends Fragment {

    private LifeListener mLifeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addLifeListener(LifeListener listener) {
        mLifeListener = listener;
    }

    public void removeLifeListener() {
        mLifeListener = null;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mLifeListener != null) {
            mLifeListener.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLifeListener != null) {
            mLifeListener.onStop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mLifeListener != null) {
            mLifeListener.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mLifeListener != null) {
            mLifeListener.onDestroy();
        }
    }
}
