package com.example.cosplan.data.cosplay.webshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WebshopViewModel extends AndroidViewModel {
    private final WebshopRepository mWebshopRepository;
    LiveData<List<Webshop>> mAllWebshops;
    private int mCosplayId;

    public WebshopViewModel(@NonNull Application application) {
        super(application);
        mWebshopRepository = new WebshopRepository(application);
        mAllWebshops = mWebshopRepository.getAllWebshops(mCosplayId);
    }

    public LiveData<List<Webshop>> getAllWebshops(int mCosplayId) {
        this.mCosplayId = mCosplayId;
        mAllWebshops = mWebshopRepository.getAllWebshops(mCosplayId);
        return mAllWebshops;
    }

    public void insert(Webshop webshop) {
        mWebshopRepository.insert(webshop);
    }

    public void delete(Webshop webshop) {
        mWebshopRepository.delete(webshop);
    }

    public void update(Webshop webshop) {
        mWebshopRepository.update(webshop);
    }
}
