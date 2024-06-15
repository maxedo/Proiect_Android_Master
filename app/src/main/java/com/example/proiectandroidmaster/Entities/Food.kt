package com.example.proiectandroidmaster.Entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "food_table")
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: Int,
    val foodCategory: String,
    val name: String,
    val calories: Int,
    val date: String

)