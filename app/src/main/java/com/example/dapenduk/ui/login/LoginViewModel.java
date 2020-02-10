package com.example.dapenduk.ui.login;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.dapenduk.data.Repo.AdminRepository;
import com.example.dapenduk.data.model.Admin;

import java.util.List;

import javax.inject.Inject;


public class LoginViewModel extends ViewModel {
    @Inject AdminRepository adminRepository;



    public LoginViewModel() {
    }



    public boolean validate(String username, String password) {
        List<Admin> admin = adminRepository.getAuthentication(username);


        if (admin.isEmpty()) {
            return false;
        }

        return admin.get(0).getPassword().equals(password);
    }


}
