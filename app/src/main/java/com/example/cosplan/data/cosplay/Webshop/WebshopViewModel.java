package com.example.cosplan.data.Coplay.Webshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WebshopViewModel extends AndroidViewModel {
    private WebshopRepository mRepository;
    LiveData<List<Webshop>> mAllWebshops;
    private int mCosplayId;
    public WebshopViewModel(@NonNull Application application) {
        super(application);
        mRepository=new WebshopRepository(application);
        mAllWebshops=mRepository.getAllWebshops(mCosplayId);
    }
    public LiveData<List<Webshop>>getAllWebshops(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllWebshops=mRepository.getAllWebshops(mCosplayId);
        return mAllWebshops;
    }
    public void insert(Webshop webshop){mRepository.insert(webshop);}
    public void delete(Webshop webshop){mRepository.delete(webshop);}
    public void update(Webshop webshop){mRepository.update(webshop);}
}
