package com.example.oneclickpay.dashboard

sealed class DashBoardScreenIntent {

    data class LoadTransactions(val phoneNumber: String) : DashBoardScreenIntent()
}