package com.example.botos.appointment.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.models.UserModel;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.platform.Engine;
import com.example.botos.appointment.ui.BaseActivity;
import com.example.botos.appointment.ui.activities.userScreens.UserMainMenuActivity;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DialogUtils;
import com.example.botos.appointment.utils.StringUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class SigninActivity extends BaseActivity {

    private static final String TAG = "SigninActivity";
    private RelativeLayout mFacebookButton;
    private AutoCompleteTextView mEmailEdit;
    private EditText mPasswordEdit;
    private LinearLayout mEmailLoginLayout;
    private Button mEmailButton;
    private LinearLayout mRegisterButton;
    private ProgressBar mLoginProgressBar;
    private CallbackManager mCallbackManager;
    private ProfileTracker mProfileTracker;
    private TextView mForgotPasswordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mCallbackManager = CallbackManager.Factory.create();
        facebookCallBack();
        findId();
        load();
        if (getUserFromDataBase()) {
            goToMainMenu();
        }
    }

    private void findId() {
        mFacebookButton = (RelativeLayout) findViewById(R.id.signinFacebookButton);
        mEmailEdit = (AutoCompleteTextView) findViewById(R.id.signinEmailEdit);
        mPasswordEdit = (EditText) findViewById(R.id.signinPasswordEdit);
        mEmailLoginLayout = (LinearLayout) findViewById(R.id.signinEmailLayout);
        mEmailButton = (Button)findViewById(R.id.signinEmailButton);
        mForgotPasswordButton = (TextView)findViewById(R.id.signinForgotPasswordButton);
        mRegisterButton = (LinearLayout)findViewById(R.id.signinRegisterButton);
        mLoginProgressBar = (ProgressBar) findViewById(R.id.login_progress);
    }

    private void load() {
        mEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(SigninActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_location", "user_friends"));
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(SigninActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPassword = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPassword);
            }
        });
    }
    private void attemptLogin() {
        showProgress(true);
        // Reset errors.
        mEmailEdit.setError(null);
        mPasswordEdit.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordEdit.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordEdit;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailEdit.setError(getString(R.string.error_field_required));
            focusView = mEmailEdit;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailEdit.setError(getString(R.string.error_invalid_email));
            focusView = mEmailEdit;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            showProgress(false);
        } else {
            loginRequest();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return StringUtils.isValidEmail(email);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        mEmailButton.setEnabled(!show);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private void facebookCallBack() {
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                if(Profile.getCurrentProfile() == null) {
//                    mProfileTracker = new ProfileTracker() {
//                        @Override
//                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
//                            // profile2 is the new profile
//                            Log.v("facebook - profile", profile2.getFirstName());
//                            mProfileTracker.stopTracking();
//                        }
//                    };
//                    // no need to call startTracking() on mProfileTracker
//                    // because it is called by its constructor, internally.
//                }
//                if (loginResult.getAccessToken() != null && Profile.getCurrentProfile() != null) {
//                    Profile.getCurrentProfile().getName();
//                }
                if (loginResult.getAccessToken() != null) {
                    serverFacebookLogin(loginResult.getAccessToken().getToken());
                }
            }

            @Override
            public void onCancel() {
                LoginManager.getInstance().logOut();
                Log.d("onCancel", "");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("onError", error.getLocalizedMessage());
                LoginManager.getInstance().logOut();
            }
        });

    }

    private void loginRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", mEmailEdit.getText().toString());
        params.put("password", mPasswordEdit.getText().toString());
        ApiLibrary.postRequestUserModel(Constants.BASE_URL + Constants.LOGIN, params, null, new AppointmentApiResponse<UserModel>() {
            @Override
            public void onSuccess(UserModel response) {
                Toast.makeText(SigninActivity.this, R.string.success_request, Toast.LENGTH_SHORT).show();
                Engine.getInstance().userModel = response;
                Engine.getInstance().userModel.setIsLoging(true);
                showProgress(false);
                addToDataBase(response);
                goToMainMenu();
            }

            @Override
            public void onFailure() {
                showProgress(false);
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure() called with: " + "error = [" + error + "]");
                Toast.makeText(SigninActivity.this, error, Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });
    }

    private void serverFacebookLogin(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        Log.d(TAG, "serverFacebookLogin() called with: " + "token = [" + token + "]");
        final ProgressDialog progreeDialog = DialogUtils.createProgressDialog(SigninActivity.this, false, null, getResources().getString(R.string.loading));
        progreeDialog.show();
        ApiLibrary.postRequestUserModel(Constants.BASE_URL + Constants.FACEBOOK_LOGIN, params, null, new AppointmentApiResponse<UserModel>() {
            @Override
            public void onSuccess(UserModel response) {
                Toast.makeText(SigninActivity.this, R.string.success_request, Toast.LENGTH_SHORT).show();
                Engine.getInstance().userModel = response;
                if (progreeDialog.isShowing())
                    progreeDialog.dismiss();
                if (response.getPhone().equals("NO")) {
                    Intent finalStepRegister = new Intent(SigninActivity.this, RegisterFinalStepActivity.class);
                    startActivity(finalStepRegister);
                } else {

                }
            }

            @Override
            public void onFailure() {
                if (progreeDialog.isShowing())
                    progreeDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure() called with: " + "error = [" + error + "]");
                Toast.makeText(SigninActivity.this, error, Toast.LENGTH_SHORT).show();
                if (progreeDialog.isShowing())
                    progreeDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
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
                Intent userMain = new Intent(SigninActivity.this, UserMainMenuActivity.class);
                startActivity(userMain);
                break;
            case UserModel.MEMBER:
                Intent memberMain = new Intent(SigninActivity.this, UserMainMenuActivity.class);
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

    private boolean getUserFromDataBase() {

        try {
            QueryBuilder<UserModel, Integer> queryBuilder = getHelper().getUserModelDao().queryBuilder();
            queryBuilder.where().eq(UserModel.IS_LOGING, true);
            Engine.getInstance().userModel = getHelper().getUserModelDao().queryForFirst(queryBuilder.prepare());
            if (Engine.getInstance().userModel != null)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
