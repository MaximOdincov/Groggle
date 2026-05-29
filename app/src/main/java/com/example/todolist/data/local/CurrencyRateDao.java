package com.example.todolist.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CurrencyRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(CurrencyRateEntity rate);

    @Query("SELECT * FROM currency_rates WHERE currencyCode = :code LIMIT 1")
    CurrencyRateEntity getRateByCode(String code);

    @Query("SELECT * FROM currency_rates")
    List<CurrencyRateEntity> getAllRates();

    @Query("DELETE FROM currency_rates")
    void clearAll();
}
