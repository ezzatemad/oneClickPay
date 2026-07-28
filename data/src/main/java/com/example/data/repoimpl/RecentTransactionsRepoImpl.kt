package com.example.data.repoimpl

import com.example.data.apiservices.RecentTransactionApi
import com.example.data.dto.RecentTransactionDto
import com.example.data.toDomain
import com.example.domain.recenttranscations.model.recenttransaction.RecentTransactionModelItem
import com.example.domain.recenttranscations.repo.recenttransactionrepo.RecentTransactionRepo

class RecentTransactionsRepoImpl(private val recentTransactionApi: RecentTransactionApi) :
    RecentTransactionRepo {
    override suspend fun getAllRecentTransactions(identifier: String): List<RecentTransactionModelItem> {
        val response = recentTransactionApi.getAllRecentTransactionApi(identifier)

        return response.map {
            it.toDomain()
        }
    }
}


