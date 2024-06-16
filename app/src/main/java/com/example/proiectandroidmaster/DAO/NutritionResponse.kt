package com.example.proiectandroidmaster.DAO

data class NutritionResponse(val items: List<NutritionItem>)

data class NutritionItem(
    val serving_size_g: Double,
    val name: String,
    val calories: Double,
    val protein_g: Double,
)
