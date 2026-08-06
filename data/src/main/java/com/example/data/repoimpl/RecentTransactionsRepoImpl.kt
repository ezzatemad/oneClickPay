package com.example.data.repoimpl

import com.example.data.apiservices.RecentTransactionApi
import com.example.data.db.dao.TransactionDao
import com.example.data.toDomain
import com.example.data.toEntity
import com.example.domain.recenttranscations.model.RecentTransactionItem
import com.example.domain.recenttranscations.repo.RecentTransactionRepo
import com.example.domain.recenttranscations.utils.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException

class RecentTransactionsRepoImpl(
    private val recentTransactionApi: RecentTransactionApi,
    private val transactionDao: TransactionDao
) :
    RecentTransactionRepo {

    override suspend fun getAllRecentTransactions(identifier: String): Resource<List<RecentTransactionItem>> {

        return try {
            val remoteResponse = recentTransactionApi.getAllRecentTransactionApi(identifier)
            val entities = remoteResponse.map { it.toEntity() }

            transactionDao.clearTransactions()
            transactionDao.insertTransactions(entities)

            val localData = transactionDao.getAllTransactions().map { it.toDomain() }
            Resource.Success(localData)
        } catch (e: Exception) {
            val localData = transactionDao.getAllTransactions().map { it.toDomain() }

            if (localData.isNotEmpty()) {
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
}






