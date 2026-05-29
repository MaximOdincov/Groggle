package com.example.todolist.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExchangeRateApi {
    @GET("live")
    Call<ExchangeRatesResponse> getRates(
        @Query("access_key") String accessKey,
        @Query("source") String source,
        @Query("currencies") String currencies
    );
}
