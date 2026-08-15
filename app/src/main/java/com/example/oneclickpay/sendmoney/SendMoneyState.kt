package com.example.oneclickpay.sendmoney

import com.example.domain.recenttranscations.model.AllUsers

sealed class SendMoneyState {

    data object Idle : SendMoneyState()
    data object Loading : SendMoneyState()
    data class Success(val message: String) : SendMoneyState()
    data class getAllUser(val users: List<AllUsers>) : SendMoneyState()
    data class Error(val message: String) : SendMoneyState()
}