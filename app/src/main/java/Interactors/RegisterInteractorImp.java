package Interactors;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Fragments.RegisterFragment;
import Interfaces.RegisterInteractor;
import Objects.UserInfo;

import static android.widget.Toast.makeText;

/**
 * Created by Pablo on 24/3/17.
 */

public class RegisterInteractorImp implements RegisterInteractor {

    private RegisterFragment registerFragment;
    public FirebaseAuth firebaseAuth;
    private ProgressDialog dialog;
    private DatabaseReference databaseReference;

    public RegisterInteractorImp(RegisterFragment registerFragment) {
        this.registerFragment = registerFragment;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void registerUser(final View view, final EditText user, final EditText email, final Spinner provinces, EditText password) {

        final String emailString =  email.getText().toString();
        final String userString = user.getText().toString();
        final String fishzoneString = provinces.getSelectedItem().toString();
        String passwString = password.getText().toString();

        Log.v("-------------", "" + userString + " " + passwString);
        dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("Registrando usuario");
        dialog.show();
        if (TextUtils.isEmpty(emailString) || TextUtils.isEmpty(userString) || TextUtils.isEmpty(passwString)|| TextUtils.isEmpty(fishzoneString)) {
            makeText(view.getContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(emailString,passwString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = task.getResult().getUser();

                        UserInfo userData = new UserInfo(userString, emailString, user.getUid(),fishzoneString );
                        databaseReference.child("users").push().setValue(userData);
                        dialog.dismiss();
                        registerFragment.openFragementLogin();
                    }else{
                        dialog.dismiss();
                        Toast.makeText(view.getContext(), "Error de registro", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }

    }
}
