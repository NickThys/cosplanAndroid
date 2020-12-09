package com.example.cosplan.data.cosplay;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cosplan.data.cosplay.checkList.CheckListPartDao;
import com.example.cosplan.data.cosplay.checkList.ChecklistPart;
import com.example.cosplan.data.cosplay.events.Event;
import com.example.cosplan.data.cosplay.events.EventDao;
import com.example.cosplan.data.cosplay.part.Part;
import com.example.cosplan.data.cosplay.part.PartDao;
import com.example.cosplan.data.cosplay.refImg.ReferenceImg;
import com.example.cosplan.data.cosplay.refImg.ReferenceImgDao;
import com.example.cosplan.data.cosplay.shoppingList.ShoppingListPart;
import com.example.cosplan.data.cosplay.shoppingList.ShoppingListPartDao;
import com.example.cosplan.data.cosplay.wIPImg.WIPImg;
import com.example.cosplan.data.cosplay.wIPImg.WIPImgDao;
import com.example.cosplan.data.cosplay.webshop.Webshop;
import com.example.cosplan.data.cosplay.webshop.WebshopDao;

@TypeConverters(Converters.class)
@Database(entities = {Cosplay.class, Part.class, ReferenceImg.class, Webshop.class, ChecklistPart.class, ShoppingListPart.class, WIPImg.class, Event.class},version = 2)
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
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CosplayDatabase.class, "cosplayDatabase").addMigrations(MIGRATION_1_2).build();
                }
            }
        }
        return INSTANCE;
    }
    static final Migration MIGRATION_1_2=new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE INDEX index_checklist_part ON  CosplayChecklist_table(CosplayId)");
            database.execSQL("CREATE INDEX index_event ON  CosplayEvent_table(CosplayId)");
            database.execSQL("CREATE INDEX index_part ON  CosplayPart_table(CosplayId)");
            database.execSQL("CREATE INDEX index_reference_img ON  CosplayRefImg_table(CosplayId)");
            database.execSQL("CREATE INDEX index_shoppinglist_part ON  CosplayShoppingList_table(CosplayId)");
            database.execSQL("CREATE INDEX index_webshop ON  CosplayWebshop_table(CosplayId)");
            database.execSQL("CREATE INDEX index_wip_img ON  CosplayWIPImg_table(CosplayId)");

        }
    };

}
