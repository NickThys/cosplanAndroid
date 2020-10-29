package com.example.cosplan.data.Coplay;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CosplayRepository {
 private CosplayDao mCosplayDao;
 private LiveData<List<Cosplay>>mAllCosplays;
 CosplayRepository(Application application){
     CosplayDatabase db=CosplayDatabase.getDatabase(application);
     mCosplayDao=db.cosplayDao();
     mAllCosplays=mCosplayDao.getAllCosplays();
 }
 LiveData<List<Cosplay>> getAllCosplays(){return mAllCosplays;}
 public void insert(Cosplay cosplay){new insertAsyncTask(mCosplayDao).execute(cosplay);}

    private class insertAsyncTask extends AsyncTask<Cosplay,Void,Void> {
        private CosplayDao mAsyncTaskDao;
        public insertAsyncTask(CosplayDao mCosplayDao) {mAsyncTaskDao=mCosplayDao;}

        @Override
        protected Void doInBackground(Cosplay... cosplays) {
            mAsyncTaskDao.insert(cosplays[0]);
            return null;
        }
    }


}
