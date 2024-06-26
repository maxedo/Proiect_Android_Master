package com.example.proiectandroidmaster.DAO
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proiectandroidmaster.Entities.Food

@Dao
interface FoodDao {

    @Query("SELECT * FROM food WHERE email = :email")
    fun getFoodsByEmail(email: String): LiveData<List<Food>>

    @Insert
    fun addFood(food: Food)

    @Query("DELETE FROM food WHERE id = :id")
    fun deleteFood(id: Int)
}
