package com.example.data.repoimpl.mycard

import android.util.Log
import com.example.data.db.dao.MyCardDao
import com.example.data.toEntity
import com.example.domain.recenttranscations.model.AddCard
import com.example.domain.recenttranscations.repo.mycards.AddCardRepo
import com.example.domain.recenttranscations.utils.Resource
import java.util.UUID

class AddCardRepoImpl(private val addCardDao: MyCardDao) : AddCardRepo {
    override suspend fun addCard(addCardRequest: AddCard): Resource<Boolean> {
        return try {

            val id = UUID.randomUUID().toString()
            val cardEntity = addCardRequest.toEntity(id = id)

            addCardDao.insertCard(cardEntity)
            Log.d("AddCard", "✅ Successfully added card: $cardEntity")

            val allCards = addCardDao.getAllCards()
            Log.d("AddCard", "📦 All Cards in DB (${allCards.size}): $allCards")
            Resource.Success(true)
        } catch (e: Exception) {
            Log.d("AddCard", "addCard: ${e.localizedMessage}")
            Resource.Error(
                e.localizedMessage ?: "An unexpected error occurred while adding the card"
            )
        }
    }
}