package com.example.dapenduk.data.Repo;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dapenduk.data.DAO.DaoSessionApp;
import com.example.dapenduk.data.model.Admin;
import com.example.dapenduk.data.model.AdminDao;
import com.example.dapenduk.data.model.DaoSession;

import java.util.List;

public class AdminRepository {


    private DaoSession daoSession;
    private MutableLiveData<List<Admin>> allAdmin;
    private MutableLiveData<List<Admin>> admin;


    public AdminRepository(Application application) {
        daoSession = ((DaoSessionApp)application).getDaoSession();
        allAdmin = new MutableLiveData<>();
        allAdmin.postValue(daoSession.getAdminDao().loadAll());
    }

    public LiveData<List<Admin>> getAllAdmin(){
        return allAdmin;
    }



    public List<Admin> getAuthentication(String username){
      return  daoSession.getAdminDao().queryBuilder().where(AdminDao.Properties.Username.eq(username)).list();

    }
}
