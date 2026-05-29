package com.example.todolist.domain.usecase;

import com.example.todolist.domain.model.Note;
import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.utils.Result;

public class AddNoteUseCase {
    private final NoteRepository repository;

    public AddNoteUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    public Result<Void> invoke(Note note) {
        return repository.addNote(note);
    }
}
