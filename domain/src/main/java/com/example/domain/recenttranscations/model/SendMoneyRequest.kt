package com.example.domain.recenttranscations.model

data class SendMoneyRequest(
    val senderIdentifier: String,
    val receiverIdentifier: String,
    val amount: Double,
    val currency: String,
    val title: String
)