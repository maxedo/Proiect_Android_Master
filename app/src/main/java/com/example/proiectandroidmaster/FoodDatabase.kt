package com.example.proiectandroidmaster
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proiectandroidmaster.DAO.FoodDao
import com.example.proiectandroidmaster.DAO.WaterDao
import com.example.proiectandroidmaster.Entities.Converters
import com.example.proiectandroidmaster.Entities.Food
import com.example.proiectandroidmaster.Entities.Water

@Database(entities = [Food::class,Water::class], version = 1)
@TypeConverters(Converters::class)
abstract class FoodDatabase : RoomDatabase() {

    companion object{
        const val NAME="Food_DB"
    }
    abstract fun foodDao(): FoodDao

    abstract fun waterDao():WaterDao
}
