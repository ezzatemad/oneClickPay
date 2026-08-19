package com.example.data.repoimpl.mycard

import android.util.Log
import com.example.data.db.dao.MyCardDao
import com.example.data.toDomain
import com.example.domain.recenttranscations.model.AddCard
import com.example.domain.recenttranscations.repo.mycards.MyCardsRepo
import com.example.domain.recenttranscations.utils.Resource

class MyCardsRepoImpl(
    private val myCardDao: MyCardDao
) : MyCardsRepo {
    override suspend fun getMyCards(): Resource<List<AddCard>> {

        return try {
            val allMyCards = myCardDao.getAllCards()
            val allMyCardsEntity = allMyCards.map {
                it.toDomain()
            }
            Log.d("AddCard", "📦 All Cards in DB (${allMyCardsEntity.size}): $allMyCardsEntity")
            Resource.Success(allMyCardsEntity)
        } catch (e: Exception) {
            Log.d("AddCard", "addCard: ${e.localizedMessage}")
            Resource.Error(e.localizedMessage)
        }
    }
}