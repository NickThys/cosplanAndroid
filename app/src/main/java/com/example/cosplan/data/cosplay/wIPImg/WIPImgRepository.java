package com.example.cosplan.data.cosplay.wIPImg;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class WIPImgRepository {
    private final WIPImgDao mWIPImgDao;
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

    public void delete(WIPImg wipImg) {
        new deleteAsyncTask(mWIPImgDao).execute(wipImg);}

    private static class insertAsyncTask extends AsyncTask<WIPImg, Void, Void> {
        private final WIPImgDao mWipImgDao;

        public insertAsyncTask(WIPImgDao mWIPImgDao) {
            mWipImgDao = mWIPImgDao;
        }

        @Override
        protected Void doInBackground(WIPImg... wipImgs) {
            mWipImgDao.insert(wipImgs[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<WIPImg,Void,Void> {
        private final WIPImgDao mWipImgDao;
        public deleteAsyncTask(WIPImgDao mWIPImgDao) {
            mWipImgDao =mWIPImgDao;}

        @Override
        protected Void doInBackground(WIPImg... wipImgs) {
            mWipImgDao.delete(wipImgs[0]);
            return null;
        }
    }
}
