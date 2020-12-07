package com.example.cosplan.data.cosplay.Webshop;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class WebshopRepository {
    private WebshopDao mWebshopDao;
    private LiveData<List<Webshop>>mAllWebshops;
    private int mCosplayId;
    public WebshopRepository(Application application) {
        CosplayDatabase db=CosplayDatabase.getDatabase(application);
        mWebshopDao=db.mWebshopDao();
        mAllWebshops=mWebshopDao.getAllWebshops(mCosplayId);
    }
    LiveData<List<Webshop>>getAllWebshops(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllWebshops=mWebshopDao.getAllWebshops(mCosplayId);
        return mAllWebshops;
    }

    public void insert(Webshop webshop){new insertAsyncTask(mWebshopDao).execute(webshop);}
    public void update(Webshop webshop){new updateAsyncTask(mWebshopDao).execute(webshop);}
    public void delete(Webshop webshop){new deleteAsyncTask(mWebshopDao).execute(webshop);}

    private class insertAsyncTask extends AsyncTask<Webshop,Void,Void> {
        private WebshopDao dao;
        public insertAsyncTask(WebshopDao mWebshopDao) {dao=mWebshopDao;}

        @Override
        protected Void doInBackground(Webshop... webshops) {
            dao.insert(webshops[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<Webshop,Void,Void>{
        private WebshopDao dao;
        public updateAsyncTask(WebshopDao mWebshopDao) {dao=mWebshopDao;}
        @Override
        protected Void doInBackground(Webshop... webshops) {
            dao.update(webshops[0]);
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<Webshop,Void,Void> {
        private WebshopDao dao;
        public deleteAsyncTask(WebshopDao mWebshopDao)  {dao=mWebshopDao;}

        @Override
        protected Void doInBackground(Webshop... webshops) {
            dao.delete(webshops[0]);
            return null;
        }
    }
}
