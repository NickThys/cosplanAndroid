package com.example.cosplan.data.Cosplay_Part;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PartRepository {
    private PartDao mPartDao;
    private LiveData<List<Part>>mAllPartsToMake;
    private LiveData<List<Part>>mAllPartsToBuy;
    PartRepository(Application application){
        PartDatabase db=PartDatabase.getDatabase(application);
        mPartDao=db.partDao();
        mAllPartsToMake=mPartDao.getPartsToMake();
        mAllPartsToBuy=mPartDao.getPartsToBuy();
    }
    public void insert(Part part){new insertAsyncTask(mPartDao).execute(part);}
    LiveData<List<Part>>getAllPartsToMake(){return mAllPartsToMake;}
    LiveData<List<Part>>getAllPartsToBuy(){return mAllPartsToBuy;}
    private class insertAsyncTask extends AsyncTask<Part,Void,Void> {
        private PartDao dao;
        insertAsyncTask(PartDao mPartDao) { dao=mPartDao;}

        @Override
        protected Void doInBackground(Part... parts) {
            dao.insert(parts[0]);
            return null;
        }
    }
}
