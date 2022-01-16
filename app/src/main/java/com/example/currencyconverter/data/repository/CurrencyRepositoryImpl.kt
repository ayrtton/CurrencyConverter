package com.example.currencyconverter.data.repository

import com.example.currencyconverter.core.exceptions.RemoteException
import com.example.currencyconverter.data.model.ErrorResponse
import com.example.currencyconverter.data.model.ExchangeResponseValue
import com.example.currencyconverter.data.services.AwesomeService
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CurrencyRepositoryImpl(private val service: AwesomeService) : CurrencyRepository {
    override suspend fun getExchangeValue(currencies: String) = flow {
        try {
            val exchangeValue = service.exchangeValue(currencies)
            val exchange = exchangeValue.values.first()
            emit(exchange)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw RemoteException(errorResponse.message)
        }
    }
}