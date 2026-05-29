package com.example.todolist.domain.repository;

import com.example.todolist.domain.model.Note;
import com.example.todolist.domain.utils.Result;

import java.util.List;

public interface NoteRepository {
    List<Note> getNotes();
    Result<Void> addNote(Note note);
    Result<Void> removeNote(int id);
    Result<Void> updateNotePrice(int id, double priceRub);
    Result<Void> updateNoteStatus(int id, boolean bought);
    Result<Void> updateNoteText(int id, String text);
    Result<Void> clearAllNotes();
}
