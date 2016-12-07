package com.example.botos.appointment.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.botos.appointment.R;
import com.example.botos.appointment.ui.BaseActivity;

import java.util.List;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent singin = new Intent(SplashActivity.this, SigninActivity.class);
                startActivity(singin);
                finish();
            }
        }, 2000);
    }

}
