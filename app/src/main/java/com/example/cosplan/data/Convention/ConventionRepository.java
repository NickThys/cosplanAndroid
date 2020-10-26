package com.example.cosplan.data.Convention;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ConventionRepository {
    private ConventionDao mConventionDao;
    private LiveData<List<Convention>>mAllConventionsBelgium;
    private LiveData<List<Convention>>mAllConventionsNetherland;
    ConventionRepository(Application application){
        ConventionDatabase db=ConventionDatabase.getDatabase(application);
        mConventionDao=db.conventionDao();
        mAllConventionsBelgium=mConventionDao.getAllConventionsBelgium();
        mAllConventionsNetherland=mConventionDao.getAllConventionsNetherland();
    }
    LiveData<List<Convention>>getAllConventionsBelgium(){return mAllConventionsBelgium;}
    LiveData<List<Convention>>getAllConventionsNetherland(){return mAllConventionsNetherland;}




}
