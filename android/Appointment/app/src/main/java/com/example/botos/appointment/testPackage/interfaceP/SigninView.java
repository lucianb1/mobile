package com.example.botos.appointment.testPackage.interfaceP;

import com.example.botos.appointment.models.UserModel;

/**
 * Created by Botos on 1/24/2017.
 */

public interface SigninView {
    String getEmail();

    String getPassword();

    void showEmptyEmailError(int error_field_required);

    void showShortPasswordError(int error_invalid_password);

    void showInvalidEmailError(int error_invalid_email);

    void startApplication(UserModel userModel);

    void signinFailure(String error);

    void signinFailure();
}
