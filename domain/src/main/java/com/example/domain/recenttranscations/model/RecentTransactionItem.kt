package com.example.domain.recenttranscations.model

data class RecentTransactionItem(
    val amount: Double,
    val currency: String,
    val date: String,
    val description: String,
    val id: String,
    val state: String,
    val time: String,
    val type: String
)