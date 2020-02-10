package com.example.dapenduk;

import android.app.Application;

import com.example.dapenduk.data.DAO.DbOpenHelper;
import com.example.dapenduk.data.model.Admin;
import com.example.dapenduk.data.model.DaoMaster;
import com.example.dapenduk.data.model.DaoSession;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class DaoSessionApp extends DaggerApplication {
    private DaoSession mDaoSession;

    public DaoSessionApp(){
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession = new DaoMaster(new DbOpenHelper(getApplicationContext(),"dapenduk.db").getReadableDb()).newSession();
    }
    public  DaoSession getDaoSession(){
        return mDaoSession;
    }
}
