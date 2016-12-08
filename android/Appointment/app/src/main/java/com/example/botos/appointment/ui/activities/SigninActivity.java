package com.example.botos.appointment.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.utils.StringUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class SigninActivity extends EmailAutocompleteBaseActivity {

    private Button mFacebookButton;
    private AutoCompleteTextView mEmailEdit;
    private EditText mPasswordEdit;
    private LinearLayout mEmailLoginLayout;
    private Button mEmailButton;
    private Button mShowEmailButton;
    private ProgressBar mLoginProgressBar;
    private CallbackManager mCallbackManager;
    private ProfileTracker mProfileTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if(Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            Log.v("facebook - profile", profile2.getFirstName());
                            mProfileTracker.stopTracking();
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                }
                if (loginResult.getAccessToken() != null && Profile.getCurrentProfile() != null) {
                    Profile.getCurrentProfile().getName();
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
            }
        });

        findId();
        load();
    }

    private void findId() {
        mFacebookButton = (Button) findViewById(R.id.signinFacebookButton);
        mEmailEdit = (AutoCompleteTextView) findViewById(R.id.signinEmailEdit);
        mPasswordEdit = (EditText) findViewById(R.id.signinPasswordEdit);
        mEmailLoginLayout = (LinearLayout) findViewById(R.id.signinEmailLayout);
        mEmailButton = (Button)findViewById(R.id.signinEmailButton);
        mShowEmailButton = (Button)findViewById(R.id.signinShowEmailButton);
        mLoginProgressBar = (ProgressBar) findViewById(R.id.login_progress);
    }

    private void load() {
        mEmailLoginLayout.setAlpha(0);
        mShowEmailButton.setOnClickListener(ShowEmailLogin);

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
    }
    private void attemptLogin() {
        showProgress(true);
        // Reset errors.
        emailView.setError(null);
        mPasswordEdit.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
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
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            showProgress(false);
        } else {
            Toast.makeText(SigninActivity.this, "Request start", Toast.LENGTH_SHORT).show();
            //request
            showProgress(false);
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

    View.OnClickListener ShowEmailLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mShowEmailButton.setVisibility(View.GONE);
            mEmailLoginLayout.animate().alpha(1).setDuration(500);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
