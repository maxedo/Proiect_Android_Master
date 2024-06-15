package com.example.proiectandroidmaster.DAO
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proiectandroidmaster.Entities.Water

@Dao
interface WaterDao {
    @Query("SELECT * FROM Water")
   fun getWater(): LiveData<List<Water>>
    @Insert
    fun addWater(user: Water)


}