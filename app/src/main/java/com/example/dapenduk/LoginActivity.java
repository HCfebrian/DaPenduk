package com.example.dapenduk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dapenduk.viewmodel.AdminViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername, etPassword;
    private AdminViewModel adminViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvLoginGuest = findViewById(R.id.tv_login_guest);

        btnLogin.setOnClickListener(this);
        tvLoginGuest.setOnClickListener(this);
        adminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);

    }

    @Override
    public void onClick(View v) {

        //default login
        // u: admin
        // p: admin

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        boolean login = adminViewModel.login(username, password);
        if (login) {
            startActivity(new Intent(this, ListPendudukActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Kombinasi Username dan Password salah", Toast.LENGTH_SHORT).show();
        }


    }
}
