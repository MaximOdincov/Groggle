package com.example.todolist.domain.usecase;

import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.utils.Result;

public class UpdateNotePriceUseCase {
    private final NoteRepository repository;

    public UpdateNotePriceUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    public Result<Void> invoke(int id, double priceRub) {
        return repository.updateNotePrice(id, priceRub);
    }
}
