package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecentTransactionDto (
    val amount: Int,
    val currency: String,
    val date: String,
    val description: String,
    val id: Int,
    val state: String,
    val time: String,
    val type: String
)