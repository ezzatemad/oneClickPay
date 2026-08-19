package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.MyCardDao
import com.example.data.db.dao.AllUsersDao
import com.example.data.db.dao.TransactionDao
import com.example.data.db.dao.UserInfoDao
import com.example.data.db.entities.MyCardEntity
import com.example.data.db.entities.AllUsersEntity
import com.example.data.db.entities.TransactionEntity
import com.example.data.db.entities.UserInfoEntity


@Database(
    entities = [TransactionEntity::class, UserInfoEntity::class, AllUsersEntity::class, MyCardEntity::class],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun userInfoDao(): UserInfoDao

    abstract fun allUserDao(): AllUsersDao

    abstract fun myCardDao(): MyCardDao
}