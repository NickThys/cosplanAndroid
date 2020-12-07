package com.example.cosplan.data.cosplay.CheckList;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class CheckListPartRepository {
    private CheckListPartDao mCheckListPartDao;
    private LiveData<List<ChecklistPart>> mAllCheckListParts;
    private int mCosplayId;

    public CheckListPartRepository(Application application){
        CosplayDatabase db=CosplayDatabase.getDatabase(application);
        mCheckListPartDao=db.checkListPartDao();
        mAllCheckListParts=mCheckListPartDao.getAllCheckListParts(mCosplayId);
    }

    LiveData<List<ChecklistPart>> getAllCheckListParts(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllCheckListParts=mCheckListPartDao.getAllCheckListParts(mCosplayId);
        return mAllCheckListParts;
    }

    public void insert(ChecklistPart checklistPart){new CheckListPartRepository.insertAsyncTask(mCheckListPartDao).execute(checklistPart);}
    public void update(ChecklistPart checklistPart){new CheckListPartRepository.updateAsyncTask(mCheckListPartDao).execute(checklistPart);}
    public void delete(ChecklistPart checklistPart){new CheckListPartRepository.deleteAsyncTask(mCheckListPartDao).execute(checklistPart);}

    private class insertAsyncTask extends AsyncTask<ChecklistPart,Void,Void> {
        private CheckListPartDao dao;
        public insertAsyncTask(CheckListPartDao mCheckListDao) {dao=mCheckListDao;}


        @Override
        protected Void doInBackground(ChecklistPart... checklistParts) {
            dao.insert(checklistParts[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<ChecklistPart,Void,Void> {
        private CheckListPartDao dao;
        public updateAsyncTask(CheckListPartDao mCheckListDao) {dao=mCheckListDao;}


        @Override
        protected Void doInBackground(ChecklistPart... checklistParts) {
            dao.update(checklistParts[0]);
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<ChecklistPart,Void,Void> {
        private CheckListPartDao dao;
        public deleteAsyncTask(CheckListPartDao mCheckListDao)  {dao=mCheckListDao;}

        @Override
        protected Void doInBackground(ChecklistPart... checklistParts) {
            dao.delete(checklistParts[0]);
            return null;
        }
    }

}
