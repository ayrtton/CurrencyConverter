package com.example.currencyconverter.domain

import com.example.currencyconverter.core.UseCase
import com.example.currencyconverter.data.model.ExchangeResponseValue
import com.example.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class ListExchangeUseCase(
    private val repository: CurrencyRepository
) : UseCase.NoParam<List<ExchangeResponseValue>>() {

    override suspend fun execute(): Flow<List<ExchangeResponseValue>> = repository.list()
}