package com.example.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.composereview.mainascreen.Constants
import com.example.data.apiservices.RecentTransactionApi
import com.example.data.db.dao.TransactionDao
import com.example.data.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionSyncWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val transactionDao: TransactionDao,
    private val recentTransactionApi: RecentTransactionApi
) : CoroutineWorker(context, workerParameters){

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        if (Constants.PHONE_IDENTIFIER.isEmpty()) {
            Log.e("WorkManager", "Identifier is null or empty")
            return@withContext Result.failure()
        }

        return@withContext try {
            // get data from API
            val remoteData = recentTransactionApi.getAllRecentTransactionApi(Constants.PHONE_IDENTIFIER)
            val entities = remoteData.map { it.toEntity() }

            // update local database
            transactionDao.clearSyncedTransactions()
            transactionDao.insertTransactions(entities)

//            Log.d("WorkManager", "Sync succeeded for identifier: ${Constants.PHONE_IDENTIFIER}")
            Result.success()

        } catch (e: Exception) {
//            Log.e("WorkManager", "Worker error: ${e.localizedMessage}", e)

            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    companion object {
        const val WORK_NAME = "TransactionSyncWork"
    }

}

