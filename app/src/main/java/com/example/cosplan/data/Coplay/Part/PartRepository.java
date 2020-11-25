package com.example.cosplan.data.Coplay.Part;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.Coplay.CosplayDatabase;

import java.util.List;

public class PartRepository {
    private PartDao mPartDao;
    private LiveData<List<Part>>mAllPartsToMake;
    private LiveData<List<Part>>mAllPartsToBuy;
    private int mCosplayIdMake;
    private int mCosplayIdBuy;

    PartRepository(Application application){
        CosplayDatabase db= CosplayDatabase.getDatabase(application);
        mPartDao=db.partDao();

        mAllPartsToMake=mPartDao.getPartsToMake(mCosplayIdMake);
        mAllPartsToBuy=mPartDao.getPartsToBuy(mCosplayIdBuy);
    }
    public void insert(Part part){new insertAsyncTask(mPartDao).execute(part);}
    public void delete(Part part){new deleteAsyncTask(mPartDao).execute(part);}
    public void update(Part part){new updateAsyncTask(mPartDao).execute(part);}

    LiveData<List<Part>>getAllPartsToMake(int mCosplayIdMake){
        this.mCosplayIdMake = mCosplayIdMake;

        mAllPartsToMake=mPartDao.getPartsToMake(mCosplayIdMake);
        return mAllPartsToMake;}
    LiveData<List<Part>>getAllPartsToBuy(int mCosplayIdBuy){
        this.mCosplayIdBuy = mCosplayIdBuy;
        mAllPartsToBuy=mPartDao.getPartsToBuy(mCosplayIdBuy);
        return mAllPartsToBuy;}

    private class insertAsyncTask extends AsyncTask<Part,Void,Void> {
        private PartDao dao;
        insertAsyncTask(PartDao mPartDao) { dao=mPartDao;}

        @Override
        protected Void doInBackground(Part... parts) {
            dao.insert(parts[0]);
            return null;
        }
    }
    private class deleteAsyncTask extends AsyncTask<Part,Void,Void> {
        private PartDao dao;
        public deleteAsyncTask(PartDao mPartDao) {dao=mPartDao;}

        @Override
        protected Void doInBackground(Part... parts) {
            dao.delete(parts[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<Part,Void,Void> {
        private PartDao dao;
        public updateAsyncTask(PartDao mPartDao) {dao=mPartDao;}

        @Override
        protected Void doInBackground(Part... parts) {
            dao.update(parts[0]);
            return null;
        }
    }
}
