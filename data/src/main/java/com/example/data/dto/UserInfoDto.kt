package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    val avatar: String,
    val balance: Int,
    val currency: String,
    val id: Int,
    val identifier: String,
    val name: String
)
