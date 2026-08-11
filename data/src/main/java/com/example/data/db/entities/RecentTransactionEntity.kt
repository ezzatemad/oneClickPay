package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_transactions")
data class RecentTransactionEntity(
    @PrimaryKey val id: Int,
    val amount: Int,
    val currency: String,
    val date: String,
    val time: String,
    val description: String,
    val state: String,
    val type: String,
    val isSynced: Boolean = true
)