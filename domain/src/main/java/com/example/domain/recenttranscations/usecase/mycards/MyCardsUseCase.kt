package com.example.domain.recenttranscations.usecase.mycards

import com.example.domain.recenttranscations.model.AddCard
import com.example.domain.recenttranscations.repo.mycards.MyCardsRepo
import com.example.domain.recenttranscations.utils.Resource

class MyCardsUseCase(private val myCardsRepo: MyCardsRepo) {

    suspend operator fun invoke(): Resource<List<AddCard>> {
        return myCardsRepo.getMyCards()
    }
}