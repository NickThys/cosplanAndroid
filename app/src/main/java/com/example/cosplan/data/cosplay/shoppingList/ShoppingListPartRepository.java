package com.example.cosplan.data.cosplay.shoppingList;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cosplan.data.cosplay.CosplayDatabase;

import java.util.List;

public class ShoppingListPartRepository {
    private ShoppingListPartDao mShoppingListPartDao;
    private LiveData<List<ShoppingListPart>> mAllShoppingListParts;
    private LiveData<List<String>> mAllShoppingListShops;
    private int mCosplayId;
    private String mShopName;

    public ShoppingListPartRepository(Application application){
        CosplayDatabase db=CosplayDatabase.getDatabase(application);
        mShoppingListPartDao=db.mShoppingListPartDao();
        mAllShoppingListParts=mShoppingListPartDao.getAllShoppingListPartsFromShop(mCosplayId);
       // mAllShoppingListShops=mShoppingListPartDao.getAllNamesFromStores(mCosplayId);
    }
    LiveData<List<ShoppingListPart>> getAllShoppingListParts(int mCosplayId){
        this.mCosplayId=mCosplayId;
        this.mShopName=mShopName;
        mAllShoppingListParts=mShoppingListPartDao.getAllShoppingListPartsFromShop(mCosplayId);
        return mAllShoppingListParts;
    }
  /*  LiveData<List<String>> getAllShoppingListShops(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllShoppingListShops=mShoppingListPartDao.getAllNamesFromStores(mCosplayId);
        return mAllShoppingListShops;
    }*/
    public void insert(ShoppingListPart shoppingListPart){new ShoppingListPartRepository.insertAsyncTask(mShoppingListPartDao).execute(shoppingListPart);}
    public void delete(ShoppingListPart shoppingListPart){new ShoppingListPartRepository.deleteAsyncTask(mShoppingListPartDao).execute(shoppingListPart);}
    public void update(ShoppingListPart shoppingListPart){new ShoppingListPartRepository.updateAsyncTask(mShoppingListPartDao).execute(shoppingListPart);}
    public void deleteAll(ShoppingListPart shoppingListPart){new ShoppingListPartRepository.DeleteAllAsyncTask(mShoppingListPartDao).execute(shoppingListPart);}
    public class insertAsyncTask extends AsyncTask<ShoppingListPart,Void,Void> {
        private ShoppingListPartDao dao;
        public insertAsyncTask(ShoppingListPartDao mShoppingListPartDao) {dao=mShoppingListPartDao;}


        @Override
        protected Void doInBackground(ShoppingListPart... shoppingListParts) {
            dao.insert(shoppingListParts[0]);
            return null;
        }
    }

    public class deleteAsyncTask extends AsyncTask<ShoppingListPart,Void,Void> {
        private ShoppingListPartDao dao;
        public deleteAsyncTask(ShoppingListPartDao mShoppingListPartDao) {dao=mShoppingListPartDao;}


        @Override
        protected Void doInBackground(ShoppingListPart... shoppingListParts) {
            dao.delete(shoppingListParts[0]);
            return null;
        }
    }

    public class updateAsyncTask extends AsyncTask<ShoppingListPart,Void,Void> {
        private ShoppingListPartDao dao;
        public updateAsyncTask(ShoppingListPartDao mShoppingListPartDao) {dao=mShoppingListPartDao;}


        @Override
        protected Void doInBackground(ShoppingListPart... shoppingListParts) {
            dao.update(shoppingListParts[0]);
            return null;
        }
    }
    public class DeleteAllAsyncTask extends AsyncTask<ShoppingListPart,Void,Void> {
        private ShoppingListPartDao dao;
        public DeleteAllAsyncTask(ShoppingListPartDao mShoppingListPartDao) {dao=mShoppingListPartDao;}


        @Override
        protected Void doInBackground(ShoppingListPart... shoppingListParts) {
            dao.deleteAll(shoppingListParts[0].mCosplayId);
            return null;
        }
    }

}
