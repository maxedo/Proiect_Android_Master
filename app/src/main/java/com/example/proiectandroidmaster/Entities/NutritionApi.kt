package com.example.proiectandroidmaster.Entities

import com.example.proiectandroidmaster.DAO.NutritionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NutritionApi {
    @GET("/v1/nutrition")
    fun getNutritionInfo(
        @Query("query") query: String,
        @Header("X-Api-Key") apiKey: String
    ): Call<NutritionResponse>
}