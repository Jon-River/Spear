package Fragments.register;

/**
 * Created by pablo.rojas on 5/5/17.
 */

public interface RegisterView {
  void showLoading();

  void hideLoading();

  void navigateToLogin();

  void showError(String error);
}
