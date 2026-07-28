package com.example.domain.recenttranscations.repo.recenttransactionrepo

import com.example.domain.recenttranscations.model.recenttransaction.RecentTransactionModelItem

interface RecentTransactionRepo {

    suspend fun getAllRecentTransactions(identifier: String): List<RecentTransactionModelItem>
}