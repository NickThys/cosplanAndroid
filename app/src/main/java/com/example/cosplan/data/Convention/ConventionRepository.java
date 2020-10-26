package com.example.cosplan.data.Convention;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ConventionRepository {
    private ConventionDao mConventionDao;
    private LiveData<List<Convention>>mAllConventionsBelgium;
    private LiveData<List<Convention>>mAllConventionsNetherland;
    ConventionRepository(Application application){
        ConventionDatabase db=ConventionDatabase.getDatabase(application);
        mConventionDao=db.conventionDao();
        mAllConventionsBelgium=mConventionDao.getAllConventionsBelgium();
        mAllConventionsNetherland=mConventionDao.getAllConventionsNetherland();
    }
    public void insert(Convention convention){new insertAsyncTaks(mConventionDao).execute(convention);}
    LiveData<List<Convention>>getAllConventionsBelgium(){return mAllConventionsBelgium;}
    LiveData<List<Convention>>getAllConventionsNetherland(){return mAllConventionsNetherland;}
    private class insertAsyncTaks extends AsyncTask<Convention,Void,Void>{
        private ConventionDao mConDao;
        insertAsyncTaks(ConventionDao dao){mConDao=dao;}

        @Override
        protected Void doInBackground(Convention... conventions) {
            mConDao.insert(conventions[0]);
            return null;
        }
    }



}
