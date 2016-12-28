package com.example.botos.appointment.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.example.botos.appointment.ui.activities.userScreens.UserMainMenuActivity;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.StringUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterFinalStepActivity extends BaseActivity {

    private static final String TAG = "registerFinalStep";
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
            if (mPhoneInput.getEditText().getText().toString().contains(" ")) {
                mPhoneInput.getEditText().setError(getResources().getString(R.string.error_invalid_phone_spaces));
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
        params.put("phone", mPhoneInput.getEditText().getText().toString());
        params.put("name", "orice");

        HashMap<String, String> header = new HashMap<>();
        header.put("authorization", Engine.getInstance().userModel.getToken());
        ApiLibrary.putRequestString(Constants.BASE_URL + Constants.ADD_PHONE, params, header, new AppointmentApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "onSuccess() called with: " + "response = [" + response + "]");
                Toast.makeText(RegisterFinalStepActivity.this, R.string.success_request, Toast.LENGTH_SHORT).show();
                Engine.getInstance().userModel.setPhone(mPhoneInput.getEditText().getText().toString());
                addToDataBase(Engine.getInstance().userModel);
                goToMainMenu();
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure() called with: " + "");
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure() called with: " + "error = [" + error + "]");
                Toast.makeText(RegisterFinalStepActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addToDataBase(UserModel userModel) {
        try {
            getHelper().getUserModelDao().createOrUpdate(userModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void goToMainMenu() {
        switch (Engine.getInstance().userModel.getUserType()) {
            case UserModel.NORMAL_USER:
                Intent userMain = new Intent(RegisterFinalStepActivity.this, UserMainMenuActivity.class);
                userMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(userMain);
                break;
            case UserModel.MEMBER:
                Intent memberMain = new Intent(RegisterFinalStepActivity.this, UserMainMenuActivity.class);
                memberMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(memberMain);
                break;
            case UserModel.ADMIN_MEMBER:
                break;
            case UserModel.ADMIN:
                break;
            default:
                break;
        }
        finish();
    }

}
