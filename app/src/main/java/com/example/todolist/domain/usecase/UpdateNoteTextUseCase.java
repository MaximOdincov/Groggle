package com.example.todolist.domain.usecase;

import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.utils.Result;

public class UpdateNoteTextUseCase {
    private final NoteRepository repository;

    public UpdateNoteTextUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    public Result<Void> invoke(int id, String text) {
        return repository.updateNoteText(id, text);
    }
}
