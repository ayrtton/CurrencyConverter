package com.example.currencyconverter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconverter.data.database.dao.ExchangeDao
import com.example.currencyconverter.data.model.ExchangeResponseValue

@Database(entities = [ExchangeResponseValue::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exchangeDao(): ExchangeDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "currency_exchange_database"
            ).build()
        }
    }
}