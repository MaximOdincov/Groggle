package com.example.todolist.data.local;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NoteEntity.class, CurrencyRateEntity.class}, version = 3, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance = null;

    public static synchronized NoteDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application, NoteDatabase.class, "DBNotes.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract NoteDao noteDao();
    public abstract CurrencyRateDao currencyRateDao();
}
