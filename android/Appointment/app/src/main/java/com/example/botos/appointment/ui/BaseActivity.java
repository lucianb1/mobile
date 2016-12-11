package com.example.botos.appointment.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.botos.appointment.R;

public class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);

        findId();
        load();
    }

    public void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            this.mToolBar = toolbar;
            mToolBar.setTitleTextColor(ContextCompat.getColor(BaseActivity.this, R.color.white_alpha));
            mToolBar.setSubtitleTextColor(ContextCompat.getColor(BaseActivity.this, R.color.white_alpha));
        }
    }

    private void findId() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void load() {
        setSupportActionBar(mToolBar);
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }
}
