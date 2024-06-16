package com.example.proiectandroidmaster.Entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: Int,
    val foodCategory: String,
    val name: String,
    val calories: Int,
    val protein: Int
)