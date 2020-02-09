package com.example.dapenduk.data.Repo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dapenduk.data.DAO.DaoSessionApp;
import com.example.dapenduk.data.model.DaoSession;
import com.example.dapenduk.data.model.Penduduk;

import java.util.List;

public class PendudukRepository {
    private DaoSession mDaoSession;
    private MutableLiveData<List<Penduduk>> allPenduduk = new MutableLiveData<>();
    private static final String TAG = "updatebos";

    public PendudukRepository(Application application){
        mDaoSession = ((DaoSessionApp) application).getDaoSession();
        allPenduduk.postValue(mDaoSession.getPendudukDao().loadAll());

    }

    public void updateData(){
        allPenduduk.postValue(mDaoSession.getPendudukDao().loadAll());
    }

    public void insert(Penduduk penduduk){
        new InsetPendudukAsynctask(mDaoSession).execute(penduduk);
    }

    public void update(Penduduk penduduk){
        new UpdatePendudukAsynctask(mDaoSession).execute(penduduk);
    }
    public void delete(Penduduk penduduk){
        new DeletePendudukAsynctask(mDaoSession).execute(penduduk);

    }
    public void deleteAllPenduduk(){
        new DeleteAllPendudukAsynctask(mDaoSession).execute();
    }

    public LiveData<List<Penduduk>> getAllPenduduk(){
        return allPenduduk;
    }


    private static class InsetPendudukAsynctask extends AsyncTask<Penduduk,Void, Void>{
        DaoSession mDaoSession;


        private  InsetPendudukAsynctask(DaoSession daoSession){
            this.mDaoSession = daoSession;

        }
        @Override
        protected Void doInBackground(Penduduk... penduduks) {
            mDaoSession.getPendudukDao().insert(penduduks[0]);
            return null;
        }

    }
    private static class UpdatePendudukAsynctask extends AsyncTask<Penduduk,Void, Void>{
        DaoSession mDaoSession;

        private  UpdatePendudukAsynctask(DaoSession daoSession){
            this.mDaoSession = daoSession;
        }

        @Override
        protected Void doInBackground(Penduduk... penduduks) {
            mDaoSession.getPendudukDao().update(penduduks[0]);
            return null;
        }
    }

    private static class DeletePendudukAsynctask extends AsyncTask<Penduduk,Void, Void>{
        DaoSession mDaoSession;

        private  DeletePendudukAsynctask(DaoSession daoSession){
            this.mDaoSession = daoSession;
        }

        @Override
        protected Void doInBackground(Penduduk... penduduks) {
            mDaoSession.getPendudukDao().delete(penduduks[0]);
            return null;
        }
    }

    private static class DeleteAllPendudukAsynctask extends AsyncTask<Void,Void, Void>{
        DaoSession mDaoSession;

        private  DeleteAllPendudukAsynctask(DaoSession daoSession){
            this.mDaoSession = daoSession;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDaoSession.deleteAll(Penduduk.class);
            return null;
        }
    }

}
