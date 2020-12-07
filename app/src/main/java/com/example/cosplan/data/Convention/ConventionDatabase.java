package com.example.cosplan.data.Convention;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Convention.class,version = 1,exportSchema = false)
abstract class ConventionDatabase extends RoomDatabase {
    public abstract ConventionDao mConventionDao();
    private static ConventionDatabase INSTANCE;
    public static ConventionDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (ConventionDatabase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),ConventionDatabase.class,"conventionDatabase").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback sRoomDatabaseCallback=new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDb(INSTANCE).execute();
        }
    };

    private static class PopulateDb extends AsyncTask<Void,Void,Void>{
        private final ConventionDao mConventiondao;
        PopulateDb(ConventionDatabase mConventionDatabase){
            mConventiondao = mConventionDatabase.mConventionDao();}
        //region Convention data
        private final String [] ConventionCountry ={"Belgium","Netherland"};

        private final String [] ConventionNamesBelgium ={"Comic Con Brussels","Facts Spring","Made in Asia","Comic Con Antwerp","Elftopia"};
        private final String [] ConventionStartDateBelgium ={"13/02/2021","3/04/2021","12/03/2021","05/05/2021","08/08/2021"};
        private final String [] ConventionEndDateBelgium ={"14/02/2021","4/04/2021","14/03/2021","06/05/2021","09/08/2021"};
        private final String [] ConventionPlaceBelgium ={"Tour & Taxis","Flanders Expo","Brussels Expo","Waagnatie","kasteel Ooidonk"};

        private final String [] ConventionNamesNetherland ={"Comic Con Ahoy","Dutch Comic Con","Magical Castle Event","Comic Con Amsterdam"};
        private final String [] ConventionStartDateNetherland ={"27/02/2021","27/03/2021","29/50/2021","28/08/2021"};
        private final String [] ConventionEndDateNetherland ={"28/02/2021","28/03/2021","30/05/2021","29/08/2021"};
        private final String [] ConventionPlaceNetherland ={"Ahoy rotterdam","Jaarbeurs Utrecht","Park Spelderholt","RAI Amsterdam"};
        //endregion

        @Override
        protected Void doInBackground(Void... voids) {
            if (mConventiondao.getAnyConvention().length<1){
                for (int i = 0; i< ConventionNamesBelgium.length; i++){
                    Convention mTempConventionBelgium=new Convention();
                    mTempConventionBelgium.mConventionName = ConventionNamesBelgium[i];
                    mTempConventionBelgium.mConventionCountry = ConventionCountry[0];
                    mTempConventionBelgium.mConventionBeginDate = ConventionStartDateBelgium[i];
                    mTempConventionBelgium.mConventionEndDate = ConventionEndDateBelgium[i];
                    mTempConventionBelgium.mConventionPlace = ConventionPlaceBelgium[i];
                    mConventiondao.insert(mTempConventionBelgium);
                }
                for (int i = 0; i< ConventionNamesNetherland.length; i++){
                    Convention mTempConventionNetherland=new Convention();
                    mTempConventionNetherland.mConventionName = ConventionNamesNetherland[i];
                    mTempConventionNetherland.mConventionCountry = ConventionCountry[1];
                    mTempConventionNetherland.mConventionBeginDate = ConventionStartDateNetherland[i];
                    mTempConventionNetherland.mConventionEndDate = ConventionEndDateNetherland[i];
                    mTempConventionNetherland.mConventionPlace = ConventionPlaceNetherland[i];
                    mConventiondao.insert(mTempConventionNetherland);
                }
            }
            return null;
        }
    }
}
