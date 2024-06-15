package com.example.proiectandroidmaster.DAO
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proiectandroidmaster.Entities.Food

@Dao
interface FoodDao {
    @Insert
    suspend fun insert(user: Food)

    @Query("SELECT * FROM food_table")
    suspend fun getAllUsers(): List<Food>
}