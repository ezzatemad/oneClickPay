package com.example.domain.recenttranscations.usecase.mycards

import com.example.domain.recenttranscations.model.AddCard
import com.example.domain.recenttranscations.repo.mycards.AddCardRepo
import com.example.domain.recenttranscations.utils.Resource

class AddCardUseCase(private val addCardRepo: AddCardRepo) {

    suspend operator fun invoke(addCardRequest: AddCard): Resource<Boolean> {
        return addCardRepo.addCard(addCardRequest)
    }
}