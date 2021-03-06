package com.leothosthoren.realestatemanager.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.leothosthoren.realestatemanager.database.converter.DateConverter;
import com.leothosthoren.realestatemanager.database.converter.TypeConverter;
import com.leothosthoren.realestatemanager.database.dao.RealEstateDao;
import com.leothosthoren.realestatemanager.database.dao.UserDao;
import com.leothosthoren.realestatemanager.entities.RealEstate;
import com.leothosthoren.realestatemanager.entities.User;

@Database(entities = {RealEstate.class, User.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, TypeConverter.class})
public abstract class RealEstateDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile RealEstateDatabase INSTANCE;

    // --- DAO ---
    public abstract RealEstateDao realEstateDao();

    public abstract UserDao userDao();

    // --- INSTANCE ---
    public static RealEstateDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateDatabase.class, "REMDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase() {
        return new Callback(){

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("username", "Sofiane");
                contentValues.put("urlPicture","http://oc-" +
     "user.imgix.net/users/avatars/15262853029714_Photo_profil_Linkedin.jpg");

                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
