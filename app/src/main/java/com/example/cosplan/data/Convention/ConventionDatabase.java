package com.example.cosplan.data.Convention;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cosplan.data.WebshopDao;

import javax.xml.validation.SchemaFactoryLoader;

@Database(entities = Convention.class,version = 1,exportSchema = false)
abstract class ConventionDatabase extends RoomDatabase {
    public abstract ConventionDao conventionDao();
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
    private static RoomDatabase.Callback sRoomDatabaseCallback=new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDb(INSTANCE).execute();
        }
    };

    private static class PopulateDb extends AsyncTask<Void,Void,Void>{
        private final ConventionDao mdao;
        PopulateDb(ConventionDatabase db){mdao= db.conventionDao();}
<<<<<<< Updated upstream
        String []ConNamesBel={"Comic Con Brussels","Facts Spring"};
        String []ConCountry={"Belgium","Netherland"};
        String []ConStartDateBel={"13/02/2021","3/04/2021"};
        String []ConEndDateBel={"14/02/2021","4/04/2021"};
        String []ConPlaceBel={"Tour & Taxis","Flanders Expo"};
=======
        String []ConNamesBel={"Comic Con Brussels","Facts Spring","Made in Asia","Comic Con Antwerp","Elftopia"};
        String []ConCountry={"Belgium","Netherland"};
        String []ConStartDateBel={"13/02/2021","3/04/2021","12/03/2021","05/05/2021","08/08/2021"};
        String []ConEndDateBel={"14/02/2021","4/04/2021","14/03/2021","06/05/2021","09/08/2021"};
        String []ConPlaceBel={"Tour & Taxis","Flanders Expo","Brussels Expo","Waagnatie","kasteel Ooidonk"};

        String []ConNamesNl={"Comic Con Ahoy","Dutch Comic Con","Magical Castle Event","Comic Con Amsterdam"};
        String []ConStartDateNl={"27/02/2021","27/03/2021","29/50/2021","28/08/2021"};
        String []ConEndDateNl={"28/02/2021","28/03/2021","30/05/2021","29/08/2021"};
        String []ConPlaceNl={"Ahoy rotterdam","Jaarbeurs Utrecht","Park Spelderholt","RAI Amsterdam"};
>>>>>>> Stashed changes

        @Override
        protected Void doInBackground(Void... voids) {
            if (mdao.getAnyConvention().length<1){
                for (int i=0;i<ConNamesBel.length;i++){
                    Convention con=new Convention();
                    con.mConName=ConNamesBel[i];
                    con.mConCountry=ConCountry[0];
                    con.mConBeginDate=ConStartDateBel[i];
                    con.mConEndDate=ConEndDateBel[i];
                    con.mConPlace=ConPlaceBel[i];
                    mdao.insert(con);
                }
            }
            return null;
        }
    }
}
