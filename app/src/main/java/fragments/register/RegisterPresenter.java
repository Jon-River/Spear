package fragments.register;

import interactors.register.RegisterInteractorImp;
import interactors.register.RegisterInteractor;



public class RegisterPresenter {
  private RegisterInteractor registerInteractor;
  private final RegisterView view;

  public RegisterPresenter(RegisterView view) {
    this.view = view;
    this.registerInteractor = new RegisterInteractorImp(onRegisterCallback);
  }

  public void registerUser(String user, String mail, String password, String province) {
    view.showLoading();
    registerInteractor.registerUser(user, mail, password, province);
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
