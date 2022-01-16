package com.example.currencyconverter.domain.di

import com.example.currencyconverter.domain.GetExchangeValueUseCase
import com.example.currencyconverter.domain.ListExchangeUseCase
import com.example.currencyconverter.domain.SaveExchangeUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {
    fun load() {
        loadKoinModules(useCaseModules())
    }

    private fun useCaseModules(): Module {
        return module {
            factory { GetExchangeValueUseCase(get()) }
            factory { ListExchangeUseCase(get()) }
            factory { SaveExchangeUseCase(get()) }
        }
    }
}