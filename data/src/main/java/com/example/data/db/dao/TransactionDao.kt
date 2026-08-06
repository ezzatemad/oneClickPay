package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entities.RecentTransactionEntity


@Dao
interface TransactionDao {

    @Query("SELECT * FROM recent_transactions")
    suspend fun getAllTransactions(): List<RecentTransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<RecentTransactionEntity>)

    @Query("DELETE FROM recent_transactions")
    suspend fun clearTransactions()

}