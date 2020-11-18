package com.example.cosplan.data.Coplay;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cosplan.data.Coplay.CheckList.CheckListPartDao;
import com.example.cosplan.data.Coplay.CheckList.ChecklistPart;
import com.example.cosplan.data.Coplay.Part.Part;
import com.example.cosplan.data.Coplay.Part.PartDao;
import com.example.cosplan.data.Coplay.RefImg.ReferenceImg;
import com.example.cosplan.data.Coplay.RefImg.ReferenceImgDao;
import com.example.cosplan.data.Coplay.ShoppingList.ShoppingListPart;
import com.example.cosplan.data.Coplay.ShoppingList.ShoppingListPartDao;
import com.example.cosplan.data.Coplay.Webshop.Webshop;
import com.example.cosplan.data.Coplay.Webshop.WebshopDao;

@TypeConverters(Converters.class)
@Database(entities = {Cosplay.class, Part.class, ReferenceImg.class, Webshop.class, ChecklistPart.class, ShoppingListPart.class}, version = 2, exportSchema = false)
public abstract class CosplayDatabase extends RoomDatabase {
    public abstract CosplayDao cosplayDao();

    public abstract PartDao partDao();

    public abstract ReferenceImgDao referenceImgDao();

    public abstract WebshopDao webshopDao();

    public abstract CheckListPartDao checkListPartDao();

    public abstract ShoppingListPartDao shoppingListPartDao();
    
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
