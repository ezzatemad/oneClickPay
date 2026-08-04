package com.example.domain.recenttranscations.model

data class UserInfo(
    val avatar: String,
    val balance: Int,
    val currency: String,
    val id: Int,
    val identifier: String,
    val name: String
)