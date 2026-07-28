package com.example.domain.recenttranscations.model.userinfo

data class UserInfoModel(
    val avatar: String,
    val balance: Int,
    val currency: String,
    val id: Int,
    val identifier: String,
    val name: String
)