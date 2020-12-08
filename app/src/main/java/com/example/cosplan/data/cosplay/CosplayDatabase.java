package com.example.cosplan.data.cosplay;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cosplan.data.cosplay.checkList.CheckListPartDao;
import com.example.cosplan.data.cosplay.checkList.ChecklistPart;
import com.example.cosplan.data.cosplay.events.Event;
import com.example.cosplan.data.cosplay.events.EventDao;
import com.example.cosplan.data.cosplay.Part.Part;
import com.example.cosplan.data.cosplay.Part.PartDao;
import com.example.cosplan.data.cosplay.RefImg.ReferenceImg;
import com.example.cosplan.data.cosplay.RefImg.ReferenceImgDao;
import com.example.cosplan.data.cosplay.ShoppingList.ShoppingListPart;
import com.example.cosplan.data.cosplay.ShoppingList.ShoppingListPartDao;
import com.example.cosplan.data.cosplay.WIPImg.WIPImg;
import com.example.cosplan.data.cosplay.WIPImg.WIPImgDao;
import com.example.cosplan.data.cosplay.Webshop.Webshop;
import com.example.cosplan.data.cosplay.Webshop.WebshopDao;

@TypeConverters(Converters.class)
@Database(entities = {Cosplay.class, Part.class, ReferenceImg.class, Webshop.class, ChecklistPart.class, ShoppingListPart.class, WIPImg.class, Event.class}, version = 1)
public abstract class CosplayDatabase extends RoomDatabase {
    public abstract CosplayDao mCosplayDao();

    public abstract PartDao mPartDao();

    public abstract ReferenceImgDao mReferenceImgDao();

    public abstract WebshopDao mWebshopDao();

    public abstract CheckListPartDao mCheckListPartDao();

    public abstract ShoppingListPartDao mShoppingListPartDao();

    public abstract WIPImgDao mWipImgDao();

    public abstract EventDao mEventDao();

    private static CosplayDatabase INSTANCE;

    public static CosplayDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CosplayDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CosplayDatabase.class, "cosplayDatabase").build();
                }
            }
        }
        return INSTANCE;
    }

}
