package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey  val id: String,
    val amount: Double,
    val currency: String,
    val date: String,
    val time: String,
    val description: String,
    val state: String,
    val type: String,
    val isSynced: Boolean = true
)