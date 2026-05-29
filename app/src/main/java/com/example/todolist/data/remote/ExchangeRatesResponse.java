package com.example.todolist.data.remote;

import java.util.Map;

public class ExchangeRatesResponse {
    public boolean success;
    public String terms;
    public String privacy;
    public long timestamp;
    public String source;
    public Map<String, Double> quotes;
    public ApiError error;

    public static class ApiError {
        public int code;
        public String info;
    }
}
