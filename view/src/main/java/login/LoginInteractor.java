package login;

/**
 * Created by Pablo on 24/3/17.
 */

public interface LoginInteractor {
    void logIn(String email, String password);

    interface OnLoginCallback {
        void onSuccess();
        void onError();
        void emptyFields();
    }
}
