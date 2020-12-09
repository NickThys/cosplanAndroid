package com.example.cosplan.data.cosplay.refImg;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ReferenceImgViewModel extends AndroidViewModel {
    private final ReferenceImgRepository mReferenceImgRepository;
    LiveData<List<ReferenceImg>> mAllRefImg;
    private int mCosplayId;

    public ReferenceImgViewModel(@NonNull Application application) {
        super(application);
        mReferenceImgRepository = new ReferenceImgRepository(application);
        mAllRefImg = mReferenceImgRepository.getAllRefImg(mCosplayId);
    }

    public LiveData<List<ReferenceImg>> GetAllRefImg(int mCosplayId) {
        this.mCosplayId = mCosplayId;
        mAllRefImg = mReferenceImgRepository.getAllRefImg(mCosplayId);
        return mAllRefImg;
    }

    public void insert(ReferenceImg referenceImg) {
        mReferenceImgRepository.insert(referenceImg);
    }

    public void delete(ReferenceImg referenceImg) {
        mReferenceImgRepository.delete(referenceImg);
    }
}
