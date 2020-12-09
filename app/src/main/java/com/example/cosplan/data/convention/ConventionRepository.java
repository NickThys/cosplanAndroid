package com.example.cosplan.data.convention;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ConventionRepository {
    private final ConventionDao mConventionDao;
    private final LiveData<List<Convention>> mAllConventionsBelgium;
    private final LiveData<List<Convention>> mAllConventionsNetherland;

    ConventionRepository(Application application) {
        ConventionDatabase mConventionDatabase = ConventionDatabase.getDatabase(application);
        mConventionDao = mConventionDatabase.mConventionDao();
        mAllConventionsBelgium = mConventionDao.getAllConventionsBelgium();
        mAllConventionsNetherland = mConventionDao.getAllConventionsNetherland();
    }

    public void insert(Convention mConvention) {
        new insertAsyncTaks(mConventionDao).execute(mConvention);
    }

    LiveData<List<Convention>> getAllConventionsBelgium() {
        return mAllConventionsBelgium;
    }

    LiveData<List<Convention>> getAllConventionsNetherland() {
        return mAllConventionsNetherland;
    }

    private static class insertAsyncTaks extends AsyncTask<Convention, Void, Void> {
        private final ConventionDao mConventionDao;

        public insertAsyncTaks(ConventionDao dao) {
            mConventionDao = dao;
        }

        @Override
        protected Void doInBackground(Convention... conventions) {
            mConventionDao.insert(conventions[0]);
            return null;
        }
    }


}
