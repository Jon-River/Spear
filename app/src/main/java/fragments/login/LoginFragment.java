package fragments.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spear.android.R;
import com.spear.android.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginView {


    private LoginPresenter loginPresenter;
    private EditText editTextUser;
    private EditText editTextPassword;
    private Button btnLogin, btnRegister;
    private ProgressDialog dialog;


    public LoginFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginPresenter = new LoginPresenter(this);
        init(view);
        listeners();
        return view;
    }


    private void init(View view) {
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        editTextUser = (EditText) view.findViewById(R.id.editTextUser);
        editTextUser.setText("usertest1@spear.com");
        editTextPassword.setText("usertest1");
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnLogin = (Button) view.findViewById(R.id.buttonLogin);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"OpenSans-Regular.ttf");

        editTextPassword.setTypeface(type);
        editTextUser.setTypeface(type);
        btnLogin.setTypeface(type);
        btnRegister.setTypeface(type);

        dialog = new ProgressDialog(view.getContext());

    }

    private void listeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                loginPresenter.logIn(editTextUser.getText().toString(), editTextPassword.getText().toString());
            }
        });
    }


    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void showErrorEmptyFields() {
        Toast.makeText(getContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAuthError() {
        Toast.makeText(getContext(), "Error de autenticación", Toast.LENGTH_SHORT).show();
    }
}
