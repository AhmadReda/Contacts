package com.example.contacts;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = ContactData.class, version = 1)
@TypeConverters(ConverterTypes.class)
public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;
    public abstract ContactDAO contactDAO();

    public static ContactDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ContactDatabase.class,
                       "contact_database")
                       .fallbackToDestructiveMigration()
                       .build();

        }
        return instance;
    }
}
