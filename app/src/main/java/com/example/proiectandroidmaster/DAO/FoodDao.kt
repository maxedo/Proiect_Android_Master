package com.example.proiectandroidmaster.DAO
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proiectandroidmaster.Entities.Food

@Dao
interface FoodDao {
    @Query("Select * from Food")
    fun getAllFood(): LiveData<List<Food>>

    @Insert
    fun addFood(food : Food)

    @Query("Delete from Food where id=:id")
    fun deletefood(id : Int)


}