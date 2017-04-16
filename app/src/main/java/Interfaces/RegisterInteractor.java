package Interfaces;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Pablo on 24/3/17.
 */

public interface RegisterInteractor {
    void registerUser(View view, EditText editTextUserRegister, EditText editTextEmailRegister, Spinner spinnerProvinces, EditText editTextPasswordRegister);
}
