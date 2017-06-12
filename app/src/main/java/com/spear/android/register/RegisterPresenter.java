package com.spear.android.register;

public class RegisterPresenter {
  private RegisterInteractor registerInteractor;
  private final RegisterView view;

  public RegisterPresenter(RegisterView view) {
    this.view = view;
    this.registerInteractor = new RegisterInteractorImp(onRegisterCallback);
  }

  public void registerUser(String user, String mail, String password) {
    view.showLoading();
    registerInteractor.registerUser(user, mail, password);
  }

  private final RegisterInteractor.OnRegisterCallback onRegisterCallback =
      new RegisterInteractor.OnRegisterCallback() {
        @Override public void onSuccess() {
          view.hideLoading();
          view.navigateToLogin();
        }

        @Override public void onError() {
          view.hideLoading();
          view.showError("error");
        }

      };
}
