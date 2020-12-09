package com.example.cosplan.data.convention;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ConventionViewModel extends AndroidViewModel {
    final LiveData<List<Convention>>mAllConventionsBelgium;
    final LiveData<List<Convention>>mAllConventionsNetherland;

    public ConventionViewModel(@NonNull Application application) {
        super(application);
        ConventionRepository mConventionRepository = new ConventionRepository(application);
        mAllConventionsBelgium= mConventionRepository.getAllConventionsBelgium();
        mAllConventionsNetherland= mConventionRepository.getAllConventionsNetherland();
    }
    public LiveData<List<Convention>>getAllConventionsBelgium(){return mAllConventionsBelgium;}
    public LiveData<List<Convention>>getAllConventionsNetherland(){return mAllConventionsNetherland;}
}
