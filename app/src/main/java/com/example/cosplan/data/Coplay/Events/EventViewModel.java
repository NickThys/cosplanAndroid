package com.example.cosplan.data.Coplay.Events;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository mEventRepository;
    LiveData<List<Event>> mAllEvents;
    private int mCosplayId;
    private String mEventType;

    public EventViewModel(@NonNull Application application) {
        super(application);
        mEventRepository=new EventRepository(application);
        mAllEvents=mEventRepository.getAllEvents(mCosplayId,mEventType);
    }
    public LiveData<List<Event>> getAllEvents(int mCosplayId,String mEventType){
        this.mCosplayId=mCosplayId;
        this.mEventType=mEventType;
        mAllEvents=mEventRepository.getAllEvents(mCosplayId, mEventType);
        return mAllEvents;
    }
    public void insert(Event event){mEventRepository.insert(event);}
    public void update(Event event){mEventRepository.update(event);}
    public void delete(Event event){mEventRepository.delete(event);}
}
