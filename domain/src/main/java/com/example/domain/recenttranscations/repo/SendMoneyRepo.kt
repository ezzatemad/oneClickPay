package com.example.domain.recenttranscations.repo

import com.example.domain.recenttranscations.model.SendMoneyRequest
import com.example.domain.recenttranscations.utils.Resource

interface SendMoneyRepo {
    suspend fun sendMoney(
        sendMoneyRequest: SendMoneyRequest
    ): Resource<Boolean>
}