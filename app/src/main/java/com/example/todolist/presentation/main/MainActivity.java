package com.example.todolist.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.todolist.R;
import com.example.todolist.data.local.NoteDatabase;
import com.example.todolist.data.mapper.CurrencyInfoService;
import com.example.todolist.data.repository.CurrencyRateRepositoryImpl;
import com.example.todolist.data.repository.NoteRepositoryImpl;
import com.example.todolist.di.MainViewModel;
import com.example.todolist.domain.model.Note;
import com.example.todolist.domain.repository.NoteRepository;
import com.example.todolist.domain.repository.CurrencyRateRepository;
import com.example.todolist.domain.usecase.*;
import com.example.todolist.domain.utils.Result;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    com.google.android.material.floatingactionbutton.FloatingActionButton addNewNoteButton;
    com.google.android.material.floatingactionbutton.FloatingActionButton clearListButton;
    TextView totalSumTextView;

    androidx.recyclerview.widget.RecyclerView recyclerView;

    com.example.todolist.presentation.main.NewNoteAdapter adapter;
    MainViewModel viewModel;

    Spinner currencySpinner;
    Switch currencySwitch;

    private String selectedCurrency;
    private boolean showLocalCurrency;
    private CurrencyRateRepository currencyRateRepository;
    private boolean isRatesLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initViews();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new com.example.todolist.presentation.main.NewNoteAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnBoughtChangeListener((note, isBought) -> {
            viewModel.updateBought(note.getId(), isBought);
        });
        adapter.setOnPriceClickListener(note -> {
            showPriceInputDialog(note);
        });

        clearListButton.setOnClickListener(v -> {
            viewModel.clearAllNotes();
        });

        addNewNoteButton.setOnClickListener(v -> {
            Intent intent = com.example.todolist.presentation.addnote.AddNoteScreen.newIntent(this);
            startActivity(intent);
        });

        adapter.setListner(note -> {
            viewModel.showCount();
        });

        androidx.recyclerview.widget.ItemTouchHelper itemTouchHelper = new androidx.recyclerview.widget.ItemTouchHelper(
                new androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(0, androidx.recyclerview.widget.ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        viewModel.remove(adapter.getNotes().get(viewHolder.getAdapterPosition()).getId());
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Result<List<Note>> result = viewModel.getNotes();
        if (result.isSuccess() && result.getData() != null) {
            adapter.setNotes(result.getData());
            updateTotalSum(result.getData());
        }
    }

    private void initViews() {
        viewModel = new MainViewModel(getApplication());
        recyclerView = findViewById(R.id.recyclerView);
        addNewNoteButton = findViewById(R.id.addNewNoteButton);
        clearListButton = findViewById(R.id.clearListButton);
        totalSumTextView = findViewById(R.id.total_sum);
        currencySpinner = findViewById(R.id.currency_spinner);
        currencySwitch = findViewById(R.id.currency_switch);

        String apiKey = getString(com.example.todolist.R.string.exchangerate_api_key);
        NoteDatabase database = NoteDatabase.getInstance((android.app.Application) getApplicationContext());
        currencyRateRepository = new CurrencyRateRepositoryImpl(database, apiKey);

        checkAndUpdateRates();

        List<String> currencyCodes = new ArrayList<>(CurrencyInfoService.getAllCodes());
        List<String> currencyDisplayNames = new ArrayList<>();
        for (String code : currencyCodes) {
            currencyDisplayNames.add(CurrencyInfoService.getDisplayName(code));
        }
        ArrayAdapter<String> adapterCurrency = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencyDisplayNames);
        adapterCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapterCurrency);

        String selectedCode = AppSettings.getSelectedCurrency(this);
        int selectedIndex = currencyCodes.indexOf(selectedCode);
        if (selectedIndex >= 0) {
            currencySpinner.setSelection(selectedIndex);
        }

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String code = currencyCodes.get(position);
                AppSettings.setSelectedCurrency(MainActivity.this, code);
                checkAndUpdateRates();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        currencySwitch.setChecked(AppSettings.isShowLocalCurrency(this));
        currencySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppSettings.setShowLocalCurrency(this, isChecked);
            updateCurrencyDisplay();
        });

        Result<List<Note>> initialResult = viewModel.getNotes();
        if (initialResult.isSuccess() && initialResult.getData() != null) {
            adapter.setNotes(initialResult.getData());
            updateTotalSum(initialResult.getData());
        }

        viewModel.getCount().observe(this, count -> {
            Toast.makeText(this, String.valueOf(count), Toast.LENGTH_SHORT).show();
        });

        checkAndUpdateRates();
    }

    private void checkAndUpdateRates() {
        if (isRatesLoading) return;
        isRatesLoading = true;
        setLoadingState(true);
        String baseCurrency = "RUB";

        new Handler(Looper.getMainLooper()).post(() -> {
            currencyRateRepository.updateRates(baseCurrency);
            isRatesLoading = false;
            setLoadingState(false);
            updateCurrencyDisplay();
        });
    }

    private void setLoadingState(boolean loading) {
        runOnUiThread(() -> {
            addNewNoteButton.setEnabled(!loading);
            clearListButton.setEnabled(!loading);
            currencySpinner.setEnabled(!loading);
            currencySwitch.setEnabled(!loading);
        });
    }

    private void updateTotalSum(List<Note> notes) {
        double sum = 0;
        String currency = AppSettings.getSelectedCurrency(this);
        boolean showLocal = AppSettings.isShowLocalCurrency(this);
        String symbol = CurrencyInfoService.getCurrencySymbol(currency);

        if (showLocal && !currency.equals("RUB")) {
            Double rate = currencyRateRepository.getRateForCode(currency);
            double usedRate = rate != null ? rate : 1.0;
            if (rate == null || rate == 1.0) {
                runOnUiThread(() -> Toast.makeText(this, "No saved rate for selected currency. Using 1:1.", Toast.LENGTH_SHORT).show());
            }
            for (Note note : notes) {
                double price = note.getPriceRub();
                price *= usedRate;
                sum += price;
            }
            final double finalSum = sum;
            runOnUiThread(() -> totalSumTextView.setText(String.format("Итого: %.2f %s", finalSum, symbol)));
        } else {
            for (Note note : notes) {
                double price = note.getPriceRub();
                sum += price;
            }
            totalSumTextView.setText(String.format("Итого: %.2f %s", sum, CurrencyInfoService.getCurrencySymbol("RUB")));
        }
    }

    private void showPriceInputDialog(Note note) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Введите цену в рублях");
        android.widget.EditText input = new android.widget.EditText(this);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(String.valueOf(note.getPriceRub()));
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            try {
                double price = Double.parseDouble(input.getText().toString());
                viewModel.updatePrice(note.getId(), price);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Некорректная цена", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void updateCurrencyDisplay() {
        adapter.notifyDataSetChanged();
        List<Note> notes = adapter.getNotes();
        if (notes != null) {
            updateTotalSum(notes);
        }
    }

    public static String getSelectedCurrency(Context context) {
        return AppSettings.getSelectedCurrency(context);
    }

    public static boolean isShowLocalCurrency(Context context) {
        return AppSettings.isShowLocalCurrency(context);
    }
}
