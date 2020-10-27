package com.example.cosplan.data.webshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WebshopViewModel extends AndroidViewModel {

    private WebshopRepository mRepository;
    LiveData<List<Webshop>> mAllWebshops;

    public WebshopViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WebshopRepository(application);
        mAllWebshops = mRepository.getAllWebshops();
    }

    public LiveData<List<Webshop>> getAllWebshops() {
        return mAllWebshops;
    }

    public void insert(Webshop webshop) {
        mRepository.insert(webshop);
    }
    public void delete(Webshop webshop){mRepository.delete(webshop);}
}
