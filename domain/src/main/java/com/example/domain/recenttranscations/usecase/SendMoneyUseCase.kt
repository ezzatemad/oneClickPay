package com.example.domain.recenttranscations.usecase

import com.example.domain.recenttranscations.model.SendMoneyRequest
import com.example.domain.recenttranscations.repo.SendMoneyRepo
import com.example.domain.recenttranscations.utils.Resource

class SendMoneyUseCase(private val sendMoneyRepo: SendMoneyRepo) {

    suspend operator fun invoke(
        sendMoneyRequest: SendMoneyRequest
    ): Resource<Boolean> {
        return sendMoneyRepo.sendMoney(
            sendMoneyRequest
        )
    }
}