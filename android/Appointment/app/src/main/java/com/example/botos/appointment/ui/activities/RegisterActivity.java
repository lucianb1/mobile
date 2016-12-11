package com.example.botos.appointment.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.models.UserModel;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.platform.Engine;
import com.example.botos.appointment.ui.BaseActivity;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DialogUtils;
import com.example.botos.appointment.utils.StringUtils;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    private TextInputLayout mEmailInputLayout;
    private TextInputLayout mNameInputLayout;
    private TextInputLayout mNumberInputLayout;
    private TextInputLayout mPasswordInputLayout;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        setSupportActionBar(getToolBar());
        setToolbar();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        findId();
        load();
    }

    private void findId() {
        mEmailInputLayout = (TextInputLayout) findViewById(R.id.registerEmailInput);
        mNameInputLayout = (TextInputLayout) findViewById(R.id.registerNameInput);
        mNumberInputLayout = (TextInputLayout) findViewById(R.id.registerNumberInput);
        mPasswordInputLayout = (TextInputLayout) findViewById(R.id.registerPasswordInput);
        mRegisterButton = (Button) findViewById(R.id.registerButton);
    }

    private void load() {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    registerRequest();
                }
            }
        });
    }

    private Boolean validate() {
        if (StringUtils.validateEditText(mEmailInputLayout, RegisterActivity.this) &&
                StringUtils.validateEditText(mNameInputLayout, RegisterActivity.this) &&
                StringUtils.validateEditText(mNumberInputLayout, RegisterActivity.this) &&
                StringUtils.validateEditText(mPasswordInputLayout, RegisterActivity.this)) {
            if (!StringUtils.isValidEmail(mEmailInputLayout.getEditText().getText().toString())) {
                mEmailInputLayout.getEditText().setError(getResources().getString(R.string.error_invalid_email));
                mEmailInputLayout.requestFocus();
                return false;
            }
            Pattern pattern = Pattern.compile("[0-9]{6}$");
            Pattern pattern1 = Pattern.compile("[0-9]{10}$");
            if (!(pattern.matcher(mNumberInputLayout.getEditText().getText().toString()).matches() ||
                    pattern1.matcher(mNumberInputLayout.getEditText().getText().toString()).matches()) ) {
                mNumberInputLayout.getEditText().setError("Number");
                mNumberInputLayout.requestFocus();
                return false;
            }
            if (mPasswordInputLayout.getEditText().getText().length() < 6) {
                mPasswordInputLayout.getEditText().setError(getResources().getString(R.string.error_invalid_password));
                mPasswordInputLayout.requestFocus();
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

    private void registerRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", mEmailInputLayout.getEditText().getText().toString());
        params.put("password", mPasswordInputLayout.getEditText().getText().toString());
        params.put("name", mNameInputLayout.getEditText().getText().toString());
        params.put("phone", mNumberInputLayout.getEditText().getText().toString());
        final ProgressDialog progreeDialog = DialogUtils.createProgressDialog(RegisterActivity.this, false, null, getResources().getString(R.string.loading));
        progreeDialog.show();
        ApiLibrary.postRequestUserModel(Constants.BASE_URL + Constants.REGISTER, params, null, new AppointmentApiResponse<UserModel>() {
            @Override
            public void onSuccess(UserModel response) {
                Log.d(TAG, "onSuccess() called with: " + "response = [" + response + "]");
                Toast.makeText(RegisterActivity.this, R.string.success_request, Toast.LENGTH_SHORT).show();
                Engine.getInstance().userModel = response;
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
            }
        });
    }
}
