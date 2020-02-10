package com.example.dapenduk.ui.login;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.dapenduk.data.Repo.AdminRepository;
import com.example.dapenduk.data.model.Admin;

import java.util.List;


public class LoginViewModel extends AndroidViewModel {
    private AdminRepository adminRepository;



    public LoginViewModel(@NonNull Application application) {
        super(application);
        adminRepository = new AdminRepository(application);
    }


    public boolean validate(String username, String password) {
        List<Admin> admin = adminRepository.getAuthentication(username);


        if (admin.isEmpty()) {
            return false;
        }

        return admin.get(0).getPassword().equals(password);
    }


}
