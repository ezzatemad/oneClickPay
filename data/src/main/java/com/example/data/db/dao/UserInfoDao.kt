package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entities.UserInfoEntity
import com.example.domain.recenttranscations.model.UserInfo


@Dao
interface UserInfoDao {

    @Query("SELECT * FROM user_info LIMIT 1")
    suspend fun getUserInfo(): UserInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfoEntity: UserInfoEntity)

    @Query("DELETE FROM user_info")
    suspend fun clearUserInfo()

}