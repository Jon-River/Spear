package com.spear.android.login;

/**
 * Created by Pablo on 7/5/17.
 */

public interface LoginView {

    void showLoading();

    void hideLoading();

    void navigateToMain();

    void showErrorEmptyFields();

    void showAuthError();
}
