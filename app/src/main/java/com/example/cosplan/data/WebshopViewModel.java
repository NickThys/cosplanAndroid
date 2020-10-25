package com.example.cosplan.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WebshopViewModel extends AndroidViewModel {
    private WebshopRepository mRepository;
    private LiveData<List<Webshop>>mAllWebshops;
    public WebshopViewModel(@NonNull Application application) {
        super(application);
        mRepository=new WebshopRepository(application);
        mAllWebshops=mRepository.getAllWebshops();
    }
    LiveData<List<Webshop>> getAllWebshops(){return mAllWebshops;}
    public void insert (Webshop webshop){mRepository.insert(webshop);}
}
