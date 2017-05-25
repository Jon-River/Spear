package register;

/**
 * Created by Pablo on 24/3/17.
 */

public interface RegisterInteractor {
    void registerUser(final String user, final String mail, final String password, final String province);

    interface OnRegisterCallback {
        void onSuccess();
        void onError();
    }
}
