package com.example.todolist.domain.usecase;

import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.utils.Result;

public class ClearAllNotesUseCase {
    private final NoteRepository repository;

    public ClearAllNotesUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    public Result<Void> invoke() {
        return repository.clearAllNotes();
    }
}
