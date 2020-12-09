package com.example.cosplan.data.cosplay.events;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class EventRepository {
    private final EventDao mEventDao;
    private LiveData<List<Event>> mAllEvents;
    private int mCosplayId;
    private String mEventType;

    public EventRepository(Application application) {
        CosplayDatabase mCosplayDatabase = CosplayDatabase.getDatabase(application);
        mEventDao = mCosplayDatabase.mEventDao();
        mAllEvents = mEventDao.getEventsByType(mCosplayId, mEventType);
    }

    LiveData<List<Event>> getAllEvents(int mCosplayId, String mEventType) {
        this.mCosplayId = mCosplayId;
        this.mEventType = mEventType;
        mAllEvents = mEventDao.getEventsByType(mCosplayId, mEventType);
        return mAllEvents;
    }

    public void insert(Event mEvent) {
        new insertAsyncTask(mEventDao).execute(mEvent);
    }

    private static class insertAsyncTask extends AsyncTask<Event, Void, Void> {
        private final EventDao mEventDao;

        public insertAsyncTask(EventDao mEventDao) {
            this.mEventDao = mEventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            mEventDao.insert(events[0]);
            return null;
        }
    }

    public void update(Event mEvent) {
        new updateAsyncTask(mEventDao).execute(mEvent);
    }

    private static class updateAsyncTask extends AsyncTask<Event, Void, Void> {
        private final EventDao mEventDao;

        public updateAsyncTask(EventDao mEventDao) {
            this.mEventDao = mEventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            mEventDao.update(events[0]);
            return null;
        }
    }

    public void delete(Event mEvent) {
        new deleteAsyncTask(mEventDao).execute(mEvent);
    }

    private static class deleteAsyncTask extends AsyncTask<Event, Void, Void> {
        private final EventDao mEventDao;

        public deleteAsyncTask(EventDao mEventDao) {
            this.mEventDao = mEventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            mEventDao.delete(events[0]);
            return null;
        }
    }

}
