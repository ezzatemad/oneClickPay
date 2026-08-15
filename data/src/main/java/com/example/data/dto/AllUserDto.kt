package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AllUserDto (
    val avatar: String,
    val id: Int,
    val identifier: String,
    val name: String
)