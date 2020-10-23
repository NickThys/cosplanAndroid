package com.example.cosplan.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalenderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalenderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the calender fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}