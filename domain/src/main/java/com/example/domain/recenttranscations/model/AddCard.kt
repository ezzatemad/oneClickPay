package com.example.domain.recenttranscations.model

data class AddCard(
    val cardNumber: String,
    val cardName: String,
    val expiryDate: String,
    val cvv: String
)