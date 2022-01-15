package com.example.currencyconverter.data.services

import com.example.currencyconverter.data.model.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AwesomeService {
    @GET("/json/last/{currencies}")
    suspend fun exchangeValue(@Path("currencies") currencies: String): ExchangeResponse
}