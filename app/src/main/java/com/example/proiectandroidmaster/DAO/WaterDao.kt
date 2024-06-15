package com.example.proiectandroidmaster.DAO
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proiectandroidmaster.Entities.Water

@Dao
interface WaterDao {
    @Insert
    suspend fun insert(user: Water)

    @Query("SELECT * FROM water_table")
    suspend fun getAllUsers(): List<Water>
}