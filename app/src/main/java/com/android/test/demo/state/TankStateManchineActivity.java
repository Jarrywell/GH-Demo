package com.android.test.demo.state;

import com.android.test.demo.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * des:
 * author: libingyan
 * Date: 18-3-15 20:06
 */
public class TankStateManchineActivity extends AppCompatActivity {
    private final String TAG = "TankActivity";
    private TankStateMachine mTankStateMachine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank);
        mTankStateMachine = TankStateMachine.make();

        findViewById(R.id.btn_setup_base).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.setupBase();
            }
        });

        findViewById(R.id.btn_remove_base).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.removeBase();
            }
        });

        findViewById(R.id.btn_setup_barrel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.setupBarrel();
            }
        });

        findViewById(R.id.btn_remove_barrel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.removeBarrel();
            }
        });

        findViewById(R.id.btn_setup_big_barrel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.setupBigBarrel();
            }
        });

        findViewById(R.id.btn_remove_big_barrel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.removeBigBarrel();
            }
        });

        findViewById(R.id.btn_add_missile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.addMissile();
            }
        });

        findViewById(R.id.btn_launch_missile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.launchMissile();
            }
        });

        findViewById(R.id.btn_add_rocket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.addRocket();
            }
        });

        findViewById(R.id.btn_launch_rocket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.launchRocket();
            }
        });

        findViewById(R.id.btn_remove_missile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.removeMissile();
            }
        });

        findViewById(R.id.btn_remove_rocket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.removeRocket();
            }
        });


        findViewById(R.id.btn_launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankStateMachine.launch();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mTankStateMachine.quitNow();

        mTankStateMachine = null;
    }
}
