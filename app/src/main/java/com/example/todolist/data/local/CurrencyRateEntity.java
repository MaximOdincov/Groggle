package com.example.todolist.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "currency_rates")
public class CurrencyRateEntity {
    @PrimaryKey
    @ColumnInfo(name = "currencyCode")
    private String code;

    @ColumnInfo(name = "rate")
    private double rate;

    @ColumnInfo(name = "updatedAt")
    private long updatedAt;

    public CurrencyRateEntity(String code, double rate, long updatedAt) {
        this.code = code;
        this.rate = rate;
        this.updatedAt = updatedAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
