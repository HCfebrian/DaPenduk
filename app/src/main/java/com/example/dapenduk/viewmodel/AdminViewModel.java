package com.example.dapenduk.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dapenduk.data.Repo.AdminRepository;
import com.example.dapenduk.data.model.Admin;

import java.util.List;


public class AdminViewModel extends AndroidViewModel {
    private AdminRepository adminRepository;
    private LiveData<List<Admin>> allAdmin;


    public AdminViewModel(@NonNull Application application) {
        super(application);
        adminRepository = new AdminRepository(application);
        allAdmin = adminRepository.getAllAdmin();
    }


    public boolean login(String username, String password) {
        List<Admin> admin = adminRepository.getAuthentication(username);
        if(!admin.isEmpty()){
            return admin.get(0).getPassword().equals(password);
        }
        return false;
    }

}
