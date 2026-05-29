package com.example.todolist.domain.usecase;

import com.example.todolist.domain.model.Note;
import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.utils.Result;

import java.util.List;

public class GetNotesUseCase {
    private final NoteRepository repository;

    public GetNotesUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    public Result<List<Note>> invoke() {
        return repository.getNotes();
    }
}
