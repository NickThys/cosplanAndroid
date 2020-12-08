package com.example.cosplan.data.cosplay.webshop;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class WebshopRepository {
    private final WebshopDao mWebshopDao;
    private LiveData<List<Webshop>>mAllWebshops;
    private int mCosplayId;
    public WebshopRepository(Application application) {
        CosplayDatabase mCosplayDatabase=CosplayDatabase.getDatabase(application);
        mWebshopDao=mCosplayDatabase.mWebshopDao();
        mAllWebshops=mWebshopDao.getAllWebshops(mCosplayId);
    }
    LiveData<List<Webshop>>getAllWebshops(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllWebshops=mWebshopDao.getAllWebshops(mCosplayId);
        return mAllWebshops;
    }

    public void insert(Webshop webshop){
        new insertAsyncTask(mWebshopDao).execute(webshop);}
    public void update(Webshop webshop){
        new updateAsyncTask(mWebshopDao).execute(webshop);}
    public void delete(Webshop webshop){
        new deleteAsyncTask(mWebshopDao).execute(webshop);}

    private static class insertAsyncTask extends AsyncTask<Webshop,Void,Void> {
        private final WebshopDao mWebshopDao;
        public insertAsyncTask(WebshopDao mWebshopDao) {
            this.mWebshopDao =mWebshopDao;}

        @Override
        protected Void doInBackground(Webshop... webshops) {
            mWebshopDao.insert(webshops[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Webshop,Void,Void>{
        private final WebshopDao mWebshopDao;
        public updateAsyncTask(WebshopDao mWebshopDao) {
            this.mWebshopDao =mWebshopDao;}
        @Override
        protected Void doInBackground(Webshop... webshops) {
            mWebshopDao.update(webshops[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Webshop,Void,Void> {
        private final WebshopDao mWebshopDao;
        public deleteAsyncTask(WebshopDao mWebshopDao)  {
            this.mWebshopDao =mWebshopDao;}

        @Override
        protected Void doInBackground(Webshop... webshops) {
            mWebshopDao.delete(webshops[0]);
            return null;
        }
    }
}
