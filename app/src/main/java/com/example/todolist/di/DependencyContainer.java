package com.example.todolist.di;

import android.app.Application;

import com.example.todolist.data.local.NoteDatabase;
import com.example.todolist.data.mapper.NoteMapper;
import com.example.todolist.data.repository.CurrencyRateRepositoryImpl;
import com.example.todolist.data.repository.NoteRepositoryImpl;
import com.example.todolist.domain.repository.CurrencyRateRepository;
import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.usecase.*;
import com.example.todolist.presentation.main.AppSettings;
import com.example.todolist.presentation.main.MainViewModel;
import com.example.todolist.presentation.addnote.AddNewScreenViewModel;

/**
 * DI контейнер для проекта (ручная инъекция зависимостей)
 */
public class DependencyContainer {
    private static DependencyContainer instance;
    private final Application application;
    
    private NoteDatabase noteDatabase;
    private NoteRepository noteRepository;
    private CurrencyRateRepository currencyRateRepository;
    
    private MainViewModel mainViewModel;
    private AddNewScreenViewModel addNewScreenViewModel;
    
    private DependencyContainer(Application app) {
        this.application = app;
    }
    
    public static synchronized DependencyContainer getInstance(Application app) {
        if (instance == null) {
            instance = new DependencyContainer(app);
        }
        return instance;
    }
    
    public NoteDatabase getNoteDatabase() {
        if (noteDatabase == null) {
            noteDatabase = NoteDatabase.getInstance(application);
        }
        return noteDatabase;
    }
    
    public NoteRepository getNoteRepository() {
        if (noteRepository == null) {
            noteRepository = new NoteRepositoryImpl(getNoteDatabase());
        }
        return noteRepository;
    }
    
    public CurrencyRateRepository getCurrencyRateRepository() {
        if (currencyRateRepository == null) {
            String apiKey = application.getString(com.example.todolist.R.string.exchangerate_api_key);
            currencyRateRepository = new CurrencyRateRepositoryImpl(getNoteDatabase(), apiKey);
        }
        return currencyRateRepository;
    }
    
    public MainViewModel getMainViewModel() {
        if (mainViewModel == null) {
            mainViewModel = new MainViewModel(application);
        }
        return mainViewModel;
    }
    
    public AddNewScreenViewModel getAddNewScreenViewModel() {
        if (addNewScreenViewModel == null) {
            addNewScreenViewModel = new AddNewScreenViewModel(application);
        }
        return addNewScreenViewModel;
    }
    
    public GetNotesUseCase getGetNotesUseCase() {
        return new GetNotesUseCase(getNoteRepository());
    }
    
    public AddNoteUseCase getAddNoteUseCase() {
        return new AddNoteUseCase(getNoteRepository());
    }
    
    public RemoveNoteUseCase getRemoveNoteUseCase() {
        return new RemoveNoteUseCase(getNoteRepository());
    }
    
    public UpdateNotePriceUseCase getUpdateNotePriceUseCase() {
        return new UpdateNotePriceUseCase(getNoteRepository());
    }
    
    public UpdateNoteStatusUseCase getUpdateNoteStatusUseCase() {
        return new UpdateNoteStatusUseCase(getNoteRepository());
    }
    
    public UpdateNoteTextUseCase getUpdateNoteTextUseCase() {
        return new UpdateNoteTextUseCase(getNoteRepository());
    }
    
    public ClearAllNotesUseCase getClearAllNotesUseCase() {
        return new ClearAllNotesUseCase(getNoteRepository());
    }
    
    public UpdateCurrencyRatesUseCase getUpdateCurrencyRatesUseCase() {
        return new UpdateCurrencyRatesUseCase(getCurrencyRateRepository());
    }
    
    public GetCurrentCurrencyRateUseCase getGetCurrentCurrencyRateUseCase() {
        return new GetCurrentCurrencyRateUseCase(getCurrencyRateRepository());
    }
}
