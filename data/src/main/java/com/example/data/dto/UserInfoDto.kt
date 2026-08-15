package com.example.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("identifier") val identifier: String,
    @SerialName("balance") val balance: Double,
    @SerialName("currency") val currency: String,
    @SerialName("avatar") val avatar: String? = null,
    @SerialName("transactions") val transactions: List<RecentTransactionDto>? = emptyList()
)
