package com.example.todolist.domain.usecase;

import com.example.todolist.domain.repository.CurrencyRateRepository;

public class GetCurrentCurrencyRateUseCase {
    private final CurrencyRateRepository repository;

    public GetCurrentCurrencyRateUseCase(CurrencyRateRepository repository) {
        this.repository = repository;
    }

    public Double invoke(String code) {
        return repository.getRateForCode(code);
    }
}
