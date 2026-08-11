package com.example.data.repoimpl

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.data.apiservices.UserInfoApi
import com.example.data.db.dao.UserInfoDao
import com.example.data.toDomain
import com.example.data.toEntity
import com.example.data.worker.TransactionSyncWorker
import com.example.data.worker.UserInfoSyncWorker
import com.example.domain.recenttranscations.model.UserInfo
import com.example.domain.recenttranscations.repo.UserInfoRepo
import com.example.domain.recenttranscations.utils.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.util.concurrent.TimeUnit

class UserInfoRepoImpl(
    private val userInfoApi: UserInfoApi,
    private val userInfoDao: UserInfoDao,
    private val context: Context
) : UserInfoRepo {

    override suspend fun getUserInfo(identifier: String): Resource<UserInfo> {

        return try {
            val remoteResponse = userInfoApi.getUserInfoApi(identifier)
            val entities = remoteResponse.toEntity()

            userInfoDao.clearUserInfo()
            userInfoDao.insertUserInfo(entities)

            val localData = userInfoDao.getUserInfo().toDomain()
            Resource.Success(localData)
        } catch (e: Exception) {
            val localData = userInfoDao.getUserInfo().toDomain()
            scheduleUserInfoSync(identifier)
            if (localData != null) {
                Resource.Success(localData)
            } else {
                val errorMessage = when (e) {
                    is ClientRequestException -> "Error in order data: ${e.response.status.value}"
                    is ServerResponseException -> "Server error: ${e.response.status.value}"
                    else -> "No internet connection and no cached data available."
                }
                Resource.Error(errorMessage, e)
            }
        }
    }

    private fun scheduleUserInfoSync(identifier: String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val inputData = Data.Builder()
            .putString("IDENTIFIER", identifier)
            .build()

        val syncWorkRequest = OneTimeWorkRequestBuilder<UserInfoSyncWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                10,
                TimeUnit.SECONDS
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            UserInfoSyncWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            syncWorkRequest
        )
    }
}
