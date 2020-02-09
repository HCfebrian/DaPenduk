package com.example.dapenduk.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dapenduk.data.Repo.PendudukRepository;
import com.example.dapenduk.data.model.Penduduk;

import java.util.List;


public class PendudukViewModel extends AndroidViewModel {
    private LiveData<List<Penduduk>> listPenduduk;
    private PendudukRepository pendudukRepository;
    private static final String TAG = "updatebos";


    public PendudukViewModel(@NonNull Application application) {
        super(application);
        pendudukRepository = new PendudukRepository(application);
        listPenduduk = pendudukRepository.getAllPenduduk();
    }

    public void updateData(){
        pendudukRepository.updateData();
    }

    public void insert(Penduduk penduduk){
        pendudukRepository.insert(penduduk);
        updateData();
    }

    public void count(){
//        ToDo : delete this
        Log.d(TAG, "count: "+listPenduduk.getValue().size());
    }

    public void update(Penduduk penduduk){
        pendudukRepository.update(penduduk);
        updateData();
    }

    public void delete(Penduduk penduduk){
        pendudukRepository.delete(penduduk);
        updateData();
    }

    public void deleteAll(){
        pendudukRepository.deleteAllPenduduk();
        updateData();
    }

    public LiveData<List<Penduduk>> getAllPenduduk(){
        return listPenduduk;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
