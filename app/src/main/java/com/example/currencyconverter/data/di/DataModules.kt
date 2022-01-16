package com.example.currencyconverter.data.di

import android.util.Log
import com.example.currencyconverter.data.database.AppDatabase
import com.example.currencyconverter.data.repository.CurrencyRepository
import com.example.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.example.currencyconverter.data.services.AwesomeService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModules {
    fun load() {
        loadKoinModules(networkModule() + repositoryModule() + databaseModule())
    }

    private const val HTTP_TAG = "OnHttp"

    private fun networkModule(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(HTTP_TAG, ": $it")
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder().addInterceptor(interceptor).build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<AwesomeService>(get(), get())
            }
        }
    }

    private fun repositoryModule(): Module {
        return module {
            single<CurrencyRepository> { CurrencyRepositoryImpl(get(), get()) }
        }
    }

    private fun databaseModule(): Module {
        return module {
            single { AppDatabase.getInstance(androidApplication()) }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl("https://economia.awesomeapi.com.br")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }
}