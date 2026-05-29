package com.example.todolist.domain.usecase;

import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.utils.Result;

public class RemoveNoteUseCase {
    private final NoteRepository repository;

    public RemoveNoteUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    public Result<Void> invoke(int id) {
        return repository.removeNote(id);
    }
}
