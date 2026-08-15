package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entities.AllUsersEntity
import com.example.data.db.entities.UserInfoEntity

@Dao
interface AllUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(allUsersEntity: List<AllUsersEntity>)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<AllUsersEntity>

    @Query("DELETE FROM users")
    suspend fun clearAllUsers()
}