package com.example.cosplan.data.Coplay.WIPImg;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WIPImgViewModel extends AndroidViewModel {
    private WIPImgRepository mRepository;
    LiveData<List<WIPImg>> mAllWIPImgs;
    private int mCosplayId;
    public WIPImgViewModel(@NonNull Application application) {
        super(application);
        mRepository=new WIPImgRepository(application);
        mAllWIPImgs=mRepository.getAllWIPImgs(mCosplayId);
    }
    public LiveData<List<WIPImg>> getAllWIPImgs(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllWIPImgs=mRepository.getAllWIPImgs(mCosplayId);
        return mAllWIPImgs;
    }
    public void insert(WIPImg wipImg){mRepository.insert(wipImg);}

    public void delete(WIPImg wipImg) {mRepository.delete(wipImg);}
}
