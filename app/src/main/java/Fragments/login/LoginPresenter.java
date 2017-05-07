package Fragments.login;

import Interactors.login.LoginInteractorImp;
import Interactors.login.LoginInteractor;

/**
 * Created by Pablo on 7/5/17.
 */

public class LoginPresenter {

    private LoginView view;
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginView view) {
        this.view = view;
        loginInteractor = new LoginInteractorImp(onLoginCallback);
    }

    public void logIn(String email, String password) {
        loginInteractor.logIn(email, password);
    }

    private final LoginInteractor.OnLoginCallback onLoginCallback = new LoginInteractor.OnLoginCallback() {
        @Override
        public void onSuccess() {
            view.hideLoading();
            view.navigateToMain();
        }

        @Override
        public void onError() {
            view.hideLoading();
            view.showAuthError();
        }

        @Override
        public void emptyFields() {
            view.showErrorEmptyFields();

        }
    };


}
