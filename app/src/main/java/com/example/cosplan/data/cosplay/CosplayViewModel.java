package com.example.cosplan.data.cosplay;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CosplayViewModel extends AndroidViewModel {
    private CosplayRepository mRepository;
    LiveData<List<Cosplay>>mAllConventions;

    public CosplayViewModel(@NonNull Application application) {
        super(application);
        mRepository=new CosplayRepository(application);
        mAllConventions=mRepository.getAllCosplays();
    }
    public LiveData<List<Cosplay>>getAllConventions(){return mAllConventions;}
    public void insert(Cosplay cosplay){mRepository.insert(cosplay);}
    public void delete(Cosplay cosplay){mRepository.delete(cosplay);}
    public void update(Cosplay cosplay){mRepository.update(cosplay);}
}
