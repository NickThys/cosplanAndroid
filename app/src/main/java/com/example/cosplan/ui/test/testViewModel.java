package com.example.cosplan.ui.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class testViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public testViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("this is the test fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
