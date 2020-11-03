package com.example.cosplan.data.webshop;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Webshop.class,version = 1, exportSchema = false)
abstract class WebshopDatabase extends RoomDatabase {
    public abstract  WebshopDao webshopDao();
    private static WebshopDatabase INSTANCE;
    public static WebshopDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (WebshopDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),WebshopDatabase.class,"webshopDatabase").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback=new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new Populatedb(INSTANCE).execute();
        }
    };
    private static class Populatedb extends AsyncTask<Void,Void,Void>{
        private final WebshopDao mdao;
        Populatedb(WebshopDatabase db){mdao=db.webshopDao();}
        String []WebshopName={"Cosplan","Cosplayshop","Facts","Comic con brussels","Comic Con gent","Comic con antwerpen","Brainfreeze","Dutch comic con","German Comic con"};
        String []WebshopLink={"www.cosplan.be","www.cosplayShop.be","www.Facts.be","www.Comicconbrussels.be","www.comiccongent.be","www.comicconantwerpen.be","www.Brainfreeze.be","www.dutchcomiccon.nl","www.germancomiccon.de"};
        @Override
        protected Void doInBackground(Void... voids) {
            if (mdao.getAnyWebshop().length<1){
                for (int i=0;i<WebshopName.length;i++){
                    Webshop temp=new Webshop();
                    temp.mId=0;
                    temp.mSiteName=WebshopName[i];
                    temp.mSiteLink=WebshopLink[i];
                    mdao.insert(temp);
                }
            }
            return null;
        }
    }
}
