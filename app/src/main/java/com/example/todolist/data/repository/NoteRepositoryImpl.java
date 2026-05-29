package com.example.todolist.data.repository;

import com.example.todolist.data.local.NoteDatabase;
import com.example.todolist.data.mapper.NoteMapper;
import com.example.todolist.domain.model.Note;
import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.utils.Result;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepositoryImpl implements NoteRepository {
    private final NoteDatabase database;
    private final ExecutorService executor;

    public NoteRepositoryImpl(NoteDatabase database) {
        this.database = database;
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public List<Note> getNotes() {
        List<com.example.todolist.data.local.NoteEntity> entities = database.noteDao().getNotes().getValue();
        return entities != null ? NoteMapper.toDomainList(entities) : null;
    }

    @Override
    public Result<Void> addNote(Note note) {
        executor.execute(() -> {
            database.noteDao().add(NoteMapper.toEntity(note));
        });
        return Result.success(null);
    }

    @Override
    public Result<Void> removeNote(int id) {
        executor.execute(() -> {
            database.noteDao().remove(id);
        });
        return Result.success(null);
    }

    @Override
    public Result<Void> updateNotePrice(int id, double priceRub) {
        executor.execute(() -> {
            database.noteDao().updatePrice(id, priceRub);
        });
        return Result.success(null);
    }

    @Override
    public Result<Void> updateNoteStatus(int id, boolean bought) {
        executor.execute(() -> {
            database.noteDao().updateBought(id, bought);
        });
        return Result.success(null);
    }

    @Override
    public Result<Void> updateNoteText(int id, String text) {
        executor.execute(() -> {
            database.noteDao().updateText(id, text);
        });
        return Result.success(null);
    }

    @Override
    public Result<Void> clearAllNotes() {
        executor.execute(() -> {
            database.noteDao().clearAll();
        });
        return Result.success(null);
    }
}
