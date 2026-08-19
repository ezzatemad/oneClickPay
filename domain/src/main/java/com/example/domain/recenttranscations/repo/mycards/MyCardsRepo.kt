package com.example.domain.recenttranscations.repo.mycards

import com.example.domain.recenttranscations.model.AddCard
import com.example.domain.recenttranscations.utils.Resource

interface MyCardsRepo {

    suspend fun getMyCards(): Resource<List<AddCard>>
}