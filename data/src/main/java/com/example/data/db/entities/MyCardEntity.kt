package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_card")
data class MyCardEntity(
    @PrimaryKey val id: String,
    val cardNumber: String,
    val cardName: String,
    val expiryDate: String,
    val cvv: String
)