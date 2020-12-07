package com.example.cosplan.data.cosplay;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CosplayViewModel extends AndroidViewModel {
    private final CosplayRepository mCosplayRepository;
    final LiveData<List<Cosplay>>mAllConventions;

    public CosplayViewModel(@NonNull Application application) {
        super(application);
        mCosplayRepository =new CosplayRepository(application);
        mAllConventions= mCosplayRepository.getAllCosplays();
    }
    public LiveData<List<Cosplay>>getAllConventions(){return mAllConventions;}
    public void insert(Cosplay cosplay){
        mCosplayRepository.insert(cosplay);}
    public void delete(Cosplay cosplay){
        mCosplayRepository.delete(cosplay);}
    public void update(Cosplay cosplay){
        mCosplayRepository.update(cosplay);}
}
