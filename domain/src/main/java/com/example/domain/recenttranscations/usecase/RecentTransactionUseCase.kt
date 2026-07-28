package com.example.domain.recenttranscations.usecase

import com.example.domain.recenttranscations.model.recenttransaction.RecentTransactionModelItem
import com.example.domain.recenttranscations.repo.recenttransactionrepo.RecentTransactionRepo

class RecentTransactionUseCase(val recentTransactionRepo: RecentTransactionRepo) {


    suspend fun getAllTransactionsUseCase(identifier: String): List<RecentTransactionModelItem> {

        return recentTransactionRepo.getAllRecentTransactions(identifier)
    }
}