package com.example.oneclickpay.card.allcard

import com.example.domain.recenttranscations.model.AddCard

sealed class MyCardsIntent {

    data object DisplayMyCards : MyCardsIntent()
}