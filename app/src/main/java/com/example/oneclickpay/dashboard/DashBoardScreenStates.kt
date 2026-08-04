package com.example.oneclickpay.dashboard

import com.example.domain.recenttranscations.model.RecentTransactionItem

sealed class DashBoardScreenStates {

    data object Idle : DashBoardScreenStates()
    data object Loading : DashBoardScreenStates()
    data class Success(val recentTransaction: List<RecentTransactionItem>) :
        DashBoardScreenStates()

    data class Error(val errorMessage: String) : DashBoardScreenStates()
}