package com.example.android.beamlargefiles.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.android.beamlargefiles.R;
import com.example.android.common.activities.SampleActivityBase;

public class SplashActivity extends SampleActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }, 2000);

    }

}