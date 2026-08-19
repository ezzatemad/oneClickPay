package com.example.oneclickpay.card.allcard

import com.example.domain.recenttranscations.model.AddCard

sealed class MyCardsState {
    data object Idle : MyCardsState()
    data class Success(val allMyCards: List<AddCard>) : MyCardsState()
    data class Error(val errorMessage: String) : MyCardsState()
}