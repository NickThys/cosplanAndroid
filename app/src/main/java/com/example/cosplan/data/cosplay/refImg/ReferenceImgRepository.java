package com.example.cosplan.data.cosplay.refImg;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class ReferenceImgRepository {
    private final ReferenceImgDao mReferenceImgDao;
    private LiveData<List<ReferenceImg>> mAllRefImg;
    private int mCosplayId;

    ReferenceImgRepository(Application application) {
        CosplayDatabase mCosplayDatabase = CosplayDatabase.getDatabase(application);
        mReferenceImgDao = mCosplayDatabase.mReferenceImgDao();
        mAllRefImg = mReferenceImgDao.getReferenceImg(mCosplayId);
    }

    LiveData<List<ReferenceImg>> getAllRefImg(int mCosplayId) {
        this.mCosplayId = mCosplayId;
        mAllRefImg = mReferenceImgDao.getReferenceImg(mCosplayId);
        return mAllRefImg;
    }

    public void insert(ReferenceImg referenceImg) {
        new insertAsyncTask(mReferenceImgDao).execute(referenceImg);
    }

    public void delete(ReferenceImg referenceImg) {
        new deleteAsyncTask(mReferenceImgDao).execute(referenceImg);
    }

    private static class insertAsyncTask extends AsyncTask<ReferenceImg, Void, Void> {
        private final ReferenceImgDao mReferenceImgDao;

        public insertAsyncTask(ReferenceImgDao mReferenceImgDao) {
            this.mReferenceImgDao = mReferenceImgDao;
        }

        @Override
        protected Void doInBackground(ReferenceImg... referenceImgs) {
            mReferenceImgDao.insert(referenceImgs[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<ReferenceImg, Void, Void> {
        final ReferenceImgDao mReferenceImgDao;

        public deleteAsyncTask(ReferenceImgDao mReferenceImgDao) {
            this.mReferenceImgDao = mReferenceImgDao;
        }

        @Override
        protected Void doInBackground(ReferenceImg... referenceImgs) {
            mReferenceImgDao.delete(referenceImgs[0]);
            return null;
        }
    }
}
