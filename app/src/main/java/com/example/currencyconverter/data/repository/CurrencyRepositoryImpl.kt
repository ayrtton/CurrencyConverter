package com.example.currencyconverter.data.repository

import com.example.currencyconverter.core.exceptions.RemoteException
import com.example.currencyconverter.data.database.AppDatabase
import com.example.currencyconverter.data.model.ErrorResponse
import com.example.currencyconverter.data.model.ExchangeResponseValue
import com.example.currencyconverter.data.services.AwesomeService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CurrencyRepositoryImpl(
    appDatabase: AppDatabase,
    private val service: AwesomeService
) : CurrencyRepository {

    private val dao = appDatabase.exchangeDao()

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

    override suspend fun save(exchange: ExchangeResponseValue) {
        dao.save(exchange)
    }

    override fun list(): Flow<List<ExchangeResponseValue>> {
        return dao.findAll()
    }
}