package com.android.test.life;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class TestView extends AppCompatImageView {
    private final String TAG = "TestView";

    private Presenter mPresenter;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPresenter = new Presenter();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Activity activity = getActivity();
        if (activity != null) {
            addLifeListener(activity);
        }
    }

    private Activity getActivity() {
        View parent = this;
        Activity activity = null;
        do {
            final Context context = parent.getContext();
            Log.d(TAG, "view: " + parent + ", context: " + context);
            if (context != null && context instanceof Activity) {
                activity = (Activity) context;
                break;
            }
        } while ((parent = (View) parent.getParent()) != null);
        return activity;
    }

    private void addLifeListener(Activity activity) {
        LifeListenerFragment fragment = getLifeListenerFragment(activity);
        fragment.addLifeListener(mLifeListener);
    }

    private LifeListenerFragment getLifeListenerFragment(Activity activity) {
        FragmentManager manager = activity.getFragmentManager();
        return getLifeListenerFragment(manager);
    }

    private LifeListenerFragment getLifeListenerFragment(FragmentManager manager) {
        LifeListenerFragment fragment = (LifeListenerFragment) manager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new LifeListenerFragment();
            manager.beginTransaction().add(fragment, TAG).commitAllowingStateLoss();
        }

        return fragment;
    }

    private LifeListener mLifeListener = new LifeListener() {
        @Override
        public void onCreate(Bundle bundle) {
            Log.d(TAG, "onCreate");
        }

        @Override
        public void onStart() {
            Log.d(TAG, "onStart");
            if (mPresenter != null) {
                mPresenter.onStart();
            }
        }

        @Override
        public void onResume() {
            Log.d(TAG, "onResume");
        }

        @Override
        public void onPause() {
            Log.d(TAG, "onPause");
        }

        @Override
        public void onStop() {
            Log.d(TAG, "onStop");
            if (mPresenter != null) {
                mPresenter.onStop();
            }
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy");
            if (mPresenter != null) {
                mPresenter.onDestory();
            }
        }
    };
}
