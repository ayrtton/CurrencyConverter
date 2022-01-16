package com.example.currencyconverter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.data.model.ExchangeResponseValue
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeDao {
    @Query("SELECT * FROM table_exchange")
    fun findAll(): Flow<List<ExchangeResponseValue>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: ExchangeResponseValue)
}