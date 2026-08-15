package com.example.domain.recenttranscations.repo

import com.example.domain.recenttranscations.model.RecentTransactionItem
import com.example.domain.recenttranscations.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RecentTransactionRepo {

    suspend fun getAllRecentTransactions(identifier: String): Flow<Resource<List<RecentTransactionItem>>>
}