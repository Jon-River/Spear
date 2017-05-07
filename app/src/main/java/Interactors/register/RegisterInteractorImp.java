package Interactors.register;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import objects.UserInfo;

/**
 * Created by Pablo on 24/3/17.
 */

public class RegisterInteractorImp implements RegisterInteractor {

  public FirebaseAuth firebaseAuth;
  private DatabaseReference databaseReference;
  final OnRegisterCallback onRegisterCallback;

  public RegisterInteractorImp(OnRegisterCallback onRegisterCallback) {
    firebaseAuth = FirebaseAuth.getInstance();
    databaseReference = FirebaseDatabase.getInstance().getReference();
    this.onRegisterCallback = onRegisterCallback;
  }

  @Override public void registerUser(final String user, final String mail, final String password, final String province) {
    firebaseAuth.createUserWithEmailAndPassword(mail, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              FirebaseUser fbUser = task.getResult().getUser();
              UserInfo userData = new UserInfo(user, mail, province);
              databaseReference.child("users").child(fbUser.getUid()).setValue(userData);
              onRegisterCallback.onSuccess();
            } else {
              onRegisterCallback.onError();
            }
          }
        });
  }
}
