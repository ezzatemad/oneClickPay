package com.example.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.apiservices.SendMoneyApi
import com.example.data.db.dao.TransactionDao
import com.example.data.toDto
import io.ktor.client.plugins.ClientRequestException
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.SerializationException

class SendMoneyWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val sendMoneyApi: SendMoneyApi,
    private val transactionDao: TransactionDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val transactionId = inputData.getString("TRANSACTION_ID") ?: return Result.failure()

        val localTransaction = transactionDao.getTransactionById(transactionId)
            ?: return Result.failure()

        return try {
            sendMoneyApi.sendMoney(localTransaction.toDto())
            // update state
            transactionDao.updateTransactionStatus(transactionId, state = "COMPLETED", isSynced = true)
            Result.success()

        } catch (e: Exception) {
            when (e) {
                is ClientRequestException -> {
                    transactionDao.updateTransactionStatus(transactionId, state = "FAILED", isSynced = true)
                    Result.failure()
                }
                is SerializationException, is JsonConvertException -> {
                    transactionDao.updateTransactionStatus(transactionId, state = "COMPLETED", isSynced = true)
                    Result.success()
                }
                else -> {
                    Result.retry()
                }
            }
        }
    }
}