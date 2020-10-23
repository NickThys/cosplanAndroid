package com.example.cosplan.ui.webshop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WebshopViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WebshopViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the webshop fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}