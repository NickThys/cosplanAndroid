package com.example.cosplan.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WebshopRepository{
    private WebshopDao mWebshopDao;
    private LiveData<List<Webshop>> mAllWebshops;
    WebshopRepository(Application application){
        WebshopDatabase db= WebshopDatabase.getDatabase(application);
        mWebshopDao=db.webshopDao();
        mAllWebshops=mWebshopDao.getAllWebshops();
    }
    LiveData<List<Webshop>> getAllWebshops(){return mAllWebshops;}
    public void insert(Webshop webshop){new insertAsyncTask(mWebshopDao).execute(webshop);}
    private class insertAsyncTask extends AsyncTask<Webshop,Void,Void>{
        private WebshopDao mAsyncTaskDao;
        insertAsyncTask(WebshopDao dao){mAsyncTaskDao=dao;}

        @Override
        protected Void doInBackground(Webshop... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
