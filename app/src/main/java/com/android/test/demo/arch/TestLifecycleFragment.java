package com.android.test.demo.arch;

import android.arch.lifecycle.LifecycleObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.test.demo.R;

public class TestLifecycleFragment extends Fragment {

    private static String TAG = "TestLifecycleFragment";

    private LifecycleObserver mLifecycleObserver;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_test_fragment_lifecycle, container, false);
        root.setBackgroundColor(0x55ff0000);

        root.findViewById(R.id.btn_bind_life_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLifecycleObserver == null) {
                    mLifecycleObserver = new TestLifecycleObserver();
                    getLifecycle().addObserver(mLifecycleObserver);
                }
            }
        });

        root.findViewById(R.id.btn_unbind_life_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLifecycleObserver != null) {
                    getLifecycle().removeObserver(mLifecycleObserver);
                    mLifecycleObserver = null;
                }
            }
        });

        return root;
    }
}
