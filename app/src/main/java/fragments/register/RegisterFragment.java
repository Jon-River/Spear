package fragments.register;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.spear.android.R;
import com.spear.android.activities.LoginActivity;

public class RegisterFragment extends Fragment implements RegisterView {


  public LoginActivity viewLogin;
  private EditText editTextUserRegister;
  private EditText editTextPasswordRegister;
  private EditText editTextEmailRegister;
  private Spinner spinnerProvinces;
  private Button buttonRegister;
  private RegisterPresenter registerPresenter;

  public RegisterFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_registro, container, false);
    init(view);
    listeners();

    registerPresenter = new RegisterPresenter(this);

    return view;
  }

  private void init(View view) {
    editTextUserRegister = (EditText) view.findViewById(R.id.editTextUserRegister);
    editTextPasswordRegister = (EditText) view.findViewById(R.id.editTextPasswordRegister);
    editTextEmailRegister = (EditText) view.findViewById(R.id.editTextEmailRegister);
    buttonRegister = (Button) view.findViewById(R.id.buttonRegister);
    spinnerProvinces = (Spinner) view.findViewById(R.id.spinnerProvinces);
    editTextPasswordRegister.setText("usertest1");
    editTextUserRegister.setText("usertest1");
    editTextEmailRegister.setText("usertest1@spear.com");
  }

  private void listeners() {
    buttonRegister.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        registerPresenter.registerUser(editTextUserRegister.getText().toString(),
            editTextEmailRegister.getText().toString(),
            editTextPasswordRegister.getText().toString(), spinnerProvinces.getSelectedItem().toString());
      }
    });
  }

  @Override public void showLoading() {
    //dialog.show();
  }

  @Override public void hideLoading() {
    //dialog.dismiss();
  }

  @Override public void navigateToLogin() {
    viewLogin.cambiarFragment(1);
  }

  @Override public void showError(String error) {

  }

  public void setActivity(LoginActivity loginActivity) {
    this.viewLogin = loginActivity;
  }
}
