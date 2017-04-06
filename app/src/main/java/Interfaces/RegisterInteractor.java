package Interfaces;

import android.view.View;
import android.widget.EditText;

/**
 * Created by Pablo on 24/3/17.
 */

public interface RegisterInteractor {
    void registerUser(View view, EditText editTextUserRegister, EditText editTextEmailRegister, EditText editTextPasswordRegister);
}
