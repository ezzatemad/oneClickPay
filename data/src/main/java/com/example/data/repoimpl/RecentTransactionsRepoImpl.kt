package com.example.data.repoimpl

import com.example.data.apiservices.RecentTransactionApi
import com.example.data.toDomain
import com.example.domain.recenttranscations.model.RecentTransactionItem
import com.example.domain.recenttranscations.repo.RecentTransactionRepo
import com.example.domain.recenttranscations.utils.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException

class RecentTransactionsRepoImpl(private val recentTransactionApi: RecentTransactionApi) :
    RecentTransactionRepo {

    override suspend fun getAllRecentTransactions(identifier: String): Resource<List<RecentTransactionItem>> {

        return try {
            val response = recentTransactionApi.getAllRecentTransactionApi(identifier)
            Resource.Success(response.map { it.toDomain() })
        } catch (e: IOException) {
            Resource.Error("There is no internet connection. Please check your network.", e)
        } catch (e: ClientRequestException) {
            Resource.Error("Error in order data: ${e.response.status.value}", e)
        } catch (e: ServerResponseException) {
            Resource.Error("Server error: ${e.response.status.value}", e)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "UnknownError")
        }
    }
}


