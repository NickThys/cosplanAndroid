package com.example.cosplan.data.Coplay.ShoppingList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingListPartViewModel extends AndroidViewModel {
    private ShoppingListPartRepository mRepository;
    private LiveData<List<ShoppingListPart>> mAllShoppingListParts;
    private LiveData<List<String>> mAllShoppingListShops;
    private int mCosplayId;
    private String mShopName;

    public ShoppingListPartViewModel(@NonNull Application application) {
        super(application);
        mRepository=new ShoppingListPartRepository(application);
        mAllShoppingListParts=mRepository.getAllShoppingListParts(mCosplayId);
        mAllShoppingListShops=mRepository.getAllShoppingListShops(mCosplayId);
    }
    public LiveData<List<ShoppingListPart>> getAllShoppingListParts(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllShoppingListParts=mRepository.getAllShoppingListParts(mCosplayId);
        return mAllShoppingListParts;
    }
    public LiveData<List<String>>getAllShoppingListShops(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllShoppingListShops=mRepository.getAllShoppingListShops(mCosplayId);
        return mAllShoppingListShops;
    }
    public void insert(ShoppingListPart shoppingListPart){mRepository.insert(shoppingListPart);}
    public void delete(ShoppingListPart shoppingListPart){mRepository.delete(shoppingListPart);}
    public void update(ShoppingListPart shoppingListPart){mRepository.update(shoppingListPart);}




}
