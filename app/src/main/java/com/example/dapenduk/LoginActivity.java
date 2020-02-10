package com.example.dapenduk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.dapenduk.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername, etPassword;
    private LoginViewModel loginViewModel;
    private CoordinatorLayout loginLayout;
    public static final String SHARED_PREFS = "com.example.dapenduk.sharedPrefs";
    public static final String IS_LOGGED = "com.example.dapenduk.isLogged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvLoginGuest = findViewById(R.id.tv_login_guest);
        loginLayout = findViewById(R.id.login_layout);

        btnLogin.setOnClickListener(this);
        tvLoginGuest.setOnClickListener(this);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        tvLoginGuest.setOnClickListener(this);

        if (isLogIn()) {
            toListActivity();
        }

    }

    @Override
    public void onClick(View v) {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (v.getId()) {
            case R.id.tv_login_guest:
                editor.putBoolean(IS_LOGGED, false);
                editor.apply();
                toListActivity();
                break;
            case R.id.btn_login:

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                boolean login = loginViewModel.validate(username, password);
                if (login) {
                    editor.putBoolean(IS_LOGGED, true);
                    editor.apply();
                    toListActivity();
                    finish();
                } else {
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        Log.d("cobabos", "onClick: " + e.getMessage());
                    }
                    Utils.showSnackbar(loginLayout,"Kombinasi Username dan Password salah");
                }
        }
    }

    public void toListActivity() {
        startActivity(new Intent(this, ListPendudukActivity.class));

    }

    public boolean isLogIn() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGGED, false);
    }


}
