package com.example.proiectandroidmaster
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proiectandroidmaster.DAO.FoodDao
import com.example.proiectandroidmaster.DAO.WaterDao
import com.example.proiectandroidmaster.Entities.Food
import com.example.proiectandroidmaster.Entities.Water

@Database(entities = [Food::class,Water::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    abstract fun waterDao(): WaterDao
}
