package com.example.cosplan.data.cosplay.part;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class PartRepository {
    private final PartDao mPartDao;
    private LiveData<List<Part>> mAllPartsToMake;
    private LiveData<List<Part>> mAllPartsToBuy;
    private Part mLastCreatedPart;
    private int mCosplayIdMake;
    private int mCosplayIdBuy;

    PartRepository(Application application) {
        CosplayDatabase db = CosplayDatabase.getDatabase(application);
        mPartDao = db.mPartDao();

        mAllPartsToMake = mPartDao.getPartsToMake(mCosplayIdMake);
        mAllPartsToBuy = mPartDao.getPartsToBuy(mCosplayIdBuy);
    }

    public void insert(Part part) {
        new insertAsyncTask(mPartDao).execute(part);
    }

    public void delete(Part part) {
        new deleteAsyncTask(mPartDao).execute(part);
    }

    public void update(Part part) {
        new updateAsyncTask(mPartDao).execute(part);
    }

    LiveData<List<Part>> getAllPartsToMake(int mCosplayIdMake) {
        this.mCosplayIdMake = mCosplayIdMake;

        mAllPartsToMake = mPartDao.getPartsToMake(mCosplayIdMake);
        return mAllPartsToMake;
    }

    LiveData<List<Part>> getAllPartsToBuy(int mCosplayIdBuy) {
        this.mCosplayIdBuy = mCosplayIdBuy;
        mAllPartsToBuy = mPartDao.getPartsToBuy(mCosplayIdBuy);
        return mAllPartsToBuy;
    }

    Part getLastCreatedPart(int mCosplayId) {
        mLastCreatedPart = mPartDao.getLastCreatedPart(mCosplayId);
        return mLastCreatedPart;
    }

    private static class insertAsyncTask extends AsyncTask<Part, Void, Void> {
        private final PartDao mPartDao;

        insertAsyncTask(PartDao mPartDao) {
            this.mPartDao = mPartDao;
        }

        @Override
        protected Void doInBackground(Part... parts) {
            mPartDao.insert(parts[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Part, Void, Void> {
        private final PartDao mPartDao;

        public deleteAsyncTask(PartDao mPartDao) {
            this.mPartDao = mPartDao;
        }

        @Override
        protected Void doInBackground(Part... parts) {
            mPartDao.delete(parts[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Part, Void, Void> {
        private final PartDao mPartDao;

        public updateAsyncTask(PartDao mPartDao) {
            this.mPartDao = mPartDao;
        }

        @Override
        protected Void doInBackground(Part... parts) {
            mPartDao.update(parts[0]);
            return null;
        }
    }
}
