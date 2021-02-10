package com.example.cosplan.data.cosplay.part;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PartViewModel extends AndroidViewModel {


    private final PartRepository mPartRepository;
    LiveData<List<Part>> mAllPartsToMake;
    LiveData<List<Part>> mAllPartsToBuy;
    Part mLastCreatedPart;
    private int mCosplayIdMake;
    private int mCosplayIdBuy;

    public PartViewModel(@NonNull Application application) {
        super(application);
        mPartRepository = new PartRepository(application);

        mAllPartsToMake = mPartRepository.getAllPartsToMake(mCosplayIdMake);
        mAllPartsToBuy = mPartRepository.getAllPartsToBuy(mCosplayIdBuy);
    }

    public LiveData<List<Part>> getAllPartsToMake(int mCosplayId) {
        this.mCosplayIdMake = mCosplayId;
        mAllPartsToMake = mPartRepository.getAllPartsToMake(mCosplayIdMake);
        return mAllPartsToMake;
    }

    public LiveData<List<Part>> getAllPartsToBuy(int mCosplayId) {
        mCosplayIdBuy = mCosplayId;
        mAllPartsToBuy = mPartRepository.getAllPartsToBuy(mCosplayIdBuy);
        return mAllPartsToBuy;
    }

    public Part getLastCreatedPart(int mCosplayId) {
        mLastCreatedPart=mPartRepository.getLastCreatedPart(mCosplayId);
        return mLastCreatedPart;
    }

    public void insert(Part part) {
        mPartRepository.insert(part);
    }

    public void delete(Part part) {
        mPartRepository.delete(part);
    }

    public void update(Part part) {
        mPartRepository.update(part);
    }


}
