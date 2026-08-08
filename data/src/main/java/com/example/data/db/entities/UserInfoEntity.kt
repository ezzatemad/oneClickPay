package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfoEntity(
    @PrimaryKey val id: Int,
    val avatar: String,
    val balance: Int,
    val currency: String,
    val identifier: String,
    val name: String
)