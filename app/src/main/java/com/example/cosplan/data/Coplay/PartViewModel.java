package com.example.cosplan.data.Coplay;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PartViewModel extends AndroidViewModel {
private PartRepository mRepository;
LiveData<List<Part>>mAllPartsToMake;
LiveData<List<Part>>mAllPartsToBuy;
    public PartViewModel(@NonNull Application application) {
        super(application);
        mRepository=new PartRepository(application);
        mAllPartsToMake=mRepository.getAllPartsToMake();
        mAllPartsToBuy=mRepository.getAllPartsToBuy();
    }
    public LiveData<List<Part>>getAllPartsToMake(){return mAllPartsToMake;}
    public LiveData<List<Part>> getAllPartsToBuy(){return mAllPartsToBuy;}
    public void insert(Part part){mRepository.insert(part);}
}
