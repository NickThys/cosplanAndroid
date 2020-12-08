package com.example.cosplan.data.cosplay.refImg;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class ReferenceImgRepository {
    private ReferenceImgDao mReferenceImgDao;
    private LiveData<List<ReferenceImg>> mAllRefImg;
    private int mCosplayId;

    ReferenceImgRepository(Application application){
        CosplayDatabase db=CosplayDatabase.getDatabase(application);
        mReferenceImgDao=db.mReferenceImgDao();
        mAllRefImg=mReferenceImgDao.getReferenceImg(mCosplayId);
    }
    LiveData<List<ReferenceImg>> getAllRefImg(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllRefImg=mReferenceImgDao.getReferenceImg(mCosplayId);
        return mAllRefImg;
    }
    public void insert(ReferenceImg referenceImg){new insertAsyncTask(mReferenceImgDao).execute(referenceImg);}

    public void delete(ReferenceImg referenceImg) {new deleteAsyncTask(mReferenceImgDao).execute(referenceImg);}

    private class insertAsyncTask extends AsyncTask<ReferenceImg,Void,Void> {
        private ReferenceImgDao dao;
        public insertAsyncTask(ReferenceImgDao mReferenceImgDao) {dao=mReferenceImgDao;}

        @Override
        protected Void doInBackground(ReferenceImg... referenceImgs) {
            dao.insert(referenceImgs[0]);
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<ReferenceImg,Void,Void>{
        ReferenceImgDao dao;
        public deleteAsyncTask(ReferenceImgDao mReferenceImgDao) {dao=mReferenceImgDao;}

        @Override
        protected Void doInBackground(ReferenceImg... referenceImgs) {
            dao.delete(referenceImgs[0]);
            return null;
        }
    }
}
