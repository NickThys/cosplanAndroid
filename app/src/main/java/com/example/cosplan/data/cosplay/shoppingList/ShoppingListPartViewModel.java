package com.example.cosplan.data.cosplay.shoppingList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingListPartViewModel extends AndroidViewModel {
    private final ShoppingListPartRepository mShoppingListPartRepository;
    private LiveData<List<ShoppingListPart>> mAllShoppingListParts;
    private int mCosplayId;


    public ShoppingListPartViewModel(@NonNull Application application) {
        super(application);
        mShoppingListPartRepository = new ShoppingListPartRepository(application);
        mAllShoppingListParts = mShoppingListPartRepository.getAllShoppingListParts(mCosplayId);
    }

    public LiveData<List<ShoppingListPart>> getAllShoppingListParts(int mCosplayId) {
        this.mCosplayId = mCosplayId;
        mAllShoppingListParts = mShoppingListPartRepository.getAllShoppingListParts(mCosplayId);
        return mAllShoppingListParts;
    }

    public void insert(ShoppingListPart shoppingListPart) {
        mShoppingListPartRepository.insert(shoppingListPart);
    }

    public void delete(ShoppingListPart shoppingListPart) {
        mShoppingListPartRepository.delete(shoppingListPart);
    }

    public void update(ShoppingListPart shoppingListPart) {
        mShoppingListPartRepository.update(shoppingListPart);
    }

    public void deleteAll(ShoppingListPart shoppingListPart) {
        mShoppingListPartRepository.deleteAll(shoppingListPart);
    }


}
