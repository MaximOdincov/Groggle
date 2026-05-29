package com.example.todolist.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.todolist.data.local.NoteDatabase;
import com.example.todolist.data.repository.CurrencyRateRepositoryImpl;
import com.example.todolist.data.repository.NoteRepositoryImpl;
import com.example.todolist.domain.model.Note;
import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.repository.CurrencyRateRepository;
import com.example.todolist.domain.usecase.*;
import com.example.todolist.domain.utils.Result;

import java.util.List;

/**
 * MainViewModel с DI-поддержкой
 */
public class MainViewModel extends AndroidViewModel {
    private final GetNotesUseCase getNotesUseCase;
    private final AddNoteUseCase addNoteUseCase;
    private final RemoveNoteUseCase removeNoteUseCase;
    private final UpdateNotePriceUseCase updateNotePriceUseCase;
    private final UpdateNoteStatusUseCase updateNoteStatusUseCase;
    private final UpdateNoteTextUseCase updateNoteTextUseCase;
    private final ClearAllNotesUseCase clearAllNotesUseCase;
    private final UpdateCurrencyRatesUseCase updateCurrencyRatesUseCase;
    private final GetCurrentCurrencyRateUseCase getCurrentCurrencyRateUseCase;

    private int count = 0;
    private MutableLiveData<Integer> countLD = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        
        NoteDatabase database = NoteDatabase.getInstance(application);
        NoteRepository noteRepository = new NoteRepositoryImpl(database);
        CurrencyRateRepository currencyRateRepository = new CurrencyRateRepositoryImpl(
                database,
                application.getString(com.example.todolist.R.string.exchangerate_api_key)
        );

        this.getNotesUseCase = new GetNotesUseCase(noteRepository);
        this.addNoteUseCase = new AddNoteUseCase(noteRepository);
        this.removeNoteUseCase = new RemoveNoteUseCase(noteRepository);
        this.updateNotePriceUseCase = new UpdateNotePriceUseCase(noteRepository);
        this.updateNoteStatusUseCase = new UpdateNoteStatusUseCase(noteRepository);
        this.updateNoteTextUseCase = new UpdateNoteTextUseCase(noteRepository);
        this.clearAllNotesUseCase = new ClearAllNotesUseCase(noteRepository);
        this.updateCurrencyRatesUseCase = new UpdateCurrencyRatesUseCase(currencyRateRepository);
        this.getCurrentCurrencyRateUseCase = new GetCurrentCurrencyRateUseCase(currencyRateRepository);
    }

    public android.arch.lifecycle.LiveData<Integer> getCount() {
        return countLD;
    }

    public void showCount() {
        count++;
        countLD.setValue(count);
    }

    public Result<List<Note>> getNotes() {
        return getNotesUseCase.invoke();
    }

    public void addNote(Note note) {
        addNoteUseCase.invoke(note);
    }

    public void remove(int id) {
        removeNoteUseCase.invoke(id);
    }

    public void updatePrice(int id, double priceRub) {
        updateNotePriceUseCase.invoke(id, priceRub);
    }

    public void updateBought(int id, boolean bought) {
        updateNoteStatusUseCase.invoke(id, bought);
    }

    public void updateText(int id, String text) {
        updateNoteTextUseCase.invoke(id, text);
    }

    public void clearAllNotes() {
        clearAllNotesUseCase.invoke();
    }

    public void updateCurrencyRates(String baseCurrency) {
        updateCurrencyRatesUseCase.invoke(baseCurrency);
    }

    public Double getRateForCurrency(String code) {
        return getCurrentCurrencyRateUseCase.invoke(code);
    }
}
