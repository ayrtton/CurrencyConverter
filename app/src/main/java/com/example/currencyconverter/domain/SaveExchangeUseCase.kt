package com.example.currencyconverter.domain

import com.example.currencyconverter.core.UseCase
import com.example.currencyconverter.data.model.ExchangeResponseValue
import com.example.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveExchangeUseCase(
    private val repository: CurrencyRepository
) : UseCase.NoSource<ExchangeResponseValue>() {

    override suspend fun execute(param: ExchangeResponseValue): Flow<Unit> {
        return flow {
            repository.save(param)
            emit(Unit)
        }
    }
}