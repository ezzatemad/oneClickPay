package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.TransactionDao
import com.example.data.db.dao.UserInfoDao
import com.example.data.db.entities.RecentTransactionEntity
import com.example.data.db.entities.UserInfoEntity


@Database(
    entities = [RecentTransactionEntity::class, UserInfoEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun userInfoDao(): UserInfoDao
}