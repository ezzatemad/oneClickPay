package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddCardDto(

    val id: Int,
    val cardNumber: String,
    val cardName: String,
    val expiryDate: String,
    val cvv: String

)
