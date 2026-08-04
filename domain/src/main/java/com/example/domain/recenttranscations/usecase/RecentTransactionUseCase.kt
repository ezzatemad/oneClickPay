package com.example.domain.recenttranscations.usecase

import com.example.domain.recenttranscations.model.RecentTransactionItem
import com.example.domain.recenttranscations.repo.RecentTransactionRepo
import com.example.domain.recenttranscations.utils.Resource

class RecentTransactionUseCase(private val recentTransactionRepo: RecentTransactionRepo) {

    suspend operator fun invoke(identifier: String): Resource<List<RecentTransactionItem>> {
        return recentTransactionRepo.getAllRecentTransactions(identifier)
    }
}