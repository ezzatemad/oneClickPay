package com.example.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecentTransactionDto(
    @SerialName("id") val id: String? = null,
    @SerialName("amount") val amount: Double? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("date") val date: String? = null,
    @SerialName("time") val time: String? = null,
    @SerialName("state") val state: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("isSynced") val isSynced: Boolean = true
)