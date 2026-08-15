package com.example.oneclickpay.sendmoney

import com.example.domain.recenttranscations.model.SendMoneyRequest

sealed class SendMoneyIntent {

    data class ConfirmTransfer(val sendMoneyRequest: SendMoneyRequest) : SendMoneyIntent()
    data object getAllUser : SendMoneyIntent()
}