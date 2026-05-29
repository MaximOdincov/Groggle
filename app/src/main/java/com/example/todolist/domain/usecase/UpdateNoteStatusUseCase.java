package com.example.todolist.domain.usecase;

import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.utils.Result;

public class UpdateNoteStatusUseCase {
    private final NoteRepository repository;

    public UpdateNoteStatusUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    public Result<Void> invoke(int id, boolean bought) {
        return repository.updateNoteStatus(id, bought);
    }
}
