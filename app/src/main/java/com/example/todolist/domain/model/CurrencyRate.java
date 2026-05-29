package com.example.todolist.domain.model;

public class CurrencyRate {
    private String code;
    private double rate;
    private long updatedAt;

    public CurrencyRate(String code, double rate, long updatedAt) {
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
