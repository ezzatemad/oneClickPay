package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY date DESC,time DESC")
    fun getAllTransactionsFlow(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions ORDER BY date DESC ,time DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    suspend fun getTransactionById(id: String): TransactionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransaction(id: String)

    @Query("DELETE FROM transactions WHERE isSynced = 1")
    suspend fun clearSyncedTransactions()

    @Query("UPDATE transactions SET state = :state, isSynced = :isSynced WHERE id = :id")
    suspend fun updateTransactionStatus(id: String, state: String, isSynced: Boolean)

    @Query("SELECT * FROM transactions WHERE isSynced = 0 AND state = 'PENDING'")
    suspend fun getPendingTransactions(): List<TransactionEntity>

}