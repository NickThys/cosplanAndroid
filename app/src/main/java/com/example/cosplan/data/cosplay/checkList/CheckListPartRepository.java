package com.example.cosplan.data.cosplay.checkList;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class CheckListPartRepository {
    private final CheckListPartDao mCheckListPartDao;
    private LiveData<List<ChecklistPart>> mAllCheckListParts;
    private int mCosplayId;

    public CheckListPartRepository(Application application) {
        CosplayDatabase mCosplayDatabase = CosplayDatabase.getDatabase(application);
        mCheckListPartDao = mCosplayDatabase.mCheckListPartDao();
        mAllCheckListParts = mCheckListPartDao.getAllCheckListParts(mCosplayId);
    }

    LiveData<List<ChecklistPart>> getAllCheckListParts(int mCosplayId) {
        this.mCosplayId = mCosplayId;
        mAllCheckListParts = mCheckListPartDao.getAllCheckListParts(mCosplayId);
        return mAllCheckListParts;
    }

    public void insert(ChecklistPart checklistPart) {
        new insertAsyncTask(mCheckListPartDao).execute(checklistPart);
    }

    public void update(ChecklistPart checklistPart) {
        new updateAsyncTask(mCheckListPartDao).execute(checklistPart);
    }

    public void delete(ChecklistPart checklistPart) {
        new deleteAsyncTask(mCheckListPartDao).execute(checklistPart);
    }

    private static class insertAsyncTask extends AsyncTask<ChecklistPart, Void, Void> {
        private final CheckListPartDao mCheckListDao;

        public insertAsyncTask(CheckListPartDao mCheckListDao) {
            this.mCheckListDao = mCheckListDao;
        }


        @Override
        protected Void doInBackground(ChecklistPart... checklistParts) {
            mCheckListDao.insert(checklistParts[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<ChecklistPart, Void, Void> {
        private final CheckListPartDao mCheckListDao;

        public updateAsyncTask(CheckListPartDao mCheckListDao) {
            this.mCheckListDao = mCheckListDao;
        }


        @Override
        protected Void doInBackground(ChecklistPart... checklistParts) {
            mCheckListDao.update(checklistParts[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<ChecklistPart, Void, Void> {
        private final CheckListPartDao mCheckListDao;

        public deleteAsyncTask(CheckListPartDao mCheckListDao) {
            this.mCheckListDao = mCheckListDao;
        }

        @Override
        protected Void doInBackground(ChecklistPart... checklistParts) {
            mCheckListDao.delete(checklistParts[0]);
            return null;
        }
    }

}
