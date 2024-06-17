package com.example.proiectandroidmaster.Entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,  // Ensure email is of type String
    val foodCategory: String,
    val name: String,
    val calories: Int,
    val protein: Int
)
