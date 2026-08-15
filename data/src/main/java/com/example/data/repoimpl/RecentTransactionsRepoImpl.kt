package com.example.data.repoimpl

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.data.apiservices.RecentTransactionApi
import com.example.data.db.dao.TransactionDao
import com.example.data.toDomain
import com.example.data.toEntity
import com.example.data.worker.TransactionSyncWorker
import com.example.domain.recenttranscations.model.RecentTransactionItem
import com.example.domain.recenttranscations.repo.RecentTransactionRepo
import com.example.domain.recenttranscations.utils.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

class RecentTransactionsRepoImpl(
    private val recentTransactionApi: RecentTransactionApi,
    private val transactionDao: TransactionDao,
    private val context: Context
) : RecentTransactionRepo {

    override suspend fun getAllRecentTransactions(identifier: String): Flow<Resource<List<RecentTransactionItem>>> =
        flow {
            emit(Resource.Loading)

            try {
                val remoteResponse = recentTransactionApi.getAllRecentTransactionApi(identifier)
                val entities = remoteResponse.map { it.toEntity() }

                transactionDao.clearSyncedTransactions()
                transactionDao.insertTransactions(entities)

            } catch (e: Exception) {
                scheduleTransactionSync(identifier)

                val localList = transactionDao.getAllTransactions().first()
                if (localList.isEmpty()) {
                    val errorMessage = when (e) {
                        is ClientRequestException -> "Error in order data: ${e.response.status.value}"
                        is ServerResponseException -> "Server error: ${e.response.status.value}"
                        else -> "No internet connection and no cached data available."
                    }
                    emit(Resource.Error(errorMessage, e))
                }
            }

            val localFlow = transactionDao.getAllTransactionsFlow().map { list ->
                val mappedList = list.map { it.toDomain() }
                Resource.Success(mappedList)
            }

            emitAll(localFlow)
        }

    private fun scheduleTransactionSync(identifier: String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val inputData = Data.Builder()
            .putString("IDENTIFIER", identifier)
            .build()

        val syncWorkRequest = OneTimeWorkRequestBuilder<TransactionSyncWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                10,
                TimeUnit.SECONDS
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            TransactionSyncWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            syncWorkRequest
        )
    }
}