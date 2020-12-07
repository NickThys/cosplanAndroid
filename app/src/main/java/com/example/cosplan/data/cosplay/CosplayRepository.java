package com.example.cosplan.data.cosplay;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CosplayRepository {
    private final CosplayDao mCosplayDao;
    private final LiveData<List<Cosplay>> mAllCosplays;

    CosplayRepository(Application application) {
        CosplayDatabase mCosplayDatabase = CosplayDatabase.getDatabase(application);
        mCosplayDao = mCosplayDatabase.mCosplayDao();
        mAllCosplays = mCosplayDao.getAllCosplays();
    }

    LiveData<List<Cosplay>> getAllCosplays() {
        return mAllCosplays;
    }

    public void insert(Cosplay cosplay) {
        new insertAsyncTask(mCosplayDao).execute(cosplay);
    }

    public void delete(Cosplay cosplay) {
        new deleteAsyncTask(mCosplayDao).execute(cosplay);
    }

    public void update(Cosplay cosplay) {
        new updateAsyncTask(mCosplayDao).execute(cosplay);
    }

    private static class insertAsyncTask extends AsyncTask<Cosplay, Void, Void> {
        private final CosplayDao mAsyncTaskDao;

        public insertAsyncTask(CosplayDao mCosplayDao) {
            mAsyncTaskDao = mCosplayDao;
        }

        @Override
        protected Void doInBackground(Cosplay... cosplays) {
            mAsyncTaskDao.insert(cosplays[0]);
            return null;
        }
    }


    private static class deleteAsyncTask extends AsyncTask<Cosplay, Void, Void> {
        private final CosplayDao mAsyncTaskDao;

        public deleteAsyncTask(CosplayDao mCosplayDao) {
            mAsyncTaskDao = mCosplayDao;
        }

        @Override
        protected Void doInBackground(Cosplay... cosplays) {
            mAsyncTaskDao.deleteCosplay(cosplays[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Cosplay, Void, Void> {
        private final CosplayDao mAsyncDao;

        public updateAsyncTask(CosplayDao dao) {
            mAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Cosplay... cosplays) {
            mAsyncDao.updateCosplay(cosplays[0]);
            return null;
        }
    }
}
