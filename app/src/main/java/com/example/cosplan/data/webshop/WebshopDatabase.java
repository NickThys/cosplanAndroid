package com.example.cosplan.data.webshop;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Webshop.class,version = 1, exportSchema = false)
abstract class WebshopDatabase extends RoomDatabase {
    public abstract  WebshopDao webshopDao();
    private static WebshopDatabase INSTANCE;
    public static WebshopDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (WebshopDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),WebshopDatabase.class,"webshopDatabase").build();
                }
            }
        }
        return INSTANCE;
    }
}
