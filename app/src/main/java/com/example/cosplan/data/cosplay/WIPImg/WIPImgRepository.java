package com.example.cosplan.data.cosplay.WIPImg;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class WIPImgRepository {
    private WIPImgDao mWIPImgDao;
    private LiveData<List<WIPImg>> mAllWIPImgs;
    private int mCosplayId;

    WIPImgRepository(Application application) {
        CosplayDatabase db = CosplayDatabase.getDatabase(application);
        mWIPImgDao = db.mWipImgDao();
        mAllWIPImgs = mWIPImgDao.getWIPImg(mCosplayId);
    }

    LiveData<List<WIPImg>> getAllWIPImgs(int mCosplayId) {
        this.mCosplayId = mCosplayId;
        mAllWIPImgs = mWIPImgDao.getWIPImg(mCosplayId);
        return mAllWIPImgs;
    }

    public void insert(WIPImg wipImg) {
        new insertAsyncTask(mWIPImgDao).execute(wipImg);
    }

    public void delete(WIPImg wipImg) {new deleteAsyncTask(mWIPImgDao).execute(wipImg);}

    private class insertAsyncTask extends AsyncTask<WIPImg, Void, Void> {
        private WIPImgDao dao;

        public insertAsyncTask(WIPImgDao mWIPImgDao) {
            dao = mWIPImgDao;
        }

        @Override
        protected Void doInBackground(WIPImg... wipImgs) {
            dao.insert(wipImgs[0]);
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<WIPImg,Void,Void> {
        private WIPImgDao dao;
        public deleteAsyncTask(WIPImgDao mWIPImgDao) {dao=mWIPImgDao;}

        @Override
        protected Void doInBackground(WIPImg... wipImgs) {
            dao.delete(wipImgs[0]);
            return null;
        }
    }
}
