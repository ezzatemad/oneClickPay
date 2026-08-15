package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SendMoneyRequestDto(
    val id: String,
    val senderIdentifier: String,
    val receiverIdentifier: String,
    val amount: Double,
    val currency: String,
    val title: String
)