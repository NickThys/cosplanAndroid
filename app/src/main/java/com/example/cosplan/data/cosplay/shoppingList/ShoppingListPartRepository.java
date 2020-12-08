package com.example.cosplan.data.cosplay.shoppingList;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class ShoppingListPartRepository {
    private final ShoppingListPartDao mShoppingListPartDao;
    private LiveData<List<ShoppingListPart>> mAllShoppingListParts;
    private int mCosplayId;

    public ShoppingListPartRepository(Application application) {
        CosplayDatabase mCosplayDatabase = CosplayDatabase.getDatabase(application);
        mShoppingListPartDao = mCosplayDatabase.mShoppingListPartDao();
        mAllShoppingListParts = mShoppingListPartDao.getAllShoppingListPartsFromShop(mCosplayId);
    }

    LiveData<List<ShoppingListPart>> getAllShoppingListParts(int mCosplayId) {
        this.mCosplayId = mCosplayId;
        mAllShoppingListParts = mShoppingListPartDao.getAllShoppingListPartsFromShop(mCosplayId);
        return mAllShoppingListParts;
    }

    public void insert(ShoppingListPart shoppingListPart) {
        new insertAsyncTask(mShoppingListPartDao).execute(shoppingListPart);
    }

    public void delete(ShoppingListPart shoppingListPart) {
        new deleteAsyncTask(mShoppingListPartDao).execute(shoppingListPart);
    }

    public void update(ShoppingListPart shoppingListPart) {
        new updateAsyncTask(mShoppingListPartDao).execute(shoppingListPart);
    }

    public void deleteAll(ShoppingListPart shoppingListPart) {
        new DeleteAllAsyncTask(mShoppingListPartDao).execute(shoppingListPart);
    }

    public static class insertAsyncTask extends AsyncTask<ShoppingListPart, Void, Void> {
        private final ShoppingListPartDao mShoppingListPartDao;

        public insertAsyncTask(ShoppingListPartDao mShoppingListPartDao) {
            this.mShoppingListPartDao = mShoppingListPartDao;
        }


        @Override
        protected Void doInBackground(ShoppingListPart... shoppingListParts) {
            mShoppingListPartDao.insert(shoppingListParts[0]);
            return null;
        }
    }

    public static class deleteAsyncTask extends AsyncTask<ShoppingListPart, Void, Void> {
        private final ShoppingListPartDao mShoppingListPartDao;

        public deleteAsyncTask(ShoppingListPartDao mShoppingListPartDao) {
            this.mShoppingListPartDao = mShoppingListPartDao;
        }


        @Override
        protected Void doInBackground(ShoppingListPart... shoppingListParts) {
            mShoppingListPartDao.delete(shoppingListParts[0]);
            return null;
        }
    }

    public static class updateAsyncTask extends AsyncTask<ShoppingListPart, Void, Void> {
        private final ShoppingListPartDao mShoppingListPartDao;

        public updateAsyncTask(ShoppingListPartDao mShoppingListPartDao) {
            this.mShoppingListPartDao = mShoppingListPartDao;
        }


        @Override
        protected Void doInBackground(ShoppingListPart... shoppingListParts) {
            mShoppingListPartDao.update(shoppingListParts[0]);
            return null;
        }
    }

    public static class DeleteAllAsyncTask extends AsyncTask<ShoppingListPart, Void, Void> {
        private final ShoppingListPartDao mShoppingListPartDao;

        public DeleteAllAsyncTask(ShoppingListPartDao mShoppingListPartDao) {
            this.mShoppingListPartDao = mShoppingListPartDao;
        }


        @Override
        protected Void doInBackground(ShoppingListPart... shoppingListParts) {
            mShoppingListPartDao.deleteAll(shoppingListParts[0].mCosplayId);
            return null;
        }
    }

}
