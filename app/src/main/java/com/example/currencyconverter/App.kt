package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.data.di.DataModules
import com.example.currencyconverter.domain.di.DomainModule
import com.example.currencyconverter.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModules.load()
        DomainModule.load()
        PresentationModule.load()
    }
}