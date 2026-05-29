package com.example.todolist.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes")
    LiveData<List<NoteEntity>> getNotes();

    @Insert
    void add(NoteEntity note);

    @Query("DELETE FROM notes WHERE id = :id")
    void remove(int id);

    @Query("UPDATE notes SET priceRub = :priceRub WHERE id = :id")
    void updatePrice(int id, double priceRub);

    @Query("UPDATE notes SET bought = :bought WHERE id = :id")
    void updateBought(int id, boolean bought);

    @Query("UPDATE notes SET text = :text WHERE id = :id")
    void updateText(int id, String text);

    @Query("DELETE FROM notes")
    void clearAll();
}
