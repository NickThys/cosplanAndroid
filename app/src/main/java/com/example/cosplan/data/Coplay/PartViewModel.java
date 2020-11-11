package com.example.cosplan.data.Coplay;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PartViewModel extends AndroidViewModel {


    private PartRepository mRepository;
    LiveData<List<Part>> mAllPartsToMake;
    LiveData<List<Part>> mAllPartsToBuy;
    private int mCosplayIdMake;
    private int mCosplayIdBuy;
    public PartViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PartRepository(application);

        mAllPartsToMake = mRepository.getAllPartsToMake(mCosplayIdMake);
        mAllPartsToBuy = mRepository.getAllPartsToBuy(mCosplayIdBuy);
    }
    public LiveData<List<Part>> getAllPartsToMake(int mCosplayId) {
        this.mCosplayIdMake = mCosplayId;
        mAllPartsToMake = mRepository.getAllPartsToMake(mCosplayIdMake);
        return mAllPartsToMake;
    }

    public LiveData<List<Part>> getAllPartsToBuy(int mCosplayId) {
        mCosplayIdBuy = mCosplayId;

        mAllPartsToBuy = mRepository.getAllPartsToBuy(mCosplayIdBuy);
        return mAllPartsToBuy;
    }

    public void insert(Part part) {
        mRepository.insert(part);
    }



}
