package com.example.domain.recenttranscations.repo.mycards

import com.example.domain.recenttranscations.model.AddCard
import com.example.domain.recenttranscations.utils.Resource

interface AddCardRepo {

    suspend fun addCard(addCardRequest: AddCard): Resource<Boolean>
}