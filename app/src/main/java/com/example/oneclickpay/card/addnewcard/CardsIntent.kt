package com.example.oneclickpay.card.addnewcard

sealed class CardsIntent {

    data class AddCard(val addCardRequest: com.example.domain.recenttranscations.model.AddCard) : CardsIntent()
}