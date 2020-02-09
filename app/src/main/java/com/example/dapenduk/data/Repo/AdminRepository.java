package com.example.dapenduk.data.Repo;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dapenduk.data.DAO.DaoSessionApp;
import com.example.dapenduk.data.model.Admin;
import com.example.dapenduk.data.model.DaoSession;

import java.util.List;

public class AdminRepository {


    private DaoSession daoSession;
    private MutableLiveData<List<Admin>> allAdmin;


    public AdminRepository(Application application) {
        daoSession = ((DaoSessionApp)application).getDaoSession();
        allAdmin = new MutableLiveData<>();
        allAdmin.postValue(daoSession.getAdminDao().loadAll());
    }

    public LiveData<List<Admin>> getAllAdmin(){
        return allAdmin;
    }

}
