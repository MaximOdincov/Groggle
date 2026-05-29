package com.example.todolist.data.repository;

import com.example.todolist.data.local.CurrencyRateDao;
import com.example.todolist.data.local.NoteDatabase;
import com.example.todolist.data.mapper.NoteMapper;
import com.example.todolist.data.remote.ExchangeRateApi;
import com.example.todolist.data.remote.ExchangeRatesResponse;
import com.example.todolist.domain.model.CurrencyRate;
import com.example.todolist.domain.repository.CurrencyRateRepository;
import com.example.todolist.domain.utils.Result;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyRateRepositoryImpl implements CurrencyRateRepository {
    private static final String BASE_URL = "https://api.exchangerate.host/";
    private static final long THREE_DAYS_MILLIS = 3L * 24 * 60 * 60 * 1000;

    private final NoteDatabase database;
    private final CurrencyRateDao currencyRateDao;
    private final ExchangeRateApi api;
    private final String apiKey;
    private final ExecutorService executor;

    public CurrencyRateRepositoryImpl(NoteDatabase database, String apiKey) {
        this.database = database;
        this.currencyRateDao = database.currencyRateDao();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.api = retrofit.create(ExchangeRateApi.class);
        this.apiKey = apiKey;
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public List<CurrencyRate> getRates() {
        List<com.example.todolist.data.local.CurrencyRateEntity> entities = currencyRateDao.getAllRates();
        return NoteMapper.toDomainList(entities);
    }

    @Override
    public Result<Void> updateRates(String baseCurrency) {
        executor.execute(() -> {
            Call<ExchangeRatesResponse> call = api.getRates(apiKey, baseCurrency, null);
            try {
                Response<ExchangeRatesResponse> response = call.execute();
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    Map<String, Double> quotes = response.body().quotes;
                    long now = System.currentTimeMillis();
                    for (Map.Entry<String, Double> entry : quotes.entrySet()) {
                        currencyRateDao.insertOrUpdate(
                                NoteMapper.toEntity(new CurrencyRate(entry.getKey(), entry.getValue(), now))
                        );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return Result.success(null);
    }

    @Override
    public Double getRateForCode(String code) {
        com.example.todolist.data.local.CurrencyRateEntity entity = currencyRateDao.getRateByCode("RUB" + code);
        return entity != null ? entity.getRate() : 1.0;
    }
}
