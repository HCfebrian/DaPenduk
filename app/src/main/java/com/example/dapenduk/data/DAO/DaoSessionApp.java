package com.example.dapenduk.data.DAO;

import android.app.Application;

import com.example.dapenduk.data.model.Admin;
import com.example.dapenduk.data.model.DaoMaster;
import com.example.dapenduk.data.model.DaoSession;

public class DaoSessionApp extends Application {
    private DaoSession mDaoSession;

    public DaoSessionApp(){
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession = new DaoMaster(new DbOpenHelper(getApplicationContext(),"dapenduk.db").getReadableDb()).newSession();
//        mDaoSession.getAdminDao().insert(new Admin("admin","admin"));
    }
    public  DaoSession getDaoSession(){
        return mDaoSession;
    }
}
