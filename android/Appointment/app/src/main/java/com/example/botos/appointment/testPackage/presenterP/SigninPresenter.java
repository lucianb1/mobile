package com.example.botos.appointment.testPackage.presenterP;

import com.example.botos.appointment.R;
import com.example.botos.appointment.models.UserModel;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.testPackage.interfaceP.SigninView;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.StringUtils;

import java.util.HashMap;

/**
 * Created by Botos on 1/24/2017.
 */

public class SigninPresenter {

    private final SigninView view;

    public SigninPresenter(SigninView view) {
        this.view = view;
    }

    public void onSigninButtonClick() {
        String email = view.getEmail();
        if (email.isEmpty()) {
            view.showEmptyEmailError(R.string.error_field_required);
            return;
        }

        String password = view.getPassword();
        if (password.length() < 6) {
            view.showShortPasswordError(R.string.error_invalid_password);
            return;
        }

        if (!StringUtils.isValidEmail(email)) {
            view.showInvalidEmailError(R.string.error_invalid_email);
            return;
        }

        loginRequest();
//        boolean loginSucces = loginServices.login(username, password);
//        if(loginSucces) {
//            view.startMainActivity();
//            return;
//        }
//        view.showWrongLoginError(R.string.wrong_login_error);
    }

    private void loginRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", view.getEmail());
        params.put("password", view.getPassword());
        ApiLibrary.postRequestUserModel(Constants.BASE_URL + Constants.LOGIN, params, null, new AppointmentApiResponse<UserModel>() {
            @Override
            public void onSuccess(UserModel response) {
                view.startApplication(response);
            }

            @Override
            public void onFailure() {
                view.signinFailure();
            }

            @Override
            public void onFailure(String error) {
                view.signinFailure(error);
            }
        });
    }
}
