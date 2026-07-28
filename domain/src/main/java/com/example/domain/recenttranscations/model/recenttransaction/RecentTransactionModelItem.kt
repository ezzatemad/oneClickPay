package com.example.domain.recenttranscations.model.recenttransaction

data class RecentTransactionModelItem(
    val amount: Int,
    val currency: String,
    val date: String,
    val description: String,
    val id: Int,
    val state: String,
    val time: String,
    val type: String
)