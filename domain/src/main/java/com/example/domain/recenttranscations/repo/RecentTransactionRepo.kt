package com.example.domain.recenttranscations.repo

import com.example.domain.recenttranscations.model.RecentTransactionItem
import com.example.domain.recenttranscations.utils.Resource

interface RecentTransactionRepo {

    suspend fun getAllRecentTransactions(identifier: String): Resource<List<RecentTransactionItem>>
}