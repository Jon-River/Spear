package Interactors.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Pablo on 24/3/17.
 */

public class LoginInteractorImp implements LoginInteractor {

    private FirebaseAuth firebaseAuth;
    final OnLoginCallback onLoginCallback;


    public LoginInteractorImp(OnLoginCallback onLoginCallback) {
        this.onLoginCallback = onLoginCallback;
        firebaseAuth = FirebaseAuth.getInstance();


    }


    @Override
    public void logIn(String email, String password) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            onLoginCallback.emptyFields();

        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        onLoginCallback.onSuccess();


                    } else {
                        onLoginCallback.onError();
                    }
                }
            });
        }

    }


}
