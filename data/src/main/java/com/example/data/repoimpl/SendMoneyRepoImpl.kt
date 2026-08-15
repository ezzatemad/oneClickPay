package com.example.data.repoimpl

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.data.apiservices.SendMoneyApi
import com.example.data.db.dao.TransactionDao
import com.example.data.toDto
import com.example.data.toEntity
import com.example.data.worker.SendMoneyWorker
import com.example.domain.recenttranscations.model.SendMoneyRequest
import com.example.domain.recenttranscations.repo.SendMoneyRepo
import com.example.domain.recenttranscations.utils.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.SerializationException
import java.io.IOException
import java.util.UUID
import java.util.concurrent.TimeUnit

class SendMoneyRepoImpl(
    private val sendMoneyApi: SendMoneyApi,
    private val transactionDao: TransactionDao,
    private val context: Context
) : SendMoneyRepo {

    override suspend fun sendMoney(sendMoneyRequest: SendMoneyRequest): Resource<Boolean> {
        val id = UUID.randomUUID().toString()
        val localTransaction = sendMoneyRequest.toEntity(id)
        val networkRequest = sendMoneyRequest.toDto(id)

        return try {
            // save in local DB and state is pending
            transactionDao.insertTransaction(localTransaction)
            // send to server
            sendMoneyApi.sendMoney(networkRequest)

            // update state
            transactionDao.updateTransactionStatus(id, state = "COMPLETED", isSynced = true)
            Resource.Success(true)
        } catch (e: Exception) {
            when (e) {
                // client error (400)
                is ClientRequestException -> {
//                    Log.e("SendMoneyRepo", "Client Error: ${e.localizedMessage}", e)
                    transactionDao.updateTransactionStatus(id, state = "FAILED", isSynced = true)
                    Resource.Error("بيانات التحويل غير صحيحة أو الرصيد غير كافٍ", e)
                }

                is SerializationException, is JsonConvertException -> {
                    transactionDao.updateTransactionStatus(id, state = "COMPLETED", isSynced = true)
                    Resource.Success(true)
                }
                // network error (500)
                is ServerResponseException, is IOException, is HttpRequestTimeoutException -> {
//                    Log.e("SendMoneyRepo", "Network/Server Error: ${e.localizedMessage}", e)
                    schedulePendingTransactionWorker(id)
                    Resource.Error(
                        "تعذر الاتصال بالسيرفر، تم حفظ المعاملة وسيتم إعادة المحاولة تلقائياً عند توفر الشبكة",
                        e
                    )
                }

                else -> {
//                    Log.e("SendMoneyRepo", "Unexpected Error: ${e.localizedMessage}", e)
                    transactionDao.updateTransactionStatus(id, state = "FAILED", isSynced = true)
                    Resource.Error(e.localizedMessage ?: "حدث خطأ غير متوقع", e)
                }
            }
        }
    }

        private fun schedulePendingTransactionWorker(transactionId: String) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val inputData = Data.Builder()
                .putString("TRANSACTION_ID", transactionId)
                .build()

            val syncRequest = OneTimeWorkRequestBuilder<SendMoneyWorker>()
                .setConstraints(constraints)
                .setInputData(inputData)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                ).build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                "sync_send_money_$transactionId",
                ExistingWorkPolicy.KEEP,
                syncRequest
            )
        }
    }