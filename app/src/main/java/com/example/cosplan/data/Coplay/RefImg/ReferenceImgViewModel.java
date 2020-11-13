package com.example.cosplan.data.Coplay.RefImg;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ReferenceImgViewModel extends AndroidViewModel {
    private ReferenceImgRepository mRepository;
    LiveData<List<ReferenceImg>> mAllRefImg;
    private int mCosplayId;

    public ReferenceImgViewModel(@NonNull Application application) {
        super(application);
        mRepository=new ReferenceImgRepository(application);
        mAllRefImg=mRepository.getAllRefImg(mCosplayId);
    }
    public LiveData<List<ReferenceImg>>GetAllRefImg(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllRefImg=mRepository.getAllRefImg(mCosplayId);
        return mAllRefImg;
    }
    public void insert(ReferenceImg referenceImg){mRepository.insert(referenceImg);}    

}
