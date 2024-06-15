package com.example.proiectandroidmaster.Entities
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "water_table")
data class Water(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: Int,
    val value:Int,
    val date:String
)