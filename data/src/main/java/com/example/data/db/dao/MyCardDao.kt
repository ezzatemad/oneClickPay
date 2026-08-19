package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entities.MyCardEntity

@Dao
interface MyCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(addCardEntity: MyCardEntity)

    @Query("SELECT * FROM my_card")
    suspend fun getAllCards(): List<MyCardEntity>

    @Query("DELETE FROM my_card WHERE id=:id")
    suspend fun deleteCardById(id: String)

    @Query("DELETE FROM my_card")
    suspend fun clearAllCards()
}