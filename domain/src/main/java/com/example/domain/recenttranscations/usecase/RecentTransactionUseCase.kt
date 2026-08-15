package com.example.domain.recenttranscations.usecase

import com.example.domain.recenttranscations.model.RecentTransactionItem
import com.example.domain.recenttranscations.repo.RecentTransactionRepo
import com.example.domain.recenttranscations.utils.Resource
import kotlinx.coroutines.flow.Flow

class RecentTransactionUseCase(private val recentTransactionRepo: RecentTransactionRepo) {

    suspend operator fun invoke(identifier: String): Flow<Resource<List<RecentTransactionItem>>> {
        return recentTransactionRepo.getAllRecentTransactions(identifier)
    }
}