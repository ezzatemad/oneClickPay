package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class AllUsersEntity(

    val avatar: String,
    @PrimaryKey val id: Int,
    val identifier: String,
    val name: String
)
