package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.model.ExchangeResponseValue
import com.example.currencyconverter.data.services.AwesomeService
import kotlinx.coroutines.flow.flow

class CurrencyRepositoryImpl(private val service: AwesomeService) : CurrencyRepository {
    override suspend fun getExchangeValue(currencies: String) = flow {
        val exchangeValue = service.exchangeValue(currencies)
        val exchange = exchangeValue.values.first()
        emit(exchange)
    }
}