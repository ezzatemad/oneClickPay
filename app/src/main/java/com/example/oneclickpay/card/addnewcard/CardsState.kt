package com.example.oneclickpay.card.addnewcard

import com.example.domain.recenttranscations.model.AddCard

sealed class CardsState {
    data object Idle : CardsState()
    data object Loading : CardsState()
    data class Success(val addCardRequest: AddCard) : CardsState()
    data class Error(val errorMassage: String) : CardsState()
}