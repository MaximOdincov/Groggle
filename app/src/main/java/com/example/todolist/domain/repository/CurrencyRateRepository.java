package com.example.todolist.domain.repository;

import com.example.todolist.domain.model.CurrencyRate;
import com.example.todolist.domain.utils.Result;

import java.util.List;

public interface CurrencyRateRepository {
    List<CurrencyRate> getRates();
    Result<Void> updateRates(String baseCurrency);
    Double getRateForCode(String code);
}
