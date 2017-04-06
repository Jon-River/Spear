package Interactors;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.spear.android.Views.MainActivity;

import Fragments.LoginFragment;
import Interfaces.LoginInteractor;

/**
 * Created by Pablo on 24/3/17.
 */

public class LoginInteractorImp implements LoginInteractor {


    private LoginFragment loginFragment;
    private FirebaseAuth firebaseAuth;
    private Context context;
    private ProgressDialog dialog;




    public LoginInteractorImp(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
        firebaseAuth = FirebaseAuth.getInstance();


    }


    @Override
    public void logIn(final View view, EditText user, EditText password) {
        String email = user.getText().toString().trim();
        String passw = password.getText().toString().trim();
        context = view.getContext();
        dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("Autenticando usuario");
        dialog.show();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(passw)) {
            Toast.makeText(view.getContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(user.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        dialog.dismiss();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        context.startActivity(intent);

                    }else{
                        Toast.makeText(context, "Error de autenticación", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }






    }



}
