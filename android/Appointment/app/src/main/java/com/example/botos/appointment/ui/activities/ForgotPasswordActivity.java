package com.example.botos.appointment.ui.activities;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.ui.BaseActivity;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DialogUtils;
import com.example.botos.appointment.utils.StringUtils;

import java.util.HashMap;

public class ForgotPasswordActivity extends BaseActivity {

    private TextInputLayout mEmailEdit;
    private Button mForgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setToolbar();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        findId();
        load();
    }

    private void findId() {
        mEmailEdit = (TextInputLayout) findViewById(R.id.forgotPasswordInput);
        mForgotPasswordButton = (Button) findViewById(R.id.forgotPasswordButton);
    }

    private void load() {
        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    forgotPasswordRequest();
                }
            }
        });
    }

    private Boolean validate() {
        if (StringUtils.validateEditText(mEmailEdit, ForgotPasswordActivity.this)) {
            if (!StringUtils.isValidEmail(mEmailEdit.getEditText().getText().toString())) {
                mEmailEdit.getEditText().setError(getResources().getString(R.string.error_invalid_email));
                mEmailEdit.requestFocus();
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

    private void forgotPasswordRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", mEmailEdit.getEditText().getText().toString());
        final ProgressDialog progreeDialog = DialogUtils.createProgressDialog(ForgotPasswordActivity.this, false, null, getResources().getString(R.string.loading));
        progreeDialog.show();
        ApiLibrary.postRequestString(Constants.BASE_URL + Constants.FORGOT_PASSWORD, params, null, new AppointmentApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(ForgotPasswordActivity.this, R.string.success_request, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
                finish();
            }

            @Override
            public void onFailure() {
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(ForgotPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
            }
        });
    }
}
