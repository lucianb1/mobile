package com.example.botos.appointment;

import com.example.botos.appointment.testPackage.presenterP.SigninPresenter;
import com.example.botos.appointment.testPackage.interfaceP.SigninView;
import com.example.botos.appointment.utils.StringUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Botos on 1/24/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SigninPresenterTest {

    @Mock
    private SigninView mView;
    private SigninPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mPresenter = new SigninPresenter(mView);
    }

    @Test
    public void emailEmptyError() throws Exception {
        when(mView.getEmail()).thenReturn("");
        mPresenter.onSigninButtonClick();

        verify(mView).showEmptyEmailError(R.string.error_field_required);
    }

    @Test
    public void invalideEmailError() throws Exception {
        when(StringUtils.isValidEmail(mView.getEmail())).thenReturn(false);
        mPresenter.onSigninButtonClick();

        verify(mView).showEmptyEmailError(R.string.error_invalid_email);
    }

    @Test
    public void passwordEmptyError() throws Exception {
        when(mView.getEmail()).thenReturn("aaa");
        when(mView.getPassword()).thenReturn("");
        mPresenter.onSigninButtonClick();

        verify(mView).showShortPasswordError(R.string.error_invalid_password);
    }

}
