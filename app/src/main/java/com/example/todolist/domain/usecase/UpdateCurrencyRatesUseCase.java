package com.example.todolist.domain.usecase;

import com.example.todolist.domain.repository.CurrencyRateRepository;
import com.example.todolist.domain.utils.Result;

public class UpdateCurrencyRatesUseCase {
    private final CurrencyRateRepository repository;

    public UpdateCurrencyRatesUseCase(CurrencyRateRepository repository) {
        this.repository = repository;
    }

    public Result<Void> invoke(String baseCurrency) {
        return repository.updateRates(baseCurrency);
    }
}
