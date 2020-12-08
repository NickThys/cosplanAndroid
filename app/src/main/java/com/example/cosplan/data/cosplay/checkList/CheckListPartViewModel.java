package com.example.cosplan.data.cosplay.checkList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CheckListPartViewModel extends AndroidViewModel {
    private final CheckListPartRepository mCheckListPartRepository;
    LiveData<List<ChecklistPart>> mAllCheckListParts;
    private int mCosplayId;

    public CheckListPartViewModel(@NonNull Application application) {
        super(application);
        mCheckListPartRepository =new CheckListPartRepository(application);
        mAllCheckListParts= mCheckListPartRepository.getAllCheckListParts(mCosplayId);
    }

    public LiveData<List<ChecklistPart>> getAllCheckListParts(int mCosplayId){
        this.mCosplayId=mCosplayId;
        mAllCheckListParts= mCheckListPartRepository.getAllCheckListParts(mCosplayId);
        return mAllCheckListParts;
    }
    public void insert(ChecklistPart checklistPart){
        mCheckListPartRepository.insert(checklistPart);}
    public void delete(ChecklistPart checklistPart){
        mCheckListPartRepository.delete(checklistPart);}
    public void update(ChecklistPart checklistPart){
        mCheckListPartRepository.update(checklistPart);}
}
