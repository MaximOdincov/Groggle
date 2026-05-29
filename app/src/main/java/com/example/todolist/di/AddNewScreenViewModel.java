package com.example.todolist.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolist.data.local.NoteDatabase;
import com.example.todolist.data.mapper.NoteMapper;
import com.example.todolist.data.repository.NoteRepositoryImpl;
import com.example.todolist.domain.model.Note;
import com.example.todolist.domain.usecase.AddNoteUseCase;
import com.example.todolist.domain.repository.NoteRepository;

public class AddNewScreenViewModel extends AndroidViewModel {

    private final AddNoteUseCase addNoteUseCase;
    private final MutableLiveData<Boolean> shouldCloseActivity = new MutableLiveData<>();

    public AddNewScreenViewModel(@NonNull Application application) {
        super(application);

        NoteDatabase database = NoteDatabase.getInstance(application);
        NoteRepository repository = new NoteRepositoryImpl(database);
        this.addNoteUseCase = new AddNoteUseCase(repository);
    }

    public void saveNote(Note note) {
        addNoteUseCase.invoke(note);
        shouldCloseActivity.postValue(true);
    }

    public LiveData<Boolean> getShouldCloseActivity() {
        return shouldCloseActivity;
    }
}
