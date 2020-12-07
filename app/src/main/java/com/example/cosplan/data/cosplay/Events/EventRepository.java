package com.example.cosplan.data.cosplay.Events;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class EventRepository {
    private EventDao mEventDao;
    private LiveData<List<Event>> mAllEvents;
    private int mCosplayId;
    private String mEventType;
    public EventRepository(Application application){
        CosplayDatabase mDatabase=CosplayDatabase.getDatabase(application);
        mEventDao=mDatabase.mEventDao();
        mAllEvents=mEventDao.getEventsByType(mCosplayId,mEventType);
    }
    LiveData<List<Event>> getAllEvents(int mCosplayId,String mEventType){
        this.mCosplayId=mCosplayId;
        this.mEventType=mEventType;
        mAllEvents=mEventDao.getEventsByType(mCosplayId,mEventType);
        return mAllEvents;
    }

    public void insert(Event mEvent){new insertAsyncTask(mEventDao).execute(mEvent);}
    private class insertAsyncTask extends AsyncTask<Event,Void,Void> {
        private EventDao mDao;
        public insertAsyncTask(EventDao mEventDao) {mDao=mEventDao;}

        @Override
        protected Void doInBackground(Event... events) {
            mDao.insert(events[0]);
            return null;
        }
    }

    public void update(Event mEvent){new updateAsyncTask(mEventDao).execute(mEvent);}
    private class updateAsyncTask extends AsyncTask<Event,Void,Void> {
        private EventDao mDao;
        public updateAsyncTask(EventDao mEventDao) {mDao=mEventDao;}

        @Override
        protected Void doInBackground(Event... events) {
            mDao.update(events[0]);
            return null;
        }
    }

    public void delete(Event mEvent){new deleteAsyncTask(mEventDao).execute(mEvent);}
    private class deleteAsyncTask extends AsyncTask<Event,Void,Void> {
        private EventDao mDao;
        public deleteAsyncTask(EventDao mEventDao) {mDao=mEventDao;}

        @Override
        protected Void doInBackground(Event... events) {
            mDao.delete(events[0]);
            return null;
        }
    }

}
