package com.example.todolist.presentation.addnote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolist.R;
import com.example.todolist.data.local.NoteDatabase;
import com.example.todolist.data.mapper.CurrencyInfoService;
import com.example.todolist.data.repository.CurrencyRateRepositoryImpl;
import com.example.todolist.data.repository.NoteRepositoryImpl;
import com.example.todolist.domain.model.Note;
import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.repository.CurrencyRateRepository;
import com.example.todolist.domain.usecase.GetCurrentCurrencyRateUseCase;
import com.example.todolist.presentation.main.AppSettings;
import com.example.todolist.di.AddNewScreenViewModel;

public class AddNoteScreen extends AppCompatActivity {
    EditText editText;
    EditText priceEditText;
    Button saveButton;

    private AddNewScreenViewModel viewModel;
    private CurrencyRateRepository currencyRateRepository;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note_screen);

        initViews();
        saveButton.setOnClickListener(v -> saveNote());
    }

    private void initViews() {
        editText = findViewById(R.id.AddNameEditText);
        priceEditText = findViewById(R.id.AddPriceEditText);
        saveButton = findViewById(R.id.AddSaveButton);

        String currency = AppSettings.getSelectedCurrency(this);
        boolean showLocal = AppSettings.isShowLocalCurrency(this);
        String symbol = showLocal && !currency.equals("RUB") 
            ? CurrencyInfoService.getCurrencySymbol(currency) 
            : CurrencyInfoService.getCurrencySymbol("RUB");
        priceEditText.setHint("Цена (" + symbol + ")");

        viewModel = new ViewModelProvider(this).get(AddNewScreenViewModel.class);
        viewModel.getShouldCloseActivity().observe(this, shouldClose -> {
            if (shouldClose) {
                finish();
            }
        });

        NoteDatabase database = NoteDatabase.getInstance((android.app.Application) getApplicationContext());
        String apiKey = getString(R.string.exchangerate_api_key);
        currencyRateRepository = new CurrencyRateRepositoryImpl(database, apiKey);
    }

    private void saveNote() {
        text = editText.getText().toString().trim();
        double price = 0.0;
        String currency = AppSettings.getSelectedCurrency(this);
        boolean showLocal = AppSettings.isShowLocalCurrency(this);

        if (priceEditText.getText() != null && !priceEditText.getText().toString().isEmpty()) {
            try {
                price = Double.parseDouble(priceEditText.getText().toString());
                if (showLocal && !currency.equals("RUB")) {
                    final double priceForLambda = price;
                    Double rate = currencyRateRepository.getRateForCode(currency);
                    double finalPrice = priceForLambda;
                    if (rate != null && rate != 0) {
                        finalPrice = priceForLambda / rate;
                    }
                    saveNoteToDb(finalPrice);
                    return;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        if (text.isEmpty()) return;
        saveNoteToDb(price);
    }

    private void saveNoteToDb(double price) {
        if (text.isEmpty()) return;
        viewModel.saveNote(new Note(0, text, price, false, 1));
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddNoteScreen.class);
        return intent;
    }
}
