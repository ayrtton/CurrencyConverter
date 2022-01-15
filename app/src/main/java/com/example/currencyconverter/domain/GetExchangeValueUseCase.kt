package com.example.currencyconverter.domain

import com.example.currencyconverter.core.UseCase
import com.example.currencyconverter.data.model.ExchangeResponseValue
import com.example.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetExchangeValueUseCase(repository: CurrencyRepository) : UseCase<String, ExchangeResponseValue>() {
    override suspend fun execute(param: String): Flow<ExchangeResponseValue> {
        TODO("Not yet implemented")
    }
}