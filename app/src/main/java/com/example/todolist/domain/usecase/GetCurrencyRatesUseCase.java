package com.example.todolist.domain.usecase;

import com.example.todolist.domain.repository.CurrencyRateRepository;
import com.example.todolist.domain.utils.Result;

import java.util.List;

public class GetCurrencyRatesUseCase {
    private final CurrencyRateRepository repository;

    public GetCurrencyRatesUseCase(CurrencyRateRepository repository) {
        this.repository = repository;
    }

    public Result<List<com.example.todolist.domain.model.CurrencyRate>> invoke() {
        return repository.getRates();
    }
}
