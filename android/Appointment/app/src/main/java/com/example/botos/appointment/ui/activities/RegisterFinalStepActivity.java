package com.example.botos.appointment.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.botos.appointment.R;
import com.example.botos.appointment.ui.BaseActivity;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.StringUtils;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterFinalStepActivity extends BaseActivity {

    private TextInputLayout mPhoneInput;
    private Button mRegisterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_final_step);
        setToolbar();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        findId();
        load();

    }

    private void findId() {
        mPhoneInput = (TextInputLayout) findViewById(R.id.finalStepPhoneEdit);
        mRegisterButton = (Button) findViewById(R.id.finalStepRegisterButton);
    }

    private void load() {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    finalResgisterRequest();
                }
            }
        });
    }

    private Boolean validate() {
        if (StringUtils.validateEditText(mPhoneInput, RegisterFinalStepActivity.this)) {
            Pattern pattern = Pattern.compile("[0-9]{6}$");
            Pattern pattern1 = Pattern.compile("[0-9]{10}$");
            if (!(pattern.matcher(mPhoneInput.getEditText().getText().toString()).matches() ||
                    pattern1.matcher(mPhoneInput.getEditText().getText().toString()).matches()) ) {
                mPhoneInput.getEditText().setError("Number");
                mPhoneInput.requestFocus();
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void finalResgisterRequest() {
        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        ApiLibrary.putRequestUserModel(Constants.BASE_URL + Constants.ADD_PHONE, )
    }

}
