package com.example.cosplan.data.cosplay.CheckList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CheckListPartViewModel extends AndroidViewModel {
    private CheckListPartRepository mRepository;
    LiveData<List<ChecklistPart>> mAllCheckListParts;
    private int mCosplayId;

    public CheckListPartViewModel(@NonNull Application application) {
        super(application);
        mRepository=new CheckListPartRepository(application);
        mAllCheckListParts=mRepository.getAllCheckListParts(mCosplayId);
    }

    public LiveData<List<ChecklistPart>> getAllCheckListParts(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllCheckListParts=mRepository.getAllCheckListParts(mCosplayId);
        return mAllCheckListParts;
    }
    public void insert(ChecklistPart checklistPart){mRepository.insert(checklistPart);}
    public void delete(ChecklistPart checklistPart){mRepository.delete(checklistPart);}
    public void update(ChecklistPart checklistPart){mRepository.update(checklistPart);}
}
