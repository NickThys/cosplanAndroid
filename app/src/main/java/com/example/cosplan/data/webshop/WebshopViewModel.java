package com.example.cosplan.data.webshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WebshopViewModel extends AndroidViewModel {

    private final WebshopRepository mWebshopRepository;
    LiveData<List<Webshop>> mAllWebshops;

    public WebshopViewModel(@NonNull Application application) {
        super(application);
        mWebshopRepository = new WebshopRepository(application);
        mAllWebshops = mWebshopRepository.getAllWebshops();
    }

    public LiveData<List<Webshop>> getAllWebshops() {
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
